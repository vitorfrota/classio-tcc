/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.classio.controller;

import br.com.classio.model.beans.Mural;
import br.com.classio.model.dao.MuralDAO;
import br.com.classio.model.dao.ProfessorDAO;
import br.com.classio.model.dao.TotalVoiceDAO;
import br.com.classio.model.dao.TurmaDAO;
import java.io.IOException;
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
@WebServlet(name = "ControllerMural", urlPatterns = {"/ControllerMural"})
public class ControllerMural extends HttpServlet {

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

        TotalVoiceDAO tvdao = new TotalVoiceDAO();
        TurmaDAO tdao = new TurmaDAO();
        ProfessorDAO pdao = new ProfessorDAO();
        Mural m = new Mural();
        MuralDAO mdao = new MuralDAO();
        String msg = request.getParameter("posta");
        int idTurma = Integer.parseInt(request.getParameter("idturma"));
        int idProfessor = Integer.parseInt(request.getParameter("idprofessor"));

        int opcao = Integer.parseInt(request.getParameter("btn-mural"));
        System.out.println(opcao);
        switch (opcao) {
            case 1: // salvar
                m.setMuraviso(msg);
                m.setMurdtpostagem(new Date());
                m.setMurturcodigo(tdao.consulta(idTurma));
                m.setMurprocodigo(pdao.consultaId(idProfessor));
                mdao.salvar(m);
                tvdao.enviaMsgMural(m.getMurturcodigo().getTurcodigo(), m.getMuraviso()); // MÉTODO PARA ENVIAR SMS AOS ALUNOS
                break;
            case 2:
                int idmsg = Integer.parseInt(request.getParameter("codigomsg"));
                mdao.excluir(idmsg);
                break;
        }

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
