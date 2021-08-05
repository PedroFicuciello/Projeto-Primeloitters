var express = require('express');
var router = express.Router();
var sequelize = require('../models').sequelize;
var Leitura = require('../models').Leitura;

/* Recuperar as últimas N leituras */
router.get('/ultimas/:idMaquina', function (req, res, next) {

	let idMaquina = req.params.idMaquina;

	// quantas são as últimas leituras que quer? 8 está bom?
	const limite_linhas = 7;

	console.log(`Recuperando as últimas ${limite_linhas} leituras`);

	const instrucaoSql = `select top ${limite_linhas}
							dataHora,
							dadosCpu, 
							dadosMemoria,
							dadosDisco, 
							FORMAT(dataHora,'HH:mm:ss') as momento_grafico,
							fkMaquina from kprDado where fkMaquina = ${idMaquina}
							order by dataHora desc`;

	sequelize.query(instrucaoSql, {
		model: Leitura,
		mapToModel: true
	})
		.then(resultado => {
			if (resultado.length > 0) {
				res.json(resultado);
			} else {
				console.log(`Não foi encontrado dados para a máquina selecionada`);
				res.status(404).send('Não foi encontrado dados para a máquina selecionada');
			}

		}).catch(erro => {
			console.error(erro);
			res.status(500).send(erro.message);
		});
});


// tempo real (último valor de cada leitura)
router.get('/temporeal/:idMaquina', function (req, res, next) {

	let idMaquina = req.params.idMaquina;

	console.log(`Recuperando as últimas leituras`);

	const instrucaoSql = `select top 1 
							dataHora, 
							dadosCPU, 
							dadosMemoria, 
							dadosDisco, 
							fkMaquina from kprDado where fkMaquina = ${idMaquina} 
							order by dataHora desc;`;
	console.log(instrucaoSql);

	sequelize.query(instrucaoSql, {
		model: Leitura,
		mapToModel: true
	})
		.then(resultado => {
			if (resultado.length > 0) {
				res.json(resultado);
			} else {
				console.log(`Não foi encontrado dados para a máquina selecionada`);
				res.status(404).send('Não foi encontrado dados para a máquina selecionada');
			}
		}).catch(erro => {
			console.error(erro);
			res.status(500).send(erro.message);
		});
});

module.exports = router;
