/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.kprunnin.DAO;

import br.com.kprunnin.modelo.Maquina;
import br.com.kprunnin.modelo.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author olive
 */
public class UsuarioDAO {
    
    private Connection connection;

    public UsuarioDAO(Connection connection) {
        this.connection = connection;
    }

    public Usuario select(Usuario usuario) throws SQLException {

        String insertSql = "select * from kprUsuario as u where u.login = ? and u.senha = ?;";
        
        try (PreparedStatement ps = connection.prepareStatement(insertSql)) {

            ps.setString(1, usuario.getLogin());
            ps.setString(2, usuario.getSenha());

            ps.execute();
            
            try(ResultSet rs = ps.getResultSet()){
                Usuario user = null;
                while(rs.next()){
                    user = new Usuario(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4));
                }
                System.out.println(user);
                
                return user;
            }
        }
        
        
    }
}
