'use strict';

/* 
lista e explicação dos Datatypes:
https://codewithhugo.com/sequelize-data-types-a-practical-guide/
*/

module.exports = (sequelize, DataTypes) => {
    let Estabelecimento = sequelize.define('Estabelecimento',{
		idEstab: {
			type: DataTypes.INTEGER,
			primaryKey: true,
			autoIncrement: true
		},		
		codEstab: {
			type: DataTypes.STRING,
			allowNull: false
		},
		fkUsuario: {
			type: DataTypes.INTEGER,
			allowNull: false
		},
		nomeEstab: {
			type: DataTypes.STRING,
			allowNull: false
		}
	}, 
	{
		tableName: 'kprEstabelecimento', 
		freezeTableName: true, 
		underscored: true,
		timestamps: false,
	});

    return Estabelecimento;
};
