package com.model;

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

