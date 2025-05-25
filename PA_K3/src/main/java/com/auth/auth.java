/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.auth;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.model.Pengguna;
import com.model.Pemasok;

/**
 *
 * @author ammar
 */
public class auth {
    private Connection conn;

    public auth(Connection conn) {
        this.conn = conn;
    }

    public void register(Pemasok pemasok) throws SQLException {
        String query = "INSERT INTO tbpemasok (idPemasok, namaPemasok, nomorTelepon, password) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, pemasok.getIdPemasok());
            stmt.setString(2, pemasok.getNamaPemasok());
            stmt.setInt(3, pemasok.getNomorTelepon());
            stmt.setString(4, pemasok.getPassword());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error during registration: " + e.getMessage());
            throw e; // Rethrow the exception for further handling
        }
    }


    // ambildata akun pemasok berdasarkan idPemasok
    public Pemasok getAkun(String idPemasok) throws SQLException {
        String query = "SELECT * FROM tbpemasok WHERE idPemasok = ?";
        Pemasok pemasok = null;

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, idPemasok);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                pemasok = new Pemasok(
                    rs.getString("idPemasok"),
                    rs.getString("namaPemasok"),
                    rs.getInt("nomorTelepon"),
                    rs.getString("password")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error fetching account: " + e.getMessage());
            throw e; // Rethrow the exception for further handling
        }

        return pemasok;
    }

    public void registerPemasok(String idPemasok, String namaPemasok, int nomorTelepon, String password) throws SQLException {
        Pemasok pemasok = new Pemasok(idPemasok, namaPemasok, nomorTelepon, password);
        register(pemasok);
    }

    public Object login(int nomorTelepon, String password) throws SQLException {
        // Coba login sebagai Pemasok
        String queryPemasok = "SELECT * FROM tbpemasok WHERE nomorTelepon = ? AND password = ?";
        try (PreparedStatement stmt = conn.prepareStatement(queryPemasok)) {
            stmt.setInt(1, nomorTelepon);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Pemasok(
                    rs.getString("idPemasok"),
                    rs.getString("namaPemasok"),
                    rs.getInt("nomorTelepon"),
                    rs.getString("password")
                );
            }
        }

        // Jika tidak ditemukan di pemasok, coba login sebagai Pengguna
        String queryPengguna = "SELECT * FROM tbpengguna WHERE nomorTelepon = ? AND password = ?";
        try (PreparedStatement stmt = conn.prepareStatement(queryPengguna)) {
            stmt.setInt(1, nomorTelepon);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Pengguna(
                    rs.getString("idPengguna"),
                    rs.getString("namaPengguna"),
                    rs.getString("nomorTelepon"),
                    rs.getString("password")
                );
            }
        }

        // Jika tidak ditemukan di kedua tabel
        return null;
    }
    
}
