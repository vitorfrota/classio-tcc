/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.classio.controller;

import br.com.classio.model.beans.Avaliacao;
import br.com.classio.model.beans.Mural;
import br.com.classio.model.dao.AvaliacaoDAO;
import br.com.classio.model.dao.MuralDAO;
import br.com.classio.model.dao.TipoAvaliacaoDAO;
import br.com.classio.model.dao.TotalVoiceDAO;
import br.com.classio.model.dao.TurmaDAO;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author vitor_9g3eyo1
 */
@WebServlet(name = "ControllerAvaliacao", urlPatterns = {"/ControllerAvaliacao"})
@MultipartConfig(maxFileSize = 16177215)    // upload file's size up to 16MB
public class ControllerAvaliacao extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);

        Avaliacao a = new Avaliacao();
        AvaliacaoDAO adao = new AvaliacaoDAO();
        Mural m = new Mural();
        MuralDAO mdao = new MuralDAO();
        TipoAvaliacaoDAO tdao = new TipoAvaliacaoDAO();
        TurmaDAO tudao = new TurmaDAO();
        TotalVoiceDAO tvdao = new TotalVoiceDAO();

        String dtabertura = request.getParameter("dtabertura");
        int opcao = Integer.parseInt(request.getParameter("btn-avaliacao")); // PARA TRATAR TIPO DE A????O NO BANCO
        String check = null;
        int checkagenda = 0;

        // Para armazenar o anexo
        Part filePart = request.getPart("anexo");
        InputStream is = null;
        if (filePart != null) {
            is = filePart.getInputStream();
            System.out.println(filePart.getSize());
            a.setAvaanexo(is);
        }
        ////////////////////////////////////////////////////////////
        ///////////////////////PARA AGENDAR AVALIA????O///////////////////////////////////
        if (null != request.getParameter("checkagenda")) {
            checkagenda = Integer.parseInt(request.getParameter("checkagenda"));
        }
        ////////////////////////////////////////////////////////////////////////////////////
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        Date data = null;
        Date dataagenda = null;
        try {
            data = formato.parse(request.getParameter("dataentrega"));
        } catch (ParseException ex) {
            Logger.getLogger(ControllerAvaliacao.class.getName()).log(Level.SEVERE, null, ex);
        }
        /////////////////////////////////////////////////////////////////////////////////////
        if (dtabertura != null && checkagenda == 2) { // SE FOR PARA PROGRAMAR AVALIACAO, SALVA DIFERENTE
            try {
                dataagenda = formato.parse(request.getParameter("dtabertura"));
                a.setAvadtabertura(dataagenda);
            } catch (ParseException ex) {
                Logger.getLogger(ControllerAvaliacao.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            a.setAvadtabertura(new Date()); // PEGA DATA ATUAL
        }
        int codigoturma = Integer.parseInt(request.getParameter("turma"));
        int codigotipo = Integer.parseInt(request.getParameter("tipo"));
        a.setAvadescricao(request.getParameter("descricao"));
        a.setAvanota(Double.parseDouble(request.getParameter("nota")));
        a.setAvadtentrega(data);
        a.setAvaobservacao(request.getParameter("observacao"));
        a.setAvatipcodigo(tdao.consulta(codigotipo));
        a.setAvaturcodigo(tudao.consulta(codigoturma));
        // CONDI????O PARA IMPEDIR QUE O PROFESSOR ENVIE UMA TAREFA QUE ULTRAPASSE O VALOR QUE IR?? SER TRABALHADO NA TURMA
        switch (opcao) {
            case 1: // salvar avaliacao
                if (a.getAvaturcodigo().getTurstacodigo().getStacodigo() == 1) {
                    if (adao.calculaNota(a.getAvaturcodigo().getTurcodigo()) + a.getAvanota() <= (a.getAvaturcodigo().getTurdividenota() * 10)) {
                        /////////PARA PUBLICAR NO MURAL DA TURMA/////////////
                        m.setMurturcodigo(tudao.consulta(codigoturma));
                        m.setMurdtpostagem(new Date());
                        m.setMurprocodigo(a.getAvaturcodigo().getTurprocodigo());
                        m.setMuraviso(a.getAvaturcodigo().getTurprocodigo().getProusucodigo().getUsunome() + " postou uma nova avalia????o: " + a.getAvadescricao());
                        mdao.salvar(m);
                        //////////////////////////////
                        adao.salvar(a); // PARA SALVAR AVALIA????O

                        check = request.getParameter("checkmensagem"); // PARA ENVIAR MSG DE TEXTO
                        if (check != null) { // CONDI????O PARA ENVIAR MENSAGEM DE TEXTO PARA ALUNOS
                            tvdao.enviaMsgAlunos(a.getAvaturcodigo().getTurcodigo());   // M??TODO PARA ENVIAR MSG DE AVISO AOS ALUNOS
                        } 
                        try (PrintWriter out = response.getWriter()) {
                            out.println("<script>alert('Avalia????o enviada com sucesso!')</script>");
                            out.println("<script>location='modulos/turma/listaAvaliacoes.jsp?codigo=" + a.getAvaturcodigo().getTurcodigo() + "';</script>");
                        }
                    } else {
                        try (PrintWriter out = response.getWriter()) {
                            out.println("<script>alert('Voc?? n??o pode mais enviar avalia????o para esta turma!')</script>");
                            out.println("<script>location='modulos/turma/listaAvaliacoes.jsp?codigo=" + codigoturma + "';</script>");
                        }
                    }
                } else {
                    try (PrintWriter out = response.getWriter()) {
                        out.println("<script>alert('Esta turma est?? arquivada!')</script>");
                        out.println("<script>location='modulos/professor/homeProfessor.jsp';</script>");
                    }
                }

                break;
            case 2: // alterar avaliacao
                a.setAvacodigo(Integer.parseInt(request.getParameter("codigo")));
                adao.alterar(a);
                try (PrintWriter out = response.getWriter()) {
                    out.println("<script>alert('Avalia????o editada com sucesso!')</script>");
                    out.println("<script>location='modulos/avaliacao/homeAvaliacao.jsp?codigo=" + a.getAvacodigo() + "';</script>");
                }
                break;
            case 3: // excluir avaliacao
                adao.excluir(Integer.parseInt(request.getParameter("codigo")));
                try (PrintWriter out = response.getWriter()) {
                    out.println("<script>alert('Avalia????o exclu??da com sucesso!')</script>");
                    out.println("<script>document.location.href='modulos/turma/listaAvaliacoes.jsp?codigo=" + a.getAvaturcodigo().getTurcodigo() + "';</script>");
                }
                break;
        }
    }
}
