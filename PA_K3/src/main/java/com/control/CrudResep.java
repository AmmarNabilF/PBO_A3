/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.control;

import com.model.Resep;

/**
 *
 * @author ammar
 */
public class CrudResep extends Crud<Resep> {
    
    public void catatResep(Resep resep){
        tambah(resep);
    }
        
    public void showResep(){
        show();
    }
}
