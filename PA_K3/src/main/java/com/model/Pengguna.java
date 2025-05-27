package com.model;

public class Pengguna {
    private String idPengguna;
    private String namaPengguna;
    private String nomorTelepon;
    private String password;
    
    public Pengguna(String idPengguna, String namaPengguna, String nomorTelepon, String password){
        this.idPengguna = idPengguna;
        this.namaPengguna = namaPengguna;
        this.nomorTelepon = nomorTelepon;
        this.password = password;
    }
    
    public String getIdPengguna(){
        return this.idPengguna;
    }
    
    public String getNamaPengguna(){
        return this.namaPengguna;
    }
    
    public String getNomorTelepon(){
        return this.nomorTelepon;
    }
    
    public String getPassword(){
        return this.password;
    }

    public void setIdPengguna(String idPengguna){
        this.idPengguna = idPengguna;
    }

    public void setNama(String namaPengguna){
        this.namaPengguna = namaPengguna;
    }

    public void setNomorTelepon(String nomorTelepon){
        this.nomorTelepon = nomorTelepon;
    }

    public void setPassword(String password){
        this.password = password;
    }
}
