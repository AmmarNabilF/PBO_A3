package com.control;

import com.model.Pasokan;
import com.DB;
import com.auth.auth;
import java.sql.*;

public class PasokanController {
    private Connection conn;
    private auth auth = new auth(conn);

    public PasokanController() {
        DB db = new DB();
        conn = db.conn;        
    }

    public void tambahPasokan(String idPemasok, String namaBahan, double hargaSatuan, int stok) {
        String sql = "INSERT INTO tbpasokan (idPemasok, namaBahan, hargaSatuan, stok) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
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
        String sql = "SELECT * FROM tbpasokan WHERE idPemasok = ?";
        boolean found = false;
        String format = "| %-12s | %-25s | %-13s | %-4s |\n";
        String line = "+--------------+---------------------------+---------------+------+"; 
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, auth.getCurrentUserId());
            try (ResultSet rs = stmt.executeQuery()) {
                System.out.println("\nDaftar Pasokan:");
                System.out.println(line);
                System.out.printf(format, "ID Pasokan", "Nama Bahan", "Harga Satuan", "Stok");
                System.out.println(line);

                while (rs.next()) {
                    Pasokan p = new Pasokan(
                        rs.getString("idPasokan"),
                        rs.getString("idPemasok"),
                        rs.getString("namaBahan"),
                        rs.getDouble("hargaSatuan"),
                        rs.getInt("stok")
                    );
                    System.out.printf(format,
                        p.getIdPasokan(),
                        p.getNamaBahan(),
                        String.format("%.2f", p.getHargaSatuan()),
                        p.getStok()
                    );
                    found = true;
                }
                System.out.println(line);

                if (!found) {
                    System.out.println("Data pasokan kosong.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Gagal menampilkan pasokan: " + e.getMessage());
        }
    }

    public boolean cekPasokan(String idPasokan) {
        String sql = "SELECT * FROM tbpasokan WHERE idPasokan = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, idPasokan);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Gagal mengecek pasokan: " + e.getMessage());
            return false;
        }
    }

    public boolean cekIsiPasokan() {
        String sql = "SELECT COUNT(*) FROM tbpasokan WHERE idPemasok = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, auth.getCurrentUserId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("Gagal mengecek isi pasokan: " + e.getMessage());
        }
        return false;
    }

    public void updatePasokan(String idPasokan, String namaBaru, double hargaBaru, int stokBaru) {
        String sql = "UPDATE tbpasokan SET namaBahan = ?, hargaSatuan = ?, stok = ? WHERE idPasokan = ? AND idPemasok = ?";
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
        String sql = "DELETE FROM tbpasokan WHERE idPasokan = ? AND idPemasok = ?";
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