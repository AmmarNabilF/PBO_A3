/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.model;

/**
 *
 * @author ammar
 */
public class Produk {
    private String idProduk;
    private String idResep;
    private String namaProduk;
    private String kategori;
    private double hargaJual;
    
    public String getidProduk() {
        return this.idProduk;
    }

    public String getidResep(){
        return this.idResep;
    }
    
    public String getnamaProduk() {
        return this.namaProduk;
    }
    
    public String getkategori() {
        return this.kategori;
    }
    
    public Double gethargaJual() {
        return this.hargaJual;
    }

    public Produk(String idProduk, String idResep, String namaProduk, String kategori, double hargaJual) {
        this.idProduk = idProduk;
        this.idResep = idResep;
        this.namaProduk = namaProduk;
        this.kategori = kategori;
        this.hargaJual = hargaJual;
    }
    
    public String setidProduk(String idp){
        return this.idProduk = idp;
    }

    public String setidResep(String idr){
        return this.idResep = idr;
    }
    
    public String setnamaProduk(String nama){
        return this.namaProduk = nama;
    }
    
    public String setkategori(String kat){
        return this.kategori = kat;
    }
    
    public double sethargaJual (double harga){
       return this.hargaJual = harga;
    }
    
    @Override
    public String toString(){
       return "ID: " + getidProduk() +", Nama: " + getnamaProduk() + 
               ", Kategori: " + getkategori() + ", Harga: " + gethargaJual();
    }
}
