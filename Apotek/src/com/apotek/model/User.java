/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author lenov
 */
package com.apotek.model;

public class User {
    private int idUser;
    private String username;
    private String password;
    private String namaLengkap;
    private String role;
    
    // Constructor
    public User() {}
    
    public User(int idUser, String username, String namaLengkap, String role) {
        this.idUser = idUser;
        this.username = username;
        this.namaLengkap = namaLengkap;
        this.role = role;
    }
    
    // Getters & Setters
    public int getIdUser() { return idUser; }
    public void setIdUser(int idUser) { this.idUser = idUser; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getNamaLengkap() { return namaLengkap; }
    public void setNamaLengkap(String namaLengkap) { this.namaLengkap = namaLengkap; }
    
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
