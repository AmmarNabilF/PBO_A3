/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crud;
import com.model.Produk;
import java.util.Iterator;
/**
 *
 * @author ammar
 */
public class CrudProduk extends Crud<Produk> implements CrudInterface<Produk> {
    
    public void simpanProduk(Produk produk){
        tambah(produk);
    }
    
    public void showProduk(){
        show();
    }
    
    public boolean updateProduk(String id, String namaBaru, String kategoriBaru, double hargaBaru){
        for (Produk p : storage){
            if (p.getidProduk().equals(id)){
                p.setnamaProduk(namaBaru);
                p.setkategori(kategoriBaru);
                p.sethargaJual(hargaBaru);
                return true;
            }
        }
        return false;
    }
    
    public Produk searchProduk(String id){
        for (Produk p : storage){
            if (p.getidProduk().equalsIgnoreCase(id)){
                return p;
            }
        }
        return null;
    }
    
    public Produk searchProduk(String nama, boolean byNama){
        for (Produk p : storage) {
            if (p.getnamaProduk().equalsIgnoreCase(nama)){
                return p;
            }
        }
        return null;
    }
    
    @Override
    public boolean hapus(Produk produk){
        Iterator<Produk> iterator = storage.iterator();
        while (iterator.hasNext()){
            Produk p = iterator.next();
            if (p.getidProduk().equals(produk.getidProduk())){
                iterator.remove();
                return true;
            }
        }
        return false;
    }
    public boolean delProduk(String id){
        for (Produk p : storage){
            if (p.getidProduk().equals(id)){
                return hapus(p);
            }
        }
        return false;   
    }
}
