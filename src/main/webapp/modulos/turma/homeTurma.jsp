<%-- 
    Document   : homeTurma
    Created on : 09/09/2018, 22:31:13
    Author     : vitor_9g3eyo1
--%>

<%@page import="br.com.classio.model.beans.Mural"%>
<%@page import="br.com.classio.model.dao.MuralDAO"%>
<%@page import="br.com.classio.model.beans.Avaliacao"%>
<%@page import="br.com.classio.model.dao.AvaliacaoDAO"%>
<%@page import="br.com.classio.model.beans.Turma"%>
<%@page import="br.com.classio.model.dao.TurmaDAO"%>
<%   if (session.getAttribute("loginUsuario") == null) {
        out.println("<script>location='../../index.html';</script>");
    }%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html;charset=iso-8859-1" />
        <script src="../../assets/js/jquery.min.js"></script>
        <script src="../../assets/vendor/popper/js/popper.min.js"></script>
        <script src="../../assets/vendor/bootstrap/js/bootstrap.min.js"></script>
        <link href="../../assets/vendor/bootstrap/css/bootstrap.css" rel="stylesheet" id="bootstrap-css">
        <link href="../../assets/vendor/material-icons/css/material-icons.css" rel="stylesheet">
        <link href="../../assets/css/tela_turma.css" rel="stylesheet">
        <title>Classio - Inovando sua turma</title>
    </head>
    <body>
        <%
            TurmaDAO tdao = new TurmaDAO();
            Turma t = new Turma();
            int codigo = Integer.parseInt(request.getParameter("codigo"));
            String professor = session.getAttribute("loginUsuario").toString();
            t = tdao.consulta(codigo);
        %>
        <nav class="navbar navbar-expand-lg navbar-light bg-dark">
            <a class="navbar-brand" href="../professor/homeProfessor.jsp"><img class="img-fluid" src="../../assets/img/logo_navbar.png"></img></a>
            <div class="navbar-collapse" id="navbarNavDropdown">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="listaAvaliacoes.jsp?codigo=<%= t.getTurcodigo()%>" style="color: white;">Avaliações</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="listaAlunos.jsp?codigo=<%= t.getTurcodigo()%>" style="color: white;">Alunos</a>
                    </li>
            </div>
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a href="calendarioTurma.jsp?codigo=<%=codigo%>" style="margin-left: -60px;" title="Agenda da Turma" name="agendaturma" class="material-icons mdi-2x nav-link text-white">event</a>
                </li>
                <li class="nav-item">

                    <a href="#" data-toggle="modal" data-target="#relatorioModal"  style="margin-right: 10px;" title="Relatório da Turma" name="relatorioturma" class="material-icons mdi-2x nav-link text-white">assignment</a>
                    <a href="#" title="Configurações da Turma" name="mais" class="material-icons mdi-2x nav-link  text-white" data-toggle="modal" data-target="#alteraTurmaModal">settings</a>
                </li>
            </ul>
        </nav>
        <!-- HEADER DA TURMA -->
        <div class="jumbotron jumbotron-fluid" id="jumbturma">
            <div class="container">
                <h1 class="display-12" id="tituloturma"><%= t.getTurdescricao()%></h1>
                <p class="lead" id="tituloturma">Esta turma foi criada em <%= t.getTurdtabertura()%>.</p>
                <small class="text-white">Código da Turma: <%=t.getTurcodigo()%></small>
            </div>
        </div>
        <!-- CONTAINER DA TURMA, MURAL PARA PUBLICAR MENSAGENS -->
        <div class="container">
            <div class="row">
                <!-- LISTA DE AVALIAÇÕES -->
                <div class="col-lg-4">
                    <h3 class="display-12">Próximas Avaliações</h3>
                    <div class="card" style="width: 20rem;">
                        <ul class="list-group list-group-flush">
                            <%
                                AvaliacaoDAO adao = new AvaliacaoDAO();

                                for (Avaliacao a : adao.listarAvaliacoesTurma(codigo)) {
                            %>
                            <a href="../avaliacao/homeAvaliacao.jsp?codigo=<%= a.getAvacodigo()%>" class="list-group-item list-group-item-action flex-column align-items-start">
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
                    <a href="listaAvaliacoes.jsp?codigo=<%= t.getTurcodigo()%>">Visualizar Tudo</a>
                </div>
                <br>
                <div class="col-lg-7">                               
                    <form name="frm" id="frm" method="post" autocomplete="off" action="../../ControllerMural">
                        <input type="hidden" name="idturma" id="idturma" value="<%=t.getTurcodigo()%>">
                        <input type="hidden" name="idprofessor" id="idprofessor" value="<%= t.getTurprocodigo().getProcodigo()%>">
                        <div class="input-group mb-3">
                            <input type="text" class="form-control" name="posta" id="posta" placeholder="Compartilhe algo com a turma" aria-label="Recipient's username" aria-describedby="button-addon2">
                            <div class="input-group-append">
                                <button type="submit" class="btn btn-primary" value="1" name="btn-mural"  id="btn-mural">Postar</button>
                            </div>
                        </div>
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
                                        <small><button type="submit" onclick="document.querySelector('frm').submit()" value="2" class="close" name="btn-mural" id="btn-mural"><span class="text-dark" aria-hidden="true">×</span></button></small>
                                    </div>
                                    <p class="mb-1"></p>
                                    <small class="text-muted pull-right" style="font-size: 10px; padding-bottom: 10px;">Postado em <%= m.getMurdtpostagem()%></small>
                                </a>                         
                            </div>
                            <br>
                            <% }%>
                        </div> 
                    </form>
                </div>
                <!-- ALUNOS QUE MERECEM ATENÇÃO -->
                <div class="col-lg-1">
                    <h3 class="display-12">&nbsp;&nbsp;&nbsp;&nbsp;</h3>
                </div>
            </div>  
        </div>  
        <!-- MODAL PARA ALTERAR TURMA -->
        <div class="modal fade" id="alteraTurmaModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <form method="post" autocomplete="off" action="../../ControllerTurma">
                        <div class="modal-header bg-dark">
                            <i class="material-icons mdi-2x text-white">edit</i><h5 class="modal-title display-4 text-white" style="font-size: 20px;" id="exampleModalLabel">&nbsp;&nbsp;Configurações da Turma</h5>
                            <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                                <span class="text-white" aria-hidden="true">×</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <input type="hidden" name="user" id="user" value="<%= session.getAttribute("loginUsuario")%>"> <!-- INPUT PARA PEGAR SESSION USUARIO -->
                            <input type="hidden" name="codigo" id="codigo" value="<%= t.getTurcodigo()%>"> <!-- INPUT PARA PEGAR CODIGO DA TURMA -->
                            <div class="form-group">    
                                <label for="descricao">Descrição:</label>
                                <input type="text" class="form-control" name="descricao" id="descricao" value="<%= t.getTurdescricao()%>">
                            </div>
                            <div class="form-group">    
                                <label for="media">Média:</label>
                                <input type="number" min="1" class="form-control" name="media" id="media" value="<%= t.getTurmedia()%>">
                            </div>
                            <div class="form-group">
                                <label for="dividenota">Vou dividir as notas por:</label>
                                <select name="dividenota" id="dividenota" class="custom-select custom-select-lg mb-3">
                                    <option value="<%= t.getTurdividenota()%>"><%= t.getTurdividenota()%></option>
                                    <option value="1">1</option>
                                    <option value="2">2</option>
                                    <option value="3">3</option>
                                    <option value="4">4</option>
                                    <option value="5">5</option>
                                </select>    
                            </div> 
                        </div>
                        <div class="modal-footer">
                            <button type="submit" class="btn btn-info btn-lg btn-block" value="2" name="btn-turma" id="btn-turma">Editar Turma</button>
                            <button type="submit" class="btn btn-light " value="3" name="btn-turma" id="btn-turma"><i class="material-icons mdi-2x">archive</i></button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <!-- MODAL RELATÓRIO -->
        <div class="modal fade" id="relatorioModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Você deseja gerar relatório da turma?</h5>
                        <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">×</span>
                        </button>
                    </div>
                    <div class="modal-body">Clique em "SIM" se você desejar gerar o relatório!</div>
                    <div class="modal-footer">
                        <button class="btn btn-secondary" type="button" data-dismiss="modal">Voltar</button>
                        <a class="btn btn-primary" href="relatorio/relatorioTurma.jsp?codigo=<%=codigo%>">Sim</a>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
