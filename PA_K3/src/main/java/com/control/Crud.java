package com.control;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;

/**
 *
 * @author ammar
 */
public abstract class Crud<T> implements CrudInterface<T> {
    protected Connection conn;

    public Crud() {
        try {
            // Ganti URL, username, dan password sesuai database Anda
            String url = "jdbc:mysql://localhost:3307/dbumkm"; //jngn lupa ganti localhost:3306 ke localhost:3307
            String username = "root";
            String password = "";
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            // Bisa lempar exception atau handle sesuai kebutuhan
        }
    }

    public Connection getConnection() {
        return conn;
    }

    // Abstract methods for subclasses to implement database logic
    protected abstract String getInsertQuery();
    protected abstract void setInsertParameters(PreparedStatement ps, T item) throws SQLException;
    protected abstract String getSelectAllQuery();
    protected abstract T mapResultSetToEntity(ResultSet rs) throws SQLException;
    protected abstract String getDeleteQuery();
    protected abstract void setDeleteParameters(PreparedStatement ps, T item) throws SQLException;

    @Override
    public void tambah(T item) {
        try (PreparedStatement ps = conn.prepareStatement(getInsertQuery())) {
            setInsertParameters(ps, item);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void show() {
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(getSelectAllQuery())) {
            boolean empty = true;
            while (rs.next()) {
                T item = mapResultSetToEntity(rs);
                System.out.println(item);
                empty = false;
            }
            if (empty) {
                System.out.println("\nItem kosong");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean hapus(T item) {
        try (PreparedStatement ps = conn.prepareStatement(getDeleteQuery())) {
            setDeleteParameters(ps, item);
            int affected = ps.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
