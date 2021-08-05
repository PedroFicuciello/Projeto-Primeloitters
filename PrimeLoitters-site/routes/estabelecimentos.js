var express = require('express');
var router = express.Router();
var sequelize = require('../models').sequelize;
var Estabelecimento = require('../models').Estabelecimento;

/* Cadastrar estabelecimento */
router.post('/cadastrar/:idUsuario', function(req, res, next) {
	console.log('Recuperando usuário por login e senha');

	var idUsuario = req.params.idUsuario;
	var nome = req.body.name; // depois de .body, use o nome (name) do campo em seu formulário de login
	var codigo = req.body.code; // depois de .body, use o nome (name) do campo em seu formulário de login	
	
	let instrucaoSql = `insert into kprEstabelecimento (codEstab, fkUsuario, nomeEstab) values 
						('${codigo}', ${idUsuario}, '${nome}')`;

	sequelize.query(instrucaoSql, {
        model: Estabelecimento,
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
router.get('/recuperar/:idUsuario', function(req, res, next) {
	
	var idUsuario = req.params.idUsuario;
	console.log(`Recuperando os estabelecimentos do usuário com id ${idUsuario}`);
	
	const instrucaoSql = `select * from kprEstabelecimento where fkusuario = ${idUsuario};`;
	sequelize.query(instrucaoSql, {
		model: Estabelecimento,
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