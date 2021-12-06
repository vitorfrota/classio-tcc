<%-- 
    Document   : homeTurma
    Created on : 09/09/2018, 22:31:13
    Author     : vitor_9g3eyo1
--%>

<%@page import="br.com.classio.model.beans.LancamentoNota"%>
<%@page import="br.com.classio.model.beans.Aluno"%>
<%@page import="br.com.classio.model.dao.LancamentoNotaDAO"%>
<%@page import="br.com.classio.model.dao.AlunoDAO"%>
<%@page import="br.com.classio.model.beans.Mural"%>
<%@page import="br.com.classio.model.dao.MuralDAO"%>
<%@page import="br.com.classio.model.beans.Avaliacao"%>
<%@page import="br.com.classio.model.dao.AvaliacaoDAO"%>
<%@page import="br.com.classio.model.beans.Turma"%>
<%@page import="br.com.classio.model.dao.TurmaDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%   if (session.getAttribute("loginUsuario") == null) {
        out.println("<script>location='../../index.html';</script>");
    }%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="../../assets/js/jquery.min.js"></script>
        <script src="../../assets/vender/popper/js/popper.min.js"></script>
        <script src="../../assets/vendor/bootstrap/js/bootstrap.min.js"></script>
        <link href="../../assets/vendor/bootstrap/css/bootstrap.css" rel="stylesheet" id="bootstrap-css">
        <link href="../../assets/vendor/material-icons/css/material-icons.css" rel="stylesheet">
        <link href="../../assets/css/tela_turma.css" rel="stylesheet">
        <title>Classio - Inovando sua turma</title>
    </head>
    <body>
        <% TurmaDAO tdao = new TurmaDAO();
            Turma t = new Turma();
            LancamentoNotaDAO ldao = new LancamentoNotaDAO();
            AlunoDAO aldao = new AlunoDAO();
            Aluno al = aldao.RetornaIdAluno(session.getAttribute("loginUsuario").toString());
            int codigo = Integer.parseInt(request.getParameter("codigo"));
            int cont = 0;
            t = tdao.consulta(codigo);
            if (t.getTurdescricao() == null) { // CASO A TURMA SEJA ARQUIVADA
                out.println("<script>");
                out.println("alert('Esta turma está arquivada!')");
                out.println("location='../aluno/homeAluno.jsp';");
                out.println("</script>");
            }
        %>
        <nav class="navbar navbar-expand-lg navbar-light bg-dark">
            <a class="navbar-brand" href="../aluno/homeAluno.jsp"><img class="img-fluid" src="../../assets/img/logo_navbar.png"></img></a>
            <div class="navbar-collapse" id="navbarNavDropdown">
                <ul class="navbar-nav mr-auto">
            </div>
            <ul class="navbar-nav">
                <div class="nav-item btn-group dropleft">
                    <a href="#" class="material-icons mdi-2x text-white" style="text-decoration: none;"data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">more_vert</a>
                    <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                        <form action="../../ControllerTurma" method="POST">
                            <input type="hidden" name="user" id="user" value="<%= session.getAttribute("loginUsuario")%>">
                            <input type="hidden" name="codigo" id="codigo" value="<%= t.getTurcodigo()%>"><!-- CODIGO DA TURMA -->
                            <button type="submit" class="dropdown-item" value="2" name="btn-turma" id="btn-turma">Sair da Turma</button>
                        </form>
                    </div>
                </div>
            </ul>
        </nav>
        <!-- HEADER DA TURMA -->
        <div class="jumbotron jumbotron-fluid" id="jumbturma">
            <div class="container">
                <h1 class="display-12" id="tituloturma"><%= t.getTurdescricao()%></h1>
                <p class="text-white">SITUAÇÃO: <%= ldao.situacaoAluno(t.getTurcodigo(), al.getAlucodigo())%></p>
                <p class="text-white">MÉDIA: <%= ldao.mediaAluno(t.getTurcodigo(), al.getAlucodigo())%></p>
            </div>
        </div>
        <!-- CONTAINER DA TURMA, MURAL PARA PUBLICAR MENSAGENS -->
        <div class="container">
            <div class="row">
                <div class="col-lg-12">
                    <div class="form-group">
                        <h5 for="p">Meus Pontos</h5>
                        <a href="#" data-toggle="modal" data-target="#notasModal">Visualizar com Detalhes</a>
                        <%

                            double desempenho = ldao.desempenhoAluno(t.getTurcodigo(), al.getAlucodigo());
                            double pontos = (t.getTurdividenota() * 10) * desempenho / 100;
                        %>
                        <div name="p" class="progress form-control" style="height: 40px;">
                            <div class="progress-bar bg-info" role="progressbar" style="width: <%=desempenho%>%; " aria-valuenow="50" aria-valuemin="0" aria-valuemax="100"><%= pontos%></div>                    
                        </div>
                        <p class="pull-left">0</p>
                        <p class="pull-right"><%= t.getTurdividenota() * 10%></p>
                    </div>
                </div>
            </div> 
            <br>
            <div class="row">
                <!-- LISTA DE AVALIAÇÕES -->
                <div class="col-lg-4">
                    <h3 class="display-12">Próxima Avaliação</h3>
                    <div class="card" style="width: 20rem;">
                        <ul class="list-group list-group-flush">
                            <%
                                AvaliacaoDAO adao = new AvaliacaoDAO();

                                for (Avaliacao a : adao.listarAvaliacoesTurma(codigo)) {
                            %>
                            <a href="../avaliacao/homeAvaAluno.jsp?codigo=<%=a.getAvacodigo()%>" class="list-group-item list-group-item-action flex-column align-items-start">
                                <div class="d-flex w-100 justify-content-between">
                                    <h5 class="mb-1"><%= a.getAvadescricao()%></h5>
                                    <small>Prazo: <%= a.getAvadtentrega()%></small>
                                </div>
                                <p class="mb-1"><%= a.getAvatipcodigo().getTipdescricao()%></p>
                            </a>
                            <%
                                }
                            %>
                        </ul>
                    </div>
                </div>
                <div class="col-lg-7">
                    <div id="muralmsg" class="list-group">
                        <%
                            MuralDAO mdao = new MuralDAO();
                            for (Mural m : mdao.listarAvisos(codigo)) {
                        %>
                        <div id="panelmural" class="bg-light">  
                            <a class="list-group-item flex-column align-items-start" style="border: none;">                   
                                <div class="d-flex w-100 justify-content-between" style="border: none;">
                                    <p class="mb-1 pull-left"><i class="material-icons mdi-2x pull-left">announcement</i>&nbsp;&nbsp;&nbsp;<%= m.getMuraviso()%></p>
                                    <input type="hidden" name="codigomsg" value="<%= m.getMurcodigo()%>">  
                                </div>
                                <p class="mb-1"></p>
                                <small class="text-muted pull-right" style="font-size: 10px; padding-bottom: 10px;">Postado em <%= m.getMurdtpostagem()%></small>
                            </a>                         
                        </div>
                        <br>
                        <% }%>
                    </div> 
                </div>
                <!-- ALUNOS QUE MERECEM ATENÇÃO -->
                <div class="col-lg-1">
                    <h3 class="display-12">&nbsp;&nbsp;&nbsp;&nbsp;</h3>
                </div>
            </div>  
        </div>
        <!--MODAL NOTAS-->
        <div class="modal fade" id="notasModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-lg modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Meus Pontos</h5>
                        <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">×</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th scope="col">&nbsp;</th>
                                    <th scope="col">Descrição</th>
                                    <th scope="col">Valor</th>
                                    <th scope="col">Prazo</th>
                                    <th scope="col">Lançada em</th>
                                    <th scope="col">Nota</th>
                                    <th scope="col">Feedback</th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    double total = 0;
                                    for (LancamentoNota ln : ldao.listarAvaliacoes(t.getTurcodigo(), al.getAlucodigo())) {
                                %>
                                <tr>               
                                    <th scope="row"><%= cont = cont + 1%></th>
                                    <td><%= ln.getConavacodigo().getAvadescricao()%></td>
                                    <td><%= ln.getConavacodigo().getAvanota()%></td>
                                    <td><%= ln.getConavacodigo().getAvadtentrega()%></td>
                                    <td><%= ln.getCondtlancamento()%></td>
                                    <td><%= ln.getConnota()%></td>
                                    <td><%=ln.getConfeedback()%></td>
                                    <% total = ln.getConnota() + total;
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
                                    <td>&nbsp;</td>
                                    <td><b>Total:</b></td>
                                    <td><b><%= pontos%></b></td>                               
                                </tr>
                            </tfoot>
                        </table>
                    </div>
                    <div class="modal-footer">
                        <button class="btn btn-secondary" type="button" data-dismiss="modal">Voltar</button>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
