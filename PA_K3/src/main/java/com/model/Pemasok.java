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
    private String nomorTelepon;
    private String password;

    public Pemasok(String idPemasok, String namaPemasok, String nomorTelepon, String password) {
        this.idPemasok = idPemasok;
        this.namaPemasok = namaPemasok;
        this.nomorTelepon = nomorTelepon;
        this.password = password;
    }
    
    public String getIdPemasok(){
        return idPemasok;
    }

    public String getNamaPemasok() {
        return namaPemasok;
    }

    public String getNomorTelepon() {
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

    public void setNomorTelepon(String nomorTelepon) {
        this.nomorTelepon = nomorTelepon;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public void terimaPesanan(String namabahan, int jumlah, String satuan){
        System.out.println("Pemasok atas nama " + namaPemasok + " telah menerima pesanan anda");
        System.out.println("Detail pesanan: " + namabahan + "[" + jumlah + satuan + "]");
    }
}
