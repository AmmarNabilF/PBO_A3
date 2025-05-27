package com.model;

public class BahanBaku {
    private String idBahan;
    private String namaBahan;
    private int stok;

    public BahanBaku(String idBahan, String namaBahan, int stok) {
        this.idBahan = idBahan;
        this.namaBahan = namaBahan;
        this.stok = stok;
    }

    public String getIdBahan() { return idBahan; }
    public String getNamaBahan() { return namaBahan; }
    public int getStok() { return stok; }

    public void setStok(int stok) { this.stok = stok; }
}
