/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.kprunnin.conexaoBanco;

import br.com.kprunnin.classes.Logger;
import com.microsoft.sqlserver.jdbc.SQLServerXADataSource;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import br.com.kprunnin.classes.Toolbox;

/**
 *
 * @author olive
 */
public class ConnectionFactory {

    private final String origem = this.getClass().getSimpleName();
    private final Logger log = new Logger();
    private final Toolbox tb = new Toolbox();
    private final DataSource dataSource;
    private static String[] detalheConexao = new String[7];

    public ConnectionFactory() {

        SQLServerXADataSource serverDataSource = new SQLServerXADataSource();
        String server = "jdbc:sqlserver://svrkprunnin.database.windows.net:1433;";
        String database = "database=bdkprunnin;";
        String user = "user=localprimeloitters@svrkprunnin;";
        String pass = "password=#Gfgrupo6b;";
        String encrypt = "encrypt=true;";
        String trust = "trustServerCertificate=false;";
        String timeout = "loginTimeout=30;";

        String connectionUrl = server
                + database
                + user
                //+ "user=userowlight;"
                + pass
                + encrypt
                + trust
                + timeout;

        //jdbc:sqlserver://svrkprunnin.database.windows.net:1433;database=bdkprunnin;
        //user=localprimeloitters@svrkprunnin;
        //password={your_password_here};
        //hostNameInCertificate=*.database.windows.net;
        serverDataSource.setURL(connectionUrl);
        this.dataSource = serverDataSource;
        detalheConexao[0] = server;
        detalheConexao[1] = database;
        detalheConexao[2] = user;
        detalheConexao[3] = "password=***;";
        detalheConexao[4] = encrypt;
        detalheConexao[5] = trust;
        detalheConexao[6] = timeout;
        
    }

    public static String[] getDetalheConexao() {
        return detalheConexao;
    }
    
    public Connection getConnection() throws SQLException {
        return this.dataSource.getConnection();
    }

}
