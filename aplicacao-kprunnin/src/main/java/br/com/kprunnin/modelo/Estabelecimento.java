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
public class Estabelecimento {
    
    private Integer idEstab;
    private String codEstab;
    private Integer fkUsuario;
    private String nomeEstab;

    public Estabelecimento(){}

    public Estabelecimento(Integer idEstab, String codEstab, String nomeEstab, Integer fkUsuario) {
        this.idEstab = idEstab;
        this.codEstab = codEstab;
        this.fkUsuario = fkUsuario;
        this.nomeEstab = nomeEstab;
    }

    public Integer getIdEstab() {
        return this.idEstab;
    }

    public String getCodEstab() {
        return this.codEstab;
    }

    public Integer getFkUsuario() {
        return this.fkUsuario;
    }

    public String getNomeEstab() {
        return this.nomeEstab;
    }
    
    @Override
    public String toString() {
        return String.format("Estabelecimento código: %s", this.codEstab);
    }
    
    
}
