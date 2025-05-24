/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.model;

/**
 *
 * @author ammar
 */
public class Resep {
    private String idResep;
    private String idProduk;
    private String idBahan;
    private int jumlahDigunakan;
    private String satuan;
    
    public String getidResep(){
        return this.idResep;
    }
    
    public String getidProduk(){
        return this.idProduk;
    }
    
    public String getidBahan(){
        return this.idBahan;
    }
    
    public int getjumlahDigunakan(){
        return this.jumlahDigunakan;
    }
    
    public String getsatuan(){
        return this.satuan;
    }

    public Resep(String idResep, String idProduk, String idBahan, int jumlahDigunakan, String satuan) {
        this.idResep = idResep;
        this.idProduk = idProduk;
        this.idBahan = idBahan;
        this.jumlahDigunakan = jumlahDigunakan;
        this.satuan = satuan;
    }
    
    @Override
    public String toString(){
        return "ID :" + getidResep() +
                ", ID Produk: " + getidProduk() +
                ", ID Bahan: " + getidBahan() +
                ", Jumlah digunakan: " + getjumlahDigunakan() +
                ", Satuan: " + getsatuan();
    }
    
}
