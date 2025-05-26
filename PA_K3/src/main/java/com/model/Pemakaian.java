package com.model;

public class Pemakaian extends Catatan {
    private String idPemakaian;
    private String idPengguna;
    private String keterangan;

    public Pemakaian(String idPemakaian, String idBahan, int jumlah, String idPengguna, String keterangan) {
        super(idBahan, jumlah);
        this.idPemakaian = idPemakaian;
        this.idPengguna = idPengguna;
        this.keterangan = keterangan;
    }    

    public String getIdPemakaian() {
        return idPemakaian;
    }

    public String getIdPengguna() {
        return idPengguna;
    }

    public String getKeterangan() {
        return keterangan;
    }

    @Override
    public String toString() {
        return "Pemakaian{" +
                "idPemakaian='" + idPemakaian + '\'' +
                ", idPengguna='" + idPengguna + '\'' +
                ", idBahan='" + idBahan + '\'' +
                ", jumlah=" + jumlah +
                ", keterangan='" + keterangan + '\'' +
                '}';
    }
}
