package com.apotek.service;

import com.apotek.config.DatabaseConnection;
import com.apotek.model.Obat;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author alfrandiano
 */
public class PurchaseOrderService {
    private Connection conn;
    private ObatService obatService;
    
    public PurchaseOrderService() {
        this.conn = DatabaseConnection.getInstance().getConnection();
        this.obatService = new ObatService();
    }
    
    // Generate Nomor PO Otomatis
    public String generateNoPO() {
        String prefix = "PO";
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String sql = "SELECT COUNT(*) as total FROM purchase_order WHERE DATE(created_at) = CURDATE()";
        
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                int urutan = rs.getInt("total") + 1;
                return String.format("%s%s%04d", prefix, date, urutan);
            }
        } catch (SQLException e) {
            // Tabel belum ada, return default
            return prefix + date + "0001";
        }
        return prefix + date + "0001";
    }
    
    // Buat Purchase Order dari Stok Rendah
    public boolean buatPurchaseOrder() {
        List<Obat> stokRendah = obatService.getStokMenipis();
        
        if (stokRendah.isEmpty()) {
            return false; // Tidak ada stok yang perlu direstock
        }
        
        try {
            conn.setAutoCommit(false);
            
            // Buat tabel purchase_order jika belum ada
            createPOTableIfNotExists();
            
            String noPO = generateNoPO();
            String sqlPO = "INSERT INTO purchase_order (no_po, tgl_po, status, total_item) VALUES (?, NOW(), 'PENDING', ?)";
            
            int idPO;
            try (PreparedStatement ps = conn.prepareStatement(sqlPO, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, noPO);
                ps.setInt(2, stokRendah.size());
                ps.executeUpdate();
                
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    idPO = rs.getInt(1);
                } else {
                    throw new SQLException("Gagal membuat PO");
                }
            }
            
            // Insert detail PO
            String sqlDetail = "INSERT INTO po_detail (id_po, kode_obat, nama_obat, qty_order, harga_satuan) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sqlDetail)) {
                for (Obat obat : stokRendah) {
                    int qtyOrder = 50 - obat.getStok(); // Target stok 50
                    ps.setInt(1, idPO);
                    ps.setString(2, obat.getKodeObat());
                    ps.setString(3, obat.getNamaObat());
                    ps.setInt(4, qtyOrder);
                    ps.setDouble(5, obat.getHargaSatuan());
                    ps.addBatch();
                }
                ps.executeBatch();
            }
            
            conn.commit();
            System.out.println("✓ Purchase Order berhasil dibuat: " + noPO);
            return true;
            
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                System.err.println("Rollback Error: " + ex.getMessage());
            }
            System.err.println("Error buatPurchaseOrder: " + e.getMessage());
            return false;
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println("Error setAutoCommit: " + e.getMessage());
            }
        }
    }
    
    // Buat tabel PO jika belum ada
    private void createPOTableIfNotExists() throws SQLException {
        String createPO = "CREATE TABLE IF NOT EXISTS purchase_order (" +
                          "id_po INT PRIMARY KEY AUTO_INCREMENT, " +
                          "no_po VARCHAR(50) UNIQUE NOT NULL, " +
                          "tgl_po DATETIME NOT NULL, " +
                          "status ENUM('PENDING','APPROVED','COMPLETED') DEFAULT 'PENDING', " +
                          "total_item INT, " +
                          "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
        
        String createDetail = "CREATE TABLE IF NOT EXISTS po_detail (" +
                              "id_detail INT PRIMARY KEY AUTO_INCREMENT, " +
                              "id_po INT NOT NULL, " +
                              "kode_obat VARCHAR(20) NOT NULL, " +
                              "nama_obat VARCHAR(150) NOT NULL, " +
                              "qty_order INT NOT NULL, " +
                              "harga_satuan DECIMAL(10,2), " +
                              "FOREIGN KEY (id_po) REFERENCES purchase_order(id_po) ON DELETE CASCADE)";
        
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(createPO);
            stmt.execute(createDetail);
        }
    }
    
    // Get Daftar PO (untuk tracking)
    public List<String> getDaftarPO() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT no_po, tgl_po, status, total_item FROM purchase_order ORDER BY tgl_po DESC LIMIT 10";
        
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                String info = String.format("%s | %s | %s | %d items",
                    rs.getString("no_po"),
                    rs.getTimestamp("tgl_po").toLocalDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                    rs.getString("status"),
                    rs.getInt("total_item")
                );
                list.add(info);
            }
        } catch (SQLException e) {
            System.err.println("Error getDaftarPO: " + e.getMessage());
        }
        return list;
    }
}
