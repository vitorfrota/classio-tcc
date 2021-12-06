/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.classio.model.dao;

import br.com.classio.model.beans.Aluno;
import br.com.classio.connection.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author vitor_9g3eyo1
 */
public class AlunoDAO {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = "";

    public void salvar(Aluno aluno) { // MÉTODO PARA SALVAR ALUNO NO BD
        conn = ConnectionFactory.getConexao();

        sql = "insert into aluno(alutelefone, aluusucodigo) values (?,?)";

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, aluno.getAlutelefone());
            ps.setInt(2, aluno.getAluusucodigo().getUsucodigo());
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

    public void alterar(Aluno aluno) { // MÉTODO PARA ALTERAR INFORMAÇÕES DO ALUNO
        conn = ConnectionFactory.getConexao();

        String sql = "UPDATE aluno SET alutelefone = ? WHERE aluusucodigo = ?";
        try {
            ps = conn.prepareStatement(sql);

            ps.setString(1, aluno.getAlutelefone());
            ps.setInt(2, aluno.getAluusucodigo().getUsucodigo());
            ps.execute();
        } catch (SQLException e) {
        } finally {
            try {
                conn.close();
                ps.close();
            } catch (SQLException e) {
            }
        }
    } // FIM DO MÉTODO ALTERAR

    public Aluno consultaId(int id) { // CONSULTA ALUNO DE ACORDO COM USUARIO
        conn = ConnectionFactory.getConexao();
        sql = "SELECT * FROM aluno where aluusucodigo = ?";
        Aluno a = null;
        UsuarioDAO udao = new UsuarioDAO();
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            ps.execute();
            if (rs.next()) {
                a = new Aluno();
                a.setAlucodigo(rs.getInt("alucodigo"));
                a.setAlutelefone(rs.getString("alutelefone"));
                a.setAluusucodigo(udao.consultaId(rs.getInt("aluusucodigo")));
            }
        } catch (SQLException e) {
        } finally {
            try {
                conn.close();
                ps.close();
            } catch (SQLException e) {
            }
        }
        return a;
    }

    public Aluno consultaIdAluno(int id) { // CONSULTA ALUNO DE ACORDO COM SEU ID
        conn = ConnectionFactory.getConexao();
        sql = "SELECT * FROM aluno where alucodigo = ?";
        Aluno a = null;
        UsuarioDAO udao = new UsuarioDAO();
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            ps.execute();
            if (rs.next()) {
                a = new Aluno();
                a.setAlucodigo(rs.getInt("alucodigo"));
                a.setAlutelefone(rs.getString("alutelefone"));
                a.setAluusucodigo(udao.consultaId(rs.getInt("aluusucodigo")));
            }
        } catch (SQLException e) {
        } finally {
            try {
                conn.close();
                ps.close();
            } catch (SQLException e) {
            }
        }
        return a;
    }

    public Aluno RetornaIdAluno(String user) { // RETORNAR ALUNO QUE ENTROU NA TURMA OU SAIU DE ACORDO COM O USUÁRIO (LOGIN)
        conn = ConnectionFactory.getConexao();
        sql = "select alucodigo, alutelefone, aluusucodigo from usuario u inner join aluno as a on u.usucodigo = a.aluusucodigo where u.usulogin = ?";
        Aluno a = null;
        UsuarioDAO udao = new UsuarioDAO();
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, user);
            rs = ps.executeQuery();
            ps.execute();
            if (rs.next()) {
                a = new Aluno();
                a.setAlucodigo(rs.getInt("alucodigo"));
                a.setAlutelefone(rs.getString("alutelefone"));
                a.setAluusucodigo(udao.consultaId(rs.getInt("aluusucodigo")));
            }
        } catch (SQLException e) {
        } finally {
            try {
                conn.close();
                ps.close();
            } catch (SQLException e) {
            }
        }
        return a;
    }
}
