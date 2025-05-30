package com.control;

import com.model.Pesan;
import com.DB;
import java.sql.*;
import java.time.LocalDate;

public class PesanControl {
    private Connection conn;

    public PesanControl() {
        DB db = new DB();
        conn = db.conn;
    }

    public boolean cekPasokanAda(){
        String sql = "SELECT COUNT(*) FROM tbpasokan";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("Gagal mengecek pasokan: " + e.getMessage());
        }
        return false;
    }

    public void tampilkanDaftarPasokan() {
        String sql = "SELECT p.idPasokan, p.namaBahan, p.hargaSatuan, p.stok, s.namaPemasok " +
                    "FROM tbpasokan p " +
                    "JOIN tbpemasok s ON p.idPemasok = s.idPemasok";
        boolean found = false;
        String format = "| %-12s | %-25s | %-13s | %-4s | %-20s |\n";
        String line   = "+--------------+---------------------------+---------------+------+----------------------+";

        try (Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("Daftar Pasokan Bahan Baku:");
            System.out.println(line);
            System.out.printf(format, "ID Pasokan", "Nama Bahan", "Harga Satuan", "Stok", "Nama Pemasok");
            System.out.println(line);

            while (rs.next()) {
                System.out.printf(format,
                    rs.getString("idPasokan"),
                    rs.getString("namaBahan"),
                    String.format("%.2f", rs.getDouble("hargaSatuan")),
                    rs.getInt("stok"),
                    rs.getString("namaPemasok")
                );
                found = true;
            }

            System.out.println(line);
            if (!found) {
                System.out.println("Data pasokan kosong.");
            }
        } catch (SQLException e) {
            System.out.println("Gagal menampilkan daftar pasokan: " + e.getMessage());
        }
    }

    public void buatPesanan(String idPesanan, String idPengguna, String idPasokan, int jumlahPesan) {
        String getPasokanSql = "SELECT namaBahan, hargaSatuan, stok, idPemasok FROM tbpasokan WHERE idPasokan = ?";
        try (PreparedStatement ps = conn.prepareStatement(getPasokanSql)) {
            ps.setString(1, idPasokan);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String namaBahan = rs.getString("namaBahan");
                double hargaSatuan = rs.getDouble("hargaSatuan");
                int stokTersedia = rs.getInt("stok");
                String idPemasok = rs.getString("idPemasok");
                if (jumlahPesan > stokTersedia) {
                    System.out.println("Stok tidak mencukupi! Stok tersedia: " + stokTersedia);
                    return;
                }
                double totalHarga = hargaSatuan * jumlahPesan;
                String tanggalSekarang = LocalDate.now().toString();

                String insertPesananSql = "INSERT INTO tbpesanan (idPesanan, idPengguna, idBahan, idPemasok, tanggalMasuk, jumlah, harga) VALUES (?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement insertStmt = conn.prepareStatement(insertPesananSql)) {
                    insertStmt.setString(1, idPesanan);
                    insertStmt.setString(2, idPengguna);
                    insertStmt.setString(3, idPasokan);
                    insertStmt.setString(4, idPemasok);
                    insertStmt.setString(5, tanggalSekarang);
                    insertStmt.setInt(6, jumlahPesan);
                    insertStmt.setDouble(7, totalHarga);
                    insertStmt.executeUpdate();
                    System.out.println("Pesanan berhasil dibuat.");
                }

                String updateStokSql = "UPDATE tbpasokan SET stok = stok - ? WHERE idPasokan = ?";
                try (PreparedStatement updateStmt = conn.prepareStatement(updateStokSql)) {
                    updateStmt.setInt(1, jumlahPesan);
                    updateStmt.setString(2, idPasokan);
                    updateStmt.executeUpdate();
                    System.out.println("Stok pasokan dikurangi sebanyak " + jumlahPesan);
                }

                BahanBakuControl bbControl = new BahanBakuControl();
                bbControl.tambahAtauUpdateBahanBaku(idPasokan, namaBahan, jumlahPesan);
            } else {
                System.out.println("ID Pasokan tidak ditemukan.");
            }
        } catch (SQLException e) {
            System.out.println("Gagal membuat pesanan: " + e.getMessage());
        }
    }

    public void tampilkanRiwayatPesanan(String idPengguna) {
        String sql = "SELECT p.idPesanan, p.idPengguna, p.idPemasok, p.idBahan, p.tanggalMasuk, p.jumlah, p.harga, " +
                    "b.namaBahan, s.namaPemasok " +
                    "FROM tbpesanan p " +
                    "JOIN tbbahanbaku b ON p.idBahan = b.idBahan " +
                    "JOIN tbpemasok s ON p.idPemasok = s.idPemasok " +
                    "WHERE p.idPengguna = ?";
        boolean found = false;
        String format = "| %-12s | %-20s | %-12s | %-6s | %-12s | %-20s |\n";
        String line = "+--------------+----------------------+--------------+--------+--------------+----------------------+";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, idPengguna);
            try (ResultSet rs = stmt.executeQuery()) {
                System.out.println("\nRiwayat Pesanan:");
                System.out.println(line);
                System.out.printf(format, "ID Pesanan", "Nama Bahan", "Tanggal", "Jumlah", "Harga", "Nama Pemasok");
                System.out.println(line);

                while (rs.next()) {
                    Pesan pesan = new Pesan(
                            rs.getString("idPesanan"),
                            rs.getString("idPengguna"),
                            rs.getString("idPemasok"),
                            rs.getString("idBahan"),
                            rs.getDate("tanggalMasuk").toLocalDate(),
                            rs.getInt("jumlah"),
                            rs.getDouble("harga"),
                            rs.getString("namaBahan"),
                            rs.getString("namaPemasok")
                    );
                    System.out.printf(format,
                            pesan.getIdPesanan(),
                            pesan.getNamaBahan(),
                            pesan.getTanggalMasuk(),
                            pesan.getJumlah(),
                            String.format("%.2f", pesan.getHarga()),
                            pesan.getNamaPemasok()
                    );
                    found = true;
                }
                System.out.println(line);
                if (!found) {
                    System.out.println("Data riwayat pesanan kosong.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Gagal menampilkan daftar pesanan: " + e.getMessage());
        }
    }

    public String generateIdPesanan() throws SQLException {
        String query = "SELECT COUNT(*) AS count FROM tbpesanan";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt("count");
                return "PS" + String.format("%02d", count + 1);
            }
        } catch (SQLException e) {
            System.err.println("Error generating ID: " + e.getMessage());
            throw e; 
        }
        return null;
    }
}
