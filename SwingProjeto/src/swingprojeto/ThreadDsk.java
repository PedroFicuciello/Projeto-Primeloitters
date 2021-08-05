package swingprojeto;

import java.awt.Color;
import oshi.SystemInfo;
import oshi.hardware.HWDiskStore;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.util.Util;
import static swingprojeto.TelaMonitoramento.barDisco;

public class ThreadDsk extends Thread {

    public boolean rodando = true;
    Toolbox tb = new Toolbox();
    public SystemInfo si = new SystemInfo();
    public HardwareAbstractionLayer hal = si.getHardware();
    long[] filas = new long[4];
    public void run() {
        
    HWDiskStore[] disk = hal.getDiskStores();
        int numeroDaMedicao = 0;
        float filaResultado;

        //String[] preset = {"SSD", "HD"};
        //System.out.println("quantidade de discos : " + hal.getDiskStores().length);
        for (numeroDaMedicao = 0; numeroDaMedicao < filas.length; numeroDaMedicao++) {
            filas[numeroDaMedicao] = disk[0].getCurrentQueueLength();
            filaResultado = (float) (filas[0] + filas[1] + filas[2] + filas[3]) / (float) 4.0;
            int filaSetada = (int) (filaResultado * 20);
            barDisco.setValue(filaSetada);
            barDisco.setForeground(Color.getHSBColor(tb.HSBFloat(barDisco.getValue()), 1.0f, 1.0f));
            Util.sleep(500);

        }
        run();

    }

}
