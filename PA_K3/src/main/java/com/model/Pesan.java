package com.model;

import java.time.LocalDate;

// -- modul Inheritance Pesan child dari Catatan --
public class Pesan extends Catatan {
    private final String idPesanan; // -- modul final variabel --
    private String idPengguna;
    private String idPemasok;
    private LocalDate tanggalMasuk;
    private int jumlah;
    private double harga;
    private String namaBahan;
    private String namaPemasok;

    public Pesan(String idPesanan, String idPengguna, String idPemasok, String idBahan,
                 LocalDate tanggalMasuk, int jumlah, double harga,
                 String namaBahan, String namaPemasok) {
        super(idBahan, jumlah);
        this.idPesanan = idPesanan;
        this.idPengguna = idPengguna;
        this.idPemasok = idPemasok;
        this.tanggalMasuk = tanggalMasuk;
        this.jumlah = jumlah;
        this.harga = harga;
        this.namaBahan = namaBahan;
        this.namaPemasok = namaPemasok;
    }

    // -- modul Polymorphism (overriding) jumlah --
    @Override
    public int getJumlah() {
        return jumlah;
    }

    public String getIdPesanan() {
        return idPesanan;
    }

    public String getIdPengguna() {
        return idPengguna;
    }

    public String getIdPemasok() {
        return idPemasok;
    }

    public LocalDate getTanggalMasuk() {
        return tanggalMasuk;
    }

    public double getHarga() {
        return harga;
    }

    public String getNamaBahan() {
        return namaBahan;
    }

    public String getNamaPemasok() {
        return namaPemasok;
    }
}
