package com.model;

public class Pesan {
    private int idPesanan;
    private int idPengguna;
    private int idBahan;
    private java.sql.Timestamp tanggalMasuk;
    private int jumlah;
    private double hargaUnit;

    public Pesan() {
    }

    public Pesan(int idPesanan, int idPengguna, int idBahan, java.sql.Timestamp tanggalMasuk, int jumlah, double hargaUnit) {
        this.idPesanan = idPesanan;
        this.idPengguna = idPengguna;
        this.idBahan = idBahan;
        this.tanggalMasuk = tanggalMasuk;
        this.jumlah = jumlah;
        this.hargaUnit = hargaUnit;
    }

    public int getIdPesanan() {
        return idPesanan;
    }

    public void setIdPesanan(int idPesanan) {
        this.idPesanan = idPesanan;
    }

    public int getIdPengguna() {
        return idPengguna;
    }

    public void setIdPengguna(int idPengguna) {
        this.idPengguna = idPengguna;
    }

    public int getIdBahan() {
        return idBahan;
    }

    public void setIdBahan(int idBahan) {
        this.idBahan = idBahan;
    }

    public java.sql.Timestamp getTanggalMasuk() {
        return tanggalMasuk;
    }

    public void setTanggalMasuk(java.sql.Timestamp tanggalMasuk) {
        this.tanggalMasuk = tanggalMasuk;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public double getHargaUnit() {
        return hargaUnit;
    }

    public void setHargaUnit(double hargaUnit) {
        this.hargaUnit = hargaUnit;
    }
}
