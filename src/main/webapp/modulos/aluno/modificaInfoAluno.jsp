<%--
    Document   : modificaInfoAluno
    Created on : 15/09/2018, 14:43:49
    Author     : vitor_9g3eyo1
--%>

<%@page import="br.com.classio.model.dao.UsuarioDAO"%>
<%@page import="br.com.classio.model.dao.AlunoDAO"%>
<%@page import="br.com.classio.model.beans.Usuario"%>
<%@page import="br.com.classio.model.beans.Aluno"%>
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
        <% String user = String.valueOf(session.getAttribute("loginUsuario"));
            Aluno a = new Aluno();
            Usuario u = new Usuario();
            AlunoDAO adao = new AlunoDAO();
            UsuarioDAO udao = new UsuarioDAO();
            u = udao.consulta(user);
            a = adao.consultaId(u.getUsucodigo());
        %>
        <nav class="navbar navbar-expand-lg navbar-light bg-dark">
            <a class="navbar-brand" href="homeAluno.jsp"><img class="img-fluid" src="../../assets/img/logo_navbar.png"></img></a>
        </nav>
        <div class="container">
            <br>
            <br>
            <div class="row">
                <div class="col-lg-4">
                    <div class="card bg-light" style="width: 100%;">
                        <ul class="list-group" style="cursor: pointer;">
                            <li class="list-group-item disabled bg-light"><i class="material-icons">edit</i>&nbsp;&nbsp;Editar Perfil</li>
                            <li style="text-decoration: none;" data-toggle="modal" data-target="#modalSenha" class="list-group-item nav-link bg-light"><i class="material-icons">more_horiz</i>&nbsp;&nbsp;Editar Senha</li>
                        </ul>
                    </div>
                </div>
                <div class="col-lg-8 bg-light">
                    <h4 class="modal-title display-4 text-muted" style="font-size: 38px;">Editar Perfil</h4>
                    <form method="post" action="../../ControllerAlteraUsuario">
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
                            <input type="text" class="form-control" maxlength="11" name="contato" id="contato" value="<%= a.getAlutelefone()%>" required>
                        </div>
                        <button type="submit" name="tipousuario" id="tipousuario" style="margin-bottom: 15px;" value="1" class="btn btn-info btn-lg btn-block">Salvar Altera??es</button>
                    </form>
                </div>
            </div>
        </div>
        <!-- MODAL PARA ALTERA??O DE SENHA -->
        <div class="modal fade" id="modalSenha" tabindex="-1" role="dialog" aria-labelledby="modalSenha" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <form name="form" method="POST" action="../../ControllerAlteraSenha">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLabel">Editar Senha</h5>
                            <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                                <span class="text-muted" aria-hidden="true">?</span>
                            </button>
                        </div>
                        <input type="hidden" name="codigo" id="codigo" value="<%= u.getUsucodigo()%>"> 
                        <div class="modal-body">
                            <div class="form-group">
                                <label for="senhaantiga">Senha Antiga:</label>
                                <input type="password" class="form-control" name="senhaantiga" id="senhaantiga">
                            </div>
                            <div class="form-group">
                                <label for="novasenha">Nova Senha:</label>
                                <input type="password" class="form-control" name="novasenha" id="novasenha">
                            </div>
                            <div class="form-group">
                                <label for="cnovasenha">Confirme a Nova Senha:</label>
                                <input type="password" class="form-control" name="cnovasenha" id="cnovasenha">
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="submit" class="btn btn-info btn-lg btn-block" name="cadastro" id="cadastro">Alterar Senha</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>