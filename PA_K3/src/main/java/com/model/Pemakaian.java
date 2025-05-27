package com.model;

public class Pemakaian extends Catatan {
    public Pemakaian(String idBahan, int jumlah) {
        super(idBahan, jumlah);
    }    

    public void setNamaBahan(String namaBahan) {
        this.idBahan = namaBahan;
    }

    public String getNamaBahan() {
        return idBahan;
    }

    @Override
    public String toString() {
        return "Pemakaian{" +
                "idBahan='" + idBahan + '\'' +
                ", jumlah=" + jumlah +
                '}';
    }
}
