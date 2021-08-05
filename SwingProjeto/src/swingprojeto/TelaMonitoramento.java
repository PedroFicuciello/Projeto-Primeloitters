package swingprojeto;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


public class TelaMonitoramento extends javax.swing.JFrame {

    public static int contagemErrosCpu = 0;
    public static int contagemErrosMem = 0;
    public static int contagemErrosDsk = 0;

    public TelaMonitoramento() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TelaMonitoramento.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(TelaMonitoramento.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(TelaMonitoramento.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(TelaMonitoramento.class.getName()).log(Level.SEVERE, null, ex);
        }
        initComponents();

        barCpu.setMaximum(101);
        barCpu.setMinimum(0);
        barCpu.setValue(0);

        barMemoria.setMaximum(101);
        barMemoria.setMinimum(0);
        barMemoria.setValue(0);

        barDisco.setMaximum(101);
        barDisco.setMinimum(0);
        barDisco.setValue(0);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        lblCpu = new javax.swing.JLabel();
        lblMemoria = new javax.swing.JLabel();
        lblDisco = new javax.swing.JLabel();
        barCpu = new javax.swing.JProgressBar();
        barMemoria = new javax.swing.JProgressBar();
        barDisco = new javax.swing.JProgressBar();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Monitoramento de Sistema");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setForeground(java.awt.Color.blue);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblCpu.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblCpu.setForeground(new java.awt.Color(255, 255, 255));
        lblCpu.setText("CPU usada:");
        getContentPane().add(lblCpu, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 75, 119, 28));

        lblMemoria.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblMemoria.setForeground(new java.awt.Color(255, 255, 255));
        lblMemoria.setText("Memória usada:");
        getContentPane().add(lblMemoria, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 121, 119, 28));

        lblDisco.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblDisco.setForeground(new java.awt.Color(255, 255, 255));
        lblDisco.setText("Disco usado:");
        getContentPane().add(lblDisco, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 167, 119, 28));

        barCpu.setForeground(new java.awt.Color(51, 102, 255));
        barCpu.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 102, 255), 2, true));
        barCpu.setStringPainted(true);
        getContentPane().add(barCpu, new org.netbeans.lib.awtextra.AbsoluteConstraints(167, 82, 200, -1));

        barMemoria.setForeground(new java.awt.Color(51, 102, 255));
        barMemoria.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 102, 255), 2, true));
        barMemoria.setStringPainted(true);
        getContentPane().add(barMemoria, new org.netbeans.lib.awtextra.AbsoluteConstraints(167, 128, 200, -1));

        barDisco.setForeground(new java.awt.Color(51, 100, 255));
        barDisco.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 102, 255), 2, true));
        barDisco.setStringPainted(true);
        getContentPane().add(barDisco, new org.netbeans.lib.awtextra.AbsoluteConstraints(167, 174, 200, -1));

        jLabel1.setFont(new java.awt.Font("DilleniaUPC", 1, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Desempenho da máquina");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, -1, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/swingprojeto/call.jpg"))); // NOI18N
        jLabel2.setFocusable(false);
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 280));

        jMenu1.setText("Janelas");

        jMenuItem1.setText("Monitoramento");
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    public static void main(String args[]) {

        ThreadCpu ThCpu = new ThreadCpu();
        ThreadMem ThMem = new ThreadMem();
        ThreadDsk ThDsk = new ThreadDsk();

        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaMonitoramento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaMonitoramento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaMonitoramento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaMonitoramento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ThCpu.start();
                ThMem.start();
                ThDsk.start();
                new TelaMonitoramento().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JProgressBar barCpu;
    public static javax.swing.JProgressBar barDisco;
    public static javax.swing.JProgressBar barMemoria;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel lblCpu;
    private javax.swing.JLabel lblDisco;
    private javax.swing.JLabel lblMemoria;
    // End of variables declaration//GEN-END:variables
}
