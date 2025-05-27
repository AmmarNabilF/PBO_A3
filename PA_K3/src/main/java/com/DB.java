package com;
import java.sql.*;

public final class DB {
    public Connection conn;
    public DB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/dbumkm", "root", "");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}   
