$(document).ready(function () {
    $('#login').change(function () {
        var nome = document.getElementById('login').value;
        $.ajax({
            type: "POST",
            url: "consultaUsuario.jsp",
            data: "pesquisausuario=" + nome,
            success: function (resultado) {
                var x = parseInt(resultado, 10);
                if (x != 1) { // SE USUARIO ESTIVER DISPONIVEL
                    alert('Usuário já existente!');
                    document.getElementById("login").style.borderColor = "red";
                    document.getElementById('login').value = "";
                    document.getElementById('login').focus();
                } else {
                    document.getElementById("login").style.borderColor = "";
                }
            },
            error: function () {
                alert("Erro ao verificar os detalhes do usuario!");
            }
        });
    });
});

function mudaCampo() { // CASO SEJA PROFESSOR
    var v = document.getElementById('tipousuario').value;
    if (v == 1) {
        ////////////////////////////////////////
        document.getElementById('contato').type = 'email';
        document.getElementById('labelcontato').innerHTML = 'E-mail:';
        $('#contato').attr('maxlength', 80);
        document.getElementById('tipousuario').value = 2;
        ////////////////////////////////////////
    } else if (v == 2) { // CASO SEJA ALUNO
        document.getElementById('contato').type = 'text';
        document.getElementById('labelcontato').innerHTML = 'Telefone:';
        $('#contato').attr('maxlength', 11);
        document.getElementById('tipousuario').value = 1;
    }
    document.getElementById('nome').value = '';
    document.getElementById('contato').value = '';
    document.getElementById('login').value = '';
    document.getElementById('senha').value = '';
}
