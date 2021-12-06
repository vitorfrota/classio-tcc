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
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <link rel='stylesheet' href='../../assets/vendor/fullcalendar-3.9.0/fullcalendar.css' />
        <link href="../../assets/vendor/bootstrap/css/bootstrap.css" rel="stylesheet" id="bootstrap-css">
        <link href="../../assets/vendor/material-icons/css/material-icons.css" rel="stylesheet">
        <script src="../../assets/js/jquery.min.js"></script>
        <script src="../../assets/vendor/bootstrap/js/bootstrap.min.js"></script>
        <script src='../../assets/vendor/fullcalendar-3.9.0/lib/moment.min.js'></script>
        <script src='../../assets/vendor/fullcalendar-3.9.0/fullcalendar.min.js'></script>
        <script src='../../assets/vendor/fullcalendar-3.9.0/locale/pt-br.js'></script>
    </head>

    <body>
        <% 
            int codigo = Integer.parseInt(request.getParameter("codigo"));
        %>
        <nav class="navbar navbar-expand-lg navbar-light bg-dark">
            <a class="navbar-brand" href="../professor/homeProfessor.jsp"><img class="img-fluid" src="../../assets/img/logo_navbar.png"></img></a>
            <div class="navbar-collapse">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link text-white" href="homeProfessor.jsp">Meu Calendário</a>
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
                        for (Avaliacao a : adao.listarAvaliacoesProfessor(codigo)) {
                    %>  
                        {
                                id: <%= a.getAvacodigo() %>,
                                title: '<%= a.getAvaturcodigo().getTurdescricao() %> - <%= a.getAvadescricao() %> ',
                                start: '<%= a.getAvadtentrega()%>'
                            },
                                    
                     <% } %>
                        ],
                 
                        color: '#cedd37', // an option!
                        textColor: 'white' // an option!
                    }

                ],

                eventClick: function (calEvent, jsEvent, view) {
                location='../avaliacao/homeAvaliacao.jsp?codigo='+ calEvent.id;                          
                },
                dayClick: function (date) {
                    $('#criaAvaliacao').modal('show');
                    document.getElementById('dataentrega').value = date.format(); // para criar nova avaliação, é passada a data clicada como parametro

                },

            })

        });
    </script>
</html>