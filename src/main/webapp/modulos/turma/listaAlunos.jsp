<%-- 
    Document   : listaAlunos
    Created on : 16/09/2018, 14:40:36
    Author     : vitor_9g3eyo1
--%>

<%@page import="br.com.classio.model.beans.Turma"%>
<%@page import="br.com.classio.model.dao.TurmaDAO"%>
<%@page import="br.com.classio.model.beans.Matricula"%>
<%@page import="br.com.classio.model.dao.MatriculaDAO"%>
<%@page import="br.com.classio.model.beans.Aluno"%>
<%@page import="br.com.classio.model.dao.AlunoDAO"%>
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
        <title>Classio - Inovando sua turma</title>
    </head>
    <body>
        <%
            int codigo = Integer.parseInt(request.getParameter("codigo"));
            Turma t = new Turma();
            TurmaDAO tdao = new TurmaDAO();
            t = tdao.consulta(codigo);
        %>
        <style type="text/css">
            #divCenter
            {
                width: 70%;
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
            </div>
            <div>
                <input type="hidden" id="codigoturma" value="<%=codigo%>">
                <a href="../turma/homeTurma.jsp?codigo=<%=codigo%>"<h4 class="text-white"><%= t.getTurdescricao()%></h4></a>
            </div>
        </nav>
        <div class="container">          
            <div class="row">             
                <div id="divCenter" class="col-mb-6 centered">                
                    <h2>Alunos</h2>
                    <ul class="list-group">
                        <%
                            MatriculaDAO mdao = new MatriculaDAO();
                            for (Matricula a : mdao.listarAlunos(codigo)) {
                        %>
                        <li class="list-group-item "><i class="material-icons pull-left mdi-2x">person</i>
                            &nbsp;&nbsp; <%= a.getMatalucodigo().getAluusucodigo().getUsunome()%>
                            <small>- <%= a.getMatalucodigo().getAlutelefone()%></small>
                            <a data-toggle="modal" data-target="#relatorioIndividual" style="cursor: pointer; text-decoration: none;" data-whatever="<%= a.getMatalucodigo().getAlucodigo()%>" class="material-icons pull-right text-black-50 mdi-2x">assignment</a></li> 
                            <%
                                }
                            %>
                    </ul>
                </div>
            </div>
        </div>
        <!-- MODAL RELATÓRIO -->
        <div class="modal fade" id="relatorioIndividual" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Você deseja gerar relatório do aluno?</h5>
                        <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">×</span>
                        </button>
                    </div>
                    <div class="modal-body">Clique em "SIM" se você desejar gerar o relatório!</div>
                    <input type="hidden" id="idaluno" value="">
                    <div class="modal-footer">
                        <button class="btn btn-secondary" type="button" data-dismiss="modal">Voltar</button>
                        <a class="btn btn-primary" onclick="relatorio()" href="#">Sim</a>
                    </div>
                </div>
            </div>
        </div>
        <script>
            $('#relatorioIndividual').on('show.bs.modal', function (event) {
                var button = $(event.relatedTarget) // Button that triggered the modal
                var codigo = button.data('whatever') // Extract info from data-* attributes
// If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
// Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
                var modal = $(this)
                document.getElementById("idaluno").value = codigo              
            })
            function relatorio() {
                var id = document.getElementById("idaluno").value;
                var idturma = document.getElementById("codigoturma").value;
                location = 'relatorio/relatorioIndividual.jsp?codigo='+id+'&codigoturma='+idturma;
            }
        </script>
    </body>
</html>
