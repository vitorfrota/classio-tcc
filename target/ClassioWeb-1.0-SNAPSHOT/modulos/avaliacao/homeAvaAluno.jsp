<%--
    Document   : homeAvaAluno
    Created on : 14/10/2018, 21:12:35
    Author     : vitor_9g3eyo1
--%>

<%@page import="br.com.classio.model.beans.Aluno"%>
<%@page import="br.com.classio.model.dao.AlunoDAO"%>
<%@page import="br.com.classio.model.beans.Comentarios"%>
<%@page import="br.com.classio.model.dao.ComentariosDAO"%>
<%@page import="br.com.classio.model.dao.AvaliacaoDAO"%>
<%@page import="br.com.classio.model.beans.Avaliacao"%>
<!DOCTYPE html>
<%   if (session.getAttribute("loginUsuario") == null) {
        out.println("<script>location='../../index.html';</script>");
    }%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html;charset=iso-8859-1" />
        <script src="../../assets/js/jquery.min.js"></script>
        <script src="../../assets/vender/popper/js/popper.min.js"></script>
        <script src="../../assets/vendor/bootstrap/js/bootstrap.min.js"></script>
        <link href="../../assets/vendor/bootstrap/css/bootstrap.css" rel="stylesheet" id="bootstrap-css">
        <link href="../../assets/vendor/material-icons/css/material-icons.css" rel="stylesheet">
        <link href="../../assets/css/tela_avaaluno.css" rel="stylesheet">
        <title>Classio - Inovando sua turma</title>
    </head>
    <%
        int codigo = Integer.parseInt(request.getParameter("codigo"));
        Avaliacao a = new Avaliacao();
        AvaliacaoDAO adao = new AvaliacaoDAO();
        Aluno al = new Aluno();
        AlunoDAO aldao = new AlunoDAO();
        a = adao.consultaId(codigo);

        al = aldao.RetornaIdAluno(session.getAttribute("loginUsuario").toString());
    %>
    <body>
        <nav class="navbar navbar-expand-lg navbar-light bg-dark">
            <a class="navbar-brand" href="../aluno/homeAluno.jsp"><img class="img-fluid" src="../../assets/img/logo_navbar.png"></img></a>
            <div class="collapse navbar-collapse" id="navbarNavDropdown">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a href="../turma/homeTurmaAluno.jsp?codigo=<%= a.getAvaturcodigo().getTurcodigo()%>"<h4 class="text-white"><%=a.getAvaturcodigo().getTurdescricao()%></h4></a>
                    </li>
                </ul>
            </div>
        </nav>
        <div class="container-fluid bg-transparent">
            <div class="row">
                <div class="panelavaaluno col-lg-12 bg-info">
                    <div class="row">
                        <div class="col-lg-3"></div>
                        <div class="col-lg-6">
                            <br>
                            <p class="text-white">Prazo: <%= a.getAvadtentrega()%></p>
                            <h2 class="text-white"><%= a.getAvadescricao()%></h2>
                            <p class="text-white">Nota: <%= a.getAvanota()%></p>
                            <h5 class="text-white">Instruções</h5>
                            <p><% if (a.getAvaobservacao() == null) {
                                    out.print("TESTE");
                                } else {
                                    out.print("<p class='text-white'>" + a.getAvaobservacao() + "</p>");
                                }
                                %></p>
                            <input type="hidden" value="<%= a.getAvaanexo().available()%>" id="sizeanexo">
                            <%
                                if (a.getAvaanexo().available() > 100) {
                                    out.println("<a href='action/downloadAnexo.jsp?avacodigo=" + a.getAvacodigo() + "'class='material-icons mdi-2x text-white' style='display: block; text-decoration: none; cursor: pointer; margin-bottom: 20px; float: right;'>file_download</a>");
                                }
                            %>
                        </div>
                    </div>
                </div>
            </div>
            <br>
            <div class="row">
                <div class="col-lg-3"></div>
                <div class="col-lg-6">
                    <div id="panelavaaluno" class="bg-white">
                        <h6 style="padding: 15px;">Comentários da Turma</h6>
                        <form name="frm" action="../../ControllerComentarios" method="POST">
                            <div class="list-group">
                                <a href="#" class="list-group-item list-group-item-action flex-column align-items-start">
                                    <div class="d-flex w-100 justify-content-between">
                                        <div class="input-group mb-3">
                                            <input type="hidden" name="idavaliacao" value="<%=a.getAvacodigo()%>">
                                            <input type="hidden" name="idaluno" value="<%=al.getAlucodigo()%>">
                                            <input type="text" maxlength="140" class="form-control" autocomplete="off" name="posta" id="posta" placeholder="Adicionar comentário para a turma">
                                            <div class="input-group-append">
                                                <button type="submit" class="btn btn-primary" value="1" name="btn-mural"  id="btn-mural">Postar</button>
                                            </div>
                                        </div>
                                    </div>
                                </a>
                                <%
                                    ComentariosDAO cdao = new ComentariosDAO();
                                    for (Comentarios c : cdao.listarComentarios(a.getAvacodigo())) {
                                %>
                                <a href="#" class="list-group-item list-group-item-action flex-column align-items-start">
                                    <div class="d-flex w-100 justify-content-between">
                                        <h6 class="mb-1"><%= c.getComalucodigo().getAluusucodigo().getUsunome()%></h6>
                                        <small class="text-muted"><%=c.getComdtpostagem()%></small>
                                    </div>
                                    <p class="mb-1"><%=c.getCommensagem()%></p>
                                </a>
                                <%
                                    }
                                %>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="col-lg-3"></div>
            </div>
        </div>
    </body>
</html>
