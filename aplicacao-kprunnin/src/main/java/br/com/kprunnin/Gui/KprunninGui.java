/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.kprunnin.Gui;

import br.com.kprunnin.classes.TelegramBot;
import br.com.kprunnin.classes.Alerta;
import br.com.kprunnin.classes.GraficoLinha;
import br.com.kprunnin.classes.GraficoPizza;
import br.com.kprunnin.classes.InfoHardware;
import br.com.kprunnin.classes.InfoMaquina;
import br.com.kprunnin.classes.Logger;
import br.com.kprunnin.classes.Monitoramento;
import br.com.kprunnin.DAO.DadoDAO;
import br.com.kprunnin.DAO.MaquinaDAO;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import javax.swing.Timer;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.DynamicTimeSeriesCollection;
import org.jfree.data.time.Second;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import br.com.kprunnin.classes.ThreadDsk;
import br.com.kprunnin.classes.Toolbox;
import br.com.kprunnin.conexaoBanco.ConexaoBanco;
import br.com.kprunnin.conexaoBanco.ConnectionFactory;
import br.com.kprunnin.modelo.Dado;
import br.com.kprunnin.modelo.Maquina;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;

/**
 *
 * @author olive
 */
public class KprunninGui extends javax.swing.JFrame {

    Logger log = new Logger();
    Toolbox tb = new Toolbox();
    private final Monitoramento monitoramento;
    String origem = this.getClass().getSimpleName();
    private final InfoHardware infoHardware;
    private final InfoMaquina infoMaquina;

    private final DynamicTimeSeriesCollection datasetCpu;
    private final DynamicTimeSeriesCollection datasetDisco;

    private final GraficoLinha graficoLinha;
    private final GraficoPizza graficoPizza;

    private String index1 = "Memoria em uso";
    private String index2 = "memoria livre";

    public static float[] dadosDsk = {10};
    public static Double espacoHD;
    public static float[] dadosMem = new float[1];
    public static float[] dadosCpu = new float[1];
    public static int usoDsk = 0;

    public static int timeTick = 0;

    private final Alerta alerta;

    public static int contagemErrosCpu;
    public static int contagemErrosMem;
    public static int contagemErrosDsk;

    private boolean testado;
    private Integer idMaquina;
    private Integer idEstabelecimento;
    private Maquina maquina;
    private boolean conectado;
    public static String detalheConexao;

    public void setDetalheConexao(String detalheConexao) {
        this.detalheConexao = detalheConexao;
    }

    public String getDetalheConexao() {
        return detalheConexao;
    }

    public KprunninGui() throws IOException {
        initComponents();
        log.inicio();
        this.conectado = false;
        this.testado = false;
        this.monitoramento = new Monitoramento();

        this.infoHardware = new InfoHardware();
        this.infoMaquina = new InfoMaquina();

        this.graficoLinha = new GraficoLinha(monitoramento.getCPU());
        this.graficoPizza = new GraficoPizza();

        this.datasetCpu = new DynamicTimeSeriesCollection(1, 60, new Second());
        this.datasetDisco = new DynamicTimeSeriesCollection(1, 60, new Second());

        this.alerta = new Alerta();
        

        if (testado == false) {

            try (Connection connection = new ConnectionFactory().getConnection()) {

                ConexaoBanco cb = new ConexaoBanco();

                testado = cb.testaConexaoComBanco();

                if (testado == true) {
                    conectado = cb.conectarComBanco(connection);
                    this.idMaquina = cb.getIdMaquina();
                    this.idEstabelecimento = cb.getIdEstabelecimento();
                    this.maquina = cb.getMaquina();
                    conectado = true;
                }

            } catch (SQLException | IOException ex) {
                System.out.println("Não possui configuração, por favor a realize para que funcione");
                ex.printStackTrace();
            }
        }

        if (conectado == true) {

            boolean validado = tb.validaMaquina(this.maquina);

            if (!validado) {
                try (Connection connection = new ConnectionFactory().getConnection()) {

                    Maquina maquinaComDados = new Maquina(this.idMaquina, infoMaquina.getNumeroDeSerie(), infoMaquina.getMarca(),
                            infoMaquina.getModelo(), infoMaquina.getSistemaOperacional(), infoHardware.getEspacoHd(), infoHardware.getMemoria(),
                            infoHardware.getProcessador());
                    MaquinaDAO maquinaDAO = new MaquinaDAO(connection);
                    maquinaDAO.insert(maquinaComDados);

                } catch (SQLException ex) {
                    System.out.println("Não foi possível inserir os dados de descrição da máquina");
                    ex.printStackTrace();
                }
            }

            getInfoEstaticas();

            lblAlerta.setText("Numeros de alertas Registrados: ");
            barraDsk.setMaximum(101);
            barraDsk.setMinimum(0);
            barraDsk.setValue(0);

            setValoresIniciais();

            atualizar();

        }
    }

    private void atualizar() {

        Timer timer = new Timer(50, e -> {

            this.datasetCpu.advanceTime();
            this.datasetCpu.appendData(monitoramento.getCPU());

            this.datasetDisco.advanceTime();
            this.datasetDisco.appendData(monitoramento.getDisco());

            graficoPizza.getDataset().setValue(this.index1, monitoramento.getMemoriaEmUso());
            graficoPizza.getDataset().setValue(this.index2, monitoramento.getMemoriaLivre());

            try {
                alerta.lancarAlerta(monitoramento.getCPU()[0], monitoramento.getDisco()[0],
                        alerta.pegaPorcentagem(monitoramento.getMemoriaTotal(), monitoramento.getMemoriaEmUso()), this.idMaquina, this.idEstabelecimento);
            } catch (IOException | SQLException ex) {
                java.util.logging.Logger.getLogger(KprunninGui.class.getName()).log(Level.SEVERE, null, ex);
            }

            lblAlerta.setText("Numeros de Alertas Registrados: " + alerta.getErrosRegistrados());

            try (Connection connection = new ConnectionFactory().getConnection()) {
                boolean insertRealizado = false;

                Dado dado = new Dado(monitoramento.getCPU()[0], monitoramento.getMemoriaEmUso(),
                        monitoramento.getDisco()[0], this.idMaquina);
                DadoDAO dadoDao = new DadoDAO(connection);
                insertRealizado = dadoDao.insert(dado);
                //debug : registrar inserts 
                //log.gravarLinha(tb.data(), "INFO", origem, "SQL - Insert de cpu, memoria e disco realizado");
                //System.out.println("Insert realizado: " + insertRealizado);

            } catch (SQLException ex) {
                System.out.println("Erro no insert de dados");
                try {
                    log.gravarLinha(tb.data(), "ALRT", origem, "SQL : erro no insert de cpu, memoria e disco");
                } catch (IOException ex1) {
                    java.util.logging.Logger.getLogger(KprunninGui.class.getName()).log(Level.SEVERE, null, ex1);
                }
                //} catch (IOException ex) {
                //    java.util.logging.Logger.getLogger(KprunninGui.class.getName()).log(Level.SEVERE, null, ex);
                //
            }
            pnlGeral.updateUI();

        });
        timer.start();
    }

    private void getInfoEstaticas() {

        lblMarca.setText("Montadora: " + infoMaquina.getMarca());
        lblModelo.setText("Modelo: " + infoMaquina.getModelo());
        lblNumeroSerie.setText("Numero de Série: " + infoMaquina.getNumeroDeSerie());
        lblSistemaOperacional.setText("Sistema Operacional: " + infoMaquina.getSistemaOperacional());
        lblMemoria.setText("Memoria total: " + infoHardware.getMemoria());
        lblProcessador.setText("Informações do Processador: " + infoHardware.getProcessador());
        lblArmazenamento.setText(String.format("Armazenamento total: %s", infoHardware.getEspacoHd()));

    }

    private void setValoresIniciais() {

        Date date = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());

        this.datasetCpu.setTimeBase(new Second(date));
        this.datasetCpu.addSeries(monitoramento.getCPU(), 0, "CPU");

        this.datasetDisco.setTimeBase(new Second(date));
        this.datasetDisco.addSeries(monitoramento.getCPU(), 0, "Disco");

        graficoPizza.adicionaValor(this.index1, monitoramento.getMemoriaEmUso());

        graficoPizza.adicionaValor(this.index2, monitoramento.getMemoriaLivre());

    }

    private ChartPanel criaGraficoCpu() {

        JFreeChart systemCpu = ChartFactory.createTimeSeriesChart("Uso da CPU", "Tempo", "% de uso da CPU", this.datasetCpu, true,
                true, false);
        ChartPanel chartPanel = new ChartPanel(systemCpu);

        return chartPanel;

    }

    private ChartPanel criaGraficoMemoria() {

        ChartPanel chartPanel = graficoPizza.criaGraficoPizza(graficoPizza.getDataset(), "Uso da Memória");
        return chartPanel;

    }

    private ChartPanel criaGraficoDisco() {

        JFreeChart graficoDisco = ChartFactory.createTimeSeriesChart("Uso do Disco", "Tempo", "% de uso da Disco", this.datasetDisco, true,
                true, false);
        ChartPanel chartPanel = new ChartPanel(graficoDisco);

        return chartPanel;
    }

    private void getGrafico(ChartPanel chartPanel) {

        chartPanel.setVisible(true);
        chartPanel.setSize(pnlGeral.getWidth(), pnlGeral.getHeight());

        pnlGeral.removeAll();
        pnlGeral.add(chartPanel);
        pnlGeral.revalidate();
        pnlGeral.repaint();

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel4 = new javax.swing.JLabel();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        lblDisco = new javax.swing.JLabel();
        barraDsk = new javax.swing.JProgressBar();
        pnlGeral = new javax.swing.JPanel();
        btnCPU = new javax.swing.JButton();
        btnMemoria = new javax.swing.JButton();
        btnDisco = new javax.swing.JButton();
        lblMarca = new javax.swing.JLabel();
        lblModelo = new javax.swing.JLabel();
        lblNumeroSerie = new javax.swing.JLabel();
        lblSistemaOperacional = new javax.swing.JLabel();
        lblArmazenamento = new javax.swing.JLabel();
        lblMemoria = new javax.swing.JLabel();
        lblProcessador = new javax.swing.JLabel();
        lblAlerta = new javax.swing.JLabel();
        fundo = new javax.swing.JLabel();

        jLabel4.setText("jLabel4");

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblDisco.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblDisco.setForeground(new java.awt.Color(255, 255, 255));
        lblDisco.setText("Espaço em disco usado:");
        getContentPane().add(lblDisco, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 420, 190, -1));

        barraDsk.setForeground(new java.awt.Color(51, 100, 255));
        barraDsk.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        barraDsk.setStringPainted(true);
        getContentPane().add(barraDsk, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 450, 470, 30));

        pnlGeral.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout pnlGeralLayout = new javax.swing.GroupLayout(pnlGeral);
        pnlGeral.setLayout(pnlGeralLayout);
        pnlGeralLayout.setHorizontalGroup(
            pnlGeralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 488, Short.MAX_VALUE)
        );
        pnlGeralLayout.setVerticalGroup(
            pnlGeralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 228, Short.MAX_VALUE)
        );

        getContentPane().add(pnlGeral, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 170, -1, 230));

        btnCPU.setText("Mostrar uso CPU");
        btnCPU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCPUActionPerformed(evt);
            }
        });
        getContentPane().add(btnCPU, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, 180, -1));

        btnMemoria.setText("Mostrar uso Memória");
        btnMemoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMemoriaActionPerformed(evt);
            }
        });
        getContentPane().add(btnMemoria, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 130, 170, -1));

        btnDisco.setText("Mostrar uso de Disco");
        btnDisco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDiscoActionPerformed(evt);
            }
        });
        getContentPane().add(btnDisco, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 130, 170, -1));

        lblMarca.setForeground(new java.awt.Color(255, 255, 255));
        lblMarca.setText("jLabel1");
        getContentPane().add(lblMarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 6, 279, -1));

        lblModelo.setForeground(new java.awt.Color(255, 255, 255));
        lblModelo.setText("jLabel2");
        getContentPane().add(lblModelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 10, 250, -1));

        lblNumeroSerie.setForeground(new java.awt.Color(255, 255, 255));
        lblNumeroSerie.setText("jLabel5");
        getContentPane().add(lblNumeroSerie, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 30, 250, -1));

        lblSistemaOperacional.setForeground(new java.awt.Color(255, 255, 255));
        lblSistemaOperacional.setText("jLabel7");
        getContentPane().add(lblSistemaOperacional, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 28, -1, -1));

        lblArmazenamento.setForeground(new java.awt.Color(255, 255, 255));
        lblArmazenamento.setText("jLabel8");
        getContentPane().add(lblArmazenamento, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 50, 250, -1));

        lblMemoria.setForeground(new java.awt.Color(255, 255, 255));
        lblMemoria.setText("jLabel9");
        getContentPane().add(lblMemoria, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 50, 279, -1));

        lblProcessador.setForeground(new java.awt.Color(255, 255, 255));
        lblProcessador.setText("jLabel10");
        getContentPane().add(lblProcessador, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 78, 460, -1));

        lblAlerta.setForeground(new java.awt.Color(255, 255, 255));
        lblAlerta.setText("jLabel11");
        getContentPane().add(lblAlerta, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 103, 245, 19));

        fundo.setBackground(new java.awt.Color(0, 0, 0));
        fundo.setFont(new java.awt.Font("Tahoma", 0, 1000)); // NOI18N
        fundo.setText(".");
        fundo.setIconTextGap(0);
        fundo.setOpaque(true);
        getContentPane().add(fundo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 660, 500));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCPUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCPUActionPerformed
        getGrafico(criaGraficoCpu());
        barraDsk.setVisible(false);
        KprunninGui.lblDisco.setVisible(false);
    }//GEN-LAST:event_btnCPUActionPerformed

    private void btnMemoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMemoriaActionPerformed
        getGrafico(criaGraficoMemoria());
        barraDsk.setVisible(false);
        KprunninGui.lblDisco.setVisible(false);
    }//GEN-LAST:event_btnMemoriaActionPerformed

    private void btnDiscoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDiscoActionPerformed
        getGrafico(criaGraficoDisco());
        barraDsk.setVisible(true);
        KprunninGui.lblDisco.setVisible(true);
    }//GEN-LAST:event_btnDiscoActionPerformed

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String args[]) throws Exception {
        ThreadDsk ThDsk = new ThreadDsk();
        Logger log = new Logger();

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
            java.util.logging.Logger.getLogger(KprunninGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(KprunninGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(KprunninGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(KprunninGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ThDsk.start();
                try {
                    new KprunninGui().setVisible(true);
                } catch (IOException ex) {
                    java.util.logging.Logger.getLogger(KprunninGui.class.getName()).log(Level.SEVERE, null, ex);
                }
                KprunninGui.barraDsk.setVisible(false);
                KprunninGui.lblDisco.setVisible(false);

            }
        });

        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new TelegramBot());

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JProgressBar barraDsk;
    private javax.swing.JButton btnCPU;
    private javax.swing.JButton btnDisco;
    private javax.swing.JButton btnMemoria;
    private javax.swing.JLabel fundo;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel lblAlerta;
    public static javax.swing.JLabel lblArmazenamento;
    public static javax.swing.JLabel lblDisco;
    private javax.swing.JLabel lblMarca;
    private javax.swing.JLabel lblMemoria;
    private javax.swing.JLabel lblModelo;
    private javax.swing.JLabel lblNumeroSerie;
    private javax.swing.JLabel lblProcessador;
    private javax.swing.JLabel lblSistemaOperacional;
    private javax.swing.JPanel pnlGeral;
    // End of variables declaration//GEN-END:variables
}
