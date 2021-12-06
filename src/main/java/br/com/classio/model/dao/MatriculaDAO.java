/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.classio.model.dao;

import br.com.classio.model.beans.Matricula;
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
public class MatriculaDAO {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = "";

    public void incluirAluno(Matricula ta) { // MÉTODO PARA INCLUIR ALUNO NA TURMA 
        conn = ConnectionFactory.getConexao();
        sql = "insert into matricula(matdtingresso,matturcodigo,matalucodigo) values (?,?,?)";
        try {
            ps = conn.prepareStatement(sql);
            ps.setDate(1, new java.sql.Date(ta.getMatdtingresso().getTime()));
            ps.setInt(2, ta.getMatturcodigo().getTurcodigo());
            ps.setInt(3, ta.getMatalucodigo().getAlucodigo());
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

    public List<Matricula> listarAlunos(int id) { // MÉTODO PARA LISTAR ALUNOS DA TURMA
        conn = ConnectionFactory.getConexao();
        sql = "select * from matricula alt\n"
                + "join aluno a on a.alucodigo = alt.matalucodigo\n"
                + "join usuario u on a.aluusucodigo = u.usucodigo where matturcodigo= ? order by u.usunome asc";
        List<Matricula> lista = new ArrayList<>();
        Matricula ta = null;
        TurmaDAO tdao = new TurmaDAO();
        AlunoDAO adao = new AlunoDAO();
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
            rs = ps.executeQuery();
            while (rs.next()) {
                ta = new Matricula();
                ta.setMatturcodigo(tdao.consulta(rs.getInt("matturcodigo")));
                ta.setMatalucodigo(adao.consultaIdAluno(rs.getInt("matalucodigo")));
                ta.setMatdtingresso(rs.getDate("matdtingresso"));
                lista.add(ta);
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

    public void sairTurma(int cturma, int caluno) { // PARA ALUNO SAIR DA TURMA
        conn = ConnectionFactory.getConexao();
        sql = "delete from matricula where matturcodigo = ? and matalucodigo = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, cturma);
            ps.setInt(2, caluno);
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
}
