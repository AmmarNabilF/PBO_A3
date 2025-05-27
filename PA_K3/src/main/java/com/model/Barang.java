package com.model;

public abstract class Barang {
    protected String id;
    protected String nama;
    protected int stok;

    public Barang(String id, String nama, int stok) {
        this.id = id;
        this.nama = nama;
        this.stok = stok;
    }

    public String getId() { 
        return id; 
    }
    public String getNama() { 
        return nama; 
    }
    public int getStok() { 
        return stok; 
    }
    public void setStok(int stok) { 
        this.stok = stok;
    }
    public void setNama(String nama) { 
        this.nama = nama;
    }
    public void setId(String id) {
        this.id = id; 
    }

    // Method abstract, wajib diisi oleh subclass
    public abstract void tampilkanInfo();

    @Override
    public String toString() {
        return "Barang{" +
                "id='" + id + '\'' +
                ", nama='" + nama + '\'' +
                ", stok=" + stok +
                '}';
    }
}