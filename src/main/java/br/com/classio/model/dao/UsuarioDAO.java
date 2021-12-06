/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.classio.model.dao;

import br.com.classio.model.beans.Usuario;
import br.com.classio.connection.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author vitor_9g3eyo1
 */
public class UsuarioDAO {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = "";

    public void salvar(Usuario usuario) { // MÉTODO PARA SALVAR USUARIO NO BD
        conn = ConnectionFactory.getConexao();
        sql = "insert into usuario(usulogin,usunome,ususenha,usutipcodigo) values (?,?,?,?)";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, usuario.getUsulogin());
            ps.setString(2, usuario.getUsunome());
            ps.setString(3, usuario.getUsusenha());
            ps.setInt(4, usuario.getUsutipcodigo().getTipcodigo());
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

    public void alterarSenha(Usuario usuario) { // MÉTODO PARA ALTERAR SENHA DO USUARIO 
        conn = ConnectionFactory.getConexao();
        sql = "UPDATE usuario SET ususenha = ? WHERE usulogin = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, usuario.getUsusenha());
            ps.setString(2, usuario.getUsulogin());
            ps.execute();
        } catch (SQLException e) {
        } finally {
            try {
                conn.close();
                ps.close();
            } catch (SQLException e) {
            }
        }
    } // FIM DO MÉTODO ALTERARSENHA

    public void alterar(Usuario usuario) { // MÉTODO PARA ALTERAR INFORMAÇÕES DO USUARIO 
        conn = ConnectionFactory.getConexao();
        sql = "UPDATE usuario SET usunome = ?, usulogin = ? WHERE usucodigo = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, usuario.getUsunome());
            ps.setString(2, usuario.getUsulogin());
            ps.setInt(3, usuario.getUsucodigo());
            ps.execute();
        } catch (SQLException e) {
        } finally {
            try {
                conn.close();
                ps.close();
            } catch (SQLException e) {
            }
        }
    } // FIM DO MÉTODO ALTERARSENHA

    public Usuario consulta(String usuario) { // MÉTODO PARA CONSULTAR USUÁRIO
        Usuario u = new Usuario();
        TipoUsuarioDAO tdao = new TipoUsuarioDAO();
        conn = ConnectionFactory.getConexao();
        sql = "select * from usuario where usulogin = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, usuario);
            rs = ps.executeQuery();
            ps.execute();
            while (rs.next()) {
                u.setUsucodigo(rs.getInt("usucodigo"));
                u.setUsunome(rs.getString("usunome"));
                u.setUsulogin(rs.getString("usulogin"));
                u.setUsusenha(rs.getString("ususenha"));
                u.setUsutipcodigo(tdao.consulta(rs.getInt("usutipcodigo")));
            }
        } catch (SQLException e) {
        } finally {
            try {
                conn.close();
                ps.close();
            } catch (SQLException e) {
            }
        }
        return u;
    } // FIM DO MÉTODO CONSULTA

    public Usuario consultaId(int id) { // CONSULTA ATRAVÉS DO CÓDIGO
        Usuario u = new Usuario();
        TipoUsuarioDAO tdao = new TipoUsuarioDAO();
        conn = ConnectionFactory.getConexao();
        sql = "select * from usuario where usucodigo = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            ps.execute();
            if (rs.next()) {
                u.setUsucodigo(rs.getInt("usucodigo"));
                u.setUsunome(rs.getString("usunome"));
                u.setUsulogin(rs.getString("usulogin"));
                u.setUsusenha(rs.getString("ususenha"));
                u.setUsutipcodigo(tdao.consulta(rs.getInt("usutipcodigo")));
            }
        } catch (SQLException e) {
        } finally {
            try {
                conn.close();
                ps.close();
            } catch (SQLException e) {
            }
        }
        return u;
    } // FIM DO MÉTODO CONSULTA

    public Usuario retornaUltimoRegistro() { // RETORNA O ULTIMO USUARIO REGISTRADO
        conn = ConnectionFactory.getConexao();
        sql = "select * from usuario ORDER BY USUCODIGO DESC LIMIT 1";
        Usuario u = new Usuario();
        TipoUsuarioDAO tdao = new TipoUsuarioDAO();
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                u.setUsucodigo(rs.getInt("usucodigo"));
                u.setUsulogin(rs.getString("usunome"));
                u.setUsulogin(rs.getString("usulogin"));
                u.setUsusenha(rs.getString("ususenha"));
                u.setUsutipcodigo(tdao.consulta(rs.getInt("usutipcodigo")));
            }
        } catch (SQLException e) {
        } finally {
            try {
                conn.close();
                ps.close();
            } catch (SQLException e) {
            }
        }
        return u;
    }

    public int verificaUsuario(String usuario) { // MÉTODO PARA RETONAR QUAL TIPO DE USUARIO É(ALUNO/PROFESSOR)
        int resultado = 0;
        conn = ConnectionFactory.getConexao();
        sql = "select usucodigo from usuario where usulogin = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, usuario);
            ps.execute();
            rs = ps.executeQuery();
            if (rs.next()) {
                resultado = rs.getInt("usucodigo");
            }
        } catch (SQLException e) {
        } finally {
            try {
                conn.close();
                ps.close();
            } catch (SQLException e) {
            }
        }
        return resultado;
    } // FIM DO MÉTODO CONSULTA
}
