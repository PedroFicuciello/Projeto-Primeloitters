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
public class AlertaBd {
    
    private String alerta;
    private Integer fkMaquinaAlerta; 
    private Integer fkEstabelecimento;

    public Integer getFkEstabelecimento() {
        return fkEstabelecimento;
    }
    
    public AlertaBd(String alerta, Integer fkMaquinaAlerta, Integer fkEstabelecimento) {
        this.alerta = alerta;
        this.fkMaquinaAlerta = fkMaquinaAlerta;
        this.fkEstabelecimento = fkEstabelecimento;
    }

    public String getAlerta() {
        return this.alerta;
    }

    public Integer getFkMaquinaAlerta() {
        return this.fkMaquinaAlerta;
    }
    
    
}
