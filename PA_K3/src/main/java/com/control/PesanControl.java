package com.control;

import com.model.Pesan;
import com.model.BahanBaku;
import com.auth.*;
import com.DB;
import java.sql.*;
import java.time.LocalDate;

public class PesanControl {
    private Connection conn;

    public PesanControl() {
        DB db = new DB();
        conn = db.conn;
    }

    public void tampilkanDaftarPasokan() {
        String sql = "SELECT p.idPasokan, p.namaBahan, p.hargaSatuan, p.stok, s.namaPemasok " +
                     "FROM tbpasokan p " +
                     "JOIN tbpemasok s ON p.idPemasok = s.idPemasok";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("Daftar Bahan Baku Tersedia:");
            while (rs.next()) {
                System.out.println(
                        "ID: " + rs.getString("idPasokan") +
                        ", Nama: " + rs.getString("namaBahan") +
                        ", Harga Satuan: " + rs.getDouble("hargaSatuan") +
                        ", Stok: " + rs.getInt("stok") +
                        ", Pemasok: " + rs.getString("namaPemasok")
                );
            }
        } catch (SQLException e) {
            System.out.println("Gagal menampilkan daftar pasokan: " + e.getMessage());
        }
    }

    public void buatPesanan(String idPesanan, String idPengguna, String idPasokan, int jumlahPesan) {
        // Ambil data pasokan beserta idPemasok
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

                // Simpan ke tbpesanan
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

                // Kurangi stok pasokan
                String updateStokSql = "UPDATE tbpasokan SET stok = stok - ? WHERE idPasokan = ?";
                try (PreparedStatement updateStmt = conn.prepareStatement(updateStokSql)) {
                    updateStmt.setInt(1, jumlahPesan);
                    updateStmt.setString(2, idPasokan);
                    updateStmt.executeUpdate();
                    System.out.println("Stok pasokan dikurangi sebanyak " + jumlahPesan);
                }

                // Tambahkan ke bahan baku
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
        String sql = "SELECT * FROM tbpesanan WHERE idPengguna = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, idPengguna);
            try (ResultSet rs = stmt.executeQuery()) {
                System.out.println("==================================================================");
                System.out.printf("| %-10s | %-10s | %-12s | %-6s | %-12s |\n", 
                                  "ID Pesanan", "ID Bahan", "Tanggal", "Jumlah", "Total Harga");
                System.out.println("==================================================================");
                while (rs.next()) {
                    Pesan pesan = new Pesan(
                        rs.getString("idPesanan"),
                        rs.getString("idPengguna"),
                        rs.getString("idBahan"),
                        rs.getString("idPemasok"),
                        rs.getDate("tanggalMasuk").toLocalDate(),
                        rs.getInt("jumlah"),
                        rs.getDouble("harga")
                    );
                    System.out.printf("| %-10s | %-10s | %-12s | %-6d | %-12.2f |\n",
                        pesan.getIdPesanan(),
                        pesan.getIdBahan(),
                        pesan.getTanggalMasuk(),
                        pesan.getJumlah(),
                        pesan.getHarga()
                    );
                }
                System.out.println("==================================================================");
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
