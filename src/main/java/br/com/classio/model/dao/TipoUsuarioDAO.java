/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.classio.model.dao;

import br.com.classio.model.beans.TipoUsuario;
import br.com.classio.connection.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vitor_9g3eyo1
 */
public class TipoUsuarioDAO {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = "";

    public TipoUsuario consulta(int id) { // MÉTODO PARA RETONAR QUAL TIPO DE USUARIO É(ALUNO/PROFESSOR)
        TipoUsuario t = new TipoUsuario();
        conn = ConnectionFactory.getConexao();
        sql = "select * from tipousuario where tipcodigo = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                t.setTipcodigo(rs.getInt("tipcodigo"));
                t.setTipdescricao(rs.getString("tipdescricao"));
            }
        } catch (SQLException e) {
        } finally {
            try {
                conn.close();
                ps.close();
            } catch (SQLException e) {
            }
        }
        return t;
    } // FIM DO MÉTODO CONSULTA

    public TipoUsuario consultaNome(String nome) { // MÉTODO PARA RETONAR QUAL TIPO DE USUARIO É(ALUNO/PROFESSOR)
        TipoUsuario t = new TipoUsuario();
        conn = ConnectionFactory.getConexao();
        sql = "select * from tipousuario where tipdescricao = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, nome);
            rs = ps.executeQuery();
            while (rs.next()) {
                t.setTipcodigo(rs.getInt("tipcodigo"));
                t.setTipdescricao(rs.getString("tipdescricao"));
            }
        } catch (SQLException e) {
        } finally {
            try {
                conn.close();
                ps.close();
            } catch (SQLException e) {
            }
        }
        return t;
    } // FIM DO MÉTODO CONSULTA

    public List<TipoUsuario> listarTipos() { // MÉTODO PARA LISTAR TIPOS DE USUÁRIOS
        conn = ConnectionFactory.getConexao();
        sql = "SELECT * FROM tipousuario";
        List<TipoUsuario> lista = new ArrayList<>();
        TipoUsuario t = null;
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                t = new TipoUsuario();
                t.setTipcodigo(rs.getInt("tipcodigo"));
                t.setTipdescricao(rs.getString("tipdescricao"));
                lista.add(t);
            }
        } catch (SQLException e) {
        } finally {
            try {
                conn.close();
                ps.close();
            } catch (SQLException e) {
            }
        }
        return lista;
    }
}
