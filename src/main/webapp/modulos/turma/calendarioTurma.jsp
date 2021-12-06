<%--
    Document   : calendarioTurma
    Created on : 04/10/2018, 13:11:09
    Author     : vitor_9g3eyo1
--%>

<%@page import="br.com.classio.model.beans.Avaliacao"%>
<%@page import="br.com.classio.model.dao.AvaliacaoDAO"%>
<%@page import="br.com.classio.model.beans.TipoAvaliacao"%>
<%@page import="br.com.classio.model.dao.TipoAvaliacaoDAO"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="br.com.classio.model.dao.TurmaDAO"%>
<%@page import="br.com.classio.model.beans.Turma"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html;charset=iso-8859-1" />
        <link rel='stylesheet' href='../../assets/vendor/fullcalendar-3.9.0/fullcalendar.css' />
        <link href="../../assets/vendor/bootstrap/css/bootstrap.css" rel="stylesheet" id="bootstrap-css">
        <link href="../../assets/vendor/material-icons/css/material-icons.css" rel="stylesheet">
        <script src="../../assets/js/jquery.min.js"></script>
        <script src="../../assets/vendor/popper/js/popper.min.js"></script>
        <script src="../../assets/vendor/bootstrap/js/bootstrap.min.js"></script>
        <script src='../../assets/vendor/fullcalendar-3.9.0/lib/moment.min.js'></script>
        <script src='../../assets/vendor/fullcalendar-3.9.0/fullcalendar.min.js'></script>
        <script src='../../assets/vendor/fullcalendar-3.9.0/locale/pt-br.js'></script>
    </head>

    <body>
        <%
            int codigo = Integer.parseInt(request.getParameter("codigo"));
            TurmaDAO tdao = new TurmaDAO();
            Turma t = new Turma();
            t = tdao.consulta(codigo);
        %>
        <nav class="navbar navbar-expand-lg navbar-light bg-dark">
            <a class="navbar-brand" href="../professor/homeProfessor.jsp"><img class="img-fluid" src="../../assets/img/logo_navbar.png"></img></a>
            <div class="navbar-collapse">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link text-white" href="homeTurma.jsp?codigo=<%= codigo%>">Calendário da Turma - <%= t.getTurdescricao()%></a>
                    </li>
                </ul>
            </div>
        </nav>
        <div class="container-fluid bg-light">
            <br>
            <div id='calendar'></div>
        </div>
        <!-- Modal Exibe Informação Avaliação -->
        <div class="modal fade" id="modalAvaliacao" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Informacao da Avaliacao</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <h2>INFORMACOES DA AVALIACAO</h2>
                    </div>
                    <div class="modal-footer">
                        <div class="btn-group" role="group">
                            <button type="button" class="btn btn-light" data-dismiss="modal">Voltar</button>
                            <button type="button" class="btn btn-light">Visualizar</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--MODAL PARA NOVA AVALIACAO-->
        <form id="frm" name="frm" method="POST" autocomplete="off" action="../../ControllerAvaliacao" enctype="multipart/form-data">
            <!-- MODAL PARA NOVA AVALIACAO -->
            <div class="modal fade" id="criaAvaliacao"  tabindex="-1" role="dialog" aria-labelledby="modalAvaliacao" aria-hidden="true">
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
                                <input type="hidden" readonly="readonly" class="form-control" name="dataentrega" id="dataentrega" required>
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
                                        <input type="checkbox" value="envia" class="form-check-input" name="checkmensagem" id="checa">
                                        <label class="form-check-label" for="checa">ENVIAR</label>
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
                                <input type="date"  class="form-control" name="dtabertura" id="dtabertura">
                            </div>
                        </div>
                        <div class="modal-footer">
                            <div class="btn-group btn-group-lg" role="group" aria-label="Basic example">
                                <button type="button" class="btn btn-info " data-dismiss="modal">Voltar</button>
                                <button type="submit" value="1" name="btn-avaliacao" class="btn btn-info">Programar</button>
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
                        <% AvaliacaoDAO adao = new AvaliacaoDAO();
                            for (Avaliacao a : adao.listarAvaliacoes(codigo)) {
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