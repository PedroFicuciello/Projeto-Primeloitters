'use strict';

/* 
lista e explicação dos Datatypes:
https://codewithhugo.com/sequelize-data-types-a-practical-guide/
*/

module.exports = (sequelize, DataTypes) => {
    let Alerta = sequelize.define('Alerta',{	
		idAlerta: {
			type: DataTypes.INTEGER,
			primaryKey: true,
			autoIncrement: true
		},
		alerta: {
			type: DataTypes.STRING,
			allowNull: false
		},	
		dataAlerta: {
			type: DataTypes.DATE,
			allowNull: false
		},
		fkMaquinaAlerta: {
			type: DataTypes.INTEGER,
			allowNull: false
		},
		fkEstabelecimento: {
			type: DataTypes.INTEGER,
			allowNull: false
		}
	}, 
	{
		tableName: 'alerta', 
		freezeTableName: true, 
		underscored: true,
		timestamps: false,
	});

    return Alerta;
};
