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
    private String idBahan;
    private String namaResep;
    private int jmlhDigunakan;
    
    public String getidResep(){
        return this.idResep;
    }
    
    public String getnamaResep(){
        return this.namaResep;
    }
    
    public String getidBahan(){
        return this.idBahan;
    }
    
    public int getjumlahDigunakan(){
        return this.jmlhDigunakan;
    }
    
    public Resep(String idResep, String namaResep, String idBahan, int jmlhDigunakan) {
        this.idResep = idResep;
        this.namaResep = namaResep;
        this.idBahan = idBahan;
        this.jmlhDigunakan = jmlhDigunakan;
    }
    
    @Override
    public String toString(){
        return "ID :" + getidResep() +
                ", ID Produk: " + getnamaResep() +
                ", ID Bahan: " + getidBahan() +
                ", Jumlah digunakan: " + getjumlahDigunakan();
    }
    
}
