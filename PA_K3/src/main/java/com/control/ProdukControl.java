package com.control;

import com.model.Produk;
import java.sql.*;
import java.util.*;

import com.DB;

public class ProdukControl {
    private Connection conn;
    BahanBakuControl bahanControl = new BahanBakuControl();

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

        String namaProduk;
        do {
            System.out.print("Masukkan nama produk: ");
            namaProduk = input.nextLine();
            if (namaProduk.trim().isEmpty() || !namaProduk.matches("[a-zA-Z\\s]+")) {
                System.out.println("Nama bahan tidak sesuai. Silakan coba lagi.");
            }
            } while (namaProduk.trim().isEmpty() || !namaProduk.matches("[a-zA-Z\\s]+"));

        Map<String, Integer> bahanMap = new LinkedHashMap<>();

        boolean tambah = true;
        while (tambah) {
            bahanControl.tampilkanBahanBaku();
            System.out.print("Masukkan ID Bahan: ");
            String idBahan = input.nextLine().trim();
            if (idBahan.trim().isEmpty()) {
                System.out.println("ID Bahan tidak boleh kosong.");
                continue;
            }

            int jumlah = -1;
            String inputStr;
            do {
                System.out.print("Masukkan jumlah bahan yang digunakan: ");
                inputStr = input.nextLine().trim();
            
                if (inputStr.isEmpty()) {
                    System.out.println("Input tidak boleh kosong. Silakan coba lagi.");
                    continue;
                }
            
                if (inputStr.matches("\\d+")) { 
                    jumlah = Integer.parseInt(inputStr);
                    if (jumlah < 0) {
                        System.out.println("Stok tidak boleh negatif.");
                    }
                } else {
                    System.out.println("Input tidak valid. Masukkan angka bulat (misal: 10).");
                }
            } while (jumlah < 0);

            bahanMap.put(idBahan, bahanMap.getOrDefault(idBahan, 0) + jumlah);

            String lanjut;
            do {
                System.out.print("Tambah bahan lagi? (y/n): ");
                lanjut = input.nextLine().trim();
                if (lanjut.isEmpty()) {
                    System.out.println("Input tidak boleh kosong. Silakan masukkan 'y' atau 'n'.");
                } else if (!lanjut.equalsIgnoreCase("y") && !lanjut.equalsIgnoreCase("n")) {
                    System.out.println("Input tidak valid. Masukkan hanya 'y' untuk ya atau 'n' untuk tidak.");
                }
            } while (!lanjut.equalsIgnoreCase("y") && !lanjut.equalsIgnoreCase("n"));
            tambah = lanjut.equalsIgnoreCase("y");
        }

        if (bahanMap.isEmpty()) {
            System.out.println("Tidak ada bahan yang dimasukkan. Gagal membuat produk.");
            return;
        }
        int jumlahProduk = -1;
        String inputStr;
        do {
        System.out.print("Masukkan jumlah produk yang dihasilkan: ");
        inputStr = input.nextLine().trim();
    
        if (inputStr.isEmpty()) {
            System.out.println("Input tidak boleh kosong. Silakan coba lagi.");
            continue;
        }
        if (inputStr.matches("\\d+")) { 
            jumlahProduk = Integer.parseInt(inputStr);
            if (jumlahProduk < 0) {
                System.out.println("Stok tidak boleh negatif.");
            }
        } else {
            System.out.println("Input tidak valid. Masukkan angka bulat (misal: 10).");
        }
        } while (jumlahProduk <= 0);

        try {
            conn.setAutoCommit(false);

            String sqlProduk = "INSERT INTO tbproduk (idProduk, namaProduk, jumlah) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sqlProduk)) {
                stmt.setString(1, idProduk);
                stmt.setString(2, namaProduk);
                stmt.setInt(3, jumlahProduk);
                stmt.executeUpdate();
            }

            PemakaianControl pemakaianControl = new PemakaianControl();
            for (Map.Entry<String, Integer> entry : bahanMap.entrySet()) {
                String idBahan = entry.getKey();
                int jumlah = entry.getValue();

                String checkDetail = "SELECT * FROM tbdetail WHERE idProduk = ? AND idBahan = ?";
                try (PreparedStatement check = conn.prepareStatement(checkDetail)) {
                    check.setString(1, idProduk);
                    check.setString(2, idBahan);
                    ResultSet rs = check.executeQuery();
                    if (rs.next()) {
                        String updateDetail = "UPDATE tbdetail SET jumlah = jumlah + ? WHERE idProduk = ? AND idBahan = ?";
                        try (PreparedStatement update = conn.prepareStatement(updateDetail)) {
                            update.setInt(1, jumlah);
                            update.setString(2, idProduk);
                            update.setString(3, idBahan);
                            update.executeUpdate();
                        }
                    } else {
                        String insertDetail = "INSERT INTO tbdetail (idProduk, idBahan, jumlah) VALUES (?, ?, ?)";
                        try (PreparedStatement insert = conn.prepareStatement(insertDetail)) {
                            insert.setString(1, idProduk);
                            insert.setString(2, idBahan);
                            insert.setInt(3, jumlah);
                            insert.executeUpdate();
                        }
                    }
                }
                // Kurangi jumlah bahan dari tbbahanbaku
                String updateStok = "UPDATE tbbahanbaku SET stok = stok - ? WHERE idBahan = ?";
                try (PreparedStatement updateStokStmt = conn.prepareStatement(updateStok)) {
                    updateStokStmt.setInt(1, jumlah);
                    updateStokStmt.setString(2, idBahan);
                    updateStokStmt.executeUpdate();
                }
            }

            // Simpan ke tbpemakaian
            pemakaianControl.simpanPemakaian(idPengguna, namaProduk, bahanMap);

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
        boolean found = false;
        String format = "| %-10s | %-25s | %-6s |\n";
        String line = "+------------+---------------------------+--------+";
        try (Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("\nDaftar Produk:");
            System.out.println(line);
            System.out.printf(format, "ID Produk", "Nama Produk", "Jumlah");
            System.out.println(line);
            while (rs.next()) {
                Produk produk = new Produk(
                    rs.getString("idProduk"),
                    rs.getString("namaProduk"),
                    rs.getInt("jumlah")
                );
                System.out.printf(format,
                    produk.getIdProduk(),
                    produk.getNamaProduk(),
                    produk.getJumlah()
                );
                found = true;
            }
            System.out.println(line);
            if (!found) {
                System.out.println("Data produk kosong.");
            }
        } catch (SQLException e) {
            System.out.println("Gagal menampilkan produk: " + e.getMessage());
        }
    }

    public boolean cekIdProduk(String idProduk) {
        String sql = "SELECT * FROM tbproduk WHERE idProduk = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, idProduk);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println("Gagal mengecek ID produk: " + e.getMessage());
            return false;
        }
    }

    public boolean cekIsiProduk() {
        String sql = "SELECT COUNT(*) FROM tbproduk";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("Gagal mengecek isi produk: " + e.getMessage());
        }
        return false;
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