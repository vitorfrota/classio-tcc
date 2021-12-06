/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.classio.controller;

import br.com.classio.model.beans.LancamentoNota;
import br.com.classio.model.dao.AlunoDAO;
import br.com.classio.model.dao.AvaliacaoDAO;
import br.com.classio.model.dao.LancamentoNotaDAO;
import br.com.classio.model.dao.TotalVoiceDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author vitor_9g3eyo1
 */
@WebServlet(name = "ControllerNotas", urlPatterns = {"/ControllerNotas"})
public class ControllerNotas extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        
        LancamentoNota ln = new LancamentoNota();
        LancamentoNotaDAO lndao = new LancamentoNotaDAO();
        AvaliacaoDAO avdao = new AvaliacaoDAO();
        AlunoDAO adao = new AlunoDAO();
        TotalVoiceDAO tdao = new TotalVoiceDAO();
        
        int idAvaliacao = Integer.parseInt(request.getParameter("idavaliacao"));
        int idAluno = Integer.parseInt(request.getParameter("idaluno"));
        Double nota = Double.parseDouble(request.getParameter("nota"));
        String feedback = request.getParameter("feedback");
        
        ln.setConavacodigo(avdao.consultaId(idAvaliacao));
        ln.setConalucodigo(adao.consultaIdAluno(idAluno));
        ln.setCondtlancamento(new Date());
        ln.setConnota(nota);
        ln.setConfeedback(feedback);
        
        lndao.salvar(ln);
        tdao.enviaMsgNota(idAluno, ln);
        
        response.sendRedirect(request.getHeader("referer"));
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
