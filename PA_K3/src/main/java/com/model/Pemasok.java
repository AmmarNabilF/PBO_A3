/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.model;

/**
 *
 * @author ammar
 */
public final class Pemasok {
    private String idPemasok;
    private String namaPemasok;
    private String alamat;
    private int nomorTelepon;
    private String password;

    public Pemasok(String idPemasok, String namaPemasok, String alamat, int nomorTelepon, String password) {
        this.idPemasok = idPemasok;
        this.namaPemasok = namaPemasok;
        this.alamat = alamat;
        this.nomorTelepon = nomorTelepon;
        this.password = password;
    }
    
    public String getIdPemasok(){
        return idPemasok;
    }

    public String getNamaPemasok() {
        return namaPemasok;
    }

    public String getAlamat() {
        return alamat;
    }

    public int getNomorTelepon() {
        return nomorTelepon;
    }

    public String getPassword() {
        return password;
    }

    public void setIdPemasok(String idPemasok) {
        this.idPemasok = idPemasok;
    }

    public void setNamaPemasok(String namaPemasok) {
        this.namaPemasok = namaPemasok;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public void setNomorTelepon(int nomorTelepon) {
        this.nomorTelepon = nomorTelepon;
    }
    
    public void terimaPesanan(String namabahan, int jumlah, String satuan){
        System.out.println("Pemasok atas nama " + namaPemasok + " telah menerima pesanan anda");
        System.out.println("Detail pesanan: " + namabahan + "[" + jumlah + satuan + "]");
    }
}
