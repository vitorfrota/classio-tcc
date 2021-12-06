<%@page import="br.com.classio.model.beans.Usuario"%>
<%@page import="br.com.classio.model.dao.UsuarioDAO"%>

<!-- ESTA PÁGINA TEM COMO OBJETIVO DIFERENCIAR O USUÁRIO E REDIRECIONAR O USUÁRIO PARA SEU RESPECTIVO TIPO -->
<%
    Usuario usuario = new Usuario();
    UsuarioDAO udao = new UsuarioDAO();

    String userm = request.getParameter("login"); // Pega o Login vindo do formulário
    String user = userm.toLowerCase();
    String pass = request.getParameter("senha"); //Pega a senha vinda do formulário

    usuario = udao.consulta(user);

    if (usuario.getUsulogin() == null) {
        out.println("<script>alert('USUÁRIO NÃO ENCONTRADO!');</script>");
        out.println("<script>document.location.href='../index.html';</script>");
    }else{
        if (usuario.getUsulogin().equals(user) && usuario.getUsusenha().equals(pass) && usuario.getUsutipcodigo().getTipcodigo() == 2) { //Caso login e senha estejam corretos...
            session.putValue("loginUsuario", user); //Grava a session com o Login
            session.putValue("senhaUsuario", pass); //Grava a session com a Senha
            out.println("<script>document.location.href='professor/homeProfessor.jsp';</script>"); //Exibe um código javascript para redireionar ao painel
        } else if (usuario.getUsulogin().equals(user) && usuario.getUsusenha().equals(pass) && usuario.getUsutipcodigo().getTipcodigo() == 1) { //Se estiverem incorretos...
            session.putValue("loginUsuario", user); //Grava a session com o Login
            session.putValue("senhaUsuario", pass); //Grava a session com a Senha
            out.println("<script>document.location.href='aluno/homeAluno.jsp';</script>"); //Exibe um código javascript para redireionar ao painel
        } else if (usuario.getUsulogin().equals(user) && usuario.getUsusenha() != pass) {
            out.println("<script>alert('Senha incorreta!');</script>");
            out.println("<script>document.location.href='../index.html';</script>");
        } else {
            out.println("<script>document.location.href='../index.html';</script>");
        }
    }
%>

