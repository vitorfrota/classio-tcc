<%--
    Document   : modificaInfoProfessor
    Created on : 15/09/2018, 14:43:49
    Author     : vitor_9g3eyo1
--%>

<%@page import="br.com.classio.model.beans.Turma"%>
<%@page import="br.com.classio.model.dao.TurmaDAO"%>
<%@page import="br.com.classio.model.dao.UsuarioDAO"%>
<%@page import="br.com.classio.model.dao.ProfessorDAO"%>
<%@page import="br.com.classio.model.beans.Usuario"%>
<%@page import="br.com.classio.model.beans.Professor"%>
<!DOCTYPE html>
<%   if(session.getAttribute("loginUsuario") == null){
    out.println("<script>location='../../index.html';</script>");
}%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html;charset=iso-8859-1" />
        <script src="../../assets/js/jquery.min.js"></script>
        <script src="../../assets/js/verificaUsuario.js"></script>
        <script src="../../assets/vender/popper/js/popper.min.js"></script>
        <script src="../../assets/vendor/bootstrap/js/bootstrap.min.js"></script>
        <link href="../../assets/vendor/bootstrap/css/bootstrap.css" rel="stylesheet" id="bootstrap-css">
        <link href="../../assets/vendor/material-icons/css/material-icons.css" rel="stylesheet">
        <link href="../../assets/css/tela_professor.css" rel="stylesheet">
        <title>Classio - Inovando sua turma</title>
    </head>
    <body>
        <% String user = String.valueOf(session.getAttribute("loginUsuario"));
            Professor p = new Professor();
            Usuario u = new Usuario();
            ProfessorDAO pdao = new ProfessorDAO();
            UsuarioDAO udao = new UsuarioDAO();

            u = udao.consulta(user); // RETORNA USUARIO
            p = pdao.consulta(u.getUsucodigo()); // RETORNA PROFESSOR
            int idprofessor = p.getProcodigo();
        %>
        <nav class="navbar navbar-expand-lg navbar-light bg-dark">
            <a class="navbar-brand" href="homeProfessor.jsp"><img class="img-fluid" src="../../assets/img/logo_navbar.png"></img></a>
        </nav>
        <br>
        <br>
        <div class="container-fluid">
            <div class="row">
                <div class="col-lg-4">
                    <div class="card bg-light" style="width: 100%;">
                        <ul class="list-group" style="cursor: pointer;">
                            <li class="list-group-item disabled bg-light"><i class="material-icons">edit</i>&nbsp;&nbsp;Editar Perfil</li>
                            <li data-toggle="modal" data-target="#modalSenha" class="list-group-item nav-link bg-light"><i class="material-icons">more_horiz</i>&nbsp;&nbsp;Editar Senha</li>
                            <li data-toggle="modal" data-target="#modalTurmasArquivadas" class="list-group-item bg-light"><i class="material-icons">list</i>&nbsp;&nbsp;Turmas Arquivadas</li>
                        </ul>
                    </div>
                </div>
                <div class="col-lg-7 bg-light">
                    <h4 class="modal-title display-4 text-muted" style="font-size: 38px;">Editar Perfil</h4>
                    <form method="post" action="../../ControllerUsuario">
                        <input type="hidden" name="codigo" id="codigo" value="<%= u.getUsucodigo()%>"> 
                        <div class="form-group">
                            <label for="nome">Nome:</label>
                            <input type="text" class="form-control" name="nome" id="nome" value="<%= u.getUsunome()%>" required>
                        </div>
                        <div class="form-group">
                            <label for="nome">Usuario:</label>
                            <input type="text" class="form-control" name="login" id="login" value="<%= u.getUsulogin()%>" required>
                        </div>
                        <div class="form-group">
                            <label for="nome">Contato:</label>
                            <input type="email" class="form-control" name="contato" id="contato" value="<%= p.getProemail()%>" required>
                        </div>
                        <input type="hidden" name="btn-usuario" value="2">
                        <button type="submit" name="tipo" id="tipo" style="margin-bottom: 15px;" value="2" class="btn btn-info btn-lg btn-block">Salvar Alterações</button>
                    </form>
                </div>
                <div class="col-lg-1">

                </div>
            </div>
        </div>
        <!-- MODAL PARA ALTERAÇÃO DE SENHA -->
        <div class="modal fade" id="modalSenha" tabindex="-1" role="dialog" aria-labelledby="modalSenha" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <form name="form" method="POST" action="../../ControllerAlteraSenha">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLabel">Editar Senha</h5>
                            <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                                <span class="text-muted" aria-hidden="true">×</span>
                            </button>
                        </div>
                        <input type="hidden" name="codigo" id="codigo" value="<%= u.getUsucodigo()%>">
                        <div class="modal-body">
                            <div class="form-group">
                                <label for="senhaantiga">Senha Antiga:</label>
                                <input type="password" minlength="5" class="form-control" name="senhaantiga" id="senhaantiga">
                            </div>
                            <div class="form-group">
                                <label for="novasenha">Nova Senha:</label>
                                <input type="password" minlength="5" class="form-control" name="novasenha" id="novasenha">
                            </div>
                            <div class="form-group">
                                <label for="cnovasenha">Confirme a Nova Senha:</label>
                                <input type="password" minlength="5" class="form-control" name="cnovasenha" id="cnovasenha">
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="submit" class="btn btn-info btn-lg btn-block" name="btn-usuario" value="3" id="btn-usuario">Alterar Senha</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div class="modal fade" id="modalTurmasArquivadas" tabindex="-1" role="dialog" aria-labelledby="modalTurmasArquivadas" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLabel">Turmas Arquivadas</h5>
                            <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                                <span class="text-muted" aria-hidden="true">×</span>
                            </button>
                        </div>
                        <div class="modal-body" style="max-height: 500px;">
                            <table class="table" style="display: block; height: 450px; overflow-y: scroll;">
                                <thead>
                                    <tr>
                                        <th scope="col">#</th>
                                        <th scope="col">Turma</th>
                                        <th scope="col">&nbsp;</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%
                                        int cont = 0;
                                        TurmaDAO tdao = new TurmaDAO();
                                        for (Turma t : tdao.listarTurmasArquivadas(p.getProcodigo())) {
                                    %> 
                                    <tr>
                                        <th scope="row"><%= cont = cont + 1%></th>
                                        <td><%= t.getTurcodigo() %></td>
                                        <td><%= t.getTurdescricao()%></td>
                                        <td>&nbsp;</td>
                                        <td>
                                            <a href="../turma/funcao/action_desarquiva.jsp?codigo=<%= t.getTurcodigo()%>" style=":hover{color: red};" class="material-icons mdi-2x text-muted">unarchive</a>
                                        </td>
                                    </tr>
                                    <%
                                        }
                                    %>
                                </tbody>
                            </table>
                        </div>
                </div>
            </div>
        </div>
    </body>
</html>