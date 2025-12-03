/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author lenov
 */
package com.apotek.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Transaksi {
    private int idTransaksi;
    private String noFaktur;
    private LocalDateTime tglTransaksi;
    private int idUser;
    private double totalBayar;
    private double uangBayar;
    private double uangKembali;
    private List<DetailTransaksi> detailList;
    
    // Constructor
    public Transaksi() {
        this.detailList = new ArrayList<>();
    }
    
    // Getters & Setters
    public int getIdTransaksi() { return idTransaksi; }
    public void setIdTransaksi(int idTransaksi) { this.idTransaksi = idTransaksi; }
    
    public String getNoFaktur() { return noFaktur; }
    public void setNoFaktur(String noFaktur) { this.noFaktur = noFaktur; }
    
    public LocalDateTime getTglTransaksi() { return tglTransaksi; }
    public void setTglTransaksi(LocalDateTime tglTransaksi) { this.tglTransaksi = tglTransaksi; }
    
    public int getIdUser() { return idUser; }
    public void setIdUser(int idUser) { this.idUser = idUser; }
    
    public double getTotalBayar() { return totalBayar; }
    public void setTotalBayar(double totalBayar) { this.totalBayar = totalBayar; }
    
    public double getUangBayar() { return uangBayar; }
    public void setUangBayar(double uangBayar) { this.uangBayar = uangBayar; }
    
    public double getUangKembali() { return uangKembali; }
    public void setUangKembali(double uangKembali) { this.uangKembali = uangKembali; }
    
    public List<DetailTransaksi> getDetailList() { return detailList; }
    public void setDetailList(List<DetailTransaksi> detailList) { this.detailList = detailList; }
}