var express = require('express');
var router = express.Router();
var sequelize = require('../models').sequelize;
var Maquina = require('../models').Maquina;

/* Cadastrar máquina */
router.post('/cadastrar/:idEstab', function (req, res, next) {
	console.log('cadastrando uma nova máquina');
	
	var idEstab = req.params.idEstab;
	var codigoMaquina = req.body.codigoMaquina;
	
	let instrucaoSql = `insert into kprMaquina (tipoMaquina, codigoMaquina, fkEstabelecimento) values
						('computador', '${codigoMaquina}', ${idEstab});`;

	sequelize.query(instrucaoSql, {
		model: Maquina,
		mapToModel: true
	}).then(resultado => {
		console.log(`Estabelecimento cadastrado`);
		res.send(resultado);
	}).catch(erro => {
		console.error(erro);
		res.status(500).send(erro.message);
	});
});

/* Recuperar os estabelecimentos de determinado usuario */
router.get('/recuperar/:idEstabelecimento', function (req, res, next) {

	var idEstabelecimento = req.params.idEstabelecimento;
	console.log(`Recuperando as máquinas do estabelecimento com id ${idEstabelecimento}`);

	const instrucaoSql = `select * from kprMaquina where fkEstabelecimento = ${idEstabelecimento};`;
	sequelize.query(instrucaoSql, {
		model: Maquina,
		mapToModel: true
	})
		.then(resultado => {
			res.json(resultado);
		}).catch(erro => {
			console.error(erro);
			res.status(500).send(erro.message);
		});
});

module.exports = router;