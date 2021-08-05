/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.kprunnin.classes;

import br.com.kprunnin.DAO.AlertaDAO;
import br.com.kprunnin.conexaoBanco.ConnectionFactory;
import br.com.kprunnin.modelo.AlertaBd;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

/**
 *
 * @author olive
 */
public class Alerta {

    String origem = this.getClass().getSimpleName();
    Logger log = new Logger();
    Toolbox tb = new Toolbox();
    private Integer contagemErrosCpu = 0;
    private Integer contagemErrosDisco = 0;
    private Integer contagemErrosMemoria = 0;

    private Integer nivelAlertaCpu = 90 ;
    private Integer nivelAlertaDisco = 90;
    private Integer nivelAlertaMemoria = 90;

    private Integer tempoAlertaCpu = 20;
    private Integer tempoAlertaDisco = 10;
    private Integer tempoAlertaMemoria = 10;

    private Integer errosRegistrados = 0;

    void PlayErro() throws IOException {

        try {
            File arquivo = new File("./lib/erro.wav");
            if (arquivo.exists()) {
                AudioInputStream ai = AudioSystem.getAudioInputStream(arquivo);
                Clip clipErro = AudioSystem.getClip();
                clipErro.open(ai);
                clipErro.start();
            } else {
                log.gravarLinha(tb.data(), "ERRO", origem, "Som de erro não encontrado");
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.gravarLinha(tb.data(), "ERRO", origem, "Erro ao reproduzir som de erro");
        }
    }

    public float pegaPorcentagem(Number valorTotal, Number valorAEncontrar) {
        float total = (float) valorTotal;
        float qualQuero = (float) valorAEncontrar;

        return (qualQuero * 100) / total;
    }

    public void lancarAlerta(float valorCpu, float valorDisco, float valorMemoria, Integer idMaquina, Integer idEstabelecimento) throws IOException, SQLException {
        if (valorCpu > nivelAlertaCpu) {
            this.contagemErrosCpu++;
        } else {
            this.contagemErrosCpu = 0;
        }

        if (valorMemoria > nivelAlertaMemoria) {
            this.contagemErrosMemoria++;
        } else {
            this.contagemErrosMemoria = 0;
        }

        if (valorDisco > nivelAlertaDisco) {
            this.contagemErrosDisco++;
        } else {
            this.contagemErrosDisco = 0;
        }

        if (this.contagemErrosCpu > tempoAlertaCpu/3) {
            PlayErro();
            String erroCpu = String.format("Uso de cpu maior que %d%% por mais de %d segundos",
                    nivelAlertaCpu, tempoAlertaCpu);
            this.contagemErrosCpu = 0;
            log.gravarLinha(tb.data(), "ALRT", origem, erroCpu);
            this.errosRegistrados++;
            
            try(Connection connection = new ConnectionFactory().getConnection()){
                
                AlertaBd alerta = new AlertaBd(erroCpu, idMaquina, idEstabelecimento);
                
                AlertaDAO alertaDao = new AlertaDAO(connection);
                alertaDao.insert(alerta);
            }
            
            //Registrar erro
        }

        if (this.contagemErrosMemoria > tempoAlertaMemoria/3) {
            PlayErro();
            String erroMemoria = String.format("Uso de memória maior que %d%% por mais de %d segundos",
                    nivelAlertaMemoria, tempoAlertaMemoria);
            this.contagemErrosMemoria = 0;
            log.gravarLinha(tb.data(), "ALRT", origem, erroMemoria);

            this.errosRegistrados++;
            
            try(Connection connection = new ConnectionFactory().getConnection()){
                
                AlertaBd alerta = new AlertaBd(erroMemoria, idMaquina, idEstabelecimento);
                
                AlertaDAO alertaDao = new AlertaDAO(connection);
                alertaDao.insert(alerta);
            }
            //Registrar erro
        }
        if (this.contagemErrosDisco > tempoAlertaDisco/3) {
            PlayErro();
            String erroDisco = String.format("Uso de disco maior que %d%% por mais de %d segundos",
                    nivelAlertaDisco, tempoAlertaDisco);
            this.contagemErrosMemoria = 0;
            log.gravarLinha(tb.data(), "ALRT", origem, erroDisco);
            this.errosRegistrados++;
            
            try(Connection connection = new ConnectionFactory().getConnection()){
                
                AlertaBd alerta = new AlertaBd(erroDisco, idMaquina, idEstabelecimento);
                
                AlertaDAO alertaDao = new AlertaDAO(connection);
                alertaDao.insert(alerta);
            }
            //Registrar erro
        }

    }

    public Integer getContagemErrosCpu() {
        return contagemErrosCpu;
    }

    public Integer getContagemErrosDisco() {
        return contagemErrosDisco;
    }

    public Integer getContagemErrosMemoria() {
        return contagemErrosMemoria;
    }

    public Integer getErrosRegistrados() {
        return errosRegistrados;
    }

}
