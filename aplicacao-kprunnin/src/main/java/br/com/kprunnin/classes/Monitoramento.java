/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.kprunnin.classes;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HWDiskStore;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.FileSystem;
import oshi.util.Util;
import br.com.kprunnin.Gui.KprunninGui;

/**
 *
 * @author olive
 */
public class Monitoramento {

    String origem = this.getClass().getSimpleName();
    Logger log = new Logger();
    Toolbox tb = new Toolbox();
    private final SystemInfo si;
    private final CentralProcessor infoProcessador;
    private final FileSystem arquivosSistema;
    private final GlobalMemory memoriaGlobal;
    private final HardwareAbstractionLayer hal;
    private final HWDiskStore[] disk;
    long[] filas = new long[4];
    private Integer filaSetada = 0;

    public Monitoramento() {
        this.si = new SystemInfo();
        this.infoProcessador = si.getHardware().getProcessor();
        this.arquivosSistema = si.getOperatingSystem().getFileSystem();
        this.memoriaGlobal = si.getHardware().getMemory();
        this.hal = si.getHardware();
        this.disk = hal.getDiskStores();
    }

    public float getMemoriaTotal() {
        return this.memoriaGlobal.getTotal();
    }

    public float getMemoriaLivre() {
        return this.memoriaGlobal.getAvailable();
    }

    public float getMemoriaEmUso() {
        return this.memoriaGlobal.getTotal() - this.memoriaGlobal.getAvailable();
    }
        public int getPorcentagemMem() {
        long total = si.getHardware().getMemory().getTotal();
        long disp = si.getHardware().getMemory().getAvailable();
        long emUso = total - disp;
        int percUso = (int) ((double) emUso / (double) total * 100);
        return percUso;
    }

    public float[] getCPU() {

        long[] medicaoOld = infoProcessador.getSystemCpuLoadTicks();
        Util.sleep(1000);
        long[] medicaoNew = infoProcessador.getSystemCpuLoadTicks();
        long user = medicaoNew[CentralProcessor.TickType.USER.getIndex()] - medicaoOld[CentralProcessor.TickType.USER.getIndex()];
        long sys = medicaoNew[CentralProcessor.TickType.SYSTEM.getIndex()] - medicaoOld[CentralProcessor.TickType.SYSTEM.getIndex()];
        long idle = medicaoNew[CentralProcessor.TickType.IDLE.getIndex()] - medicaoOld[CentralProcessor.TickType.IDLE.getIndex()];
        double cpuEmUso = (long) (100d * (user + sys)) / (long) (user + sys + idle);

        float[] cpu = new float[1];
        double d = (double) cpuEmUso;
        cpu[0] = (float) (d);
        return cpu;
    }

    public float[] getDisco() {

        return KprunninGui.dadosDsk;
    }

    public float memoriaEmUso() {
    	float memoria = memoriaGlobal.getTotal() - memoriaGlobal.getAvailable();
		return memoria;
	}

}
