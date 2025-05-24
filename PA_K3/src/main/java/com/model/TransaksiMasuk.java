/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.model;

import java.time.LocalDate;

/**
 *
 * @author ammar
 */
public class TransaksiMasuk extends Transaksi implements CetakTransaksi {
    private final String idBahan;
    private LocalDate tanggalMasuk;
    private int jumlah;
    private double hargaUnit;

    public TransaksiMasuk(String idTransaksi, String idBahan, String idPemasok, LocalDate tanggalMasuk, int jumlah, double hargaUnit) {
        super(idTransaksi, idPemasok, null);
        this.idBahan = idBahan;
        this.tanggalMasuk = tanggalMasuk;
        this.jumlah = jumlah;
        this.hargaUnit = hargaUnit;
    }
    
    
    @Override
    public void cetakResi(){
        System.out.println("::::::::: Resi Transaksi Masuk :::::::::");
        System.out.println("|| ID Transaksi : " + idTransaksi + " ||");
        System.out.println("|| ID Pemasok : " + idPemasok + "     ||");
        System.out.println("|| ID Bahan : " + idBahan + "         ||");
        System.out.println("|| Tanggal : " + tanggalMasuk + "     ||");
        System.out.println("|| Jumlah : " + jumlah + "            ||");
        System.out.println("|| Harga/unit : " + hargaUnit + "     ||");
        System.out.println("||------------------------------------||");
        System.out.println("|| Total Harga : " + (hargaUnit * jumlah) + "||");
        
    }
   
    public final void informasi(){
        System.out.println("Transaksi telah dilakukan dan dicetak");
    }
}
