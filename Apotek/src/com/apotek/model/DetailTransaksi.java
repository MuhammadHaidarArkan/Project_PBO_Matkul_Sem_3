/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author lenov
 */
package com.apotek.model;

public class DetailTransaksi {
    private int idDetail;
    private int idTransaksi;
    private String kodeObat;
    private String namaObat;
    private int jumlah;
    private double hargaSatuan;
    private double subtotal;
    
    // Constructor
    public DetailTransaksi() {}
    
    public DetailTransaksi(String kodeObat, String namaObat, int jumlah, double hargaSatuan) {
        this.kodeObat = kodeObat;
        this.namaObat = namaObat;
        this.jumlah = jumlah;
        this.hargaSatuan = hargaSatuan;
        this.subtotal = jumlah * hargaSatuan;
    }
    
    // Getters & Setters
    public int getIdDetail() { return idDetail; }
    public void setIdDetail(int idDetail) { this.idDetail = idDetail; }
    
    public int getIdTransaksi() { return idTransaksi; }
    public void setIdTransaksi(int idTransaksi) { this.idTransaksi = idTransaksi; }
    
    public String getKodeObat() { return kodeObat; }
    public void setKodeObat(String kodeObat) { this.kodeObat = kodeObat; }
    
    public String getNamaObat() { return namaObat; }
    public void setNamaObat(String namaObat) { this.namaObat = namaObat; }
    
    public int getJumlah() { return jumlah; }
    public void setJumlah(int jumlah) { 
        this.jumlah = jumlah;
        this.subtotal = jumlah * hargaSatuan;
    }
    
    public double getHargaSatuan() { return hargaSatuan; }
    public void setHargaSatuan(double hargaSatuan) { 
        this.hargaSatuan = hargaSatuan;
        this.subtotal = jumlah * hargaSatuan;
    }
    
    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }
}
