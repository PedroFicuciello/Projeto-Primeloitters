let login_usuario;
let nome_usuario;

function showRegisterForm() {
    $('.loginBox').fadeOut('fast', function () {
        $('.registerBox').fadeIn('fast');
        $('.login-footer').fadeOut('fast', function () {
            $('.register-footer').fadeIn('fast');
        });
        $('.modal-title').html('Registro');
    });
    $('.error').removeClass('alert alert-danger').html('');

}
function showLoginForm() {
    $('#loginModal .registerBox').fadeOut('fast', function () {
        $('.loginBox').fadeIn('fast');
        $('.esqueciSenhaBox').fadeOut('fast');
        $('.register-footer' && '.esqueciSenhaBox').fadeOut('fast', function () {
            $('.login-footer').fadeIn('fast');

        });

        $('.modal-title').html('Login');
    });
    $('.error').removeClass('alert alert-danger').html('');
}
function showEsqueciSenhaForm(){
    $('#loginModal .loginBox').fadeOut('fast', function(){
        $('.esqueciSenhaBox').fadeIn('fast');
        $('.login-footer').fadeOut('fast', function(){
            $('.register-footer').fadeIn('fast');
        });
        $('.modal-title').html('Recuperar senha');
    });
    $('.error').removeClass('alert alert-danger').html('');

    $('#loginModal .registerBox').fadeOut('fast', function(){
        $('.esqueciSenhaBox').fadeIn('fast');
        $('.login-footer').fadeOut('fast', function(){
            $('.register-footer').fadeIn('fast');
        });
        $('.modal-title').html('Recuperar senha');
    });
    $('.error').removeClass('alert alert-danger').html('');
}

function openLoginModal() {
    showLoginForm();
    setTimeout(function () {
        $('#loginModal').modal('show');
    }, 230);

}
function openRegisterModal() {
    showRegisterForm();
    setTimeout(function () {
        $('#loginModal').modal('show');
    }, 230);
}
function entrar() {
    var formulario = new URLSearchParams(new FormData(form_login));
    fetch("/usuarios/autenticar", {
        method: "POST",
        body: formulario
    }).then(resposta => {

        if (resposta.ok) {

            resposta.json().then(json => {

                sessionStorage.login_usuario_meuapp = json.login;

                window.location.href = './dashboard.html';
            });

        } else {

            console.log('Erro de login!');
            shakeLogin();
            response.text().then(texto => {
                console.error(texto);
            });
        }
    });

    return false;
}

function cadastrar() {
    var formulario = new URLSearchParams(new FormData(form_cadastro));
    fetch("/usuarios/cadastrar", {
        method: "POST",
        body: formulario
    }).then(function (response) {

        if (response.ok) {

            alert(`cadastrado`);
            window.location.href = './index.html';

        } else {

            console.log('Erro de cadastro!');
            shakeCadastro();
            response.text().then(function (resposta) { });
        }
    });

    return false;
}


function shakeCadastro() {
    $('#loginModal .modal-dialog').addClass('shake');
    $('.error').addClass('alert alert-danger').html("Algum campo invalido");
    $('input[type="password"]').val('');
    setTimeout(function () {
        $('#loginModal .modal-dialog').removeClass('shake');
    }, 1000);
}

function shakeLogin() {
    $('#loginModal .modal-dialog').addClass('shake');
    $('.error').addClass('alert alert-danger').html(" login e/ou senha invalidos");
    $('input[type="password"]').val('');
    setTimeout(function () {
        $('#loginModal .modal-dialog').removeClass('shake');
    }, 1000);
}


function redirecionar_login() {
    window.location.href = 'index.html';
}

function finalizar_sessao() {
    fetch(`/usuarios/sair/${login_usuario}`, { cache: 'no-store' });
}

function validar_sessao() {
    fetch(`/usuarios/sessao/${login_usuario}`, { cache: 'no-store' })
        .then(resposta => {
            if (resposta.ok) {
                resposta.text().then(texto => {
                    console.log('Sessão :) ', texto);
                });
            } else {
                console.error('Sessão :.( ');
                logoff();
            }
        });
}

function verificar_autenticacao() {
    login_usuario = sessionStorage.login_usuario_meuapp;
    nome_usuario = sessionStorage.nome_usuario_meuapp;

    if (login_usuario == undefined) {
        redirecionar_login();
    } else {
        validar_sessao();
    }

}
function logoff() {
    finalizar_sessao();
    sessionStorage.clear();
    redirecionar_login();
}