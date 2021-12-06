<%-- 
    Document   : relatorioTurma
    Created on : 01/10/2018, 16:08:53
    Author     : vitor_9g3eyo1
--%>

<%@page import="br.com.classio.model.beans.LancamentoNota"%>
<%@page import="br.com.classio.model.dao.AlunoDAO"%>
<%@page import="br.com.classio.model.beans.Aluno"%>
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
    int codigot = Integer.parseInt(request.getParameter("codigoturma"));
    Aluno al = new Aluno();
    AlunoDAO aldao = new AlunoDAO();
    al = aldao.consultaIdAluno(codigo);
    LancamentoNotaDAO ldao = new LancamentoNotaDAO();
    Turma t = new Turma();
    TurmaDAO tdao = new TurmaDAO();

    t = tdao.consulta(codigot);

    int cont = 0;
    int cont1 = 0;
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html;charset=iso-8859-1" />
        <link href="../../../assets/vendor/bootstrap/css/bootstrap.css" rel="stylesheet" id="bootstrap-css">    
        <title>relatorio_individual_<%=al.getAluusucodigo().getUsulogin()%></title>
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
                    <h5 class="text-muted">Ministrada por <%= t.getTurprocodigo().getProusucodigo().getUsunome()%></h5>
                    <p>Média: <%= t.getTurmedia()%> / Divisão das notas: <%= t.getTurdividenota()%> </p>
                    <h5>Aluno: <%= al.getAluusucodigo().getUsunome() %></h5>
                    <p>Média: <%= ldao.mediaAluno(codigot, codigo) %></p>
                    <p>Situação: <%= ldao.situacaoAluno(codigot, codigo) %></p>
                </div>
                <div class="col-md-3">
                    <p>Iniciada em <%= t.getTurdtabertura()%></p>
                </div>
            </div>
            <center><h3>Minhas Notas</h3></center>
            <table class="table">
                <thead>
                    <tr>
                        <th scope="col">&nbsp;</th>
                        <th scope="col">Descrição</th>
                        <th scope="col">Tipo</th>
                        <th scope="col">Valor</th>
                        <th scope="col">Prazo</th>
                        <th scope="col">Nota</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        double total=0;
                        AvaliacaoDAO adao = new AvaliacaoDAO();

                        for (LancamentoNota ln : ldao.listarAvaliacoes(codigot,codigo)) {
                    %>
                    <tr>               
                        <th scope="row"><%= cont = cont + 1%></th>
                        <td><%= ln.getConavacodigo().getAvadescricao() %></td>
                        <td><%= ln.getConavacodigo().getAvatipcodigo().getTipdescricao() %></td>
                        <td><%= ln.getConavacodigo().getAvanota() %></td>
                        <td><%= ln.getConavacodigo().getAvadtentrega() %></td>
                        <td><%= ln.getConnota() %></td>
                        <% total = ln.getConnota()+total;
                            }
                        %>
                    </tr>
                </tbody>
                <tfoot>
                    <tr>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td><b>Total:</b></td>
                        <td><b><%= total%></b></td>                               
                    </tr>
                </tfoot>
            </table>
            <br>
        </div>
    </body>
</html>
