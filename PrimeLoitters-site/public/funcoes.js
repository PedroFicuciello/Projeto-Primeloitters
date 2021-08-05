let login_usuario;
let nome_usuario;
let id_usuario;
let id_estabelecimento;
let id_maquina;

function redirecionar_login() {
    window.location.href = 'index.html';
}

function getIdUsuario(){
    return sessionStorage.id_usuario_meuapp;
}

function getIdEstab(){
    return sessionStorage.id_estabelecimento_meuapp;
}

function getIdMaquina(){
    return sessionStorage.id_maquina_meuapp;
}

function getNomeEstab(){
    return sessionStorage.nome_estabelecimento_meuapp;
}

function getCodEstab(){
    return sessionStorage.codigo_estabelecimento_meuapp;
}

function verificar_autenticacao() {
    login_usuario = sessionStorage.login_usuario_meuapp;
    nome_usuario = sessionStorage.nome_usuario_meuapp;
    id_usuario = sessionStorage.id_usuario_meuapp;

    if (login_usuario == undefined)  {
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

function validar_sessao() {
    fetch(`/usuarios/sessao/${login_usuario}`, {cache:'no-store'})
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

function finalizar_sessao() {
    fetch(`/usuarios/sair/${login_usuario}`, {cache:'no-store'}); 
}
