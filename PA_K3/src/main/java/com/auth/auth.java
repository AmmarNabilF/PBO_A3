/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.auth;
import com.model.Pengguna;

/**
 *
 * @author ammar
 */
public class auth {
<<<<<<< Updated upstream
    private static Pengguna akun = new Pengguna("006","Ammar","006@mail.com","2309106006");
=======
    private Connection conn;

    public auth(Connection conn) {
        this.conn = conn;
    }

    public void register(Pemasok pemasok) throws SQLException {
        String query = "INSERT INTO tbpemasok (idPemasok, namaPemasok, alamat, nomorTelepon, password) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, pemasok.getIdPemasok());
            stmt.setString(2, pemasok.getNamaPemasok());
            stmt.setString(3, pemasok.getAlamat());
            stmt.setInt(4, pemasok.getNomorTelepon());
            stmt.setString(5, pemasok.getPassword());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error during registration: " + e.getMessage());
            throw e; // Rethrow the exception for further handling
        }
    }

    // ambildata akun pemasok berdasarkan idPemasok
    public Pemasok getAkun(String idPemasok) throws SQLException {
        String query = "SELECT * FROM tbpemasok WHERE idPemasok = ?";
        Pemasok pemasok = null;

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, idPemasok);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                pemasok = new Pemasok(
                    rs.getString("idPemasok"),
                    rs.getString("namaPemasok"),
                    rs.getString("alamat"),
                    rs.getInt("nomorTelepon"),
                    rs.getString("password")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error fetching account: " + e.getMessage());
            throw e; // Rethrow the exception for further handling
        }

        return pemasok;
    }

    public void registerPemasok(String idPemasok, String namaPemasok, String alamat, int nomorTelepon, String password) throws SQLException {
        Pemasok pemasok = new Pemasok(idPemasok, namaPemasok, alamat, nomorTelepon, password);
        register(pemasok);
    }

    public Object login(int nomorTelepon, String password) throws SQLException {
        // Coba login sebagai Pemasok
        String queryPemasok = "SELECT * FROM tbpemasok WHERE nomorTelepon = ? AND password = ?";
        try (PreparedStatement stmt = conn.prepareStatement(queryPemasok)) {
            stmt.setInt(1, nomorTelepon);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Pemasok(
                    rs.getString("idPemasok"),
                    rs.getString("namaPemasok"),
                    rs.getString("alamat"),
                    rs.getInt("nomorTelepon"),
                    rs.getString("password")
                );
            }
        }

        // Jika tidak ditemukan di pemasok, coba login sebagai Pengguna
        String queryPengguna = "SELECT * FROM tbpengguna WHERE nomorTelepon = ? AND password = ?";
        try (PreparedStatement stmt = conn.prepareStatement(queryPengguna)) {
            stmt.setInt(1, nomorTelepon);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Pengguna(
                    rs.getString("idPengguna"),
                    rs.getString("namaPengguna"),
                    rs.getString("nomorTelepon"),
                    rs.getString("password")
                );
            }
        }
        // Jika tidak ditemukan di kedua tabel
        return null;
    }
>>>>>>> Stashed changes
    
    public static boolean signIn(String email, String password){
        if (email.equals(akun.getemail()) && password.equals(akun.getpassword())){
            return true;
        }
        else{
            System.out.println("email atau password tidak tepat!!");
        }
    return false;
    }
    public static String getAdminData(){
        return "\n>> SELAMAT DATANG <<\n" + "( " + akun.getidUser() + " )" + akun.getnama();
    }
}
