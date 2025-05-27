package com.control;

import com.model.Pemakaian;
import com.DB;

import java.sql.*;
import java.util.Map;

public class PemakaianControl {
    private Connection conn;

    public PemakaianControl() {
        DB db = new DB();
        conn = db.conn;
    }

    public void simpanPemakaian(String idPengguna, String namaProduk, Map<String, Integer> bahanMap) throws SQLException {
        for (Map.Entry<String, Integer> entry : bahanMap.entrySet()) {
            String idBahan = entry.getKey();
            int jumlah = entry.getValue();

            String checkSql = "SELECT * FROM tbpemakaian WHERE idBahan = ?";
            try (PreparedStatement check = conn.prepareStatement(checkSql)) {
                check.setString(1, idBahan);
                ResultSet rs = check.executeQuery();

                if (rs.next()) {
                    String updateSql = "UPDATE tbpemakaian SET jumlah = jumlah + ? WHERE idBahan = ?";
                    try (PreparedStatement update = conn.prepareStatement(updateSql)) {
                        update.setInt(1, jumlah);
                        update.setString(2, idBahan);
                        update.executeUpdate();
                    }
                } else {
                    String insertSql = "INSERT INTO tbpemakaian (idBahan, jumlah) VALUES (?, ?)";
                    try (PreparedStatement insert = conn.prepareStatement(insertSql)) {
                        insert.setString(1, idBahan);
                        insert.setInt(2, jumlah);
                        insert.executeUpdate();
                    }
                }
            }
        }
    }

    public java.util.List<Pemakaian> lihatRiwayatPemakaian() {
        String sql = """
            SELECT p.idBahan, b.namaBahan, p.jumlah
            FROM tbpemakaian p
            JOIN tbbahanbaku b ON p.idBahan = b.idBahan
            ORDER BY p.idBahan
            """;

        java.util.List<Pemakaian> riwayat = new java.util.ArrayList<>();
        String format = "| %-6s | %-25s | %-6s |\n";
        String line = "+--------+---------------------------+--------+";
        boolean found = false;
        int no = 1;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            System.out.println("Riwayat Pemakaian Bahan Baku:");
            System.out.println(line);
            System.out.printf(format, "No", "Nama Bahan", "Jumlah");
            System.out.println(line);

            while (rs.next()) {
                Pemakaian pemakaian = new Pemakaian(
                    rs.getString("idBahan"),
                    rs.getInt("jumlah")
                );
                pemakaian.setIdBahan(rs.getString("idBahan"));
                pemakaian.setNamaBahan(rs.getString("namaBahan"));
                pemakaian.setJumlah(rs.getInt("jumlah"));
                riwayat.add(pemakaian);

                System.out.printf(format,
                    no++,
                    pemakaian.getNamaBahan(),
                    pemakaian.getJumlah()
                );
                found = true;
            }
            System.out.println(line);

            if (!found) {
                System.out.println("Data riwayat pemakaian kosong.");
            }

        } catch (SQLException e) {
            System.err.println("Gagal menampilkan riwayat pemakaian: " + e.getMessage());
        }
        return riwayat;
    }
}
