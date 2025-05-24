/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.auth;
import com.model.Pengguna;

/**
 *
 * @author ammar
 */
public class auth {
    private static Pengguna akun = new Pengguna("006","Ammar","006@mail.com","2309106006");
    
    public static boolean signIn(String email, String password){
        if (email.equals(akun.getemail()) && password.equals(akun.getpassword())){
            return true;
        }
        else{
            System.out.println("email atau password tidak tepat!!");
        }
    return false;
    }
    public static String getAdminData(){
        return "\n>> SELAMAT DATANG <<\n" + "( " + akun.getidUser() + " )" + akun.getnama();
    }
}
