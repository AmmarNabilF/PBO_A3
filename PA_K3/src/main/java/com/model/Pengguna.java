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
    private String idUser;
    private String nama;
    protected String email;
    protected String password;
    
    public String getidUser(){
        return this.idUser;
    }
    
    public String getnama(){
        return this.nama;
    }
    
    public String getemail(){
        return this.email;
    }
    
    public String getpassword(){
        return this.password;
    }
    
    
    public Pengguna(String idUser, String nama, String email, String password){
        this.idUser = idUser;
        this.nama = nama;
        this.email = email;
        this.password = password;
    }
    
    
}
