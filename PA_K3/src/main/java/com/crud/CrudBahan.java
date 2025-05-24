/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crud;

import com.model.BahanBaku;
import java.util.Iterator;
/**
 *
 * @author ammar
 */
public class CrudBahan extends Crud<BahanBaku>{
    
    public void tambahBahan(BahanBaku bahanBaru) {
        tambah(bahanBaru);
    }
    
    public void showBahan(){
        show();
    }
    
    @Override
    public boolean hapus(BahanBaku bahan){
        Iterator<BahanBaku> iterator = storage.iterator();
        while (iterator.hasNext()){
            BahanBaku b = iterator.next();
            if (b.getidBahan().equals(bahan.getidBahan())){
                iterator.remove();
                return true;
            }
        }
        return false;
    }
    public boolean delBahan(String id){
        for (BahanBaku b : storage){
            if (b.getidBahan().equals(id)){
                return hapus(b);
            }
        }
        return false;   
    }
    
}
