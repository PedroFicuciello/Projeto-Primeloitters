/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swingprojeto;

import java.io.File;
import java.util.Random;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import static swingprojeto.TelaMonitoramento.barCpu;
import static swingprojeto.TelaMonitoramento.barDisco;
import static swingprojeto.TelaMonitoramento.barMemoria;

/**
 *
 * @author Ramon
 */
public class Toolbox {


    public float HSBFloat(int cor) {
        float porcentagemInvertida = cor - 100;
        float porcentagemInvertidaPositiva = porcentagemInvertida * -1;
        float corHsbFloat = porcentagemInvertidaPositiva / 333;
        return corHsbFloat;
    }

    void PlayErro() {

        try {
            File arquivo = new File("./src/swingprojeto/erro.wav");
            if (arquivo.exists()) {
                AudioInputStream ai = AudioSystem.getAudioInputStream(arquivo);
                Clip clipErro = AudioSystem.getClip();
                clipErro.open(ai);
                clipErro.start();
            } else {
                System.out.println("Som de erro não encontrado");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void SvcAlertaSonoro() throws InterruptedException {
        if (barCpu.getValue() > 90) {
            TelaMonitoramento.contagemErrosCpu++;
        } else {
            TelaMonitoramento.contagemErrosCpu = 0;
        }
        if (barMemoria.getValue() > 90) {
            TelaMonitoramento.contagemErrosMem++;
        } else {
            TelaMonitoramento.contagemErrosMem = 0;
        }
        if (barDisco.getValue() > 90) {
            TelaMonitoramento.contagemErrosDsk++;
        } else {
            TelaMonitoramento.contagemErrosDsk = 0;
        }

        if (TelaMonitoramento.contagemErrosCpu > 10) {
            PlayErro();
            TelaMonitoramento.contagemErrosCpu = 50;
        } else {
            TelaMonitoramento.contagemErrosCpu = 0;
        }
        if (TelaMonitoramento.contagemErrosMem > 20) {
            PlayErro();
            TelaMonitoramento.contagemErrosMem = 10;
        } else {
            TelaMonitoramento.contagemErrosMem = 0;
        }
        if (TelaMonitoramento.contagemErrosDsk > 20) {
            PlayErro();
            TelaMonitoramento.contagemErrosDsk = 10;
        } else {
            TelaMonitoramento.contagemErrosDsk = 0;
        }

    }
}
