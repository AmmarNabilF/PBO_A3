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

public class TransaksiKeluar extends Transaksi implements CetakTransaksi{
    private final String idBahan;
    private LocalDate tanggalKeluar;
    private int jumlah;

    public TransaksiKeluar(String idTransaksi, String idBahan, String idPengguna, LocalDate tanggalKeluar, int jumlah) {
        super(idTransaksi, null, idPengguna);
        this.idBahan = idBahan;
        this.tanggalKeluar = tanggalKeluar;
        this.jumlah = jumlah;
    }
    
    @Override
    public void cetakResi(){
        System.out.println("::::::::: Resi Transaksi Keluar :::::::::");
        System.out.println("|| ID Transaksi: "+idTransaksi+"  :::::::||");
        System.out.println("|| ID Pengguna: "+idPengguna+"   :::::::||");
        System.out.println("|| ID Bahan: "+idBahan+"     :::::::||");
        System.out.println("|| Tanggal Keluar: "+tanggalKeluar+"    :::::::::||");
        System.out.println("|| Jumlah: "+jumlah+"        :::::::||");
    }
    
    public final void informasi(){
        System.out.println("Transaksi telah dibuat");
    }
}
