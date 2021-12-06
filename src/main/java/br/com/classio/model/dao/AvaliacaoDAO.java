/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.classio.model.dao;

import br.com.classio.model.beans.Avaliacao;
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
public class AvaliacaoDAO {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = "";

    public void salvar(Avaliacao a) { // MÉTODO PARA SALVAR AVALIACAO NO BD
        conn = ConnectionFactory.getConexao();
        sql = "insert into AVALIACAO(avadescricao,avadtentrega,avadtabertura,avanota,avaobservacao,avaturcodigo,avatipcodigo, avaanexo) values (?,?,?,?,?,?,?,?)";

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, a.getAvadescricao());
            ps.setDate(2, new java.sql.Date(a.getAvadtentrega().getTime()));
            ps.setDate(3, new java.sql.Date(a.getAvadtabertura().getTime()));
            ps.setDouble(4, a.getAvanota());
            ps.setString(5, a.getAvaobservacao());
            ps.setInt(6, a.getAvaturcodigo().getTurcodigo());
            ps.setInt(7, a.getAvatipcodigo().getTipcodigo());
            ps.setBinaryStream(8, a.getAvaanexo());
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

    public void alterar(Avaliacao a) { // MÉTODO PARA ALTERAR AS INFORMAÇÕES DA AVALIAÇÃO
        conn = ConnectionFactory.getConexao();
        sql = "UPDATE avaliacao SET avadescricao = ?, avanota = ? , avadtentrega = ?, avaobservacao = ?, avatipcodigo = ?, avaanexo = ? WHERE avacodigo = ?";

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, a.getAvadescricao());
            ps.setDouble(2, a.getAvanota());
            ps.setDate(3, new java.sql.Date(a.getAvadtentrega().getTime()));
            ps.setString(4, a.getAvaobservacao());
            ps.setInt(5, a.getAvatipcodigo().getTipcodigo());
            ps.setBinaryStream(6, a.getAvaanexo());
            ps.setInt(7, a.getAvacodigo());
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

    public void excluir(int codigo) { // MÉTODO PARA EXCLUIR A AVALIAÇÃO
        conn = ConnectionFactory.getConexao();
        sql = "DELETE FROM AVALIACAO WHERE avacodigo = ?";

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

    public Avaliacao consultaId(int id) { // MÉTODO PARA CONSULTA AVALIAÇÃO

        Avaliacao a = new Avaliacao();
        TipoAvaliacaoDAO tdao = new TipoAvaliacaoDAO();
        TurmaDAO tudao = new TurmaDAO();
        conn = ConnectionFactory.getConexao();

        sql = "select * from avaliacao where avacodigo = ?";

        try {

            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            ps.execute();
            while (rs.next()) {
                a.setAvacodigo(rs.getInt("avacodigo"));
                a.setAvadescricao(rs.getString("avadescricao"));
                a.setAvadtabertura(rs.getDate("avadtabertura"));
                a.setAvadtentrega(rs.getDate("avadtentrega"));
                a.setAvanota(rs.getDouble("avanota"));
                a.setAvaobservacao(rs.getString("avaobservacao"));
                a.setAvaanexo(rs.getBinaryStream("avaanexo"));
                a.setAvaturcodigo(tudao.consulta(rs.getInt("avaturcodigo")));
                a.setAvatipcodigo(tdao.consulta(rs.getInt("avatipcodigo")));
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

    public List<Avaliacao> listarAvaliacoes(int id) { // LISTA AS AVALIAÇÕES COM A TURMA ESPECÍFICA

        conn = ConnectionFactory.getConexao();

        sql = "SELECT * from avaliacao where avaturcodigo = ? order by avadtentrega desc";

        List<Avaliacao> lista = new ArrayList<>();

        Avaliacao a = null;
        TurmaDAO tdao = new TurmaDAO();
        TipoAvaliacaoDAO tidao = new TipoAvaliacaoDAO();

        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            ps.execute();
            while (rs.next()) {
                a = new Avaliacao();
                a.setAvacodigo(rs.getInt("avacodigo"));
                a.setAvadescricao(rs.getString("avadescricao"));
                a.setAvaobservacao(rs.getString("avaobservacao"));
                a.setAvadtentrega(rs.getDate("avadtentrega"));
                a.setAvadtabertura(rs.getDate("avadtabertura"));
                a.setAvanota(rs.getDouble("avanota"));
                a.setAvaturcodigo(tdao.consulta(rs.getInt("avaturcodigo")));
                a.setAvatipcodigo(tidao.consulta(rs.getInt("avatipcodigo")));
                lista.add(a);
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

    public List<Avaliacao> listarAvaliacoesTurma(int id) { // RETORNA A AVALIAÇÃO QUE ESTÁ MAIS PRÓXIMA PARA SER CORRIGIDA

        conn = ConnectionFactory.getConexao();

        sql = "select * from avaliacao a inner join turma t on t.turcodigo = a.avaturcodigo\n"
                + "where t.turcodigo = ? and avadtentrega > curdate() order by avadtentrega asc LIMIT 1";

        List<Avaliacao> lista = new ArrayList<>();

        Avaliacao a = null;
        TurmaDAO tdao = new TurmaDAO();
        TipoAvaliacaoDAO tidao = new TipoAvaliacaoDAO();

        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            ps.execute();
            while (rs.next()) {
                a = new Avaliacao();
                a.setAvacodigo(rs.getInt("avacodigo"));
                a.setAvadescricao(rs.getString("avadescricao"));
                a.setAvaobservacao(rs.getString("avaobservacao"));
                a.setAvadtentrega(rs.getDate("avadtentrega"));
                a.setAvadtabertura(rs.getDate("avadtabertura"));
                a.setAvanota(rs.getDouble("avanota"));
                a.setAvaturcodigo(tdao.consulta(rs.getInt("avaturcodigo")));
                a.setAvatipcodigo(tidao.consulta(rs.getInt("avatipcodigo")));
                lista.add(a);
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

    public List<Avaliacao> listarAvaliacoesProfessor(int id) { // LISTA TODAS AS AVALIAÇÕES DAS TURMAS DO PROFESSOR
        conn = ConnectionFactory.getConexao();
        sql = "select * from avaliacao a\n"
                + "inner join turma t on t.turcodigo = a.avaturcodigo\n"
                + "where t.turprocodigo = ?";
        List<Avaliacao> lista = new ArrayList<>();
        Avaliacao a = null;
        TurmaDAO tdao = new TurmaDAO();
        TipoAvaliacaoDAO tidao = new TipoAvaliacaoDAO();
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
            rs = ps.executeQuery();
            while (rs.next()) {
                a = new Avaliacao();
                a.setAvacodigo(rs.getInt("avacodigo"));
                a.setAvadescricao(rs.getString("avadescricao"));
                a.setAvaobservacao(rs.getString("avaobservacao"));
                a.setAvadtentrega(rs.getDate("avadtentrega"));
                a.setAvadtabertura(rs.getDate("avadtabertura"));
                a.setAvanota(rs.getDouble("avanota"));
                a.setAvaturcodigo(tdao.consulta(rs.getInt("avaturcodigo")));
                a.setAvatipcodigo(tidao.consulta(rs.getInt("avatipcodigo")));
                lista.add(a);
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

    public List<Avaliacao> listarAvaliacoesAluno(int id) { // LISTA AS AVALIAÇÕES DAS TURMAS DO ALUNO
        conn = ConnectionFactory.getConexao();
        sql = "select a.avacodigo, a.avadescricao, a.avaobservacao, a.avadtabertura, a.avadtentrega, \n"
                + "a.avanota, a.avaturcodigo, a.avatipcodigo, a.avaanexo\n"
                + " from avaliacao a \n"
                + "join matricula alt on alt.matturcodigo = a.avaturcodigo\n"
                + "join turma t on alt.matturcodigo = t.turcodigo\n"
                + "where (alt.matalucodigo = ? and (avadtentrega >= curdate() and avadtabertura <= curdate()))\n"
                + "and t.turstacodigo = 1 order by avadtentrega LIMIT 5";
        List<Avaliacao> lista = new ArrayList<>();
        Avaliacao a = null;
        TurmaDAO tdao = new TurmaDAO();
        TipoAvaliacaoDAO tidao = new TipoAvaliacaoDAO();
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            ps.execute();
            while (rs.next()) {
                a = new Avaliacao();
                a.setAvacodigo(rs.getInt("avacodigo"));
                a.setAvadescricao(rs.getString("avadescricao"));
                a.setAvaobservacao(rs.getString("avaobservacao"));
                a.setAvadtentrega(rs.getDate("avadtentrega"));
                a.setAvadtabertura(rs.getDate("avadtabertura"));
                a.setAvanota(rs.getDouble("avanota"));
                a.setAvaturcodigo(tdao.consulta(rs.getInt("avaturcodigo")));
                a.setAvatipcodigo(tidao.consulta(rs.getInt("avatipcodigo")));
                lista.add(a);
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

    public Double calculaNota(int id) { // MÉTODO PARA CALCULAR QUANTOS PONTOS O PROFESSOR JÁ TRABALHOU NA TURMA
        conn = ConnectionFactory.getConexao();
        double total = 0;
        sql = "select sum(avanota) as total from avaliacao where avaturcodigo = ?";

        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            ps.execute();
            while (rs.next()) {
                total = rs.getDouble("total");
            }
        } catch (SQLException e) {
        } finally {
            try {
                conn.close();
                ps.close();
            } catch (SQLException e) {
            }
        }
        return total;
    }

}
