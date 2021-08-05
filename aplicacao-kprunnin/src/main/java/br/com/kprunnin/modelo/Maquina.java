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
public class Maquina {

    private Integer idMaquina;
    private String tipoMaquina;
    private String codigoMaquina;
    private String numeroSerie;
    private Integer fkEstabelecimento;
    private String marcaMaquina;
    private String modelo;
    private String sistemaOperacional;
    private String espacoTotalHd;
    private String memoriaTotal;
    private String infoProcessador;

    public Maquina() {
    }

    public Maquina(Integer idMaquina, String numeroSerie,
            String marcaMaquina, String modelo, String sistemaOperacional, String espacoTotalHd,
            String memoriaTotal, String infoProcessador) {
        this.idMaquina = idMaquina;
        this.numeroSerie = numeroSerie;
        this.marcaMaquina = marcaMaquina;
        this.modelo = modelo;
        this.sistemaOperacional = sistemaOperacional;
        this.espacoTotalHd = espacoTotalHd;
        this.memoriaTotal = memoriaTotal;
        this.infoProcessador = infoProcessador;
    }

    public Maquina(Integer idMaquina, String tipoMaquina, String codigoMaquina, String numeroSerie,
            String marcaMaquina, String modelo, String sistemaOperacional, String espacoTotalHd,
            String memoriaTotal, String infoProcessador, Integer fkEstabelecimento) {
        this.idMaquina = idMaquina;
        this.tipoMaquina = tipoMaquina;
        this.codigoMaquina = codigoMaquina;
        this.numeroSerie = numeroSerie;
        this.fkEstabelecimento = fkEstabelecimento;
        this.marcaMaquina = marcaMaquina;
        this.modelo = modelo;
        this.sistemaOperacional = sistemaOperacional;
        this.espacoTotalHd = espacoTotalHd;
        this.memoriaTotal = memoriaTotal;
        this.infoProcessador = infoProcessador;
    }

    public Integer getIdMaquina() {
        return this.idMaquina;
    }

    public String getTipoMaquina() {
        return this.tipoMaquina;
    }

    public String getCodigoMaquina() {
        return this.codigoMaquina;
    }

    public String getNumeroSerie() {
        return this.numeroSerie;
    }

    public Integer getFkEstabelecimento() {
        return this.fkEstabelecimento;
    }

    public String getMarcaMaquina() {
        return this.marcaMaquina;
    }

    public String getModelo() {
        return this.modelo;
    }

    public String getSistemaOperacional() {
        return this.sistemaOperacional;
    }

    public String getEspacoTotalHd() {
        return this.espacoTotalHd;
    }

    public String getMemoriaTotal() {
        return this.memoriaTotal;
    }

    public String getInfoProcessador() {
        return this.infoProcessador;
    }

    @Override
    public String toString() {
        return String.format("Codigo da maquina: %s, fkEstabelecimento: %d", this.codigoMaquina, this.fkEstabelecimento);
    }

}
