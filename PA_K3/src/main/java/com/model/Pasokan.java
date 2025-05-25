package com.model;

public class Pasokan {
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
        return "ID Pasokan: " + idPasokan +
               ", ID Pemasok: " + idPemasok +
               ", Nama Bahan: " + namaBahan +
               ", Harga Satuan: " + hargaSatuan +
               ", Stok: " + stok;
    }
}