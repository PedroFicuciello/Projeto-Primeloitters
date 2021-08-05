/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.kprunnin.modelo;

/**
 *
 * @author olive
 */
public class Dado {
    
    private float dadoCpu;
    private float dadoMemoria;
    private float dadoDisco;
    private Integer chaveEstrangeira;

    public Dado(float dadoCpu, float dadoMemoria, float dadoDisco, Integer chaveEstrangeira) {
        this.dadoCpu = dadoCpu;
        this.dadoMemoria = dadoMemoria;
        this.dadoDisco = dadoDisco;
        this.chaveEstrangeira = chaveEstrangeira;
    }

    public float getDadoCpu() {
        return dadoCpu;
    }

    public float getDadoMemoria() {
        return dadoMemoria;
    }

    public float getDadoDisco() {
        return dadoDisco;
    }

    public Integer getChaveEstrangeira() {
        return chaveEstrangeira;
    }
    
    
}
