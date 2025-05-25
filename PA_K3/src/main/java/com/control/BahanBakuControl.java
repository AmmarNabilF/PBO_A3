package com.control;

import com.model.BahanBaku;
import com.DB;
import java.sql.*;

public class BahanBakuControl {
    private Connection conn;

    public BahanBakuControl() {
        DB db = new DB();
        conn = db.conn;
    }

    public void tampilkanBahanBaku() {
        String sql = "SELECT * FROM tbbahanbaku";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("Daftar Bahan Baku Tersedia:");
            while (rs.next()) {
                BahanBaku bb = new BahanBaku(
                        rs.getString("idBahan"),
                        rs.getString("namaBahan"),
                        rs.getInt("stok")
                );
                System.out.println(bb);
            }
        } catch (SQLException e) {
            System.out.println("Gagal menampilkan bahan baku: " + e.getMessage());
        }
    }

    public void hapusBahanBaku(String idBahan) {
        String sql = "DELETE FROM tbbahanbaku WHERE idBahan = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, idBahan);
            int affected = stmt.executeUpdate();
            if (affected > 0) {
                System.out.println("Bahan baku berhasil dihapus.");
            } else {
                System.out.println("Bahan baku dengan ID tersebut tidak ditemukan.");
            }
        } catch (SQLException e) {
            System.out.println("Gagal menghapus bahan baku: " + e.getMessage());
        }
    }

    public void tambahAtauUpdateBahanBaku(String idBahan, String namaBahan, int jumlahTambahan) {
        String cekSql = "SELECT stok FROM tbbahanbaku WHERE idBahan = ?";
        String updateSql = "UPDATE tbbahanbaku SET stok = stok + ? WHERE idBahan = ?";
        String insertSql = "INSERT INTO tbbahanbaku (idBahan, namaBahan, stok) VALUES (?, ?, ?)";

        try (PreparedStatement cekStmt = conn.prepareStatement(cekSql)) {
            cekStmt.setString(1, idBahan);
            ResultSet rs = cekStmt.executeQuery();

            if (rs.next()) {
                // Jika idBahan sudah ada, update stok
                try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                    updateStmt.setInt(1, jumlahTambahan);
                    updateStmt.setString(2, idBahan);
                    updateStmt.executeUpdate();
                    System.out.println("Stok bahan baku diperbarui.");
                }
            } else {
                // Jika tidak ada, insert baru
                try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                    insertStmt.setString(1, idBahan);
                    insertStmt.setString(2, namaBahan);
                    insertStmt.setInt(3, jumlahTambahan);
                    insertStmt.executeUpdate();
                    System.out.println("Bahan baku baru ditambahkan.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Gagal menambah/memperbarui bahan baku: " + e.getMessage());
        }
    }
}
