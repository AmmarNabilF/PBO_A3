package com.model;

import java.time.LocalDate;

public class Pesan extends Catatan {
    private String idPengguna;
    private LocalDate tanggalMasuk;
    private int jumlah;
    private double harga;

    public Pesan(String idPengguna, String idBahan, LocalDate tanggalMasuk, int jumlah, double harga) {
        super(idBahan, jumlah);
        this.idPengguna = idPengguna;
        this.tanggalMasuk = tanggalMasuk;
        this.harga = harga;
    }

    public String getIdPengguna() { 
        return idPengguna; 
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
                "idPengguna='" + idPengguna + '\'' +
                ", idPasokan='" + idBahan + '\'' +
                ", tanggalMasuk=" + tanggalMasuk +
                ", jumlah=" + jumlah +
                ", harga=" + harga +
                '}';
    }
}
