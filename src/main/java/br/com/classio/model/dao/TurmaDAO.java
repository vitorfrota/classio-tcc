/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.classio.model.dao;

import br.com.classio.model.beans.Turma;
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
public class TurmaDAO {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = "";

    public void salvar(Turma turma) { // MÉTODO PARA SALVAR TURMA NO BD
        conn = ConnectionFactory.getConexao();
        sql = "insert into turma(turdescricao,turdtabertura,turmedia,turdividenota,turstacodigo,turprocodigo) values (?,?,?,?,?,?)";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, turma.getTurdescricao());
            ps.setDate(2, new java.sql.Date(turma.getTurdtabertura().getTime()));
            ps.setDouble(3, turma.getTurmedia());
            ps.setInt(4, turma.getTurdividenota());
            ps.setInt(5, turma.getTurstacodigo().getStacodigo());
            ps.setInt(6, turma.getTurprocodigo().getProcodigo());
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

    public void alterar(Turma turma) { // MÉTODO PARA ALTERAR AS INFORMAÇÕES DA TURMA
        conn = ConnectionFactory.getConexao();
        sql = "UPDATE turma SET turdescricao = ?, turmedia = ? , turdividenota = ? WHERE turcodigo = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, turma.getTurdescricao());
            ps.setDouble(2, turma.getTurmedia());
            ps.setInt(3, turma.getTurdividenota());
            ps.setInt(4, turma.getTurcodigo());
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

    public void mudaStatusTurma(int idturma, int idstatus) { // MÉTODO PARA ALTERAR STATUS DA TURMA (ABERTA OU ARQUIVADA)
        conn = ConnectionFactory.getConexao();
        sql = "UPDATE turma SET turstacodigo = ? WHERE turcodigo = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idstatus);
            ps.setInt(2, idturma);
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

    public Turma consulta(int id) { // MÉTODO PARA CONSULTAR TURMA
        Turma t = new Turma();
        StatusTurmaDAO edao = new StatusTurmaDAO();
        ProfessorDAO pdao = new ProfessorDAO();
        conn = ConnectionFactory.getConexao();
        sql = "select * from turma where turcodigo = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            ps.execute();
            rs = ps.executeQuery();
            while (rs.next()) {
                t.setTurcodigo(rs.getInt("turcodigo"));
                t.setTurdescricao(rs.getString("turdescricao"));
                t.setTurdtabertura(rs.getDate("turdtabertura"));
                t.setTurmedia(rs.getDouble("turmedia"));
                t.setTurdividenota(rs.getInt("turdividenota"));
                t.setTurstacodigo(edao.consulta(rs.getInt("turstacodigo")));
                t.setTurprocodigo(pdao.consultaId(rs.getInt("turprocodigo")));
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
    }
    
    public int verificaStatus(int id) { // PARA VERIFICAR O STATUS DA TURMA
        int i = 0;
        conn = ConnectionFactory.getConexao();
        sql = "select * from turma where turcodigo = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
              i =  rs.getInt("turstacodigo");
            }
        } catch (SQLException e) {
        } finally {
            try {
                conn.close();
                ps.close();
            } catch (SQLException e) {
            }
        }
        return i;
    }

    public List<Turma> listarTurmasProfessor(int id) { // MÉTODO PARA LISTAR TURMAS QUE O PROFESSOR CRIOU
        conn = ConnectionFactory.getConexao();
        sql = "select turcodigo as codigo, turdescricao as descricao, turdtabertura as dataabertura,\n"
                + "turmedia as media, turdividenota as dividenota, turstacodigo as estado, turprocodigo as codigop from turma t  \n"
                + "inner join professor p on p.procodigo = t.turprocodigo\n"
                + "where p.procodigo = ? and turstacodigo = 1";

        List<Turma> lista = new ArrayList<>();
        Turma t = null;
        StatusTurmaDAO stdao = new StatusTurmaDAO();
        ProfessorDAO pdao = new ProfessorDAO();
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                t = new Turma();
                t.setTurcodigo(rs.getInt("codigo"));
                t.setTurdescricao(rs.getString("descricao"));
                t.setTurdtabertura(rs.getDate("dataabertura"));
                t.setTurmedia(rs.getDouble("media"));
                t.setTurdividenota(rs.getInt("dividenota"));
                t.setTurstacodigo(stdao.consulta(rs.getInt("estado")));
                t.setTurprocodigo(pdao.consultaId(rs.getInt("codigop")));
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

    public List<Turma> listarTurmasArquivadas(int id) { // MÉTODO PARA LISTAR TURMAS QUE O PROFESSOR ARQUIVOU
        conn = ConnectionFactory.getConexao();
        sql = "select turcodigo as codigo, turdescricao as descricao, turdtabertura as dataabertura,\n"
                + "turmedia as media, turdividenota as dividenota, turstacodigo as estado, turprocodigo as codigop from turma t\n"
                + "inner join professor p on p.procodigo = t.turprocodigo\n"
                + "where p.procodigo = ? and turstacodigo = 2";
        List<Turma> lista = new ArrayList<>();
        Turma t = null;
        StatusTurmaDAO stdao = new StatusTurmaDAO();
        ProfessorDAO pdao = new ProfessorDAO();
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
            rs = ps.executeQuery();
            while (rs.next()) {
                t = new Turma();
                t.setTurcodigo(rs.getInt("codigo"));
                t.setTurdescricao(rs.getString("descricao"));
                t.setTurdtabertura(rs.getDate("dataabertura"));
                t.setTurmedia(rs.getDouble("media"));
                t.setTurdividenota(rs.getInt("dividenota"));
                t.setTurstacodigo(stdao.consulta(rs.getInt("estado")));
                t.setTurprocodigo(pdao.consultaId(rs.getInt("codigop")));
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

    public List<Turma> listarTurmasAluno(String login) { // MÉTODO PARA LISTAR TURMAS QUE O ALUNO ESTÁ MATRICULADO
        conn = ConnectionFactory.getConexao();
        sql = "select turcodigo as codigo, turdescricao as descricao, turdtabertura as dataabertura,\n"
                + "turmedia as media, turdividenota as dividenota, turstacodigo as estado, turprocodigo as codigop\n"
                + " from turma t inner join matricula alt on alt.matturcodigo = t.turcodigo\n"
                + "inner join aluno a on a.alucodigo = alt.matalucodigo\n"
                + "inner join usuario u on u.usucodigo = a.aluusucodigo where u.usulogin = ? and t.turstacodigo = 1;";
        List<Turma> lista = new ArrayList<>();
        Turma t = null;
        StatusTurmaDAO stdao = new StatusTurmaDAO();
        ProfessorDAO pdao = new ProfessorDAO();
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, login);
            ps.execute();
            rs = ps.executeQuery();
            while (rs.next()) {
                t = new Turma();
                t.setTurcodigo(rs.getInt("codigo"));
                t.setTurdescricao(rs.getString("descricao"));
                t.setTurdtabertura(rs.getDate("dataabertura"));
                t.setTurmedia(rs.getDouble("media"));
                t.setTurdividenota(rs.getInt("dividenota"));
                t.setTurstacodigo(stdao.consulta(rs.getInt("estado")));
                t.setTurprocodigo(pdao.consultaId(rs.getInt("codigop")));
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

    public double progressoTurma(int id) { // MÉTODO PARA MOSTRAR PROGRESSO DE AVALIAÇÕES DA TURMA
        conn = ConnectionFactory.getConexao();
        double notas = 0;
        int divisao = 0;
        sql = "select sum(avanota) as total, t.turdividenota as divisao from avaliacao a \n"
                + "inner join turma t on a.avaturcodigo = t.turcodigo\n"
                + "where avaturcodigo = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            ps.execute();
            rs = ps.executeQuery();
            while (rs.next()) {
                notas = rs.getDouble("total");
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
        double total = (notas * 100) / (divisao * 10); //calculo de progresso da turma
        return total;
    }
}
