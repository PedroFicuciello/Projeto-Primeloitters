
var exibiu_grafico = false;

// só mexer se quiser alterar o tempo de atualização
// ou se souber o que está fazendo!
function atualizarGraficoDisco() {
    obterDadosGraficoDisco();
    setTimeout(atualizarGraficoDisco, 10000);
}

// altere aqui as configurações do gráfico
// (tamanhos, cores, textos, etc)
function configurarGraficoDisco() {
    var configuracoes = {
        responsive: true,
        animation: exibiu_grafico ? false : { duration: 1500 },
        hoverMode: 'index',
        stacked: false,
        title: {
            display: true,
            text: 'Gráfico do uso de disco'
        },
        scales: {
            yAxes: [{
                type: 'linear', // only linear but allow scale type registration. This allows extensions to exist solely for log scale for instance
                display: true,
                position: 'left',
                id: 'y-disco',
            }
            ],
        }
    };

    exibiu_grafico = true;

    return configuracoes;
}

// altere aqui como os dados serão exibidos
// e como são recuperados do BackEnd
function obterDadosGraficoDisco() {

    // neste JSON tem que ser 'labels', 'datasets' etc, 
    // porque é o padrão do Chart.js
    var dados = {
        labels: [],
        datasets: [
            {
                yAxisID: 'y-disco',
                label: 'Disco',
                borderColor: window.chartColors.red,
                backgroundColor: window.chartColors.red,
                fill: false,
                data: []
            }
        ]
    };

    fetch(`/leituras/ultimas/${getIdMaquina()}`, { cache: 'no-store' }).then(function (response) {

        if (response.ok) {
            response.json().then(function (resposta) {

                console.log(`Dados recebidos`);
                resposta.reverse();

                for (i = 0; i < resposta.length; i++) {
                    var registro = resposta[i];

                    dados.labels.push(registro.momento_grafico);
                    dados.datasets[0].data.push(registro.dadosDisco);
                }

                plotarGraficoDisco(dados);
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
function plotarGraficoDisco(dados) {
    console.log('iniciando plotagem do gráfico...');
    var ctx = canvas_grafico_disco.getContext('2d');
    window.grafico_linha = Chart.Line(ctx, {
        data: dados,
        options: configurarGraficoDisco()
    });
}
