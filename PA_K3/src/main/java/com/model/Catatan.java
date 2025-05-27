package com.model;

public class Catatan {
    protected String idBahan;
    protected int jumlah;
    protected String idPengguna;

    public Catatan(String idBahan, int jumlah) {
        this.idBahan = idBahan;
        this.jumlah = jumlah;
    }

    public String getIdBahan() {
        return idBahan;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setIdBahan(String idBahan) {
        this.idBahan = idBahan;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }
}
