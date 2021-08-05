/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.kprunnin.DAO;

import br.com.kprunnin.modelo.Estabelecimento;
import br.com.kprunnin.modelo.Maquina;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author olive
 */
public class MaquinaDAO {

    private Connection connection;

    public MaquinaDAO(Connection connection) {
        this.connection = connection;
    }

    public Maquina select(Estabelecimento estabelecimento, String codigoMaquina) throws SQLException {

        String insertSql = "select kprMaquina.IdMaquina, kprMaquina.tipoMaquina,kprMaquina.codigoMaquina, kprMaquina.numeroSerie,"
                + "kprMaquina.marcaMaquina, kprMaquina.modelo, kprMaquina.sistemaOperacional,"
                + "kprMaquina.espacoTotalHd, kprMaquina.memoriaTotal, kprMaquina.infoProcessador, kprMaquina.fkEstabelecimento "
                + "from kprMaquina inner join kprEstabelecimento "
                + "on kprMaquina.fkEstabelecimento = ? and kprEstabelecimento.idEstab = ? and kprMaquina.codigoMaquina = ?;";

        try (PreparedStatement ps = connection.prepareStatement(insertSql)) {

            ps.setInt(1, estabelecimento.getIdEstab());
            ps.setInt(2, estabelecimento.getIdEstab());
            ps.setString(3, codigoMaquina);

            ps.execute();

            try (ResultSet rs = ps.getResultSet()) {

                Maquina maquina = null;

                while (rs.next()) {

                    maquina = new Maquina(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
                            rs.getString(5), rs.getString(6), rs.getString(7),
                            rs.getString(8), rs.getString(9), rs.getString(10), rs.getInt(11));

                }

                return maquina;
            }
        }
    }

    public boolean insert(Maquina maquina) throws SQLException {

        String insertSql = "update kprMaquina set marcaMaquina = ?, sistemaOperacional = ?, memoriaTotal = ?,"
                + "infoProcessador = ?, modelo = ?, numeroSerie = ?, espacoTotalHd = ? where idMaquina = ?";

        try (PreparedStatement ps = connection.prepareStatement(insertSql)) {

            ps.setString(1, maquina.getMarcaMaquina());
            ps.setString(2, maquina.getSistemaOperacional());
            ps.setString(3, maquina.getMemoriaTotal());
            ps.setString(4, maquina.getInfoProcessador());
            ps.setString(5, maquina.getModelo());
            ps.setString(6, maquina.getNumeroSerie());
            ps.setString(7, maquina.getEspacoTotalHd());
            ps.setInt(8, maquina.getIdMaquina());

            ps.execute();

            return true;

        }
    }
}
