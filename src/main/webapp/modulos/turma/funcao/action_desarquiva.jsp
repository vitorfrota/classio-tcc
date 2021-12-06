<%-- 
    Document   : teste
    Created on : 29/09/2018, 18:17:19
    Author     : vitor_9g3eyo1
--%>

<%@page import="br.com.classio.model.dao.StatusTurmaDAO"%>
<%@page import="br.com.classio.model.dao.TurmaDAO"%>
<%@page import="br.com.classio.model.beans.StatusTurma"%>
<%@page import="br.com.classio.model.beans.Turma"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
 Turma t = new Turma();
 StatusTurma st = new StatusTurma();
 StatusTurmaDAO stdao = new StatusTurmaDAO();
 TurmaDAO tdao = new TurmaDAO();
    
    
 int idturma = Integer.parseInt(request.getParameter("codigo"));
                    st = stdao.consulta(1); // CÓDIGO DE TURMA ABERTA
                    t.setTurstacodigo(st);

                    tdao.mudaStatusTurma(idturma, st.getStacodigo());   

out.println("<script>alert('Você desarquivou a turma!')</script>");
out.println("<script>document.location.href='../../professor/modificaInfoProfessor.jsp';</script>");


%>
