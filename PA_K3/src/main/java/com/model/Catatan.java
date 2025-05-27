package com.model;

// -- modul Abstract Class dan Parent Class Catatan --
public abstract class Catatan {
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

    // -- modul Abstract Method jumlah --
    public abstract int getJumlah();

    public void setIdBahan(String idBahan) {
        this.idBahan = idBahan;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }
}
