<%-- 
    Document   : listaAvaliacoes
    Created on : 16/09/2018, 13:15:34
    Author     : vitor_9g3eyo1
--%>

<%@page import="br.com.classio.controller.ControllerAvaliacao"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="br.com.classio.model.beans.Turma"%>
<%@page import="br.com.classio.model.dao.TurmaDAO"%>
<%@page import="br.com.classio.model.beans.TipoAvaliacao"%>
<%@page import="br.com.classio.model.dao.TipoAvaliacaoDAO"%>
<%@page import="br.com.classio.model.beans.Avaliacao"%>
<%@page import="br.com.classio.model.dao.AvaliacaoDAO"%>
<%   if (session.getAttribute("loginUsuario") == null) {
        out.println("<script>location='../../index.html';</script>");
    }%>
<!DOCTYPE html>
<html>
    <header>
        <meta http-equiv="Content-Type" content="text/html;charset=iso-8859-1" />
        <script src="../../assets/js/jquery.min.js"></script>
        <script src="../../assets/vendor/popper/js/popper.min.js"></script>
        <script src="../../assets/vendor/bootstrap/js/bootstrap.min.js"></script>
        <link href="../../assets/vendor/bootstrap/css/bootstrap.css" rel="stylesheet" id="bootstrap-css">    
        <link href="../../assets/vendor/material-icons/css/material-icons.css" rel="stylesheet">
        <title>Classio - Inovando sua turma</title>
    </header>
    <body onload="">
        <%
            int codigo = Integer.parseInt(request.getParameter("codigo"));
            Turma t = new Turma();
            TurmaDAO tdao = new TurmaDAO();
            t = tdao.consulta(codigo);
        %>
        <style type="text/css">
            #divCenter
            {
                width: 80%;
                height:auto;
                margin:40px auto 0 auto;

            }
        </style>
        <nav class="navbar navbar-expand-lg navbar-light bg-dark">
            <a class="navbar-brand" href="../professor/homeProfessor.jsp"><img class="img-fluid" src="../../assets/img/logo_navbar.png"></img></a>
            <div class="navbar-collapse" id="navbarNavDropdown">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item">
                        <a class="nav-link active" href="listaAvaliacoes.jsp?codigo=<%= codigo%>" style="color: white;">Avaliações</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="listaAlunos.jsp?codigo=<%= codigo%>" style="color: white;">Alunos</a>
                    </li>
                </ul>    
            </div>
            <div>
                <a href="../turma/homeTurma.jsp?codigo=<%=codigo%>"<h4 class="text-white"><%= t.getTurdescricao()%></h4></a>
            </div>
        </nav>
        <div class="container">
            <div class="row">
                <div id="divCenter" class="col-mb-6 centered">
                    <button type="button" name="criaAvaliacao" data-toggle="modal" data-target="#modalAvaliacao" class="btn btn-light btn-lg"><i style="" class="material-icons mdi-2x pull-left">add</i><span style="margin-top: 5px;" class="pull-right">&nbsp;CRIAR AVALIAÇÃO</span></button>
                    <br>
                    <br>
                    <h2>LISTA DE AVALIAÇÕES</h2>
                    <div class="progress">
                        <div class="progress-bar bg-success" role="progressbar" style="width: <%= tdao.progressoTurma(t.getTurcodigo())%>%" aria-valuenow="<%= tdao.progressoTurma(t.getTurcodigo())%>" aria-valuemin="0" aria-valuemax="100"><%=Math.round(tdao.progressoTurma(t.getTurcodigo()))%>%</div>
                    </div>
                    <br>
                    <div id="accordion">
                        <%
                            AvaliacaoDAO adao = new AvaliacaoDAO();

                            for (Avaliacao a : adao.listarAvaliacoes(codigo)) {
                        %>
                        <a style="text-decoration: none;" class="text-muted" href="#">
                            <div class="card">
                                <div class="card-header" id="headingTwo">
                                    <div>
                                        <img class="img-fluid" style="display: block; float: left; padding-right: 10px;" src="../../assets/img/avaliacao.png"></img>
                                        <h5 class="mb-0" data-toggle="collapse" data-target="#collapse<%= a.getAvacodigo()%>" aria-expanded="false" aria-controls="collapseTwo">
                                            <%= a.getAvadescricao()%></h5>
                                        <small class="text-muted">Prazo definido em: <%= a.getAvadtentrega() %></small>
                                        <a href="../avaliacao/homeAvaliacao.jsp?codigo=<%= a.getAvacodigo()%>" name="visualiza" style="margin-top: -15px;" class="material-icons mdi-2x pull-right text-muted">remove_red_eye</a>
                                    </div>  
                                </div>
                                <div id="collapse<%= a.getAvacodigo()%>" class="collapse" aria-labelledby="headingTwo" data-parent="#accordion">
                                    <div class="card-body">
                                        <p>Nota: <%= a.getAvanota()%> </p>
                                        <p>Prazo: <%= a.getAvadtentrega()%></p>
                                        <p>Observação: <%= a.getAvaobservacao()%> </p>
                                    </div>
                                </div>
                            </div></a>
                            <%                            }
                            %>
                    </div>
                </div>
            </div>                 
        </div>
        <!-- MODAL PARA NOVA AVALIACAO -->                     
        <form id="frm" name="frm" method="POST" autocomplete="off" action="../../ControllerAvaliacao" enctype="multipart/form-data">
            <!-- MODAL PARA NOVA AVALIACAO -->
            <div class="modal fade" id="modalAvaliacao"  tabindex="-1" role="dialog" aria-labelledby="modalAvaliacao" aria-hidden="true">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header bg-dark">
                            <i class="material-icons mdi-2x text-white">content_paste</i><h5 class="modal-title display-4 text-white" style="font-size: 20px;" id="exampleModalLabel">&nbsp;&nbsp;Nova Avaliação</h5>
                            <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                                <span class="text-white" aria-hidden="true">×</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <input type="hidden" name="user" id="user" value="<%= session.getAttribute("loginUsuario")%>"> <!-- INPUT PARA PEGAR SESSION USUARIO -->
                            <input type="hidden" name="turma" id="turma" value="<%= codigo%>"> <!-- INPUT PARA PEGAR SESSION USUARIO -->
                            <div class="form-group">
                                <label for="descricao">Descrição:</label>
                                <input type="text" class="form-control" name="descricao" id="descricao" required>
                            </div>
                            <div class="form-group">
                                <label for="observacao">Observação: <span class="text-muted">(Instruções)</span></label>
                                <textarea class="form-control" name="observacao" id="observacao" rows="2"></textarea>
                            </div>
                            <div class="row">
                                <div class="col">
                                    <label for="nota">Nota:</label>
                                    <input type="number" min="0"  class="form-control" name="nota" id="nota" required>
                                </div>
                                <div class="col">
                                    <%
                                        Date date = new Date();
                                        String data = new SimpleDateFormat("yyyy/MM/dd").format(date);
                                    %>
                                    <label for="dataentrega">Prazo:</label>
                                    <input type="date" min="<%= data%>" class="form-control" name="dataentrega" id="dataentrega" required>
                                </div>
                                <div class="col">
                                    <label for="tipo">Tipo:</label>
                                    <select name="tipo" id="tipo" class="custom-select">
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
                                <div class="col">
                                    <label for="checkmensagem">Notificação?</label>
                                    <div class="form-group form-check">
                                        <input type="checkbox" value="envia" class="form-check-input" name="checkmensagem" id="checkmensagem">
                                        <label class="form-check-label" for="checkmensagem">ENVIAR</label>
                                    </div>   
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <div class="row">
                                <div class="col">
                                    <div>
                                        <label for="anexo" title="Anexar Arquivo"><i class="material-icons mdi-2x text-muted" style="cursor: pointer; margin-top:10px;">attach_file</i></label>
                                        <input type="file" style="width: 10px; display: none;" accept="application/pdf" id="anexo" name="anexo">
                                    </div>
                                </div>
                                <div class="col">
                                    <div class="btn-group btn-lg">
                                        <button type="submit" onclick="document.querySelector('frm').submit()" value="1" name="btn-avaliacao" class="btn btn-info btn-block">
                                            Criar Avaliação
                                        </button>
                                        <button class="btn btn-lg btn-info dropdown-toggle dropdown-toggle-split" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                            <span class="sr-only">Toggle Dropdown</span>
                                        </button>
                                        <div class="dropdown-menu">
                                            <button type="submit" class="dropdown-item" name="btn-avaliacao" value="1">Criar Avaliação</button>
                                            <a class="dropdown-item" href="#modalAgenda" data-toggle="modal" data-target="#modalAgenda">Programar</a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
            <!-- MODAL PARA AGENDAR AVALIAÇÃO -->
            <div class="modal fade" id="modalAgenda"  tabindex="1" role="dialog" aria-labelledby="modalAgenda" aria-hidden="true">
                <div class="modal-dialog modal-sm  modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header bg-dark">
                            <h5 class="modal-title display-4 text-white" style="font-size: 20px;" id="exampleModalLabel">&nbsp;&nbsp;Programar Avaliação</h5>
                            <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                                <span class="text-white" aria-hidden="true">×</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <div class="col">
                                <label for="dtabertura">Programar para:</label>
                                <input type="date" min="<%= data%>" class="form-control" name="dtabertura" id="dtabertura">
                            </div>
                        </div>
                        <div class="modal-footer">
                            <div class="btn-group btn-group-lg" role="group" aria-label="Basic example">
                                <button type="button" class="btn btn-info " data-dismiss="modal">Voltar</button>
                                <button type="submit" value="1" name="btn-avaliacao" class="btn btn-outline-info">Programar</button>
                            </div>
                        </div>
                    </div>
                </div>
                <input type="hidden" name="checkagenda" id="checkagenda" value="1"> // SE O VALOR MUDAR PARA 2, A AVALIAÇÃO É AGENDADA
                <script>
                    $('#modalAgenda').on('show.bs.modal', function (e) {
                        document.getElementById("dtabertura").required = true;
                        document.getElementById("checkagenda").value = 2;

                    });
                    $('#modalAgenda').on('hide.bs.modal', function (e) {
                        document.getElementById("dtabertura").required = false;
                        document.getElementById("checkagenda").value = 1;
                    });
                </script>
            </div>
        </form>
    </body>
</html>
