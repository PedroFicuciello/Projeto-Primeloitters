package br.com.kprunnin.classes;

import oshi.SystemInfo;
import oshi.hardware.HWDiskStore;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.util.Util;
import br.com.kprunnin.Gui.KprunninGui;
import java.awt.Color;
import java.io.File;

public class ThreadDsk extends Thread {

    String origem = this.getClass().getSimpleName();
    Logger log = new Logger();
    Toolbox tb = new Toolbox();
    public boolean rodando = true;
    public SystemInfo si = new SystemInfo();
    public HardwareAbstractionLayer hal = si.getHardware();
    long[] filas = new long[4];
    File[] disco = File.listRoots();
    HWDiskStore[] disk = hal.getDiskStores();
    int tamanho = (int) (disk[0].getSize() / 1073741824);
    
    
    public void run() {
        HWDiskStore[] disk = hal.getDiskStores();
        int numeroDaMedicao = 0;
        float filaResultado;
        KprunninGui.espacoHD = (double) disco[0].getFreeSpace() / 1073741824;
        Double espacoTotal = (double) disco[0].getTotalSpace() / 1073741824;
        KprunninGui.lblArmazenamento.setText("Armazenamento Total: " + (disk[0].getSize() / 1073741824) + " GB");
        int espacoUsado = (int) Math.round(((espacoTotal - KprunninGui.espacoHD) / espacoTotal) * 100);
        KprunninGui.barraDsk.setValue(espacoUsado);
        KprunninGui.barraDsk.setForeground(Color.getHSBColor(tb.HSBFloat(KprunninGui.barraDsk.getValue()), 1.0f, 1.0f));
        if (KprunninGui.timeTick < 5) {
            KprunninGui.dadosDsk[0]++;
            KprunninGui.timeTick++;
            Util.sleep(1000);
        } else {
            for (numeroDaMedicao = 0; numeroDaMedicao < filas.length; numeroDaMedicao++) {
                filas[numeroDaMedicao] = disk[0].getCurrentQueueLength();
                filaResultado = (float) (filas[0] + filas[1] + filas[2] + filas[3]) / (float) 4.0;
                int filaSetada = (int) (filaResultado * 20);
                KprunninGui.dadosDsk[0] = (float) filaSetada;
                Util.sleep(500);

            }
        }
        run();

    }

}
