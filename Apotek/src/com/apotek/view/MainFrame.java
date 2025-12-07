/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.apotek.view;

import com.apotek.model.*;
import com.apotek.service.*;
import com.apotek.util.SessionManager;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


/**
 *
 * @author Asus
 */
public class MainFrame extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MainFrame.class.getName());
    private DashboardService dashService;
    private ObatService obatService;
    private TransaksiService transaksiService;
    private LaporanService laporanService;
    private PurchaseOrderService poService;
    
    private DefaultTableModel modelKeranjang;
    private double totalBelanja = 0;
    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();
        initializeServices();
        setupInitialData();
        setupEventListeners();
    }
    
    private void initializeServices() {
        dashService = new DashboardService();
        obatService = new ObatService();
        transaksiService = new TransaksiService();
        laporanService = new LaporanService();
        poService = new PurchaseOrderService();
    }
    
    private void setupInitialData() {
        // Set user info
        User currentUser = SessionManager.getCurrentUser();
        this.setTitle("Apotek Lite - " + currentUser.getNamaLengkap());
        
        // Load semua data
        loadDashboardData();
        setupKasirTable();
        loadStokData();
        loadLaporanData();
    }
    
    // ==================== TAB DASHBOARD ====================
    
    private void loadDashboardData() {
        // Total Pendapatan Kotor (bukan total bayar)
        lblTotalPenjualanHariIni.setText(String.format("Rp. %,.0f", 
            dashService.getTotalPenjualanHariIni()));
        
        lblTotalTransaksi.setText(String.valueOf(
            dashService.getTotalTransaksiHariIni()));
        
        lblBarangStokRendah.setText(String.valueOf(
            dashService.getBarangStokRendah()));
        
        lblTotalProduk.setText(String.valueOf(
            dashService.getTotalProduk()));
    }
    
    // ==================== TAB KASIR ====================
    
    private void setupKasirTable() {
        // Setup model tabel keranjang dengan kolom yang BENAR
        modelKeranjang = (DefaultTableModel) tblKeranjang.getModel();
        
        // PENTING: Set kolom tabel sesuai struktur: Nama Barang, Harga, Qty, Subtotal
        modelKeranjang.setColumnCount(4);
        modelKeranjang.setColumnIdentifiers(new Object[]{"Nama Barang", "Harga", "Qty", "Subtotal"});
        modelKeranjang.setRowCount(0);
        
        // Reset total
        txtTotalHarga.setText("Rp 0");
        txtUangBayar.setText("");
        txtKembalian.setText("Rp 0");
    }
    
    // Method untuk menambah obat ke keranjang (dipanggil dari double-click tabel atau dialog)
    public void tambahKeKeranjang(String kodeObat, String namaObat, int jumlah, double harga) {
        // Cek apakah obat sudah ada di keranjang (berdasarkan NAMA, bukan kode)
        boolean found = false;
        for (int i = 0; i < modelKeranjang.getRowCount(); i++) {
            // Kolom 0 = Nama Barang
            if (modelKeranjang.getValueAt(i, 0).toString().equals(namaObat)) {
                // Update jumlah jika sudah ada
                int jmlLama = Integer.parseInt(modelKeranjang.getValueAt(i, 2).toString());
                int jmlBaru = jmlLama + jumlah;
                double subtotal = jmlBaru * harga;
                
                modelKeranjang.setValueAt(jmlBaru, i, 2);        // Kolom 2 = Qty
                modelKeranjang.setValueAt(subtotal, i, 3);       // Kolom 3 = Subtotal
                found = true;
                break;
            }
        }
        
        // Tambah baris baru jika belum ada
        // FORMAT: Nama Barang | Harga | Qty | Subtotal
        if (!found) {
            double subtotal = jumlah * harga;
            modelKeranjang.addRow(new Object[]{
                namaObat,   // Kolom 0
                harga,      // Kolom 1
                jumlah,     // Kolom 2
                subtotal    // Kolom 3
            });
        }
        
        // Update total
        hitungTotal();
    }
    
    private void hitungTotal() {
        totalBelanja = 0;
        for (int i = 0; i < modelKeranjang.getRowCount(); i++) {
            // Kolom 3 = Subtotal
            totalBelanja += Double.parseDouble(modelKeranjang.getValueAt(i, 3).toString());
        }
        txtTotalHarga.setText(String.format("Rp %,.0f", totalBelanja));
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblTotalPenjualanHariIni = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblTotalTransaksi = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lblTotalProduk = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblBarangStokRendah = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblKeranjang = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtTotalHarga = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtUangBayar = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtKembalian = new javax.swing.JTextField();
        btnBayar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtCariObat = new javax.swing.JTextField();
        btnCariObat = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblStok = new javax.swing.JTable();
        btnTambah = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblLaporan = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblStokHabis = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        btnProdukPerluDirestock = new javax.swing.JLabel();
        btnBuatPurchaseOrder = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Dashboard");

        jLabel2.setFont(new java.awt.Font("Bakso Sapi", 0, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Total Penjualan Hari Ini");

        lblTotalPenjualanHariIni.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblTotalPenjualanHariIni.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotalPenjualanHariIni.setText("Rp. 2.500.000");

        jLabel4.setFont(new java.awt.Font("Bakso Sapi", 0, 18)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Total Transaksi");

        lblTotalTransaksi.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblTotalTransaksi.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotalTransaksi.setText("47");

        jLabel6.setFont(new java.awt.Font("Bakso Sapi", 0, 18)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Total Produk");

        lblTotalProduk.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblTotalProduk.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotalProduk.setText("156");

        jLabel8.setFont(new java.awt.Font("Bakso Sapi", 0, 18)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Barang Stok Rendah");

        lblBarangStokRendah.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblBarangStokRendah.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBarangStokRendah.setText("8");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTotalTransaksi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTotalProduk, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblTotalPenjualanHariIni, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 739, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblBarangStokRendah, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTotalPenjualanHariIni, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTotalTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblBarangStokRendah, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTotalProduk, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(126, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Dashboard", jPanel1);

        tblKeranjang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Nama Barang", "Harga", "Qty", "Subtotal"
            }
        ));
        jScrollPane1.setViewportView(tblKeranjang);

        jLabel10.setText("Pembayaran");

        jLabel11.setText("Total Harga");

        jLabel12.setText("Uang Bayar");

        jLabel13.setText("Kembalian");

        btnBayar.setText("Bayar");
        btnBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBayarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtTotalHarga)
            .addComponent(txtUangBayar)
            .addComponent(txtKembalian)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(btnBayar, javax.swing.GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTotalHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtUangBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtKembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 180, Short.MAX_VALUE)
                .addComponent(btnBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel3.setText("Cari Obat :");

        btnCariObat.setText("Cari & Tambah");
        btnCariObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariObatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCariObat, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCariObat, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(33, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCariObat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCariObat)))))
        );

        jTabbedPane1.addTab("Kasir", jPanel2);

        tblStok.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Nama Produk", "Harga", "Stok"
            }
        ));
        jScrollPane2.setViewportView(tblStok);

        btnTambah.setText("Tambah");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 745, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnTambah)
                .addGap(200, 200, 200)
                .addComponent(btnEdit)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnHapus))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambah)
                    .addComponent(btnEdit)
                    .addComponent(btnHapus))
                .addGap(0, 67, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Stok", jPanel3);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel14.setText("Produk Terlaris");

        tblLaporan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Rank", "Nama Produk", "Qty", "Pendapatan"
            }
        ));
        jScrollPane3.setViewportView(tblLaporan);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 2, Short.MAX_VALUE))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addContainerGap(279, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3)
                .addContainerGap())
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel18.setText("Peringatan Stok Habis");

        tblStokHabis.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Kode", "Nama Produk", "Stok", "Min Stok"
            }
        ));
        jScrollPane4.setViewportView(tblStokHabis);

        jPanel8.setBackground(new java.awt.Color(255, 102, 102));

        btnProdukPerluDirestock.setFont(new java.awt.Font("Bakso Sapi", 0, 18)); // NOI18N
        btnProdukPerluDirestock.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnProdukPerluDirestock.setText("Produk Perlu Direstock");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnProdukPerluDirestock, javax.swing.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnProdukPerluDirestock, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnBuatPurchaseOrder.setBackground(new java.awt.Color(153, 204, 255));
        btnBuatPurchaseOrder.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        btnBuatPurchaseOrder.setText("Buat Purchase Order");
        btnBuatPurchaseOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuatPurchaseOrderActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnBuatPurchaseOrder, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBuatPurchaseOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(48, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Laporan", jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBayarActionPerformed
        // Validasi keranjang tidak kosong
        if (modelKeranjang.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, 
                "Keranjang masih kosong!", 
                "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Validasi uang bayar
        String bayarStr = txtUangBayar.getText().trim();
        if (bayarStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Masukkan jumlah uang bayar!", 
                "Error", JOptionPane.WARNING_MESSAGE);
            txtUangBayar.requestFocus();
            return;
        }
        
        try {
            double uangBayar = Double.parseDouble(bayarStr.replace(".", "").replace(",", ""));
            
            if (uangBayar < totalBelanja) {
                JOptionPane.showMessageDialog(this, 
                    "Uang bayar kurang!\nKurang: Rp " + String.format("%,.0f", totalBelanja - uangBayar), 
                    "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            double kembalian = uangBayar - totalBelanja;
            txtKembalian.setText(String.format("Rp %,.0f", kembalian));
            
            // Konfirmasi
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Total: Rp " + String.format("%,.0f", totalBelanja) + "\n" +
                "Bayar: Rp " + String.format("%,.0f", uangBayar) + "\n" +
                "Kembali: Rp " + String.format("%,.0f", kembalian) + "\n\n" +
                "Proses transaksi?", 
                "Konfirmasi Pembayaran", 
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
            
            if (confirm != JOptionPane.YES_OPTION) return;
            
            // Buat objek Transaksi
            Transaksi transaksi = new Transaksi();
            transaksi.setNoFaktur(transaksiService.generateNoFaktur());
            transaksi.setTglTransaksi(LocalDateTime.now());
            transaksi.setIdUser(SessionManager.getCurrentUser().getIdUser());
            transaksi.setTotalBayar(totalBelanja);
            transaksi.setUangBayar(uangBayar);
            transaksi.setUangKembali(kembalian);
            
            // Ambil detail dari tabel keranjang
            // FORMAT TABEL: Nama Barang | Harga | Qty | Subtotal
            for (int i = 0; i < modelKeranjang.getRowCount(); i++) {
                String namaObat = modelKeranjang.getValueAt(i, 0).toString();  // Kolom 0
                double harga = Double.parseDouble(modelKeranjang.getValueAt(i, 1).toString()); // Kolom 1
                int jumlah = Integer.parseInt(modelKeranjang.getValueAt(i, 2).toString()); // Kolom 2
                
                // Cari kode obat dari nama
                List<Obat> listObat = obatService.cariObat(namaObat);
                if (!listObat.isEmpty()) {
                    String kodeObat = listObat.get(0).getKodeObat();
                    DetailTransaksi detail = new DetailTransaksi(kodeObat, namaObat, jumlah, harga);
                    transaksi.getDetailList().add(detail);
                }
            }
            
            // Simpan transaksi
            if (transaksiService.simpanTransaksi(transaksi)) {
                JOptionPane.showMessageDialog(this, 
                    "✓ Transaksi Berhasil!\n\n" +
                    "No Faktur: " + transaksi.getNoFaktur() + "\n" +
                    "Total: Rp " + String.format("%,.0f", totalBelanja) + "\n" +
                    "Kembalian: Rp " + String.format("%,.0f", kembalian), 
                    "Sukses", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                // PENTING: Refresh SEMUA data setelah transaksi berhasil
                resetKasir();
                refreshAllData();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "✗ Transaksi Gagal!\nPeriksa stok obat.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "Format angka tidak valid!", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnBayarActionPerformed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        // TODO add your handling code here:
        JTextField txtKode = new JTextField();
        JTextField txtNama = new JTextField();
        JTextField txtKategori = new JTextField();
        JTextField txtStok = new JTextField();
        JTextField txtHarga = new JTextField();
        JTextField txtExpired = new JTextField("2025-12-31");
        JTextField txtSupplier = new JTextField();
        
        Object[] message = {
            "Kode Obat:", txtKode,
            "Nama Obat:", txtNama,
            "Kategori:", txtKategori,
            "Stok Awal:", txtStok,
            "Harga Satuan:", txtHarga,
            "Tgl Expired (YYYY-MM-DD):", txtExpired,
            "Supplier:", txtSupplier
        };
        
        int option = JOptionPane.showConfirmDialog(this, message, 
            "Tambah Obat Baru", JOptionPane.OK_CANCEL_OPTION);
        
        if (option == JOptionPane.OK_OPTION) {
            try {
                Obat obat = new Obat();
                obat.setKodeObat(txtKode.getText().trim());
                obat.setNamaObat(txtNama.getText().trim());
                obat.setKategori(txtKategori.getText().trim());
                obat.setStok(Integer.parseInt(txtStok.getText().trim()));
                obat.setHargaSatuan(Double.parseDouble(txtHarga.getText().trim()));
                obat.setTglExpired(java.time.LocalDate.parse(txtExpired.getText().trim()));
                obat.setSupplier(txtSupplier.getText().trim());
                
                if (obatService.tambahObat(obat)) {
                    JOptionPane.showMessageDialog(this, "✓ Obat berhasil ditambahkan!");
                    loadStokData();
                    loadDashboardData();
                } else {
                    JOptionPane.showMessageDialog(this, "✗ Gagal menambahkan obat!");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
                int selectedRow = tblStok.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih obat yang akan diedit!");
            return;
        }
        
        String kodeObat = tblStok.getValueAt(selectedRow, 0).toString();
        
        // Get data obat dari database
        List<Obat> list = obatService.cariObat(kodeObat);
        if (list.isEmpty()) return;
        
        Obat obat = list.get(0);
        
        // Dialog edit (sama seperti tambah tapi pre-filled)
        JTextField txtNama = new JTextField(obat.getNamaObat());
        JTextField txtKategori = new JTextField(obat.getKategori());
        JTextField txtStok = new JTextField(String.valueOf(obat.getStok()));
        JTextField txtHarga = new JTextField(String.valueOf(obat.getHargaSatuan()));
        JTextField txtExpired = new JTextField(obat.getTglExpired().toString());
        JTextField txtSupplier = new JTextField(obat.getSupplier());
        
        Object[] message = {
            "Kode: " + kodeObat + " (tidak bisa diubah)",
            "Nama Obat:", txtNama,
            "Kategori:", txtKategori,
            "Stok:", txtStok,
            "Harga Satuan:", txtHarga,
            "Tgl Expired:", txtExpired,
            "Supplier:", txtSupplier
        };
        
        int option = JOptionPane.showConfirmDialog(this, message, 
            "Edit Obat", JOptionPane.OK_CANCEL_OPTION);
        
        if (option == JOptionPane.OK_OPTION) {
            try {
                obat.setNamaObat(txtNama.getText().trim());
                obat.setKategori(txtKategori.getText().trim());
                obat.setStok(Integer.parseInt(txtStok.getText().trim()));
                obat.setHargaSatuan(Double.parseDouble(txtHarga.getText().trim()));
                obat.setTglExpired(java.time.LocalDate.parse(txtExpired.getText().trim()));
                obat.setSupplier(txtSupplier.getText().trim());
                
                if (obatService.updateObat(obat)) {
                    JOptionPane.showMessageDialog(this, "✓ Obat berhasil diupdate!");
                    loadStokData();
                } else {
                    JOptionPane.showMessageDialog(this, "✗ Gagal mengupdate obat!");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        // TODO add your handling code here:
                int selectedRow = tblStok.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih obat yang akan dihapus!");
            return;
        }
        
        String kodeObat = tblStok.getValueAt(selectedRow, 0).toString();
        String namaObat = tblStok.getValueAt(selectedRow, 1).toString();
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Hapus obat: " + namaObat + "?", 
            "Konfirmasi Hapus", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            if (obatService.deleteObat(kodeObat)) {
                JOptionPane.showMessageDialog(this, "✓ Obat berhasil dihapus!");
                loadStokData();
                loadDashboardData();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "✗ Gagal menghapus obat!\nMungkin masih ada transaksi terkait.");
            }
        }
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnBuatPurchaseOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuatPurchaseOrderActionPerformed
                int confirm = JOptionPane.showConfirmDialog(this, 
            "Buat Purchase Order untuk semua produk dengan stok rendah?", 
            "Konfirmasi PO", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            if (poService.buatPurchaseOrder()) {
                JOptionPane.showMessageDialog(this, 
                    "✓ Purchase Order berhasil dibuat!\n\n" +
                    "PO dapat dilihat di database atau diekspor ke Excel.", 
                    "Sukses", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                loadLaporanData(); // Refresh data
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Tidak ada produk yang perlu direstock.", 
                    "Info", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnBuatPurchaseOrderActionPerformed

    private void btnCariObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariObatActionPerformed
        // TODO add your handling code here:
            String keyword = txtCariObat.getText().trim();
    
        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Masukkan nama atau kode obat yang ingin dicari!", 
                "Info", 
                JOptionPane.INFORMATION_MESSAGE);
            txtCariObat.requestFocus();
            return;
        }

        // Cari obat di database
        List<Obat> listObat = obatService.cariObat(keyword);

        if (listObat.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Obat tidak ditemukan!\nCoba kata kunci lain.", 
                "Tidak Ditemukan", 
                JOptionPane.WARNING_MESSAGE);
            txtCariObat.selectAll();
            return;
        }

        // Jika hanya 1 hasil, langsung tampilkan dialog input jumlah
        if (listObat.size() == 1) {
            Obat obat = listObat.get(0);
            tampilkanDialogTambahObat(obat);
        } else {
            // Jika lebih dari 1 hasil, tampilkan dialog pilihan
            tampilkanDialogPilihanObat(listObat);
        }

        // Clear search box
        txtCariObat.setText("");
    }//GEN-LAST:event_btnCariObatActionPerformed
    
    private void tampilkanDialogTambahObat(Obat obat) {
    // Cek stok tersedia
    if (obat.getStok() <= 0) {
        JOptionPane.showMessageDialog(this, 
            "Maaf, stok " + obat.getNamaObat() + " habis!", 
            "Stok Habis", 
            JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    // Dialog input jumlah
    String info = String.format(
        "Obat: %s\n" +
        "Harga: Rp %,.0f\n" +
        "Stok Tersedia: %d\n\n" +
        "Masukkan jumlah:",
        obat.getNamaObat(),
        obat.getHargaSatuan(),
        obat.getStok()
    );
    
    String jumlahStr = JOptionPane.showInputDialog(this, info, "1");
    
    if (jumlahStr != null && !jumlahStr.isEmpty()) {
        try {
            int jumlah = Integer.parseInt(jumlahStr);
            
            // Validasi jumlah
            if (jumlah <= 0) {
                JOptionPane.showMessageDialog(this, 
                    "Jumlah harus lebih dari 0!", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (jumlah > obat.getStok()) {
                JOptionPane.showMessageDialog(this, 
                    "Jumlah melebihi stok tersedia!\nStok: " + obat.getStok(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Tambahkan ke keranjang
            tambahKeKeranjang(
                obat.getKodeObat(), 
                obat.getNamaObat(), 
                jumlah, 
                obat.getHargaSatuan()
            );
            
            JOptionPane.showMessageDialog(this, 
                "✓ " + obat.getNamaObat() + " berhasil ditambahkan ke keranjang!", 
                "Sukses", 
                JOptionPane.INFORMATION_MESSAGE);
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "Format jumlah tidak valid!", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}

    // Method untuk menampilkan dialog pilihan jika hasil pencarian > 1
    private void tampilkanDialogPilihanObat(List<Obat> listObat) {
        // Buat array untuk JOptionPane
        String[] options = new String[listObat.size()];

        for (int i = 0; i < listObat.size(); i++) {
            Obat obat = listObat.get(i);
            options[i] = String.format("%s - Rp %,.0f (Stok: %d)", 
                obat.getNamaObat(), 
                obat.getHargaSatuan(), 
                obat.getStok()
            );
        }

        // Tampilkan dialog pilihan
        String pilihan = (String) JOptionPane.showInputDialog(
            this,
            "Ditemukan " + listObat.size() + " obat. Pilih salah satu:",
            "Pilih Obat",
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]
        );

        if (pilihan != null) {
            // Cari index obat yang dipilih
            for (int i = 0; i < options.length; i++) {
                if (options[i].equals(pilihan)) {
                    tampilkanDialogTambahObat(listObat.get(i));
                    break;
                }
            }
        }
    }

// ====================================================================
// BONUS: Tambahkan KeyListener untuk Enter di txtCariObat
// Panggil method ini di setupEventListeners()
// ====================================================================

    private void setupCariObatEnterKey() {
        txtCariObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    btnCariObatActionPerformed(null);
                }
            }
        });
    }
    
    private void refreshAllData() {
        loadDashboardData();  // Update statistik dashboard
        loadStokData();       // Update tabel stok
        loadLaporanData();    // Update laporan
    }


    private void resetKasir() {
        modelKeranjang.setRowCount(0);
        txtUangBayar.setText("");
        txtTotalHarga.setText("Rp 0");
        txtKembalian.setText("Rp 0");
        totalBelanja = 0;
    }
    
    private void loadStokData() {
        List<Obat> listObat = obatService.getAllObat();
        
        DefaultTableModel model = (DefaultTableModel) tblStok.getModel();
        model.setRowCount(0);
        
        for (Obat obat : listObat) {
            model.addRow(new Object[]{
                obat.getKodeObat(),
                obat.getNamaObat(),
                String.format("Rp %,.0f", obat.getHargaSatuan()),
                obat.getStok()
            });
        }
    }
    
    private void loadLaporanData() {
        // Tabel Produk Terlaris
        List<Map<String, Object>> terlaris = laporanService.getProdukTerlaris(10);
        DefaultTableModel modelTerlaris = (DefaultTableModel) tblLaporan.getModel();
        modelTerlaris.setRowCount(0);
        
        for (Map<String, Object> row : terlaris) {
            modelTerlaris.addRow(new Object[]{
                row.get("rank"),
                row.get("nama_produk"),
                row.get("qty"),
                String.format("Rp %,.0f", row.get("pendapatan"))
            });
        }
        
        // Tabel Stok Habis
        List<Map<String, Object>> stokHabis = laporanService.getPeringatanStokHabis();
        DefaultTableModel modelStokHabis = (DefaultTableModel) tblStokHabis.getModel();
        modelStokHabis.setRowCount(0);
        
        for (Map<String, Object> row : stokHabis) {
            modelStokHabis.addRow(new Object[]{
                row.get("kode"),
                row.get("nama_produk"),
                row.get("stok"),
                row.get("min_stok")
            });
        }
        int jumlahStokRendah = stokHabis.size();
        if (jumlahStokRendah > 0) {
            btnProdukPerluDirestock.setText(jumlahStokRendah + " Produk Perlu Direstock");
        } else {
            btnProdukPerluDirestock.setText("Stok Aman");
        }
    
    }
    

    
     private void setupEventListeners() {
        // Double click di tabel stok untuk tambah ke keranjang kasir
        tblStok.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int row = tblStok.getSelectedRow();
                    if (row != -1) {
                        String kode = tblStok.getValueAt(row, 0).toString();
                        String nama = tblStok.getValueAt(row, 1).toString();
                        String hargaStr = tblStok.getValueAt(row, 2).toString()
                            .replace("Rp ", "").replace(".", "").replace(",", "");
                        double harga = Double.parseDouble(hargaStr);
                        
                        String jumlahStr = JOptionPane.showInputDialog(
                            MainFrame.this, 
                            "Masukkan jumlah untuk " + nama + ":", 
                            "1"
                        );
                        
                        if (jumlahStr != null && !jumlahStr.isEmpty()) {
                            int jumlah = Integer.parseInt(jumlahStr);
                            tambahKeKeranjang(kode, nama, jumlah, harga);
                        }
                    }
                }
            }
        });
        
        // Auto calculate kembalian saat input uang bayar
        txtUangBayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                try {
                    String bayarStr = txtUangBayar.getText().trim();
                    if (!bayarStr.isEmpty()) {
                        double bayar = Double.parseDouble(bayarStr.replace(".", "").replace(",", ""));
                        double kembalian = bayar - totalBelanja;
                        txtKembalian.setText(String.format("Rp %,.0f", kembalian));
                        
                        // Ubah warna jika kurang
                        if (kembalian < 0) {
                            txtKembalian.setForeground(Color.RED);
                        } else {
                            txtKembalian.setForeground(Color.GREEN);
                        }
                    }
                } catch (NumberFormatException e) {
                    txtKembalian.setText("Rp 0");
                }
            }
        });
        
        txtCariObat.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyPressed(java.awt.event.KeyEvent evt) {
            if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                btnCariObatActionPerformed(null);
            }
        }
        });
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new MainFrame().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBayar;
    private javax.swing.JButton btnBuatPurchaseOrder;
    private javax.swing.JButton btnCariObat;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnHapus;
    private javax.swing.JLabel btnProdukPerluDirestock;
    private javax.swing.JButton btnTambah;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblBarangStokRendah;
    private javax.swing.JLabel lblTotalPenjualanHariIni;
    private javax.swing.JLabel lblTotalProduk;
    private javax.swing.JLabel lblTotalTransaksi;
    private javax.swing.JTable tblKeranjang;
    private javax.swing.JTable tblLaporan;
    private javax.swing.JTable tblStok;
    private javax.swing.JTable tblStokHabis;
    private javax.swing.JTextField txtCariObat;
    private javax.swing.JTextField txtKembalian;
    private javax.swing.JTextField txtTotalHarga;
    private javax.swing.JTextField txtUangBayar;
    // End of variables declaration//GEN-END:variables
}
