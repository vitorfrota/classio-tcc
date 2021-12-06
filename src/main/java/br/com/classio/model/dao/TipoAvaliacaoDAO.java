/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.classio.model.dao;

import br.com.classio.model.beans.TipoAvaliacao;
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
public class TipoAvaliacaoDAO {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = "";

    public TipoAvaliacao consultaNome(String nome) { // MÉTODO PARA consultar tipo de avaliação
        TipoAvaliacao t = new TipoAvaliacao();
        conn = ConnectionFactory.getConexao();
        sql = "select * from tipoavaliacao where tipdescricao = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, nome);
            ps.execute();
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

    public TipoAvaliacao consulta(int id) { // MÉTODO PARA consultar tipo de avaliação
        TipoAvaliacao t = new TipoAvaliacao();
        conn = ConnectionFactory.getConexao();
        sql = "select * from tipoavaliacao where tipcodigo = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
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

    public List<TipoAvaliacao> listarTipos() { // LISTAR TIPOS DE AVALIAÇÃO
        conn = ConnectionFactory.getConexao();
        sql = "SELECT * FROM tipoavaliacao";
        List<TipoAvaliacao> lista = new ArrayList<>();
        TipoAvaliacao t = null;
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                t = new TipoAvaliacao();
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
