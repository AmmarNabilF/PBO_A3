/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.control;

import com.model.Resep;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ammar
 */

public class CrudResep {
    private final Connection conn;
    private String idResep;

    public CrudResep(Connection conn) {
        this.conn = conn;
    }

    public void addResep(Resep resep) {
        String query = "INSERT INTO tbresep (idResep, idBahan, namaResep, jmlhDigunakan) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, resep.getidResep());
            stmt.setString(2, resep.getidBahan());
            stmt.setString(3, resep.getnamaResep());
            stmt.setInt(4, resep.getjumlahDigunakan());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error adding recipe: " + e.getMessage());
        }
    }

    public void showResep() {
        String query = "SELECT * FROM tbresep";
        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String idResep = rs.getString("idResep");
                String idBahan = rs.getString("idBahan");
                String namaResep = rs.getString("namaResep");
                int jmlhDigunakan = rs.getInt("jmlhDigunakan");
                System.out.println(new Resep(idResep, namaResep, idBahan, jmlhDigunakan));
            }
        } catch (SQLException e) {
            System.err.println("Error showing recipes: " + e.getMessage());
        }
    }

    public boolean delResep(String idResep) {
        String query = "DELETE FROM tbresep WHERE idResep = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, idResep);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting recipe: " + e.getMessage());
            return false;
        }
    }

    public String generateIdResep() {
        String query = "SELECT COUNT(*) AS count FROM tbresep";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt("count");
                return "R" + String.format("%03d", count + 1);
            }
        } catch (SQLException e) {
            System.err.println("Error generating ID: " + e.getMessage());
        }
        return null;
    }
}
