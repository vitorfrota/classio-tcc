<%-- 
Document   : cadastroUsuario
Created on : 29/08/2018, 14:19:28
Author     : vitor_9g3eyo1
--%>

<%@page import="br.com.classio.model.dao.TipoUsuarioDAO"%>
<%@page import="br.com.classio.model.beans.TipoUsuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link href="../assets/vendor/bootstrap/css/bootstrap.css" rel="stylesheet" id="bootstrap-css">
        <link href="../assets/css/pagina_login.css" rel="stylesheet">
        <script src="../assets/vendor/bootstrap/js/bootstrap.js"></script>
        <script src="../assets/js/jquery.min.js"></script>
        <script src="../assets/js/verificaUsuario.js"></script>
    </head>
    <body id="LoginForm">
        <div class="container">
            <div class="login-form">
                <div class="main-div">
                    <div class="panel">
                        <h1>Cadastre-se agora!</h1>
                    </div>
                    <form name="form" autocomplete="off" method="post" action="../ControllerUsuario">
                        <label for="tipo">Eu sou:</label>
                        <select name="tipo" id="tipo" onchange="mudaCampo()"  class="custom-select custom-select-lg mb-3">                         
                            <% TipoUsuarioDAO tdao = new TipoUsuarioDAO();
                                for (TipoUsuario t : tdao.listarTipos()) {
                            %>                            
                            <option value="<%= t.getTipcodigo()%>"><%= t.getTipdescricao()%></option>
                            <%
                                }
                            %>
                        </select>   
                        <input type="hidden" value="1" id="tipousuario">
                        <div class="form-group">
                            <label for="nome">Nome:</label>  
                            <input type="text" class="form-control" name="nome" id="nome" required>
                        </div>
                        <div class="form-group">
                            <label id="labelcontato" for="contato">Telefone:</label> 
                            <input type="number" class="form-control" maxlength="11" name="contato" id="contato" required>
                        </div>
                        <div class="form-group">
                            <label for="login">Login:</label>  
                            <input type="text" class="form-control" name="login" id="login" required>
                        </div>
                        <div class="form-group">
                            <label for="senha">Senha:</label>
                            <input type="password" class="form-control" name="senha" minlength="5" id="senha" required>
                        </div>
                        <button type="submit" name="btn-usuario" value="1" class="btn btn-primary">Cadastrar</button>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
