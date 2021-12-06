<%-- 
    Document   : consultaUsuario
    Created on : 02/10/2018, 22:21:36
    Author     : vitor_9g3eyo1
--%>

<%@page import="br.com.classio.model.beans.Usuario"%>
<%@page import="br.com.classio.model.dao.UsuarioDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    UsuarioDAO udao = new UsuarioDAO();
    Usuario u = new Usuario();

    String user = request.getParameter("pesquisausuario");
    int r;
    r = udao.verificaUsuario(user);

if(r == 0){
out.print("1"); // usuario disponivel
}else{
out.print("0");
}
%>
