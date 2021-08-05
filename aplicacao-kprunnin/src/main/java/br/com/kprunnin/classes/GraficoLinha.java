/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.kprunnin.classes;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.DynamicTimeSeriesCollection;
import org.jfree.data.time.Second;

/**
 *
 * @author olive
 */
public class GraficoLinha {

    String origem = this.getClass().getSimpleName();
    Logger log = new Logger();
    Toolbox tb = new Toolbox();
    private final DynamicTimeSeriesCollection dataset;
    private Date horaMinuto;
    private ChartPanel chartPanel;

    public GraficoLinha(float[] valor) {

        this.dataset = new DynamicTimeSeriesCollection(1, 60, new Second());
        this.horaMinuto = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
    }

    public void adicionaValor(float[] valor, Comparable titulo) {
        this.dataset.setTimeBase(new Second(this.horaMinuto));
        this.dataset.addSeries(valor, 0, titulo);
    }

    public void atualizaDados(float[] valor) {

        this.dataset.appendData(valor);
        this.dataset.advanceTime();

    }

    public DynamicTimeSeriesCollection getDataset() {
        return this.dataset;
    }

    public ChartPanel getChartPanel() {
        return this.chartPanel;
    }

    public ChartPanel criaGraficoBarra(String titulo, String eixoY, String eixoX) {

        JFreeChart grafico = ChartFactory.createTimeSeriesChart(titulo, eixoX, eixoY, this.dataset, true, true, false);
        ChartPanel myChartPanel = new ChartPanel(grafico);
        this.chartPanel = myChartPanel;
        return myChartPanel;

    }

    @Override
    public String toString() {
        return "Criando um gráfico de linha";
    }

}
