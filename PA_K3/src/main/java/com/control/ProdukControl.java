package com.control;

import java.sql.*;
import java.util.*;

import com.DB;

public class ProdukControl {
    private Connection conn;

    public ProdukControl() {
        DB db = new DB();
        conn = db.conn;
    }

    public String generateIdProduk() {
        String sql = "SELECT MAX(idProduk) FROM tbproduk";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next() && rs.getString(1) != null) {
                int nextId = Integer.parseInt(rs.getString(1).substring(1)) + 1;
                return "P" + String.format("%03d", nextId);
            }
        } catch (SQLException e) {
            System.out.println("Error generating ID Produk: " + e.getMessage());
        }
        return "P001";
    }

    public void buatProduk(String idPengguna, Scanner input) {
        String idProduk = generateIdProduk();

        System.out.print("Masukkan nama produk: ");
        String namaProduk = input.nextLine();

        Map<String, Integer> bahanMap = new LinkedHashMap<>();

        boolean tambah = true;
        while (tambah) {
            System.out.print("Masukkan ID Bahan: ");
            String idBahan = input.nextLine();

            System.out.print("Masukkan jumlah bahan yang digunakan: ");
            int jumlah = input.nextInt();
            input.nextLine();

            bahanMap.put(idBahan, bahanMap.getOrDefault(idBahan, 0) + jumlah);

            System.out.print("Tambah bahan lagi? (y/n): ");
            tambah = input.nextLine().equalsIgnoreCase("y");
        }

        System.out.print("Masukkan jumlah produk yang dihasilkan: ");
        int jumlahProduk = input.nextInt();
        input.nextLine();

        try {
            conn.setAutoCommit(false);

            // Simpan ke tbproduk
            String sqlProduk = "INSERT INTO tbproduk (idProduk, namaProduk, jumlah) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sqlProduk)) {
                stmt.setString(1, idProduk);
                stmt.setString(2, namaProduk);
                stmt.setInt(3, jumlahProduk);
                stmt.executeUpdate();
            }

            // Simpan ke tbdetail dan tbpemakaian
            for (Map.Entry<String, Integer> entry : bahanMap.entrySet()) {
                String idBahan = entry.getKey();
                int jumlah = entry.getValue();

                // tbdetail
                String checkDetail = "SELECT * FROM tbdetail WHERE idProduk = ? AND idBahan = ?";
                try (PreparedStatement check = conn.prepareStatement(checkDetail)) {
                    check.setString(1, idProduk);
                    check.setString(2, idBahan);
                    ResultSet rs = check.executeQuery();
                    if (rs.next()) {
                        // update jumlah
                        String updateDetail = "UPDATE tbdetail SET jumlah = jumlah + ? WHERE idProduk = ? AND idBahan = ?";
                        try (PreparedStatement update = conn.prepareStatement(updateDetail)) {
                            update.setInt(1, jumlah);
                            update.setString(2, idProduk);
                            update.setString(3, idBahan);
                            update.executeUpdate();
                        }
                    } else {
                        // insert baru
                        String insertDetail = "INSERT INTO tbdetail (idProduk, idBahan, jumlah) VALUES (?, ?, ?)";
                        try (PreparedStatement insert = conn.prepareStatement(insertDetail)) {
                            insert.setString(1, idProduk);
                            insert.setString(2, idBahan);
                            insert.setInt(3, jumlah);
                            insert.executeUpdate();
                        }
                    }
                }

                // tbpemakaian
                String checkPemakaian = "SELECT * FROM tbpemakaian WHERE idPengguna = ? AND idBahan = ? AND keterangan = ?";
                try (PreparedStatement check = conn.prepareStatement(checkPemakaian)) {
                    check.setString(1, idPengguna);
                    check.setString(2, idBahan);
                    check.setString(3, namaProduk);
                    ResultSet rs = check.executeQuery();
                    if (rs.next()) {
                        String updatePemakaian = "UPDATE tbpemakaian SET jumlah = jumlah + ? WHERE idPengguna = ? AND idBahan = ? AND keterangan = ?";
                        try (PreparedStatement update = conn.prepareStatement(updatePemakaian)) {
                            update.setInt(1, jumlah);
                            update.setString(2, idPengguna);
                            update.setString(3, idBahan);
                            update.setString(4, namaProduk);
                            update.executeUpdate();
                        }
                    } else {
                        String insertPemakaian = "INSERT INTO tbpemakaian (idPengguna, idBahan, jumlah, keterangan) VALUES (?, ?, ?, ?)";
                        try (PreparedStatement insert = conn.prepareStatement(insertPemakaian)) {
                            insert.setString(1, idPengguna);
                            insert.setString(2, idBahan);
                            insert.setInt(3, jumlah);
                            insert.setString(4, namaProduk);
                            insert.executeUpdate();
                        }
                    }
                }
            }

            conn.commit();
            System.out.println("Produk berhasil dibuat.");
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                System.err.println("Rollback gagal: " + ex.getMessage());
            }
            System.err.println("Gagal membuat produk: " + e.getMessage());
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println("Gagal mengaktifkan autoCommit: " + e.getMessage());
            }
        }
    }

    public void tampilkanProduk() {
        String sql = "SELECT * FROM tbproduk";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("=== Daftar Produk ===");
            while (rs.next()) {
                String idProduk = rs.getString("idProduk");
                String namaProduk = rs.getString("namaProduk");
                int jumlah = rs.getInt("jumlah");
                System.out.println("ID Produk: " + idProduk + ", Nama: " + namaProduk + ", Jumlah: " + jumlah);
            }
        } catch (SQLException e) {
            System.out.println("Gagal menampilkan produk: " + e.getMessage());
        }
    }

    public void ubahJumlahProduk(String idProduk, int jumlahBaru) {
        String sql = "UPDATE tbproduk SET jumlah = ? WHERE idProduk = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, jumlahBaru);
            stmt.setString(2, idProduk);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Jumlah produk berhasil diubah.");
            } else {
                System.out.println("Produk dengan ID " + idProduk + " tidak ditemukan.");
            }
        } catch (SQLException e) {
            System.out.println("Gagal mengubah jumlah produk: " + e.getMessage());
        }
    }

    public void hapusProduk(String idProduk) {
        String sql = "DELETE FROM tbproduk WHERE idProduk = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, idProduk);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Produk berhasil dihapus.");
            } else {
                System.out.println("Produk dengan ID " + idProduk + " tidak ditemukan.");
            }
        } catch (SQLException e) {
            System.out.println("Gagal menghapus produk: " + e.getMessage());
        }
    }
}
