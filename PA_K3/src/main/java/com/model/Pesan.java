package com.model;

import java.time.LocalDate;

public class Pesan extends Catatan {
    private String idPesanan;
    private String idPengguna;
    private String idPemasok;
    private LocalDate tanggalMasuk;
    private int jumlah;
    private double harga;

    public Pesan(String idPesanan, String idPengguna, String idPemasok, String idBahan, LocalDate tanggalMasuk, int jumlah, double harga) {
        super(idBahan, jumlah);
        this.idPesanan = idPesanan;
        this.idPengguna = idPengguna;
        this.idPemasok = idPemasok;
        this.tanggalMasuk = tanggalMasuk;
        this.jumlah = jumlah;
        this.harga = harga;
    }

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
    
    @Override
    public String toString() {
        return "Pesan{" +
                "idPesanan='" + idPesanan + '\'' +
                "idPengguna='" + idPengguna + '\'' +
                ", idBahan='" + idBahan + '\'' +
                ", idPemasok='" + idPemasok + '\'' +
                ", tanggalMasuk=" + tanggalMasuk +
                ", jumlah=" + jumlah +
                ", harga=" + harga +
                '}';
    }
}
