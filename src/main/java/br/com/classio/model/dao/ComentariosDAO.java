/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.classio.model.dao;

import br.com.classio.connection.ConnectionFactory;
import br.com.classio.model.beans.Comentarios;
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
public class ComentariosDAO {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = "";

    public void salvar(Comentarios c) { // MÉTODO PARA SALVAR MENSAGEM DO MURAL NO BD
        conn = ConnectionFactory.getConexao();
        sql = "insert into comentarios(commensagem,comdtpostagem,comalucodigo,comavacodigo) values(?,?,?,?)";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, c.getCommensagem());
            ps.setDate(2, new java.sql.Date(c.getComdtpostagem().getTime()));
            ps.setInt(3, c.getComalucodigo().getAlucodigo());
            ps.setInt(4, c.getComavacodigo().getAvacodigo());
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

    public List<Comentarios> listarComentarios(int id) { // LISTA AS MENSAGENS DO MURAL
        conn = ConnectionFactory.getConexao();
        sql = "SELECT * from comentarios where comavacodigo = ? order by comcodigo desc";
        List<Comentarios> lista = new ArrayList<>();
        Comentarios c = null;
        AvaliacaoDAO adao = new AvaliacaoDAO();
        AlunoDAO aldao = new AlunoDAO();
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            ps.execute();
            while (rs.next()) {
                c = new Comentarios();
                c.setComcodigo(rs.getInt("comcodigo"));
                c.setCommensagem(rs.getString("commensagem"));
                c.setComdtpostagem(rs.getDate("comdtpostagem"));
                c.setComavacodigo(adao.consultaId(rs.getInt("comavacodigo")));
                c.setComalucodigo(aldao.consultaIdAluno(rs.getInt("comalucodigo")));
                lista.add(c);
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
