package com.model;

public class Pemakaian extends Catatan {
    public Pemakaian(String idBahan, int jumlah) {
        super(idBahan, jumlah);
    }    

    @Override
    public int getJumlah() {
        return jumlah;
    }

    public void setNamaBahan(String namaBahan) {
        this.idBahan = namaBahan;
    }

    public String getNamaBahan() {
        return idBahan;
    }
    
}
