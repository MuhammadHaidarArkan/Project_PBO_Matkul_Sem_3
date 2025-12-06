/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apotek.service;
import com.apotek.config.DatabaseConnection;
import java.sql.*;
import java.time.LocalDate;
/**
 *
 * @author alfrandiano
 */
public class DashboardService {
    private Connection conn;
    
    public DashboardService() {
        this.conn = DatabaseConnection.getInstance().getConnection();
    }
    
    // Total Penjualan Hari Ini
    public double getTotalPenjualanHariIni() {
        String sql = "SELECT COALESCE(SUM(total_bayar), 0) as total " +
                     "FROM transaksi WHERE DATE(tgl_transaksi) = CURDATE()";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getDouble("total");
            }
        } catch (SQLException e) {
            System.err.println("Error getTotalPenjualanHariIni: " + e.getMessage());
        }
        return 0;
    }
    
    // Total Transaksi Hari Ini
    public int getTotalTransaksiHariIni() {
        String sql = "SELECT COUNT(*) as total FROM transaksi " +
                     "WHERE DATE(tgl_transaksi) = CURDATE()";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            System.err.println("Error getTotalTransaksiHariIni: " + e.getMessage());
        }
        return 0;
    }
    
    // Total Produk
    public int getTotalProduk() {
        String sql = "SELECT COUNT(*) as total FROM obat";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            System.err.println("Error getTotalProduk: " + e.getMessage());
        }
        return 0;
    }
    
    // Barang Stok Rendah (< 20)
    public int getBarangStokRendah() {
        String sql = "SELECT COUNT(*) as total FROM obat WHERE stok < 20";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            System.err.println("Error getBarangStokRendah: " + e.getMessage());
        }
        return 0;
    }
}
