// package com.control;

// import com.model.Pesan;
// import com.model.Pasokan;
// import java.sql.*;
// import java.util.*;

// public class PesanControl {
//     private Connection conn;

//     public PesanControl(Connection conn) {
//         this.conn = conn;
//     }

//     public List<Pasokan> getDaftarPasokan() throws SQLException {
//         List<Pasokan> daftar = new ArrayList<>();
//         String sql = "SELECT * FROM tbpasokan";
//         Statement stmt = conn.createStatement();
//         ResultSet rs = stmt.executeQuery(sql);
//         while (rs.next()) {
//             Pasokan p = new Pasokan(
//                 rs.getInt("idPasokan"),
//                 rs.getString("namaPasokan"),
//                 rs.getInt("stok")
//             );
//             daftar.add(p);
//         }
//         rs.close();
//         stmt.close();
//         return daftar;
//     }

//     // 2 & 3. Memvalidasi idPasokan
//     public boolean isValidIdPasokan(int idPasokan) throws SQLException {
//         String sql = "SELECT COUNT(*) FROM tbpasokan WHERE idPasokan = ?";
//         PreparedStatement ps = conn.prepareStatement(sql);
//         ps.setInt(1, idPasokan);
//         ResultSet rs = ps.executeQuery();
//         rs.next();
//         boolean valid = rs.getInt(1) > 0;
//         rs.close();
//         ps.close();
//         return valid;
//     }

//     // 4. Memvalidasi stok (misal: stok > 0)
//     public boolean isValidStok(int stok) {
//         return stok > 0;
//     }

//     // 5. Menyimpan pesanan ke tbpesanan
//     public void simpanPesanan(Pesan pesan) throws SQLException {
//         String sql = "INSERT INTO tbpesanan (idPasokan, jumlah) VALUES (?, ?)";
//         PreparedStatement ps = conn.prepareStatement(sql);
//         ps.setInt(1, pesan.getIdPasokan());
//         ps.setInt(2, pesan.getJumlah());
//         ps.executeUpdate();
//         ps.close();
//     }
// }
