/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.classio.model.dao;

import br.com.classio.connection.ConnectionFactory;
import br.com.classio.model.beans.Aluno;
import br.com.classio.model.beans.LancamentoNota;
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
public class LancamentoNotaDAO {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = "";

    public void salvar(LancamentoNota ln) { // MÉTODO PARA SALVAR LANÇAMENTO DE NOTA NO BD
        conn = ConnectionFactory.getConexao();
        sql = "insert into consulta(conavacodigo,conalucodigo,condtlancamento,connota,confeedback) values(?,?,?,?,?)";

        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, ln.getConavacodigo().getAvacodigo());
            ps.setInt(2, ln.getConalucodigo().getAlucodigo());
            ps.setDate(3, new java.sql.Date(ln.getCondtlancamento().getTime()));
            ps.setDouble(4, ln.getConnota());
            ps.setString(5, ln.getConfeedback());
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

    public List<Aluno> listarAlunosSemNota(int idturma, int idavaliacao) { // MÉTODO LISTAR ALUNOS QUE ESTÃO SEM NOTA EM DETERMINADA AVALIAÇÃO

        List<Aluno> lista = new ArrayList<>();
        AlunoDAO aldao = new AlunoDAO();
        Aluno aluno = new Aluno();
        conn = ConnectionFactory.getConexao();

        sql = "select a.matalucodigo as codigo from matricula a\n"
                + "where a.matturcodigo = ? and\n"
                + "not exists(select * from consulta where conavacodigo = ? and conalucodigo=a.matalucodigo) group by a.matalucodigo;";

        try {

            ps = conn.prepareStatement(sql);
            ps.setInt(1, idturma);
            ps.setInt(2, idavaliacao);
            rs = ps.executeQuery();
            ps.execute();
            while (rs.next()) {
                aluno = new Aluno();
                aluno = aldao.consultaIdAluno(rs.getInt("codigo"));
                lista.add(aluno);
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

    public List<LancamentoNota> listarAvaliacoes(int idturma, int idaluno) { // MÉTODO PARA LISTAR AVALIAÇÕES DO ALUNO PARA O RELATÓRIO

        List<LancamentoNota> lista = new ArrayList<>();
        LancamentoNota l = new LancamentoNota();
        AvaliacaoDAO adao = new AvaliacaoDAO();
        AlunoDAO aldao = new AlunoDAO();
        conn = ConnectionFactory.getConexao();

        sql = "select * from consulta avl\n"
                + "inner join avaliacao a on avl.conavacodigo = a.avacodigo\n"
                + "where a.avaturcodigo = ? and avl.conalucodigo = ? order by a.avadtentrega asc";

        try {

            ps = conn.prepareStatement(sql);
            ps.setInt(1, idturma);
            ps.setInt(2, idaluno);
            rs = ps.executeQuery();
            ps.execute();
            while (rs.next()) {
                l = new LancamentoNota();
                l.setConavacodigo(adao.consultaId(rs.getInt("conavacodigo")));
                l.setConnota(rs.getDouble("connota"));
                l.setConfeedback(rs.getString("confeedback"));
                l.setCondtlancamento(rs.getDate("condtlancamento"));
                l.setConalucodigo(aldao.consultaId(rs.getInt("conalucodigo")));
                lista.add(l);
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

    public long desempenhoAluno(int idturma, int idaluno) { // MÉTODO PARA RETORNAR AS NOTAS DO ALUNO para utilizar na barra de progresso
        conn = ConnectionFactory.getConexao();
        double soma = 0;
        int divisao = 0;
        sql = "select sum(connota) as soma, t.turdividenota as divisao from consulta avl\n"
                + "join avaliacao a on avl.conavacodigo = a.avacodigo\n"
                + "join turma t on a.avaturcodigo = t.turcodigo\n"
                + "where t.turcodigo = ? and conalucodigo = ?";

        try {

            ps = conn.prepareStatement(sql);
            ps.setInt(1, idturma);
            ps.setInt(2, idaluno);
            rs = ps.executeQuery();
            ps.execute();
            while (rs.next()) {
                soma = rs.getDouble("soma");
                divisao = rs.getInt("divisao");
            }
        } catch (SQLException e) {
        } finally {
            try {
                conn.close();
                ps.close();
            } catch (SQLException e) {
            }
        }
        double total = (soma * 100) / (divisao * 10); //calculo de progresso da turma
        return Math.round(total);
    }

    public double mediaAluno(int idturma, int idaluno) { // MÉTODO PARA CALCULAR A MÉDIA DO ALUNO
        conn = ConnectionFactory.getConexao();
        double media = 0;
        sql = "select (sum(connota)/t.turdividenota) as media from consulta avl\n"
                + "join avaliacao a on a.avacodigo = avl.conavacodigo\n"
                + "join turma t on t.turcodigo = a.avaturcodigo\n"
                + "where t.turcodigo = ? and avl.conalucodigo = ?";

        try {

            ps = conn.prepareStatement(sql);
            ps.setInt(1, idturma);
            ps.setInt(2, idaluno);
            rs = ps.executeQuery();
            ps.execute();
            while (rs.next()) {
                media = rs.getDouble("media");
            }
        } catch (SQLException e) {
        } finally {
            try {
                conn.close();
                ps.close();
            } catch (SQLException e) {
            }
        }
        return media;
    }

    public String situacaoAluno(int idturma, int idaluno) { // MÉTODO PARA VERIFICAR A SITUAÇÃO DO ALUNO
        conn = ConnectionFactory.getConexao();
        String status = "";
        int dividenota = 0;
        double media = 0;
        double sturma = 0; // SOMA NOTA TURMA
        double maluno = 0; // MÉDIA ALUNO
        sql = "select sum(avanota) as snota, turdividenota as dividenota, turmedia as media from avaliacao a\n"
                + "join turma t on t.turcodigo = a.avaturcodigo where a.avaturcodigo = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idturma);
            rs = ps.executeQuery();
            ps.execute();
            while (rs.next()) {
                dividenota = rs.getInt("dividenota");
                sturma = rs.getDouble("snota");
                media = rs.getDouble("media");
            }
        } catch (SQLException e) {
        } finally {
            try {
                conn.close();
                ps.close();
            } catch (SQLException e) {
            }
        }
        maluno = mediaAluno(idturma, idaluno); // PARA RETORNAR MEDIA DO ALUNO
        if (maluno >= media) {
            status = "APROVADO";
        } else if (sturma == (dividenota * 10) && maluno < media) {
            status = "REPROVADO";
        } else if (sturma == (dividenota * 10) && maluno >= media) {
            status = "APROVADO";
        } else {
            status = "EM ANDAMENTO";
        }
        return status;
    }

}
