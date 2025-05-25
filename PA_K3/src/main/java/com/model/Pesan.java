package com.model;

import java.time.LocalDate;

public class Pesan {
    private String idPengguna;
    private String idPasokan;
    private LocalDate tanggalMasuk;
    private int jumlah;
    private double harga;

    public Pesan(String idPengguna, String idPasokan, LocalDate tanggalMasuk, int jumlah, double harga) {
        this.idPengguna = idPengguna;
        this.idPasokan = idPasokan;
        this.tanggalMasuk = tanggalMasuk;
        this.jumlah = jumlah;
        this.harga = harga;
    }

    public String getIdPengguna() { 
        return idPengguna; 
    }
    public String getIdPasokan() { 
        return idPasokan; 
    }
    public LocalDate getTanggalMasuk() { 
        return tanggalMasuk; 
    }
    public int getJumlah() { 
        return jumlah; 
    }
    public double getHarga() { 
        return harga; 
    }
    
    @Override
    public String toString() {
        return "Pesan{" +
                "idPengguna='" + idPengguna + '\'' +
                ", idPasokan='" + idPasokan + '\'' +
                ", tanggalMasuk=" + tanggalMasuk +
                ", jumlah=" + jumlah +
                ", harga=" + harga +
                '}';
    }
}
