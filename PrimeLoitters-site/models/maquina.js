'use strict';

/* 
lista e explicação dos Datatypes:
https://codewithhugo.com/sequelize-data-types-a-practical-guide/
*/

module.exports = (sequelize, DataTypes) => {
    let Maquina = sequelize.define('Maquina',{
		idMaquina: {
			type: DataTypes.INTEGER,
			primaryKey: true,
			autoIncrement: true
		},		
		tipoMaquina: {
			type: DataTypes.STRING,
			allowNull: false
		},
		codigoMaquina: {
			type: DataTypes.STRING,
			allowNull: false
		},
		numeroSerie: {
			type: DataTypes.STRING,
			allowNull: false
		},
		fkEstabelecimento: {
			type: DataTypes.INTEGER,
			allowNull: false
		},
		marcaMaquina: {
			type: DataTypes.STRING,
			allowNull: false
		},
		modelo: {
			type: DataTypes.STRING,
			allowNull: false
		},
		sistemaOperacional: {
			type: DataTypes.STRING,
			allowNull: false
		},
		espacoTotalHd: {
			type: DataTypes.STRING,
			allowNull: false
		},
		memoriaTotal: {
			type: DataTypes.STRING,
			allowNull: false
		},
		infoProcessador: {
			type: DataTypes.STRING,
			allowNull: false
		}
		
	}, 
	{
		tableName: 'kprMaquina', 
		freezeTableName: true, 
		underscored: true,
		timestamps: false,
	});

    return Maquina;
};
