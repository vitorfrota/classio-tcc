<%-- 
    Document   : relatorioTurma
    Created on : 01/10/2018, 16:08:53
    Author     : vitor_9g3eyo1
--%>

<%@page import="br.com.classio.model.dao.LancamentoNotaDAO"%>
<%@page import="br.com.classio.model.beans.Matricula"%>
<%@page import="br.com.classio.model.dao.MatriculaDAO"%>
<%@page import="br.com.classio.model.beans.Avaliacao"%>
<%@page import="br.com.classio.model.dao.AvaliacaoDAO"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="br.com.classio.model.beans.Turma"%>
<%@page import="br.com.classio.model.dao.TurmaDAO"%>
<!DOCTYPE html>
<%
    int codigo = Integer.parseInt(request.getParameter("codigo"));
    Turma t = new Turma();
    TurmaDAO tdao = new TurmaDAO();
    LancamentoNotaDAO ldao = new LancamentoNotaDAO();
    t = tdao.consulta(codigo);

    int cont = 0;
    int cont1 = 0;
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html;charset=iso-8859-1" />
        <link href="../../../assets/vendor/bootstrap/css/bootstrap.css" rel="stylesheet" id="bootstrap-css">    
        <title>relatorio_<%=t.getTurdescricao()%></title>
    </head>
    <body onload="window.print(); window.history.back();">
        <div class="container">
            <img style="width:160px; height: 60px; float: right;" class="profile-img-card" src="../../../assets/img/login_logo.png" />
            <br>
            <br>
            <br>
            <div class="row">
                <div class="col-md-9">
                    <h4><%= t.getTurdescricao()%></h4>
                    <h5>Ministrada por <%= t.getTurprocodigo().getProusucodigo().getUsunome()%></h5>
                    <p>Média: <%= t.getTurmedia()%></p>
                    <p>Divisão das notas: <%= t.getTurdividenota()%></p>
                    <p>Pontuação Estimada: <%= t.getTurdividenota() * 10%></p>
                </div>
                <div class="col-md-3">
                    <p>Iniciada em <%= t.getTurdtabertura()%></p>
                </div>
            </div>
            <center><h3>Avaliações</h3></center>
            <table class="table">
                <thead>
                    <tr>
                        <th scope="col">&nbsp;</th>
                        <th scope="col">Descrição</th>
                        <th scope="col">Tipo</th>
                        <th scope="col">Aberto</th>
                        <th scope="col">Prazo</th>
                        <th scope="col">Nota</th>
                    </tr>
                </thead>
                <tbody>
                    <%

                        AvaliacaoDAO adao = new AvaliacaoDAO();

                        for (Avaliacao a : adao.listarAvaliacoes(codigo)) {
                    %>
                    <tr>               
                        <th scope="row"><%= cont = cont + 1%></th>
                        <td><%= a.getAvadescricao()%></td>
                        <td><%= a.getAvatipcodigo().getTipdescricao()%></td>
                        <td><%= a.getAvadtabertura()%></td>
                        <td><%= a.getAvadtentrega()%></td>
                        <td><%= a.getAvanota()%></td>                    
                    </tr>
                    <%}%>
                </tbody>
                <tfoot>
                    <tr>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td><b>Total:</b></td>
                        <td><b><%= adao.calculaNota(codigo)%></b></td>
                    </tr>
                </tfoot>
            </table>
            <br>
            <br>
            <br>    
            <center><h3>Alunos</h3></center>
            <table class="table">
                <thead>
                    <tr>
                        <th scope="col">&nbsp;</th>
                        <th scope="col">Nome</th>
                        <th scope="col">Média</th>
                        <th scope="col">Situação</th>
                    </tr>
                </thead>
                <tbody>
                    <%

                        MatriculaDAO aldao = new MatriculaDAO();
                        for (Matricula al : aldao.listarAlunos(codigo)) {
                    %>
                    <tr>               
                        <th scope="row"><%= cont1 = cont1 + 1%></th>
                        <td><%= al.getMatalucodigo().getAluusucodigo().getUsunome()%></td>
                        <td><%= ldao.mediaAluno(codigo, al.getMatalucodigo().getAlucodigo()) %></td>
                        <td><%= ldao.situacaoAluno(codigo, al.getMatalucodigo().getAlucodigo()) %></td>
                    </tr>
                    <%}%>
                </tbody>
            </table>
        </div>
    </body>
</html>
