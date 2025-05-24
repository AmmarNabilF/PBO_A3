/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crud;

/**
 *
 * @author ammar
 */
public interface CrudInterface<T> {
    void tambah(T item);
    void show();
    boolean hapus(T item);
}
