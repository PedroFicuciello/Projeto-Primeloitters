'use strict';

/* 
lista e explicação dos Datatypes:
https://codewithhugo.com/sequelize-data-types-a-practical-guide/
*/

module.exports = (sequelize, DataTypes) => {
    let Leitura = sequelize.define('Leitura',{	
		dataHora: {
			type: DataTypes.DATE,
			allowNull: false
		},
		dadosCpu: {
			type: DataTypes.INTEGER,
			allowNull: false
		},	
		dadosMemoria: {
			type: DataTypes.INTEGER,
			allowNull: false
		},
		dadosDisco: {
			type: DataTypes.INTEGER,
			allowNull: false
		},
		fkMaquina: {
			type: DataTypes.INTEGER, 
			allowNull: false
		}
	}, 
	{
		tableName: 'kprDado', 
		freezeTableName: true, 
		underscored: true,
		timestamps: false,
	});

    return Leitura;
};
