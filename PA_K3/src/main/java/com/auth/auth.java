package com.auth;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.model.Pengguna;
import com.model.Pemasok;

public class auth {
    private Connection conn;
    private static String currentUserId;

    public auth(Connection conn) {
        this.conn = conn;
    }

    public static String getCurrentUserId() {
        return currentUserId;
    }

    public void logout() {
        currentUserId = null;
    }

    private boolean isPhoneNumberExists(String nomorTelepon) throws SQLException {
        String checkPemasok = "SELECT 1 FROM tbpemasok WHERE nomorTelepon = ?";
        String checkPengguna = "SELECT 1 FROM tbpengguna WHERE nomorTelepon = ?";

        try (
            PreparedStatement stmtPemasok = conn.prepareStatement(checkPemasok);
            PreparedStatement stmtPengguna = conn.prepareStatement(checkPengguna)
        ) {
            stmtPemasok.setString(1, nomorTelepon);
            ResultSet rs1 = stmtPemasok.executeQuery();
            if (rs1.next()) return true;

            stmtPengguna.setString(1, nomorTelepon);
            ResultSet rs2 = stmtPengguna.executeQuery();
            if (rs2.next()) return true;
        }

        return false;
    }

    public void register(Pemasok pemasok) throws SQLException {
        if (isPhoneNumberExists(pemasok.getNomorTelepon())) {
            throw new SQLException("duplikat");
        }
        String query = "INSERT INTO tbpemasok (idPemasok, namaPemasok, nomorTelepon, password) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, pemasok.getIdPemasok());
            stmt.setString(2, pemasok.getNamaPemasok());
            stmt.setString(3, pemasok.getNomorTelepon());
            stmt.setString(4, pemasok.getPassword());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error during registration: " + e.getMessage());
            throw e; 
        }
    }

    public void register(Pengguna pengguna) throws SQLException {
        if (isPhoneNumberExists(pengguna.getNomorTelepon())) {
            throw new SQLException("duplikat");
        }
        String query = "INSERT INTO tbpengguna (idPengguna, namaPengguna, nomorTelepon, password) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, pengguna.getIdPengguna());
            stmt.setString(2, pengguna.getNamaPengguna());
            stmt.setString(3, pengguna.getNomorTelepon());
            stmt.setString(4, pengguna.getPassword());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error during registration: " + e.getMessage());
            throw e; 
        }
    }

    public void registerPengguna(String idPengguna, String namaPengguna, String nomorTelepon, String password) throws SQLException {
        Pengguna pengguna = new Pengguna(idPengguna, namaPengguna, nomorTelepon, password);
        register(pengguna);
    }

    public void registerPemasok(String idPemasok, String namaPemasok, String nomorTelepon, String password) throws SQLException {
        Pemasok pemasok = new Pemasok(idPemasok, namaPemasok, nomorTelepon, password);
        register(pemasok);
    }
    
    public String generateIdPemasok() throws SQLException {
        String query = "SELECT COUNT(*) AS count FROM tbpemasok";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt("count");
                return "PM" + String.format("%02d", count + 1);
            }
        } catch (SQLException e) {
            System.err.println("Error generating ID: " + e.getMessage());
            throw e; 
        }
        return null;
    }
    
    public String generateIdPengguna() throws SQLException {
        String query = "SELECT COUNT(*) AS count FROM tbpengguna";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt("count");
                return "PG" + String.format("%02d", count + 1);
            }
        } catch (SQLException e) {
            System.err.println("Error generating ID: " + e.getMessage());
            throw e; 
        }
        return null;
    }

    public Object login(String nomorTelepon, String password) throws SQLException {
        String queryPemasok = "SELECT * FROM tbpemasok WHERE nomorTelepon = ? AND password = ?";
        try (PreparedStatement stmt = conn.prepareStatement(queryPemasok)) {
            stmt.setString(1, nomorTelepon);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Pemasok pemasok = new Pemasok(
                    rs.getString("idPemasok"),
                    rs.getString("namaPemasok"),
                    rs.getString("nomorTelepon"),
                    rs.getString("password")
                );
                currentUserId = pemasok.getIdPemasok();
                return pemasok;
            }
        }

        String queryPengguna = "SELECT * FROM tbpengguna WHERE nomorTelepon = ? AND password = ?";
        try (PreparedStatement stmt = conn.prepareStatement(queryPengguna)) {
            stmt.setString(1, nomorTelepon);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Pengguna pengguna = new Pengguna(
                    rs.getString("idPengguna"),
                    rs.getString("namaPengguna"),
                    rs.getString("nomorTelepon"),
                    rs.getString("password")
                );
                currentUserId = pengguna.getIdPengguna();
                return pengguna;
            }
        }
        return null;
    }
}
