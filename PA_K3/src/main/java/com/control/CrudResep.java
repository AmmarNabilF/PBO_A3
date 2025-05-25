/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.control;

import com.model.Resep;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ammar
 */
public class CrudResep extends Crud<Resep> {
    @Override
    protected String getInsertQuery() {
        return "INSERT INTO tbresep (idResep, idBahan, namaResep, jmlhDigunakan) VALUES (?, ?, ?, ?)";
    }

    @Override
    protected void setInsertParameters(PreparedStatement ps, Resep item) throws SQLException {
        ps.setString(1, item.getidResep());
        ps.setString(2, item.getidBahan());
        ps.setString(3, item.getnamaResep());
        ps.setInt(4, item.getjumlahDigunakan());
    }

    @Override
    protected String getSelectAllQuery() {
        return "SELECT * FROM tbresep";
    }

    @Override
    protected Resep mapResultSetToEntity(ResultSet rs) throws SQLException {
        return new Resep(rs.getString("idResep"), rs.getString("namaResep"), 
        rs.getString("idBahan"), rs.getInt("jmlhDigunakan"));
    }

    @Override
    protected String getDeleteQuery() {
        return "DELETE FROM tbresep WHERE idResep = ?";
    }

    @Override
    protected void setDeleteParameters(PreparedStatement ps, Resep item) throws SQLException {
        ps.setString(1, item.getidResep());
    }
}
