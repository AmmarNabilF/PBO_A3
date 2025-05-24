/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author ammar
 */
public class BahanBaku {
    private String idBahan;
    private String namaBahan;
    private String kategori;
    private int stok;
    private String satuan;
    private LocalDate tanggalexp;
    
    public String getidBahan() {
        return this.idBahan;
    }
    
    public String getnamaBahan() {
        return this.namaBahan;
    }
    
    public String getkategori() {
        return this.kategori;
    }
    
    public int getstok() {
        return this.stok;
    }
    
    public String getsatuan() {
        return this.satuan;
    }
    
    public LocalDate gettanggalexp() {
        return this.tanggalexp;
    }
    
    
    public BahanBaku(String idBahan, String namaBahan, String kategori, int stok, String satuan, LocalDate tanggalexp){
        this.idBahan = idBahan;
        this.namaBahan = namaBahan;
        this.kategori = kategori;
        this.stok = stok;
        this.satuan = satuan;
        this.tanggalexp = tanggalexp;
    }
    
    public void setidBahan(String idb){
        this.idBahan = idb;
    }
    
    public void settanggalexp(LocalDate tanggalexp){
        this.tanggalexp = tanggalexp;
    }
    
    @Override
    public String toString() {

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if (tanggalexp != null){
            tanggalexp.format(format);
        }
        else {
            System.out.println("Tanggal belum ditentukan");
        }
        return "ID: " + getidBahan() +
        ", Nama: " + getnamaBahan() + 
        ", Kategori: " + getkategori() + 
        ", Stok: " + getstok() +
        ", Satuan: " + getsatuan() + 
        ",Tanggal Expired: " + gettanggalexp() ;
    }
}
