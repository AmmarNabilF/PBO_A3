/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.control;
import java.util.ArrayList;
import java.util.List;
//import java.util.Iterator;
        

/**
 *
 * @author ammar
 */
public class Crud<T> implements CrudInterface<T> {
    protected List<T> storage = new ArrayList<>();
    
    @Override
    public void tambah(T item){
        storage.add(item);
    }
    
    @Override
    public void show(){
        if (storage.isEmpty()){
            System.out.println("\nItem kosong");
        }
        for (T item: storage){
            System.out.println(item);
        }
    }
    
    @Override
    public boolean hapus(T item){
        if (storage.contains(item)){
            storage.remove(item);
            return true;
        }
        else {
            return false;
        }
    }
}
