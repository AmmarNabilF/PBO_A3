package com.control;

import com.model.Pasokan;
import com.DB;
import java.sql.*;

public class PasokanController {
    private Connection conn;

    public PasokanController() {
        DB db = new DB();
        conn = db.conn;        
    }

    public void tambahPasokan(String idPemasok, String namaBahan, double hargaSatuan, int stok) {
        String sql = "INSERT INTO tbpasokan (idPemasok, namaBahan, hargaSatuan, stok) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // stmt.setString(1, idPasokan);
            stmt.setString(1, idPemasok);
            stmt.setString(2, namaBahan);
            stmt.setDouble(3, hargaSatuan);
            stmt.setInt(4, stok);
            stmt.executeUpdate();
            System.out.println("Pasokan berhasil ditambahkan.");
        } catch (SQLException e) {
            System.out.println("Gagal menambahkan pasokan: " + e.getMessage());
        }
    }

    public void lihatPasokan() {
        String sql = "SELECT * FROM tbpasokan";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Pasokan p = new Pasokan(
                    rs.getString("idPasokan"),
                    rs.getString("idPemasok"),
                    rs.getString("namaBahan"),
                    rs.getDouble("hargaSatuan"),
                    rs.getInt("stok")
                );
                System.out.println(p);
            }
        } catch (SQLException e) {
            System.out.println("Gagal menampilkan pasokan: " + e.getMessage());
        }
    }

    public void updatePasokan(String idPasokan, String namaBaru, double hargaBaru, int stokBaru) {
        String sql = "UPDATE tbpasokan SET namaBahan = ?, hargaSatuan = ?, stok = ? WHERE idPasokan = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, namaBaru);
            stmt.setDouble(2, hargaBaru);
            stmt.setInt(3, stokBaru);
            stmt.setString(4, idPasokan);
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Pasokan berhasil diperbarui.");
            } else {
                System.out.println("Pasokan tidak ditemukan.");
            }
        } catch (SQLException e) {
            System.out.println("Gagal memperbarui pasokan: " + e.getMessage());
        }
    }

    public void hapusPasokan(String idPasokan) {
        String sql = "DELETE FROM tbpasokan WHERE idPasokan = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, idPasokan);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Pasokan berhasil dihapus.");
            } else {
                System.out.println("Pasokan tidak ditemukan.");
            }
        } catch (SQLException e) {
            System.out.println("Gagal menghapus pasokan: " + e.getMessage());
        }
    }
}