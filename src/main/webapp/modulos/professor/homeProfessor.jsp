<%--
    Document   : homeProfessor
    Created on : 29/08/2018, 16:08:31
    Author     : vitor_9g3eyo1
--%>

<%@page import="br.com.classio.model.beans.Professor"%>
<%@page import="br.com.classio.model.beans.Usuario"%>
<%@page import="br.com.classio.model.dao.UsuarioDAO"%>
<%@page import="br.com.classio.model.dao.ProfessorDAO"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="br.com.classio.model.dao.AvaliacaoDAO"%>
<%@page import="br.com.classio.model.beans.TipoAvaliacao"%>
<%@page import="br.com.classio.model.dao.TipoAvaliacaoDAO"%>
<%@page import="br.com.classio.model.beans.Turma"%>
<%@page import="br.com.classio.model.dao.TurmaDAO"%>
<!DOCTYPE html>
<%   if (session.getAttribute("loginUsuario") == null) {
        out.println("<script>location='../../index.html';</script>");
    }
    ProfessorDAO pdao = new ProfessorDAO();
    UsuarioDAO udao = new UsuarioDAO();
    Usuario u = udao.consulta(session.getAttribute("loginUsuario").toString());
    Professor p = pdao.consulta(u.getUsucodigo());
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html;charset=iso-8859-1" />
        <script src="../../assets/js/jquery.min.js"></script>
        <script src="../../assets/vendor/popper/js/popper.min.js"></script>
        <script src="../../assets/vendor/bootstrap/js/bootstrap.min.js"></script>
        <link href="../../assets/vendor/bootstrap/css/bootstrap.css" rel="stylesheet" id="bootstrap-css">
        <link href="../../assets/vendor/material-icons/css/material-icons.css" rel="stylesheet">
        <link href="../../assets/css/tela_professor.css" rel="stylesheet">
        <link href="../../assets/css/materialdesign.css" rel="stylesheet">
        <title>Classio - Inovando sua turma</title>
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-light bg-dark">
            <a class="navbar-brand" href="#"><img class="img-fluid" src="../../assets/img/logo_navbar.png"></img></a>
            <div class="navbar-collapse" id="navbarNavDropdown">
                <ul class="navbar-nav">
                    <li class="nav-item dropdown">
                        <a style="color: white;" class="nav-link dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">Novo</a>
                        <div class="dropdown-menu">
                            <a class="dropdown-item" href="#modalTurma" data-toggle="modal" data-target="#modalTurma">Turma</a>
                    </li>
                </ul>
            </div>
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a href="calendarioProfessor.jsp?codigo=<%= p.getProcodigo()%>" style="margin-left: -60px;" title="Agenda do Professor" name="agendaprofessor" class="material-icons mdi-2x nav-link text-white">event</a>
                </li>
                <li class="nav-item pull-left">                   
                    <a style="color: white; margin-top: 5px;" href="modificaInfoProfessor.jsp" class="nav-link"><% out.println(session.getValue("loginUsuario"));%></a>
                </li>
                <li class="nav-item pull-right">
                    <a href="#" data-toggle="modal" data-target="#sairModal" class="material-icons nav-link mdi-2x text-white">power_settings_new</a>
                </li>
            </ul>
        </nav>

        <!-- LISTA DE TURMAS DO PROFESSOR -->
        <div class="container-fluid">
            <br>
            <br>
            <div class="row" style="margin-left: 15px;">
                <%
                    AvaliacaoDAO adao = new AvaliacaoDAO();
                    TurmaDAO tdao = new TurmaDAO();
                    for (Turma t : tdao.listarTurmasProfessor(p.getProcodigo())) {
                %>
                <div class="col-mb-3">
                    <a href="../turma/homeTurma.jsp?codigo=<%= t.getTurcodigo()%>" style="color: black;">
                        <div id="cardturma" class="card mb-3" style="max-width: 17rem; display: inline-block; font-color: inherit;">
                            <img class="card-img-top" src="../../assets/img/bg_card.png" alt="Card image cap">
                            <div class="card-img-overlay">
                                <h5 class="card-title text-white"><%= t.getTurdescricao()%></h5>
                                <p class="card-text text-white">CÓDIGO: <%= t.getTurcodigo()%></p>
                            </div>
                            <div class="card-body">
                                <p class="card-text">Média: <%= t.getTurmedia()%></p>
                                <p class="card-text">Pontos: <%= t.getTurdividenota() * 10 - adao.calculaNota(t.getTurcodigo())%></p>
                            </div>
                            <div class="card-footer">
                                <div class="progress">
                                    <div class="progress-bar bg-success" role="progressbar" style="width: <%= tdao.progressoTurma(t.getTurcodigo())%>%" aria-valuenow="<%= tdao.progressoTurma(t.getTurcodigo())%>" aria-valuemin="0" aria-valuemax="100"><%=Math.round(tdao.progressoTurma(t.getTurcodigo()))%>%</div>
                                </div> 
                                <small class="text-muted">Criada em <%= t.getTurdtabertura()%></small>
                            </div>
                        </div>
                    </a>
                </div>
                <!-- ESPAÇAMENTO ENTRE CARDS DAS TURMAS -->
                <div class="col-mb-1">
                    <p>&nbsp;&nbsp;&nbsp;&nbsp;</p>
                </div>
                <!---------------------------------------->
                <%
                    }
                %>
            </div>
        </div>
        <!-- MODAL PARA CRIAR UMA NOVA TURMA -->
        <div class="modal fade" id="modalTurma" tabindex="-1" role="dialog" aria-labelledby="modalTurma" aria-hidden="true">
            <div class="modal-dialog modal-lg modal-dialog-centered">
                <div class="modal-content">
                    <form name="form" method="POST" autocomplete="off" action="../../ControllerTurma">
                        <div class="modal-header bg-dark">
                            <i class="material-icons mdi-2x text-white">group</i><h5 class="modal-title display-4 text-white" style="font-size: 20px;" id="exampleModalLabel">&nbsp; Nova Turma</h5>
                            <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                                <span class="text-white" aria-hidden="true">×</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <input type="hidden" name="user" id="user" value="<%= session.getAttribute("loginUsuario")%>"> <!-- INPUT PARA PEGAR SESSION USUARIO -->                              
                           <!-- <div class="form-group form-check">
                                <input type="checkbox" value="true" class="form-check-input" id="check">
                                <label class="form-check-label" for="check">NÃO QUERO CALCULAR NOTAS</label>
                            </div>-->
                            <div class="form-group">
                                <label for="descricao">Descrição:</label>
                                <input type="text" minlength="1" class="form-control" name="descricao" id="descricao" required>
                            </div>
                            <div class="row" id="divisaonotas">
                                <div class="col" >
                                    <div class="form-group">
                                        <label for="media">Média:</label>
                                        <input type="number" class="form-control" name="media" id="media" required>
                                    </div>
                                </div>
                                <div class="col">
                                    <div class="form-group">
                                        <label for="dividenota">Vou dividir as notas por:</label>
                                        <select name="dividenota" id="dividenota" class="custom-select">
                                            <option value="1">1</option>
                                            <option value="2">2</option>
                                            <option value="3">3</option>
                                            <option value="4">4</option>
                                            <option value="5">5</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="submit" class="btn btn-info btn-lg btn-block" value="1" name="btn-turma" id="btn-turma">Criar Turma</button>
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
        <script>
            $("#check").click(function () {
                if ($(this).val() == "true") {
                    $("#divisaonotas").css("visibility", "hidden");
                    document.getElementById("media").required = false;
                    document.getElementById("dividenota").required = false;
                    $(this).val("false");
                } else {
                    $("#divisaonotas").css("visibility", "visible");
                    $(this).val("true");
                }
            });
        </script>
    </body>
</html>
