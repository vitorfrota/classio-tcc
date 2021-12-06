/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.classio.model.dao;

import br.com.totalvoice.TotalVoiceClient;
import br.com.totalvoice.api.Sms;
import br.com.classio.connection.ConnectionFactory;
import br.com.classio.model.beans.Avaliacao;
import br.com.classio.model.beans.LancamentoNota;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONObject;

/**
 *
 * @author vitor_9g3eyo1
 */
public class TotalVoiceDAO {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = "";
    TotalVoiceClient client = new TotalVoiceClient("f75422c386ca0a63ec75420d0f5c0fe5");

    public void enviaMsgAlunos(int id) { // MÉTODO PARA ENVIAR MENSAGEM AOS ALUNOS DA TURMA INFORMANDO TAREFA LANÇADA
        conn = ConnectionFactory.getConexao();
        try {
            Sms sms = new Sms(client);
            sql = "select a.alutelefone as telefone, u.usunome as nome, t.turdescricao as turmadescricao from matricula alt \n"
                    + "inner join turma t on t.turcodigo = alt.matturcodigo\n"
                    + "inner join aluno a on a.alucodigo = alt.matalucodigo \n"
                    + "inner join usuario u on u.usucodigo = a.aluusucodigo\n"
                    + "where t.turcodigo = ?";
            try {
                ps = conn.prepareStatement(sql);
                ps.setInt(1, id);
                ps.execute();
                rs = ps.executeQuery();
                while (rs.next()) {
                    sms.enviar(rs.getString("telefone"), "Oi " + rs.getString("nome") + ", você tem uma nova avaliacao na turma de " + rs.getString("turmadescricao"));
                }
            } catch (SQLException e) {
            } finally {
                try {
                    conn.close();
                    ps.close();
                } catch (SQLException e) {
                }
            }
            JSONObject result = sms.enviar("NUMERO", "SUA MENSAGEM");
            System.out.println(result);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    
     public void enviaMsgMural(int id, String msg) { // MÉTODO PARA ENVIAR MENSAGEM AOS ALUNOS DA TURMA INFORMANDO MENSAGEM NO MURAL
        conn = ConnectionFactory.getConexao();
        try {
            Sms sms = new Sms(client);
            sql = "select a.alutelefone as telefone, t.turdescricao as turma from matricula alt \n"
                    + "inner join turma t on t.turcodigo = alt.matturcodigo\n"
                    + "inner join aluno a on a.alucodigo = alt.matalucodigo \n"
                    + "inner join usuario u on u.usucodigo = a.aluusucodigo\n"
                    + "where t.turcodigo = ?";
            try {
                ps = conn.prepareStatement(sql);
                ps.setInt(1, id);
                ps.execute();
                rs = ps.executeQuery();
                while (rs.next()) {
                    sms.enviar(rs.getString("telefone"),"MURAL DA TURMA " +rs.getString("turma")+": " + msg);
                }
            } catch (SQLException e) {
            } finally {
                try {
                    conn.close();
                    ps.close();
                } catch (SQLException e) {
                }
            }
            JSONObject result = sms.enviar("NUMERO", "SUA MENSAGEM");
            System.out.println(result);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
     
          public void enviaMsgNota(int id, LancamentoNota ln) { // MÉTODO PARA ENVIAR MENSAGEM AOS ALUNOS DA TURMA INFORMANDO MENSAGEM NO MURAL
        conn = ConnectionFactory.getConexao();
        try {
            Sms sms = new Sms(client);
            sql = "select a.alutelefone as telefone, t.turdescricao as turma from matricula alt \n"
                    + "inner join turma t on t.turcodigo = alt.matturcodigo\n"
                    + "inner join aluno a on a.alucodigo = alt.matalucodigo \n"
                    + "inner join usuario u on u.usucodigo = a.aluusucodigo\n"
                    + "where a.alucodigo = ?";
            try {
                ps = conn.prepareStatement(sql);
                ps.setInt(1, id);
                ps.execute();
                rs = ps.executeQuery();
                while (rs.next()) {
                    sms.enviar(rs.getString("telefone"),"Nota lançada da avaliação " + ln.getConavacodigo().getAvadescricao() + ": " + ln.getConnota() );
                }
            } catch (SQLException e) {
            } finally {
                try {
                    conn.close();
                    ps.close();
                } catch (SQLException e) {
                }
            }
            JSONObject result = sms.enviar("NUMERO", "SUA MENSAGEM");
            System.out.println(result);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
