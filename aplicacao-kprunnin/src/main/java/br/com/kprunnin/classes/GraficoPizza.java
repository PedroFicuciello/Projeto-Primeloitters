/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.kprunnin.classes;

import java.util.Locale;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.util.TableOrder;
import org.jfree.data.category.CategoryToPieDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author olive
 */
public class GraficoPizza {

    String origem = this.getClass().getSimpleName();
    Logger log = new Logger();
    Toolbox tb = new Toolbox();
    private DefaultPieDataset dataset;

    public GraficoPizza() {
        this.dataset = new DefaultPieDataset();
    }

    public void adicionaValor(Comparable legenda, Number valor) {
        this.dataset.setValue(legenda, valor);
    }

    public DefaultPieDataset getDataset() {
        return this.dataset;
    }

    public double getPorcentagem(float valorTotal, float valorADescobrir) {
        long total = (long) valorTotal;
        long qualQuero = (long) valorADescobrir;

        return (double) (qualQuero * 100) / total;
    }

    public void limparDataset() {
        this.dataset.clear();
    }

    public ChartPanel criaGraficoPizza(DefaultPieDataset dataset, String titulo) {

        JFreeChart grafico = ChartFactory.createPieChart3D(titulo, dataset, true, true, Locale.forLanguageTag("pt-BR"));
        ChartPanel chartPanel = new ChartPanel(grafico);
        return chartPanel;

    }

    @Override
    public String toString() {
        return "Criando um gráfico de pizza";
    }
}
