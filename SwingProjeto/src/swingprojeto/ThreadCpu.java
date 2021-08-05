package swingprojeto;

import java.awt.Color;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.util.Util;
import static swingprojeto.TelaMonitoramento.barCpu;

public class ThreadCpu extends Thread {

    public boolean rodando = true;
    Toolbox tb = new Toolbox();
    CentralProcessor cpu = new SystemInfo().getHardware().getProcessor();
    
    public void run() {            
            while (rodando) {
            long[] medicaoOld = cpu.getSystemCpuLoadTicks();
            Util.sleep(1000);
            long[] medicaoNew = cpu.getSystemCpuLoadTicks();
            long user = medicaoNew[CentralProcessor.TickType.USER.getIndex()] - medicaoOld[CentralProcessor.TickType.USER.getIndex()];
            long sys = medicaoNew[CentralProcessor.TickType.SYSTEM.getIndex()] - medicaoOld[CentralProcessor.TickType.SYSTEM.getIndex()];
            long idle = medicaoNew[CentralProcessor.TickType.IDLE.getIndex()] - medicaoOld[CentralProcessor.TickType.IDLE.getIndex()];
            double cpuEmUso = (long) (100d * (user + sys)) / (long) (user + sys + idle);
            barCpu.setValue((int) cpuEmUso+1);
            barCpu.setForeground(Color.getHSBColor(tb.HSBFloat(barCpu.getValue()), 1.0f, 1.0f));
            try {
                tb.SvcAlertaSonoro();
            } catch (InterruptedException ex) {
                System.out.println(ex);            
            }
            run();
            System.exit(1);
        }
    }
}
