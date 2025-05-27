package com.model;

public class Pasokan extends Barang {
    private String idPemasok;
    private double hargaSatuan;

    public Pasokan(String idPasokan, String idPemasok, String namaBahan, double hargaSatuan, int stok) {
        super(idPasokan, namaBahan, stok);
        this.idPemasok = idPemasok;
        this.hargaSatuan = hargaSatuan;
    }

    public String getIdPemasok() { 
        return idPemasok; 
    }
    public double getHargaSatuan() { 
        return hargaSatuan; 
    }
    public void setHargaSatuan(double hargaSatuan) {
        this.hargaSatuan = hargaSatuan; 
    }

    @Override
    public void tampilkanInfo() {
        System.out.println("Pasokan: " + nama + ", Stok: " + stok + ", Harga: " + hargaSatuan);
    }
}