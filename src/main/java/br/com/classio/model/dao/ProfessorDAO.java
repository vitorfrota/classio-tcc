/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.classio.model.dao;

import br.com.classio.model.beans.Professor;
import br.com.classio.connection.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author vitor_9g3eyo1
 */
public class ProfessorDAO {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = "";

    public void salvar(Professor professor) { // MÉTODO PARA SALVAR PROFESSOR NO BD
        conn = ConnectionFactory.getConexao();
        sql = "insert into professor(proemail,prousucodigo) values (?,?)";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, professor.getProemail());
            ps.setInt(2, professor.getProusucodigo().getUsucodigo());
            ps.execute();
        } catch (SQLException e) {
        } finally {
            try {
                conn.close();
                ps.close();
            } catch (SQLException e) {
            }
        }

    } // FIM DO MÉTODO SALVAR

    public Professor consulta(int id) { // MÉTODO PARA CONSULTAR PROFESSOR DE ACORDO COM O CÓDIGO DO USUÁRIO
        conn = ConnectionFactory.getConexao();
        sql = "SELECT * FROM professor where prousucodigo = ?";
        Professor p = null;
        UsuarioDAO udao = new UsuarioDAO();
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
            rs = ps.executeQuery();
            if (rs.next()) {
                p = new Professor();
                p.setProcodigo(rs.getInt("procodigo"));
                p.setProemail(rs.getString("proemail"));
                p.setProusucodigo(udao.consulta(rs.getString("prousulogin")));
            }
        } catch (SQLException e) {
        } finally {
            try {
                conn.close();
                ps.close();
            } catch (SQLException e) {
            }
        }
        return p;
    }

    public Professor consultaId(int id) { // MÉTODO PARA CONSULTAR PROFESSOR DE ACORDO COM O SEU CÓDIGO
        conn = ConnectionFactory.getConexao();
        sql = "SELECT * FROM professor where procodigo = ?";
        Professor p = null;
        UsuarioDAO udao = new UsuarioDAO();
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
            rs = ps.executeQuery();
            if (rs.next()) {
                p = new Professor();
                p.setProcodigo(rs.getInt("procodigo"));
                p.setProemail(rs.getString("proemail"));
                p.setProusucodigo(udao.consultaId(rs.getInt("prousucodigo")));
            }
        } catch (SQLException e) {
        } finally {
            try {
                conn.close();
                ps.close();
            } catch (SQLException e) {
            }
        }
        return p;
    }

    public void alterar(Professor p) { // MÉTODO PARA ALTERAR AS INFORMAÇÕES DO PROFESSOR
        conn = ConnectionFactory.getConexao();
        String sql = "UPDATE professor SET proemail = ? WHERE prousucodigo = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, p.getProemail());
            ps.setInt(2, p.getProusucodigo().getUsucodigo());
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

    public Professor RetornaIdProfessor(String user) { // MÉTODO PARA RETORNAR PROFESSOR DE ACORDO COM O SEU USUÁRIO(LOGIN)
        conn = ConnectionFactory.getConexao();
        sql = "select procodigo, proemail, prousucodigo from usuario u inner join professor as p on u.usucodigo = p.prousucodigo where u.usulogin = ?";
        Professor p = null;
        UsuarioDAO udao = new UsuarioDAO();
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, user);
            rs = ps.executeQuery();

            if (rs.next()) {
                p = new Professor();
                p.setProcodigo(rs.getInt("procodigo"));
                p.setProemail(rs.getString("proemail"));
                p.setProusucodigo(udao.consultaId(rs.getInt("prousucodigo")));
            }
        } catch (SQLException e) {
        } finally {
            try {
                conn.close();
                ps.close();
            } catch (SQLException e) {
            }
        }
        return p;
    }
}
