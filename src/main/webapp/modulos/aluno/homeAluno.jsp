<%-- 
    Document   : homeAluno
    Created on : 29/08/2018, 16:08:31
    Author     : vitor_9g3eyo1
--%>

<%@page import="br.com.classio.model.beans.Aluno"%>
<%@page import="br.com.classio.model.dao.AlunoDAO"%>
<%@page import="br.com.classio.model.beans.Avaliacao"%>
<%@page import="br.com.classio.model.dao.AvaliacaoDAO"%>
<%@page import="br.com.classio.model.beans.Turma"%>
<%@page import="br.com.classio.model.beans.Turma"%>
<%@page import="br.com.classio.model.dao.TurmaDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%   if (session.getAttribute("loginUsuario") == null) {
        out.println("<script>location='../../index.html';</script>");
    }%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html;charset=iso-8859-1" />
        <script src="../../assets/js/jquery.min.js"></script>
        <script src="../../assets/vender/popper/js/popper.min.js"></script>
        <script src="../../assets/vendor/bootstrap/js/bootstrap.min.js"></script>
        <link href="../../assets/vendor/bootstrap/css/bootstrap.css" rel="stylesheet" id="bootstrap-css">     
        <link href="../../assets/vendor/material-icons/css/material-icons.css" rel="stylesheet">
        <link href="../../assets/css/tela_aluno.css" rel="stylesheet">
        <title>Classio - Inovando sua turma</title>
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-light bg-dark">
            <a class="navbar-brand" href="#"><img class="img-fluid" src="../../assets/img/logo_navbar.png"></img></a>
            <div class="navbar-collapse" id="navbarNavDropdown">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a style="color: white;" href="#modalEntraTurma" data-toggle="modal" data-target="#modalEntraTurma" class="nav-link">Entrar em Turma</a>
                    </li>
                </ul>
            </div>
            <ul class="navbar-nav">
                <li class="nav-item pull-left">
                    <a style="color: white;" href="modificaInfoAluno.jsp" class="nav-link"><% out.println(session.getValue("loginUsuario"));%></a>
                </li>
                <li class="nav-item pull-right">      
                    <a href="#" data-toggle="modal" data-target="#sairModal" class="material-icons nav-link mdi-2x text-white">power_settings_new</a>
                </li>
            </ul>
        </nav>
        <!-- LISTA DE TURMAS DO PROFESSOR -->
        <div class="container-fluid">
            <br>
            <div class="row">
                <div class="col-lg-6">
                    <h5>Próximas Avaliações</h5>
                    <div class="list-group">
                        <%
                            AvaliacaoDAO adao = new AvaliacaoDAO();
                            AlunoDAO aldao = new AlunoDAO();
                            Aluno al = aldao.RetornaIdAluno(session.getAttribute("loginUsuario").toString());
                            for (Avaliacao a : adao.listarAvaliacoesAluno(al.getAlucodigo())) {
                        %>
                        <a href="../avaliacao/homeAvaAluno.jsp?codigo=<%=a.getAvacodigo()%>" class="list-group-item list-group-item-action flex-column align-items-start sombra">
                            <i class="material-icons mdi-3x pull-left">description</i>
                            <div class="d-flex w-90 justify-content-between">                          
                                <h5 class="mb-1"><%= a.getAvadescricao()%></h5>
                                <small>Prazo: <%= a.getAvadtentrega()%></small>
                            </div>
                            <p class="mb-1">Nota: <%= a.getAvanota()%> - <small><%= a.getAvaturcodigo().getTurdescricao()%></small></p>
                        </a>
                        <br>
                        <%
                            }
                        %>
                    </div>
                </div>
                <div class="col-lg-6">
                    <h5>Turmas</h5>
                    <div class="list-group turmas">
                        <%
                            String aluno = String.valueOf(session.getAttribute("loginUsuario"));
                            TurmaDAO tdao = new TurmaDAO();
                            for (Turma t : tdao.listarTurmasAluno(aluno)) {
                        %>  
                        <a class="list-group-item list-group-item-action d-flex justify-content-between align-items-center item-turma" style="text-decoration: none;" href="../turma/homeTurmaAluno.jsp?codigo=<%= t.getTurcodigo()%>">
                            <i class="material-icons mdi-2x pull-left">group</i>                          
                            <span><%= t.getTurdescricao()%></span>
                        </a>
                        <%
                            }
                        %>
                    </div>
                </div>
            </div>
        </div>
        <!-- MODAL PARA ENTRAR EM NOVA TURMA -->
        <div class="modal fade" id="modalEntraTurma" tabindex="-1" role="dialog" aria-labelledby="modalEntraTurma" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <form name="form" method="POST" action="../../ControllerTurma">
                        <div class="modal-header bg-dark">
                            <i class="material-icons mdi-2x text-white">group</i><h5 class="modal-title display-4 text-white" style="font-size: 20px;" id="exampleModalLabel">&nbsp; Entrar em Turma</h5>
                            <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                                <span class="text-white" aria-hidden="true">×</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <input type="hidden" name="user" id="user" value="<%= session.getAttribute("loginUsuario")%>"> <!-- INPUT PARA PEGAR SESSION USUARIO -->
                            <div class="form-group">    
                                <label for="codigoturma">Código da Turma:</label>
                                <input type="text" class="form-control" name="codigoturma" id="codigoturma">
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="submit" class="btn btn-info btn-lg btn-block" value="1" name="btn-turma" id="btn-turma">Participar da Turma!</button>   
                        </div>
                    </form>
                </div>
            </div>
        </div>    
        <!-- Logout Modal-->
        <div class="modal fade" id="sairModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Você deseja realmente sair?</h5>
                        <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">×</span>
                        </button>
                    </div>
                    <div class="modal-body">Clique em "Sair" se você desejar encerrar a sessão!</div>
                    <div class="modal-footer">
                        <button class="btn btn-secondary" type="button" data-dismiss="modal">Voltar</button>
                        <a class="btn btn-primary" href="../logoff.jsp">Sair</a>
                    </div>
                </div>
            </div>
        </div>  

    </body>
    <script>
        $(document).ready(function () {

            $('#calendar').fullCalendar({
                header: {
                    left: '',
                    center: 'title',
                    right: 'prev,next today,month,listWeek'
                },
                defaultView: 'listDay',
                eventSources: [

                    // your event source
                    {

                        events: [// put the array in the `events` property  
        <%
            for (Avaliacao a : adao.listarAvaliacoesAluno(al.getAlucodigo())) {
        %>
                            {
                                id: <%= a.getAvacodigo()%>,
                                title: '<%= a.getAvadescricao()%>',
                                start: '<%= a.getAvadtentrega()%>'
                            },

        <% }%>
                        ],

                        color: '#cedd37', // an option!
                        textColor: 'white' // an option!
                    }

                ],

                eventClick: function (calEvent, jsEvent, view) {
                    location = '../avaliacao/homeAvaliacao.jsp?codigo=' + calEvent.id;
                },
                dayClick: function (date) {
                    $('#criaAvaliacao').modal('show');
                    document.getElementById('dataentrega').value = date.format(); // para criar nova avaliação, é passada a data clicada como parametro

                },

            })

        });
    </script>
</html>
