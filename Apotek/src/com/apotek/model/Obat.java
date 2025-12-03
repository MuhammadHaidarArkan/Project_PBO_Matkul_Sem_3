/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author lenov
 */
package com.apotek.model;

import java.time.LocalDate;

public class Obat {
    private String kodeObat;
    private String namaObat;
    private String kategori;
    private int stok;
    private double hargaSatuan;
    private LocalDate tglExpired;
    private String supplier;
    
    // Constructor
    public Obat() {}
    
    public Obat(String kodeObat, String namaObat, int stok, double hargaSatuan, LocalDate tglExpired) {
        this.kodeObat = kodeObat;
        this.namaObat = namaObat;
        this.stok = stok;
        this.hargaSatuan = hargaSatuan;
        this.tglExpired = tglExpired;
    }
    
    // Getters & Setters
    public String getKodeObat() { return kodeObat; }
    public void setKodeObat(String kodeObat) { this.kodeObat = kodeObat; }
    
    public String getNamaObat() { return namaObat; }
    public void setNamaObat(String namaObat) { this.namaObat = namaObat; }
    
    public String getKategori() { return kategori; }
    public void setKategori(String kategori) { this.kategori = kategori; }
    
    public int getStok() { return stok; }
    public void setStok(int stok) { this.stok = stok; }
    
    public double getHargaSatuan() { return hargaSatuan; }
    public void setHargaSatuan(double hargaSatuan) { this.hargaSatuan = hargaSatuan; }
    
    public LocalDate getTglExpired() { return tglExpired; }
    public void setTglExpired(LocalDate tglExpired) { this.tglExpired = tglExpired; }
    
    public String getSupplier() { return supplier; }
    public void setSupplier(String supplier) { this.supplier = supplier; }
}
