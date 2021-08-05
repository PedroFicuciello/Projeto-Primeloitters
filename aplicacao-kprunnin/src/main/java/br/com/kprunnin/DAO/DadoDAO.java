/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.kprunnin.DAO;

import br.com.kprunnin.modelo.Dado;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author olive
 */
public class DadoDAO {

    private Connection connection;

    public DadoDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean insert(Dado dado) throws SQLException {
        
        boolean insertRealizado = false;
        
        String insertSql = "INSERT INTO kprDado (dataHora, dadosCpu, dadosMemoria, dadosDisco,fkMaquina)VALUES "
                + "(CURRENT_TIMESTAMP AT TIME ZONE 'Tocantins Standard Time',?,?,?,?);";

        String valorCpu = String.valueOf(dado.getDadoCpu());
        String valorMemoria = String.valueOf(dado.getDadoMemoria());
        String valorDisco = String.valueOf(dado.getDadoDisco());

        try (PreparedStatement ps = connection.prepareStatement(insertSql)) {

            ps.setString(1, valorCpu);
            ps.setString(2, valorMemoria);
            ps.setString(3, valorDisco);
            ps.setInt(4, dado.getChaveEstrangeira());
            
            ps.execute();
            insertRealizado = true;
        }
        
        return insertRealizado;
    }
    
    

}
