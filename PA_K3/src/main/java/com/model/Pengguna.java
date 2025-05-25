/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.model;


/**
 *
 * @author ammar
 */
public class Pengguna {
    private String idPengguna;
    private String namaPengguna;
    protected String nomorTelepon;
    protected String password;
    
    public String getidPengguna(){
        return this.idPengguna;
    }
    
    public String getnama(){
        return this.namaPengguna;
    }
    
    public String getnomorTelepon(){
        return this.nomorTelepon;
    }
    
    public String getpassword(){
        return this.password;
    }

    public void setidPengguna(String idPengguna){
        this.idPengguna = idPengguna;
    }

    public void setnama(String namaPengguna){
        this.namaPengguna = namaPengguna;
    }

    public void setnomorTelepon(String nomorTelepon){
        this.nomorTelepon = nomorTelepon;
    }

    public void setpassword(String password){
        this.password = password;
    }
    
    public Pengguna(String idPengguna, String nama, String nomorTelepon, String password){
        this.idPengguna = idPengguna;
        this.namaPengguna = namaPengguna;
        this.nomorTelepon = nomorTelepon;
        this.password = password;
    }
    
    
}
