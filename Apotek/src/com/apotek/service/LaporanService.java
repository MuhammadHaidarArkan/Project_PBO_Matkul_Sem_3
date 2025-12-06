/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apotek.service;

import com.apotek.config.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 *
 * @author alfrandiano
 */
public class LaporanService {
    private Connection conn;
    
    public LaporanService() {
        this.conn = DatabaseConnection.getInstance().getConnection();
    }
    
    // Produk Terlaris (untuk tabel kiri di Laporan)
    public List<Map<String, Object>> getProdukTerlaris(int limit) {
        List<Map<String, Object>> list = new ArrayList<>();
        String sql = "SELECT o.nama_obat, SUM(dt.jumlah) as total_qty, " +
                     "SUM(dt.subtotal) as total_pendapatan " +
                     "FROM detail_transaksi dt " +
                     "JOIN obat o ON dt.kode_obat = o.kode_obat " +
                     "GROUP BY o.nama_obat " +
                     "ORDER BY total_qty DESC LIMIT ?";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            ResultSet rs = ps.executeQuery();
            
            int rank = 1;
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("rank", rank++);
                row.put("nama_produk", rs.getString("nama_obat"));
                row.put("qty", rs.getInt("total_qty"));
                row.put("pendapatan", rs.getDouble("total_pendapatan"));
                list.add(row);
            }
        } catch (SQLException e) {
            System.err.println("Error getProdukTerlaris: " + e.getMessage());
        }
        return list;
    }
    
    // Stok Habis / Peringatan Stok (untuk tabel kanan di Laporan)
    public List<Map<String, Object>> getPeringatanStokHabis() {
        List<Map<String, Object>> list = new ArrayList<>();
        String sql = "SELECT kode_obat, nama_obat, stok FROM obat " +
                     "WHERE stok < 20 ORDER BY stok ASC";
        
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("kode", rs.getString("kode_obat"));
                row.put("nama_produk", rs.getString("nama_obat"));
                row.put("stok", rs.getInt("stok"));
                row.put("min_stok", 20); // threshold minimum
                list.add(row);
            }
        } catch (SQLException e) {
            System.err.println("Error getPeringatanStokHabis: " + e.getMessage());
        }
        return list;
    }
    
    // Statistik Penjualan per Kategori (untuk progress bar Mie, Sanmol, Panadol)
    public Map<String, Integer> getPenjualanPerKategori() {
        Map<String, Integer> map = new HashMap<>();
        String sql = "SELECT o.kategori, SUM(dt.jumlah) as total " +
                     "FROM detail_transaksi dt " +
                     "JOIN obat o ON dt.kode_obat = o.kode_obat " +
                     "GROUP BY o.kategori";
        
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                map.put(rs.getString("kategori"), rs.getInt("total"));
            }
        } catch (SQLException e) {
            System.err.println("Error getPenjualanPerKategori: " + e.getMessage());
        }
        return map;
    }
    
    // Atau jika Mie, Sanmol, Panadol adalah nama produk spesifik:
    public Map<String, Integer> getPenjualanProdukSpesifik() {
        Map<String, Integer> map = new HashMap<>();
        String sql = "SELECT o.nama_obat, SUM(dt.jumlah) as total " +
                     "FROM detail_transaksi dt " +
                     "JOIN obat o ON dt.kode_obat = o.kode_obat " +
                     "WHERE o.nama_obat IN ('Mie', 'Sanmol', 'Panadol') " +
                     "GROUP BY o.nama_obat";
        
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                map.put(rs.getString("nama_obat"), rs.getInt("total"));
            }
        } catch (SQLException e) {
            System.err.println("Error getPenjualanProdukSpesifik: " + e.getMessage());
        }
        return map;
    }
}