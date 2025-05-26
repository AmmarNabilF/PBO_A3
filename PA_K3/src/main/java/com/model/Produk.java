/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.model;

/**
 *
 * @author ammar
 */
public class Produk {
    private String idProduk;
    private String namaProduk;
    private int jumlah;

    public Produk(String idProduk, String namaProduk, int jumlah) {
        this.idProduk = idProduk;
        this.namaProduk = namaProduk;
        this.jumlah = jumlah;
    }

    public String getIdProduk() { return idProduk; }
    public String getNamaProduk() { return namaProduk; }
    public int getJumlah() { return jumlah; }
}

