<%-- 
    Document   : homeAvaliacao
    Created on : 25/09/2018, 17:29:24
    Author     : vitor_9g3eyo1
--%>

<%@page import="br.com.classio.model.dao.LancamentoNotaDAO"%>
<%@page import="br.com.classio.model.beans.Aluno"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="br.com.classio.model.beans.TipoAvaliacao"%>
<%@page import="br.com.classio.model.dao.TipoAvaliacaoDAO"%>
<%@page import="br.com.classio.model.beans.Matricula"%>
<%@page import="br.com.classio.model.dao.MatriculaDAO"%>
<%@page import="br.com.classio.model.dao.AvaliacaoDAO"%>
<%@page import="br.com.classio.model.beans.Avaliacao"%>
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
        <link href="../../assets/css/tela_avaliacao.css" rel="stylesheet">
        <title>Classio - Inovando sua turma</title>
    </head>
    <%
        int codigo = Integer.parseInt(request.getParameter("codigo"));
        Avaliacao a = new Avaliacao();
        AvaliacaoDAO adao = new AvaliacaoDAO();
        LancamentoNotaDAO ldao = new LancamentoNotaDAO();
        a = adao.consultaId(codigo);
    %>
    <body>
        <nav class="navbar navbar-expand-lg navbar-light bg-nav">
            <a class="navbar-brand" href="../professor/homeProfessor.jsp"><img class="img-fluid" src="../../assets/img/logo_navbar.png"></img></a>
            <div class="collapse navbar-collapse" id="navbarNavDropdown">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item">
                        <h5><a class="nav-link" href="../turma/homeTurma.jsp?codigo=<%= a.getAvaturcodigo().getTurcodigo()%>" style="color: white;"><%= a.getAvaturcodigo().getTurdescricao()%></a></h5>
                    </li>
            </div>
            <ul class="navbar-nav">
                <div class="nav-item">
                    <a href="#" title="Configurações da Avaliação" name="mais" class="material-icons nav-link mdi-2x text-white" data-toggle="modal" data-target="#alteraAvaliacaoModal" aria-haspopup="true" aria-expanded="false">settings</a>
                </div>
            </ul>
        </nav>
        <!-- HEADER DA AVALIAÇÃO -->
        <div class="jumbotron jumbotron-fluid" id="jumbturma">
            <div class="container">
                <h1 class="display-12" id="tituloturma"><%= a.getAvadescricao()%></h1>
                <p class="lead" id="tituloturma">Esta avaliação foi postada em <%= a.getAvadtabertura()%>.</p>
            </div>
        </div>
        <!-- CONTAINER DA TURMA, MURAL PARA PUBLICAR MENSAGENS -->
        <div class="container">
            <div class="row">
                <!-- LISTA DE AVALIAÇÕES -->
                <div class="col-lg-4">
                    <h3 class="display-12">Informações da Avaliação</h3>
                    <div class="card" style="width: 20rem;">
                        <ul class="list-group">
                            <li class="list-group-item"><b>Tipo:</b> <%= a.getAvatipcodigo().getTipdescricao()%></li>
                            <li class="list-group-item"><b>Descrição:</b> <%= a.getAvadescricao()%></li>
                            <li class="list-group-item"><b>Nota:</b> <%= a.getAvanota()%></li>
                            <li class="list-group-item"><b>Prazo:</b> <%= a.getAvadtentrega()%></li>
                            <li class="list-group-item"><b>Observação:</b> <%= a.getAvaobservacao()%></li>
                        </ul>
                    </div>
                </div>
                <div id="panelmural" class="col-lg-7 bg-light">
                    <h3 id="titulomural" class="display-12">Lançamento das Notas</h3>
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th scope="col">&nbsp;</th>
                                <th scope="col">Aluno</th>
                                <th scope="col">Nota</th>
                                <th scope="col">&nbsp;</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                int cont = 0;
                                MatriculaDAO aldao = new MatriculaDAO();
                                for (Aluno al : ldao.listarAlunosSemNota(a.getAvaturcodigo().getTurcodigo(), a.getAvacodigo())) {
                            %>
                            <tr>
                                <th scope="row"><%= cont = cont + 1%></th>                
                        <form method="post" action="../../ControllerNotas">
                            <input type="hidden" name="idaluno" id="idaluno" value="<%= al.getAlucodigo()%>">
                            <input type="hidden" name="idavaliacao" id="idavaliacao" value="<%= a.getAvacodigo()%>">
                            <td><%= al.getAluusucodigo().getUsunome()%></td>
                            <td><input type="number" min="0" max="<%= a.getAvanota()%>" step="0.01" name="nota" id="nota" required></td>
                            <td><button class="btn btn-outline-info" type="submit"><i class="material-icons mdi-2x">send</i></button></td>
                            <td><button class="btn btn-outline-info" type="button"><i data-toggle="modal" data-target="#modalFeedback" class="material-icons mdi-2x">message</i></button></td>
                            <div class="modal fade" id="modalFeedback"  tabindex="1" role="dialog" aria-labelledby="modalFeedback" aria-hidden="true">
                                <div class="modal-dialog modal-sm  modal-dialog-centered">
                                    <div class="modal-content">
                                        <div class="modal-header bg-dark">
                                            <h5 class="modal-title display-4 text-white" style="font-size: 20px;" id="exampleModalLabel">&nbsp;&nbsp;Enviar Feedback</h5>
                                            <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                                                <span class="text-white" aria-hidden="true">×</span>
                                            </button>
                                        </div>
                                        <div class="modal-body">
                                            <div class="col">
                                                <label for="dtabertura">Feedback:</label>
                                                <input type="text" maxlength="50" class="form-control"  name="feedback" id="feedback">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </form>    
                        </tr>
                        <%
                            }
                        %>
                        </tbody>
                    </table> 
                </div>
                <!-- ESPAÇO -->
                <div class="col-lg-1">
                    <h3 class="display-12">&nbsp;&nbsp;&nbsp;&nbsp;</h3>
                </div>
            </div>  
        </div>  
        <!-- MODAL PARA ALTERAR AVALIAÇÃO -->                    
        <div class="modal fade" id="alteraAvaliacaoModal"  tabindex="-1" role="dialog" aria-labelledby="alteraAvaliacaoModal" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <form name="form" method="POST" action="../../ControllerAvaliacao" enctype="multipart/form-data">
                        <div class="modal-header bg-dark">
                            <i class="material-icons mdi-2x text-white">content_paste</i><h5 class="modal-title display-4 text-white" style="font-size: 20px;" id="exampleModalLabel">&nbsp;&nbsp;Editar Avaliação</h5>
                            <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                                <span class="text-white" aria-hidden="true">×</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <input type="hidden" name="codigo" id="codigo" value="<%= a.getAvacodigo()%>"> <!-- INPUT PARA PEGAR CODIGO DA AVALIACAO -->
                            <input type="hidden" name="turma" id="turma" value="<%= a.getAvaturcodigo().getTurcodigo()%>">
                            <div class="form-group">
                                <label for="descricao">Descrição:</label>
                                <input type="text" class="form-control" name="descricao"value="<%= a.getAvadescricao()%>" id="descricao" required>
                            </div>
                            <div class="form-group">
                                <label for="observacao">Observação: <span class="text-muted">(Instruções)</span></label>
                                <textarea class="form-control" name="observacao" id="observacao" value="<%=a.getAvaobservacao()%>" rows="2"><%=a.getAvaobservacao()%></textarea>
                            </div>
                            <div class="row">
                                <div class="col">
                                    <label for="nota">Nota:</label>
                                    <input type="number" min="0" class="form-control" value="<%=a.getAvanota()%>" name="nota" id="nota" required>
                                </div>
                                <div class="col">
                                    <%
                                        Date date = new Date();
                                        String data = new SimpleDateFormat("dd/MM/yyyy").format(date);
                                    %>
                                    <label for="dataentrega">Prazo:</label>
                                    <input type="date" min="<%= data%>" class="form-control" value="<%= a.getAvadtentrega()%>" name="dataentrega" id="dataentrega" required>
                                </div>
                                <div class="col">
                                    <label for="tipo">Tipo:</label>
                                    <select name="tipo" id="tipo" class="custom-select">
                                        <option value ="<%= a.getAvatipcodigo().getTipcodigo()%>"><%= a.getAvatipcodigo().getTipdescricao()%></option>
                                        <%
                                            TipoAvaliacaoDAO tipdao = new TipoAvaliacaoDAO();
                                            for (TipoAvaliacao tipo : tipdao.listarTipos()) {

                                        %>
                                        }
                                        <option value="<%= tipo.getTipcodigo()%>"><%= tipo.getTipdescricao()%></option>
                                        <%
                                            }
                                        %>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="submit" class="btn btn-info btn-lg btn-block" value="2" name="btn-avaliacao" id="btn-avaliacao">Editar Avaliação</button> 
                            <button type="submit" class="btn btn-outline-light" value="3" name="btn-avaliacao" id="btn-avaliacao"><i class="material-icons mdi-2x text-muted">delete</i></button>
                        </div>
                    </form>
                </div>
            </div>
        </div>


    </body>
</html>
