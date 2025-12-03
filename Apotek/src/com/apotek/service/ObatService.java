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
import com.apotek.model.Obat;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class ObatService {
    private Connection conn;
    
    public ObatService() {
        this.conn = DatabaseConnection.getInstance().getConnection();
    }
    
    // Alert Obat Expired (< 30 hari)
    public List<Obat> getExpiredAlert() {
        List<Obat> list = new ArrayList<>();
        LocalDate batasWaktu = LocalDate.now().plusDays(30);
        
        String sql = "SELECT * FROM obat WHERE tgl_expired <= ? ORDER BY tgl_expired ASC";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(batasWaktu));
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Obat obat = mapResultSetToObat(rs);
                list.add(obat);
            }
        } catch (SQLException e) {
            System.err.println("Error getExpiredAlert: " + e.getMessage());
        }
        return list;
    }
    
    // Cari Obat (untuk Kasir)
    public List<Obat> cariObat(String keyword) {
        List<Obat> list = new ArrayList<>();
        String sql = "SELECT * FROM obat WHERE kode_obat LIKE ? OR nama_obat LIKE ? ORDER BY nama_obat";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            String pattern = "%" + keyword + "%";
            ps.setString(1, pattern);
            ps.setString(2, pattern);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapResultSetToObat(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error cariObat: " + e.getMessage());
        }
        return list;
    }
    
    // Get All Obat
    public List<Obat> getAllObat() {
        List<Obat> list = new ArrayList<>();
        String sql = "SELECT * FROM obat ORDER BY nama_obat";
        
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                list.add(mapResultSetToObat(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getAllObat: " + e.getMessage());
        }
        return list;
    }
    
    // Kurangi Stok (dipanggil saat transaksi)
    public boolean kurangiStok(String kodeObat, int jumlah) {
        String sql = "UPDATE obat SET stok = stok - ? WHERE kode_obat = ? AND stok >= ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, jumlah);
            ps.setString(2, kodeObat);
            ps.setInt(3, jumlah);
            
            int affected = ps.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            System.err.println("Error kurangiStok: " + e.getMessage());
            return false;
        }
    }
    
    // Tambah Obat
    public boolean tambahObat(Obat obat) {
        String sql = "INSERT INTO obat (kode_obat, nama_obat, kategori, stok, harga_satuan, tgl_expired, supplier) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, obat.getKodeObat());
            ps.setString(2, obat.getNamaObat());
            ps.setString(3, obat.getKategori());
            ps.setInt(4, obat.getStok());
            ps.setDouble(5, obat.getHargaSatuan());
            ps.setDate(6, Date.valueOf(obat.getTglExpired()));
            ps.setString(7, obat.getSupplier());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error tambahObat: " + e.getMessage());
            return false;
        }
    }
    
    // Update Obat
    public boolean updateObat(Obat obat) {
        String sql = "UPDATE obat SET nama_obat=?, kategori=?, stok=?, harga_satuan=?, " +
                     "tgl_expired=?, supplier=? WHERE kode_obat=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, obat.getNamaObat());
            ps.setString(2, obat.getKategori());
            ps.setInt(3, obat.getStok());
            ps.setDouble(4, obat.getHargaSatuan());
            ps.setDate(5, Date.valueOf(obat.getTglExpired()));
            ps.setString(6, obat.getSupplier());
            ps.setString(7, obat.getKodeObat());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updateObat: " + e.getMessage());
            return false;
        }
    }
    
    // Delete Obat
    public boolean deleteObat(String kodeObat) {
        String sql = "DELETE FROM obat WHERE kode_obat = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, kodeObat);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleteObat: " + e.getMessage());
            return false;
        }
    }
    
    // Get Stok Menipis (< 20)
    public List<Obat> getStokMenipis() {
        List<Obat> list = new ArrayList<>();
        String sql = "SELECT * FROM obat WHERE stok < 20 ORDER BY stok ASC";
        
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                list.add(mapResultSetToObat(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getStokMenipis: " + e.getMessage());
        }
        return list;
    }
    
    // Helper Method
    private Obat mapResultSetToObat(ResultSet rs) throws SQLException {
        Obat obat = new Obat();
        obat.setKodeObat(rs.getString("kode_obat"));
        obat.setNamaObat(rs.getString("nama_obat"));
        obat.setKategori(rs.getString("kategori"));
        obat.setStok(rs.getInt("stok"));
        obat.setHargaSatuan(rs.getDouble("harga_satuan"));
        obat.setTglExpired(rs.getDate("tgl_expired").toLocalDate());
        obat.setSupplier(rs.getString("supplier"));
        return obat;
    }
}
