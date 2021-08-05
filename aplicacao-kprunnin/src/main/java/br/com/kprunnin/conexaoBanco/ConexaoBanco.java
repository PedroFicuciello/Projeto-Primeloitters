/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.kprunnin.conexaoBanco;

import br.com.kprunnin.DAO.EstabelecimentoDAO;
import br.com.kprunnin.DAO.MaquinaDAO;
import br.com.kprunnin.DAO.UsuarioDAO;
import br.com.kprunnin.classes.Logger;
import br.com.kprunnin.modelo.Estabelecimento;
import br.com.kprunnin.modelo.Maquina;
import br.com.kprunnin.modelo.Usuario;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import br.com.kprunnin.classes.Toolbox;

/**
 *
 * @author olive
 */
public class ConexaoBanco {

    private final String origem = this.getClass().getSimpleName();
    private final Logger log = new Logger();
    private final Toolbox tb = new Toolbox();
    private String login = "vazia";
    private String senha = "vazia";
    private String codigoEstab = "vazia";
    private String codigoMaquina = "vazia";
    private Integer idMaquina;
    private Integer idEstabelecimento;
    private Maquina maquina;
    
    public boolean testaConexaoComBanco() throws SQLException, IOException {

        boolean conectado = false;
        String conexao = "";

        try (Connection connection = new ConnectionFactory().getConnection()) {

            try {

                BufferedReader br = new BufferedReader(new FileReader("autenticacao.txt"));
                conexao = br.readLine();
                br.close();

                String[] stringConexao = conexao.split(";");

                this.login = stringConexao[0];
                this.senha = stringConexao[1];
                this.codigoEstab = stringConexao[2];
                this.codigoMaquina = stringConexao[3];
                conectado = true;
                log.gravarLinha(tb.data(), "INFO", origem, "Teste de conexão com banco de dados feito");

            } catch (IOException e) {
                log.gravarLinha(tb.data(), "ERRO", origem, "SQLException : Arquivo de configuração incompleto ou inexistente");
                throw new SQLException("Ainda não configurado");

            } finally {
                return conectado;
            }
        }
    }

    public boolean configuraConexao(String login, String senha, String codigoEstab, String codigoMaquina) throws IOException {

        boolean configurado = false;

        log.gravarLinha(tb.data(), "INFO", origem, "Iniciando configuracao da aplicação");
        BufferedWriter bw;

        try {

            bw = new BufferedWriter(new FileWriter("autenticacao.txt"));
            bw.write(login + ";" + senha + ";" + codigoEstab + ";" + codigoMaquina);

            log.gravarLinha(tb.data(), "INFO", origem, "Registro efetuado");

            this.login = login;
            this.senha = senha;
            this.codigoEstab = codigoEstab;
            this.codigoMaquina = codigoMaquina;

            configurado = true;
            bw.close();

        } catch (IOException e) {
        }

        return configurado;
    }

    public boolean conectarComBanco(Connection connection) throws SQLException, IOException {

        boolean conectado = false;

        Usuario usuarioLogin = new Usuario(this.login, this.senha);

        UsuarioDAO usuarioDao = new UsuarioDAO(connection);
        Usuario usuario = usuarioDao.select(usuarioLogin);

        EstabelecimentoDAO estabDao = new EstabelecimentoDAO(connection);
        Estabelecimento estabelecimento = estabDao.select(usuario, this.codigoEstab);
        
        MaquinaDAO maquinaDao = new MaquinaDAO(connection);
        Maquina maquina = maquinaDao.select(estabelecimento, this.codigoMaquina);
        this.maquina = maquina;
        
        String mensagem = String.format("Usuario: %s, Estabelecimento: %s, Maquina: %d ",
                usuario.getNome(), estabelecimento.getCodEstab(), maquina.getIdMaquina());

        log.gravarLinha(tb.data(), "INFO", origem, mensagem);
        this.idMaquina = maquina.getIdMaquina();
        this.idEstabelecimento = estabelecimento.getIdEstab();
        conectado = true;

        log.gravarLinha(tb.data(), "INFO", origem, "Banco de dados conectado. Informações da conexão :");
        for (int cc = 0; cc < ConnectionFactory.getDetalheConexao().length; cc++) {
            log.gravarLinha(ConnectionFactory.getDetalheConexao()[cc]);
        }

        return conectado;
    }

    public Integer getIdMaquina() {
        return this.idMaquina;
    }

    public Integer getIdEstabelecimento() {
        return this.idEstabelecimento;
    }

    public Maquina getMaquina(){
        return this.maquina;
    }
}
