package com;
import java.sql.*;

public class DB {
    public Connection conn;
    public DB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(
            "jdbc:mysql://localhost:3307/dbumkm", "root", "");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}   
