/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author lenov
 */
package com.apotek.service;

import com.apotek.config.DatabaseConnection;
import com.apotek.model.Transaksi;
import com.apotek.model.DetailTransaksi;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransaksiService {
    private Connection conn;
    private ObatService obatService;
    
    public TransaksiService() {
        this.conn = DatabaseConnection.getInstance().getConnection();
        this.obatService = new ObatService();
    }
    
    // Generate Nomor Faktur Otomatis
    public String generateNoFaktur() {
        String prefix = "TRX";
        String date = LocalDate.now().toString().replace("-", "");
        String sql = "SELECT COUNT(*) as total FROM transaksi WHERE DATE(tgl_transaksi) = CURDATE()";
        
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                int urutan = rs.getInt("total") + 1;
                return String.format("%s%s%04d", prefix, date, urutan);
            }
        } catch (SQLException e) {
            System.err.println("Error generateNoFaktur: " + e.getMessage());
        }
        return prefix + date + "0001";
    }
    
    // Simpan Transaksi (Insert ke 2 tabel + Update Stok)
    public boolean simpanTransaksi(Transaksi transaksi) {
        try {
            conn.setAutoCommit(false); // Mulai Transaction
            
            // 1. Insert ke tabel transaksi
            String sqlTransaksi = "INSERT INTO transaksi (no_faktur, tgl_transaksi, id_user, " +
                                  "total_bayar, uang_bayar, uang_kembali) VALUES (?, ?, ?, ?, ?, ?)";
            
            int idTransaksi;
            try (PreparedStatement ps = conn.prepareStatement(sqlTransaksi, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, transaksi.getNoFaktur());
                ps.setTimestamp(2, Timestamp.valueOf(transaksi.getTglTransaksi()));
                ps.setInt(3, transaksi.getIdUser());
                ps.setDouble(4, transaksi.getTotalBayar());
                ps.setDouble(5, transaksi.getUangBayar());
                ps.setDouble(6, transaksi.getUangKembali());
                
                ps.executeUpdate();
                
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    idTransaksi = rs.getInt(1);
                } else {
                    throw new SQLException("Gagal mendapatkan ID Transaksi");
                }
            }
            
            // 2. Insert ke tabel detail_transaksi + Update Stok
            String sqlDetail = "INSERT INTO detail_transaksi (id_transaksi, kode_obat, nama_obat, " +
                               "jumlah, harga_satuan, subtotal) VALUES (?, ?, ?, ?, ?, ?)";
            
            try (PreparedStatement ps = conn.prepareStatement(sqlDetail)) {
                for (DetailTransaksi detail : transaksi.getDetailList()) {
                    ps.setInt(1, idTransaksi);
                    ps.setString(2, detail.getKodeObat());
                    ps.setString(3, detail.getNamaObat());
                    ps.setInt(4, detail.getJumlah());
                    ps.setDouble(5, detail.getHargaSatuan());
                    ps.setDouble(6, detail.getSubtotal());
                    ps.addBatch();
                    
                    // Update stok obat
                    if (!obatService.kurangiStok(detail.getKodeObat(), detail.getJumlah())) {
                        throw new SQLException("Gagal mengurangi stok untuk " + detail.getNamaObat());
                    }
                }
                ps.executeBatch();
            }
            
            conn.commit(); // Commit Transaction
            return true;
            
        } catch (SQLException e) {
            try {
                conn.rollback(); // Rollback jika ada error
            } catch (SQLException ex) {
                System.err.println("Rollback Error: " + ex.getMessage());
            }
            System.err.println("Error simpanTransaksi: " + e.getMessage());
            return false;
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println("Error setAutoCommit: " + e.getMessage());
            }
        }
    }
    
    // Laporan Penjualan Harian
    public List<Transaksi> getLaporanHarian(LocalDate tanggal) {
        List<Transaksi> list = new ArrayList<>();
        String sql = "SELECT t.*, u.nama_lengkap FROM transaksi t " +
                     "JOIN users u ON t.id_user = u.id_user " +
                     "WHERE DATE(t.tgl_transaksi) = ? ORDER BY t.tgl_transaksi DESC";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(tanggal));
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Transaksi t = new Transaksi();
                t.setIdTransaksi(rs.getInt("id_transaksi"));
                t.setNoFaktur(rs.getString("no_faktur"));
                t.setTglTransaksi(rs.getTimestamp("tgl_transaksi").toLocalDateTime());
                t.setTotalBayar(rs.getDouble("total_bayar"));
                t.setUangBayar(rs.getDouble("uang_bayar"));
                t.setUangKembali(rs.getDouble("uang_kembali"));
                list.add(t);
            }
        } catch (SQLException e) {
            System.err.println("Error getLaporanHarian: " + e.getMessage());
        }
        return list;
    }
    
    // Get Detail Transaksi by ID
    public List<DetailTransaksi> getDetailTransaksi(int idTransaksi) {
        List<DetailTransaksi> list = new ArrayList<>();
        String sql = "SELECT * FROM detail_transaksi WHERE id_transaksi = ?";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idTransaksi);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                DetailTransaksi dt = new DetailTransaksi();
                dt.setIdDetail(rs.getInt("id_detail"));
                dt.setKodeObat(rs.getString("kode_obat"));
                dt.setNamaObat(rs.getString("nama_obat"));
                dt.setJumlah(rs.getInt("jumlah"));
                dt.setHargaSatuan(rs.getDouble("harga_satuan"));
                dt.setSubtotal(rs.getDouble("subtotal"));
                list.add(dt);
            }
        } catch (SQLException e) {
            System.err.println("Error getDetailTransaksi: " + e.getMessage());
        }
        return list;
    }
}