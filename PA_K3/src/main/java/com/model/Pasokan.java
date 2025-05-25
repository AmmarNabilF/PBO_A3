package com.model;

public class Pasokan {
    private int idPasokan;
    private int idPemasok;
    private String namaBahan;
    private double hargaSatuan;
    private int stok;

    // Constructor
    public Pasokan(int idPasokan, int idPemasok, String namaBahan, double hargaSatuan, int stok) {
        this.idPasokan = idPasokan;
        this.idPemasok = idPemasok;
        this.namaBahan = namaBahan;
        this.hargaSatuan = hargaSatuan;
        this.stok = stok;
    }

    // Getters and Setters
    public int getIdPasokan() {
        return idPasokan;
    }

    public void setIdPasokan(int idPasokan) {
        this.idPasokan = idPasokan;
    }

    public int getIdPemasok() {
        return idPemasok;
    }

    public void setIdPemasok(int idPemasok) {
        this.idPemasok = idPemasok;
    }

    public String getNamaBahan() {
        return namaBahan;
    }

    public void setNamaBahan(String namaBahan) {
        this.namaBahan = namaBahan;
    }

    public double getHargaSatuan() {
        return hargaSatuan;
    }

    public void setHargaSatuan(double hargaSatuan) {
        this.hargaSatuan = hargaSatuan;
    }

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }
}
