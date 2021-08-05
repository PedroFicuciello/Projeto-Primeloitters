/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.kprunnin.DAO;

import br.com.kprunnin.modelo.AlertaBd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author olive
 */
public class AlertaDAO {
    
    private Connection connection;

    public AlertaDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean insert(AlertaBd alerta) throws SQLException {
        
        boolean insertRealizado = false;
        
        String insertSql = "INSERT INTO alerta (alerta, dataAlerta, fkMaquinaAlerta, fkEstabelecimento)VALUES "
                + "(?, CURRENT_TIMESTAMP AT TIME ZONE 'Tocantins Standard Time',?,?);";
        
        try (PreparedStatement ps = connection.prepareStatement(insertSql)) {

            ps.setString(1, alerta.getAlerta());
            ps.setInt(2, alerta.getFkMaquinaAlerta());
            ps.setInt(3, alerta.getFkEstabelecimento());
            ps.execute();
            insertRealizado = true;
        }
        
        return insertRealizado;
    }
}
