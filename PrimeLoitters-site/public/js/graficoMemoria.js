
var exibiu_grafico = false;

// só mexer se quiser alterar o tempo de atualização
// ou se souber o que está fazendo!
function atualizarGraficoMemoria() {
    obterDadosGraficoMemoria();
    setTimeout(atualizarGraficoMemoria, 10000);
}

// altere aqui as configurações do gráfico
// (tamanhos, cores, textos, etc)
function configurarGraficoMemoria() {
    var configuracoes = {
        responsive: true,
        animation: exibiu_grafico ? false : { duration: 1500 },
        hoverMode: 'index',
        stacked: false,
        title: {
            display: true,
            text: 'Gráfico do uso de memória'
        },
        scales: {
        }
    };

    exibiu_grafico = true;

    return configuracoes;
}

// altere aqui como os dados serão exibidos
// e como são recuperados do BackEnd
function obterDadosGraficoMemoria() {

    // neste JSON tem que ser 'labels', 'datasets' etc, 
    // porque é o padrão do Chart.js
    var dados = {
        labels: ['Memoria disponível', 'Memoria em uso'],
        datasets: [
            {
                borderColor: [window.chartColors.blue, window.chartColors.red],
                backgroundColor: [window.chartColors.blue, window.chartColors.red],
                fill: false,
                data: []
            },

        ]
    };

    fetch(`/leituras/temporeal/${getIdMaquina()}`, { cache: 'no-store' }).then(function (response) {
        if (response.ok) {
            response.json().then(function (resposta) {
                console.log(`Dados recebidos`);
                resposta.reverse();

                for (i = 0; i < resposta.length; i++) {
                    var registro = resposta[i];

                    dados.datasets[0].data.push(Number(8000000000 - registro.dadosMemoria));
                    dados.datasets[0].data.push(registro.dadosMemoria);
                }

                plotarGraficoMemoria(dados);


            });
        } else {
            if (contador == 0) {
                abreModal();
                contador++;
            }
        }
    })
        .catch(function (error) {
            console.error(`Erro na obtenção dos dados p/ gráfico: ${error.message}`);
        });

}

// só altere aqui se souber o que está fazendo!
function plotarGraficoMemoria(dados) {
    console.log('iniciando plotagem do gráfico...');

    var ctx = canvas_grafico_memoria.getContext('2d');
    var myPieChart = new Chart(ctx, {
        type: 'pie',
        data: dados,
        options: configurarGraficoMemoria()
    });


    // window.grafico_linha = Chart.Line(ctx, {
    //     type: 'pie',
    //     data: dados,
    //     options: configurarGraficoMemoria()
    // });
}
