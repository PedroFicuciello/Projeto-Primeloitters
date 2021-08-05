package swingprojeto;

import java.awt.Color;
import oshi.SystemInfo;
import oshi.util.Util;
import static swingprojeto.TelaMonitoramento.barMemoria;

public class ThreadMem extends Thread {

    public boolean rodando = true;
    Toolbox tb = new Toolbox();
    SystemInfo si = new SystemInfo();
    
    public void run() {
        long total = si.getHardware().getMemory().getTotal();
        long disp = si.getHardware().getMemory().getAvailable();
        long emUso = total - disp;
        int percUso = (int) ((double) emUso / (double) total * 100);
        Util.sleep(1000);
        barMemoria.setValue(percUso);
        barMemoria.setForeground(Color.getHSBColor(tb.HSBFloat(barMemoria.getValue()), 1.0f, 1.0f));

        try {
            tb.SvcAlertaSonoro();
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }
        run();
        System.exit(1);
    }

}
