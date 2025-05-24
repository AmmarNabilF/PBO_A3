/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.model;

/**
 *
 * @author ammar
 */
public abstract class Transaksi {
    protected String idPengguna;
    protected String idPemasok;
    protected String idTransaksi;
    
    public Transaksi(String idTransaksi, String idPemasok, String idPengguna){
        this.idPengguna = idPengguna;
        this.idTransaksi = idTransaksi;
        this.idPemasok = idPemasok;
    }
    
    public static String generateId(String numId){
        return numId + System.currentTimeMillis();
    }
}
