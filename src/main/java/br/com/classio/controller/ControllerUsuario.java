/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.classio.controller;

import br.com.classio.model.beans.Aluno;
import br.com.classio.model.beans.Professor;
import br.com.classio.model.beans.Usuario;
import br.com.classio.model.dao.AlunoDAO;
import br.com.classio.model.dao.ProfessorDAO;
import br.com.classio.model.dao.TipoUsuarioDAO;
import br.com.classio.model.dao.UsuarioDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;

/**
 *
 * @author vitor_9g3eyo1
 */
@WebServlet(name = "ControllerUsuario", urlPatterns = {"/ControllerUsuario"})
public class ControllerUsuario extends HttpServlet {

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
        Professor professor = new Professor();
        ProfessorDAO professordao = new ProfessorDAO();
        Aluno aluno = new Aluno();
        AlunoDAO alunodao = new AlunoDAO();
        Usuario usuario = new Usuario();
        UsuarioDAO usuariodao = new UsuarioDAO();
        TipoUsuarioDAO tdao = new TipoUsuarioDAO();

        int opcao = Integer.parseInt(request.getParameter("btn-usuario")); // VARIAVEL DE BOTÃO

        int tipo = Integer.parseInt(request.getParameter("tipo")); // VARIAVEL PARA DECISÃO SE É CADASTRO DE ALUNO OU PROFESSOR
        usuario.setUsunome(request.getParameter("nome"));
        String user = request.getParameter("login");
        String contato = request.getParameter("contato");
        usuario.setUsulogin(user.toLowerCase());
        usuario.setUsusenha(request.getParameter("senha"));
        usuario.setUsutipcodigo(tdao.consulta(tipo));

        switch (opcao) {
            case 1: // CADASTRO DE USUARIO
                switch (usuario.getUsutipcodigo().getTipcodigo()) {
                    case 1: // CADASTRO DO ALUNO
                        aluno.setAlutelefone(request.getParameter("contato"));
                        usuariodao.salvar(usuario); //salvar usuario
                        aluno.setAluusucodigo(usuariodao.retornaUltimoRegistro());
                        alunodao.salvar(aluno);   // salvar aluno
                        break;
                    case 2: // CADASTRO DO PROFESSOR
                        professor.setProemail(request.getParameter("contato"));
                        usuariodao.salvar(usuario); //salvar usuario
                        professor.setProusucodigo(usuariodao.retornaUltimoRegistro());
                        professordao.salvar(professor); //salvar professor
                        break;
                }
                response.sendRedirect("index.html"); // RETORNAR PARA PÁGINA INICIAL
                break;
            case 2: // ALTERA USUARIO
                int id = Integer.parseInt(request.getParameter("codigo"));
                usuario.setUsucodigo(id);
                switch (tipo) {
                    case 1: // ALUNO
                        aluno.setAlutelefone(contato);
                        usuariodao.alterar(usuario); // ALTERA INFO NA TABELA USUARIO
                        aluno.setAluusucodigo(usuariodao.consulta(user));
                        alunodao.alterar(aluno);
                        try (PrintWriter out = response.getWriter()) {
                            out.println("<script>");
                            out.println("alert('Informações alteradas com sucesso, você terá que entrar no sistema novamente!!!')");
                            out.println("location='modulos/logoff.jsp';");
                            out.println("</script>");
                        }
                        break;
                    case 2: // PROFESSOR
                        professor.setProemail(contato);
                        usuariodao.alterar(usuario); // ALTERA INFO NA TABELA USUARIO
                        professor.setProusucodigo(usuariodao.consulta(user));
                        professordao.alterar(professor); // ALTERA INFO NA TABELA PROFESSOR
                        try (PrintWriter out = response.getWriter()) {
                            out.println("<script>");
                            out.println("alert('Informações alteradas com sucesso!')");
                            out.println("location='modulos/professor/modificaInfoProfessor.jsp';");
                            out.println("</script>");
                        }
                        break;
                }
                break;
        } // FIM DO SWITCH GERAL
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
