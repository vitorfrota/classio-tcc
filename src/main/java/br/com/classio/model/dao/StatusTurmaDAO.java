/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.classio.model.dao;

import br.com.classio.model.beans.StatusTurma;
import br.com.classio.connection.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author vitor_9g3eyo1
 */
public class StatusTurmaDAO {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = "";

    public StatusTurma consulta(int id) { // MÉTODO PARA CONSULTAR STATUSTURMA
        StatusTurma es = new StatusTurma();
        conn = ConnectionFactory.getConexao();
        sql = "select * from statusturma where stacodigo = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
            rs = ps.executeQuery();
            while (rs.next()) {
                es.setStacodigo(rs.getInt("stacodigo"));
                es.setStadescricao(rs.getString("stadescricao"));
            }
        } catch (SQLException e) {
        } finally {
            try {
                conn.close();
                ps.close();
            } catch (SQLException e) {
            }
        }
        return es;
    }
} // FIM DO MÉTODO CONSULTA

