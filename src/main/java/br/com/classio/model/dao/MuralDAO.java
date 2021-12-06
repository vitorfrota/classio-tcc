/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.classio.model.dao;

import br.com.classio.connection.ConnectionFactory;
import br.com.classio.model.beans.Mural;
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
public class MuralDAO {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = "";

    public void salvar(Mural m) { // MÉTODO PARA SALVAR MENSAGEM DO MURAL NO BD
        conn = ConnectionFactory.getConexao();
        sql = "insert into mural(muraviso,murdtpostagem,murprocodigo,murturcodigo) values(?,?,?,?)";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, m.getMuraviso());
            ps.setDate(2, new java.sql.Date(m.getMurdtpostagem().getTime()));
            ps.setInt(3, m.getMurprocodigo().getProcodigo());
            ps.setInt(4, m.getMurturcodigo().getTurcodigo());
            ps.execute();
        } catch (SQLException e) {
        } finally {
            try {
                ps.close();
                conn.close();

            } catch (SQLException e) {
            }
        }
    } // FIM DO MÉTODO SALVAR

    public void excluir(int codigo) { // MÉTODO PARA EXCLUIR MENSAGEM DO MURAL
        conn = ConnectionFactory.getConexao();
        sql = "DELETE FROM MURAL WHERE murcodigo = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, codigo);
            ps.execute();
        } catch (SQLException e) {
        } finally {
            try {
                conn.close();
                ps.close();
            } catch (SQLException e) {
            }
        }
    }

    public List<Mural> listarAvisos(int id) { // LISTA AS MENSAGENS DO MURAL
        conn = ConnectionFactory.getConexao();
        sql = "SELECT * from mural where murturcodigo = ? order by murcodigo desc";
        List<Mural> lista = new ArrayList<>();
        Mural m = null;
        TurmaDAO tdao = new TurmaDAO();
        ProfessorDAO pdao = new ProfessorDAO();
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            ps.execute();
            while (rs.next()) {
                m = new Mural();
                m.setMurcodigo(rs.getInt("murcodigo"));
                m.setMuraviso(rs.getString("muraviso"));
                m.setMurdtpostagem(rs.getDate("murdtpostagem"));
                m.setMurprocodigo(pdao.consultaId(rs.getInt("murprocodigo")));
                m.setMurturcodigo(tdao.consulta(rs.getInt("murturcodigo")));
                lista.add(m);
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
