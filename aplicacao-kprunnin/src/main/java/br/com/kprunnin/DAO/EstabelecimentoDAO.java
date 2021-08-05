/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.kprunnin.DAO;

import br.com.kprunnin.modelo.Estabelecimento;
import br.com.kprunnin.modelo.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author olive
 */
public class EstabelecimentoDAO {
    
    private Connection connection;

    public EstabelecimentoDAO(Connection connection) {
        this.connection = connection;
    }

    public Estabelecimento select(Usuario usuario,String codigoEstab) throws SQLException {
        
        String insertSql = "select e.idEstab,e.codEstab,e.nomeEstab,e.fkUsuario " +
                            "from kprEstabelecimento as e " +
                            "inner join kprUsuario as u on e.fkUsuario = ? and u.idUsuario = ? and e.codEstab = ?;";
     
        try (PreparedStatement ps = connection.prepareStatement(insertSql)) {

            ps.setInt(1, usuario.getId());
            ps.setInt(2, usuario.getId());
            ps.setString(3, codigoEstab);
            
            ps.execute();
            
            try(ResultSet rs = ps.getResultSet()){
                
                Estabelecimento estabelecimento = null;
                
                while(rs.next()){
                    
                    estabelecimento = new 
                        Estabelecimento(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4));
            
                }
                
                return estabelecimento;
            }
        }
    }
}
