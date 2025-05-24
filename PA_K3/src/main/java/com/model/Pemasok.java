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
    private String nomorTelepon;

    public Pemasok(String idPemasok, String namaPemasok, String alamat, String nomorTelepon) {
        this.idPemasok = idPemasok;
        this.namaPemasok = namaPemasok;
        this.alamat = alamat;
        this.nomorTelepon = nomorTelepon;
    }
    
    public String getIdpemasok(){
        return idPemasok;
    }
    
    public void terimaPesanan(String namabahan, int jumlah, String satuan){
        System.out.println("Pemasok atas nama " + namaPemasok + " telah menerima pesanan anda");
        System.out.println("Detail pesanan: " + namabahan + "[" + jumlah + satuan + "]");
    }
}
