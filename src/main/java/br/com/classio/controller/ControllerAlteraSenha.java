/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.classio.controller;

import br.com.classio.model.beans.Usuario;
import br.com.classio.model.dao.UsuarioDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author vitor_9g3eyo1
 */
@WebServlet(name = "ControllerAlteraSenha", urlPatterns = {"/ControllerAlteraSenha"})
public class ControllerAlteraSenha extends HttpServlet {

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

        int id = Integer.parseInt(request.getParameter("codigo"));
        String senhaantiga = request.getParameter("senhaantiga");
        String novasenha = request.getParameter("novasenha");
        String cnovasenha = request.getParameter("cnovasenha");
        Usuario u = new Usuario();
        UsuarioDAO udao = new UsuarioDAO();
        u = udao.consultaId(id);
        if (u.getUsusenha().equals(senhaantiga)) {
            if (novasenha.equals(cnovasenha)) {
                u.setUsusenha(novasenha);
                udao.alterarSenha(u); // ALTERA A SENHA DO USUÁRIO         
                if (u.getUsutipcodigo().getTipcodigo() == 2) { // CASO SEJA PROFESSOR
                    response.sendRedirect("modulos/professor/modificaInfoProfessor.jsp");
                } else { // CASO SEJA ALUNO
                    response.sendRedirect("modulos/aluno/modificaInfoAluno.jsp");
                }
            } else {
                if (u.getUsutipcodigo().getTipcodigo() == 2) {
                    try (PrintWriter out = response.getWriter()) {
                        out.println("<script>");
                        out.println("alert('Senhas não coincidem!')");
                        out.println("location='modulos/professor/modificaInfoProfessor.jsp';");
                        out.println("</script>");
                    } // fim do try
                } // fim do if
                else {
                    try (PrintWriter out = response.getWriter()) {
                        out.println("<script>");
                        out.println("alert('Senhas não coincidem!')");
                        out.println("location='modulos/aluno/modificaInfoAluno.jsp';");
                        out.println("</script>");
                    }
                } // fim do else
            }
        } else {
            if (u.getUsutipcodigo().getTipcodigo() == 2) {
                try (PrintWriter out = response.getWriter()) {
                    out.println("<script>");
                    out.println("alert('Senha Antiga incorreta!')");
                    out.println("location='modulos/professor/modificaInfoProfessor.jsp';");
                    out.println("</script>");
                } // fim do try
            } // fim do if
            else {
                try (PrintWriter out = response.getWriter()) {
                    out.println("<script>");
                    out.println("alert('Senha Antiga incorreta!')");
                    out.println("location='modulos/aluno/modificaInfoAluno.jsp';");
                    out.println("</script>");
                }
            } // fim do else
        } // fim do else principal
}
/**
 * Returns a short description of the servlet.
 *
 * @return a String containing servlet description
 */
@Override
        public String getServletInfo
        
            () {
        return "Short description";
        }// </editor-fold>

    }
