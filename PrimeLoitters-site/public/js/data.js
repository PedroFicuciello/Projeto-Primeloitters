// Função para formatar 1 em 01
const zeroFill = n => {
    return ('0' + n).slice(-2);
}

// Cria intervalo
const interval = setInterval(() => {
    // Pega o horário atual
    const now = new Date();

    // Formata a data conforme dd/mm/aaaa
    const dataHora = zeroFill(now.getUTCDate()) + '/' + zeroFill((now.getMonth() + 1)) + '/' + now.getFullYear();
    const dataHora1 = zeroFill(now.getUTCDate()) + '/' + zeroFill((now.getMonth() + 1)) + '/' + now.getFullYear();
    const dataHora2 = zeroFill(now.getUTCDate()) + '/' + zeroFill((now.getMonth() + 1)) + '/' + now.getFullYear();
    const dataHora3 = zeroFill(now.getUTCDate()) + '/' + zeroFill((now.getMonth() + 1)) + '/' + now.getFullYear();

    const dataHora4 = zeroFill(now.getUTCDate()) + '/' + zeroFill((now.getMonth() + 1)) + '/' + now.getFullYear();
    const dataHora5 = zeroFill(now.getUTCDate()) + '/' + zeroFill((now.getMonth() + 1)) + '/' + now.getFullYear();
    const dataHora6 = zeroFill(now.getUTCDate()) + '/' + zeroFill((now.getMonth() + 1)) + '/' + now.getFullYear();


    // Exibe na tela usando a div#data-hora
    // document.getElementById('data-hora').innerHTML = dataHora;
    // document.getElementById('data-hora1').innerHTML = dataHora1;
    // document.getElementById('data-hora2').innerHTML = dataHora2;
    // document.getElementById('data-hora3').innerHTML = dataHora3;


    // document.getElementById('data-hora3').innerHTML = dataHora4;
    // document.getElementById('data-hora3').innerHTML = dataHora5;
    // document.getElementById('data-hora3').innerHTML = dataHora6;

}, 1000);