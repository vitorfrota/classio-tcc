/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.classio.controller;

import br.com.classio.model.beans.Aluno;
import br.com.classio.model.beans.Turma;
import br.com.classio.model.beans.Matricula;
import br.com.classio.model.beans.StatusTurma;
import br.com.classio.model.beans.Usuario;
import br.com.classio.model.dao.AlunoDAO;
import br.com.classio.model.dao.StatusTurmaDAO;
import br.com.classio.model.dao.ProfessorDAO;
import br.com.classio.model.dao.MatriculaDAO;
import br.com.classio.model.dao.TurmaDAO;
import br.com.classio.model.dao.UsuarioDAO;
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
@WebServlet(name = "ControllerTurma", urlPatterns = {"/ControllerTurma"})
public class ControllerTurma extends HttpServlet {

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

        String usuario = request.getParameter("user"); // CAPTURA O USUARIO LOGADO
        Usuario u = new Usuario();
        UsuarioDAO udao = new UsuarioDAO();
        TurmaDAO tdao = new TurmaDAO();
        int idturma = 0;
        u = udao.consulta(usuario); // RETORNA USUARIO
        int opcao = Integer.parseInt(request.getParameter("btn-turma")); // PARA DEFINIR O TIPO DE AÇÃO

        if (u.getUsutipcodigo().getTipcodigo() == 2) { // SALVA A TURMA SE USUARIO FOR DO TIPO PROFESSOR
            ProfessorDAO pdao = new ProfessorDAO();
            StatusTurmaDAO stdao = new StatusTurmaDAO();
            Turma t = new Turma();
            StatusTurma st = new StatusTurma();
            t.setTurdescricao(request.getParameter("descricao"));
            t.setTurmedia(Double.parseDouble(request.getParameter("media")));
            t.setTurdividenota(Integer.parseInt(request.getParameter("dividenota")));

            if (request.getParameter("descricao").isEmpty()) {
                try (PrintWriter out = response.getWriter()) {
                    out.println("<script>alert('Campos vazios!')</script>");
                    out.println("<script>location='modulos/professor/homeProfessor.jsp';</script>");
                }
            }

            switch (opcao) {
                case 1: // CRIAR TURMA
                    t.setTurdtabertura(new Date()); // PEGA DATA ATUAL
                    t.setTurstacodigo(stdao.consulta(1)); // RETORNA ESTADO DA TURMA 1-ABERTA 2-ARQUIVADA
                    t.setTurprocodigo(pdao.RetornaIdProfessor(usuario)); // RETORNA PROFESSOR DE ACORDO COM O LOGIN
                    tdao.salvar(t); // SALVA TURMA
                    try (PrintWriter out = response.getWriter()) {
                        out.println("<script>alert('Turma criada com sucesso!')</script>");
                        out.println("<script>location='modulos/professor/homeProfessor.jsp';</script>");
                    }
                    break;
                case 2: // ALTERAR TURMA
                    t.setTurcodigo(Integer.parseInt(request.getParameter("codigo")));
                    tdao.alterar(t);
                    try (PrintWriter out = response.getWriter()) {
                        out.println("<script>alert('Turma editada com sucesso!')</script>");
                        out.println("<script>location='modulos/turma/homeTurma.jsp?codigo=" + t.getTurcodigo() + "';</script>");
                    }
                    response.sendRedirect("modulos/turma/homeTurma.jsp?codigo=" + t.getTurcodigo());
                    break;
                case 3: // ARQUIVA TURMA
                    idturma = Integer.parseInt(request.getParameter("codigo"));
                    st = stdao.consulta(2); // CÓDIGO DE TURMA ARQUIVADA
                    t.setTurstacodigo(st);
                    tdao.mudaStatusTurma(idturma, t.getTurstacodigo().getStacodigo());
                    try (PrintWriter out = response.getWriter()) {
                        out.println("<script>alert('Turma arquivada com sucesso!')</script>");
                        out.println("<script>location='modulos/professor/homeProfessor.jsp';</script>");
                    }
                    break;
                case 4: // DESARQUIVA TURMA
                    idturma = Integer.parseInt(request.getParameter("codigot"));
                    st = stdao.consulta(1); // CÓDIGO DE TURMA ABERTA
                    t.setTurstacodigo(st);
                    tdao.mudaStatusTurma(idturma, t.getTurstacodigo().getStacodigo());
                    try (PrintWriter out = response.getWriter()) {
                        out.println("<script>alert('Você desarquivou a turma!')</script>");
                        out.println("<script>document.location.href='../professor/modificaInfoProfessor.jsp';</script>");
                    }
                    break;
            }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////ALUNO/////////////////////////////////////////////////////////////////////////////////////////
        } else if (u.getUsutipcodigo().getTipcodigo() == 1) { // ALUNO ENTRA NA TURMA ATRAVÉS DO CÓDIGO
            Matricula ta = new Matricula();
            MatriculaDAO tadao = new MatriculaDAO();
            AlunoDAO adao = new AlunoDAO();
            switch (opcao) {
                case 1: // ALUNO ENTRA NA TURMA
                    ta.setMatdtingresso(new Date()); // PEGA A DATA DE ENTRADA DO ALUNO NA TURMA
                    int codigoturma = Integer.parseInt(request.getParameter("codigoturma")); // CAPTURA A ID DA TURMA
                    ta.setMatturcodigo(tdao.consulta(codigoturma)); // ID DA TURMA
                    switch (tdao.verificaStatus(codigoturma)) {
                        case 1:
                            // ESTA CONDIÇÃO VERIFICA SE A TURMA ESTÁ DISPONÍVEL
                            ta.setMatalucodigo(adao.RetornaIdAluno(u.getUsulogin()));
                            tadao.incluirAluno(ta); // INCLUI ALUNO NA TURMA
                            // ALUNO ENTRA NA TURMA
                            try (PrintWriter out = response.getWriter()) {
                                out.println("<script>alert('Parabéns! Você acaba de entrar na turma " + ta.getMatturcodigo().getTurdescricao() + "')</script>");
                                out.println("<script>location='modulos/aluno/homeAluno.jsp';</script>");
                            }
                            break;
                        case 2:
                            try (PrintWriter out = response.getWriter()) {
                                out.println("<script>alert('Esta turma está arquivada!!!')</script>");
                                out.println("<script>location='modulos/aluno/homeAluno.jsp';</script>");
                            }
                            break;
                        default:
                            try (PrintWriter out = response.getWriter()) {
                                out.println("<script>alert('Esta turma não existe!!!')</script>");
                                out.println("<script>location='modulos/aluno/homeAluno.jsp';</script>");
                            }
                            break;
                    }
                    break;
                case 2: // ALUNO SAI DA TURMA
                    Aluno a = new Aluno();
                    a = adao.RetornaIdAluno(request.getParameter("user"));
                    idturma = Integer.parseInt(request.getParameter("codigo"));
                    tadao.sairTurma(idturma, a.getAlucodigo());
                    try (PrintWriter out = response.getWriter()) {
                        out.println("<script>alert('Você saiu da Turma!')</script>");
                        out.println("<script>document.location.href='modulos/aluno/homeAluno.jsp';</script>");
                    }
                    break;
            }
            response.sendRedirect(request.getHeader("referer"));
        }
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
