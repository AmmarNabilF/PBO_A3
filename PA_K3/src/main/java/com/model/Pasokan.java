package com.model;

public class Pasokan implements InterPasokan {
    private String idPasokan;
    private String idPemasok;
    private String namaBahan;
    private double hargaSatuan;
    private int stok;

    public Pasokan(String idPasokan, String idPemasok, String namaBahan, double hargaSatuan, int stok) {
        this.idPasokan = idPasokan;
        this.idPemasok = idPemasok;
        this.namaBahan = namaBahan;
        this.hargaSatuan = hargaSatuan;
        this.stok = stok;
    }

    public String getIdPasokan() {
        return idPasokan;
    }

    public String getIdPemasok() {
        return idPemasok;
    }

    public String getNamaBahan() {
        return namaBahan;
    }

    public double getHargaSatuan() {
        return hargaSatuan;
    }

    public int getStok() {
        return stok;
    }

    public void setNamaBahan(String namaBahan) {
        this.namaBahan = namaBahan;
    }

    public void setHargaSatuan(double hargaSatuan) {
        this.hargaSatuan = hargaSatuan;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }

    @Override
    public String toString() {
        return String.format("%-12s | %-25s | Rp%-13.2f | %-5d", 
                            idPasokan, namaBahan, hargaSatuan, stok);
    }
}