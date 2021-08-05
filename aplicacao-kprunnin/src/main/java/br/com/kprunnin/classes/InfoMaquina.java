/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.kprunnin.classes;

import oshi.SystemInfo;
import oshi.hardware.ComputerSystem;
import oshi.software.os.OperatingSystem;

/**
 *
 * @author olive
 */
public class InfoMaquina {

    String origem = this.getClass().getSimpleName();
    Logger log = new Logger();
    Toolbox tb = new Toolbox();
    private final SystemInfo si;
    private final OperatingSystem sistemaOperacional;
    private final ComputerSystem infoComputador;

    public InfoMaquina() {
        this.si = new SystemInfo();
        this.sistemaOperacional = si.getOperatingSystem();
        this.infoComputador = si.getHardware().getComputerSystem();
    }

    public String getSistemaOperacional() {
        String sistemaOperacional = String.valueOf(this.sistemaOperacional);
        return sistemaOperacional;
    }

    public String getMarca() {
        String marca = String.valueOf(this.infoComputador.getManufacturer());
        return marca;
    }

    public String getModelo() {
        String modelo = String.valueOf(this.infoComputador.getModel());
        return modelo;
    }

    public String getNumeroDeSerie() {
        String numeroSerie = String.valueOf(this.infoComputador.getSerialNumber());
        return numeroSerie;
    }

    @Override
    public String toString() {
        return "Informações da máquina como fabricante e número de série";
    }
}
