package com.neona.numbiosis.util;

import android.graphics.Color;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;
import com.neona.numbiosis.metodos.Raiz;

import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;

import java.util.Arrays;

public class GraphHandler {

    private String funcao;
    private GraphView graph;

    private LineGraphSeries<DataPoint> series_funcao;
    private PointsGraphSeries<DataPoint> series_x;
    private PointsGraphSeries<DataPoint> series_raiz;

    public GraphHandler(String funcao, GraphView graph){
        this.setFuncao(funcao);
        this.setGraph(graph);
    }

    public void initSerieFuncao(){
        setSeries_funcao(criaSerieFuncao(Raiz.getMenorX(),Raiz.getMaiorX(), getFuncao()));
        getSeries_funcao().setColor(Color.RED);
    }

    public void initSerieRaizes(){
        setSeriesX(criaSerieRaizes());
        getSeriesX().setShape(PointsGraphSeries.Shape.POINT);
        getSeriesX().setColor(Color.BLUE);
        getSeriesX().setSize(8f);
    }

    public void initSerieRaizFinal(double raiz){
        setSeriesRaiz(new PointsGraphSeries<>(new DataPoint[]{new DataPoint(raiz,0)}));
        getSeriesRaiz().setShape(PointsGraphSeries.Shape.POINT);
        getSeriesRaiz().setColor(Color.RED);
        getSeriesRaiz().setSize(15f);

        graph.setTitle("Raiz aproximada: "+raiz);
    }

    public void initGraph(){
        // Adiciono as series
        if(getSeries_funcao() != null)
            getGraph().addSeries(getSeries_funcao());
        if(getSeriesX() != null)
            getGraph().addSeries(getSeriesX());
        if(getSeriesRaiz() != null)
            getGraph().addSeries(getSeriesRaiz());

        getGraph().getViewport().setScalable(true);
        getGraph().getViewport().setScrollable(true);
        getGraph().getViewport().setScrollableY(true);
        getGraph().getViewport().setScalableY(true);

        // impoe limites
        double menorX = Raiz.getMenorX() - 1;
        double maiorX = Raiz.getMaiorX() + 1;

        getGraph().getViewport().setMinimalViewport(menorX, maiorX,0,0);
    }

    private LineGraphSeries<DataPoint> criaSerieFuncao(double menorX, double maiorX, String funcao){
        int inf = (int)Math.ceil(menorX) - 1;
        int sup = (int)Math.ceil(maiorX) + 1;
        int qtd_pontos = (Math.abs(inf) + Math.abs(sup)) * 10;
        Argument arg_x = new Argument("x");
        Expression _f = new Expression(funcao, arg_x);
        double x = inf;
        double y;
        qtd_pontos++;
        DataPoint[] dataPoints = new DataPoint[qtd_pontos];

        for(int i = 0; i < qtd_pontos; i++){
            arg_x.setArgumentValue(x);
            y = _f.calculate();
            dataPoints[i] = new DataPoint(x,y);
            x += 0.1d;
        }

        return new LineGraphSeries<>(dataPoints);
    }

    private PointsGraphSeries<DataPoint> criaSerieRaizes(){
        DataPoint[] ar = Arrays.copyOfRange(Raiz.getDataPointsX(),0,Raiz.getNumeroIteracoes() - 1);
        insertionSort(ar, ar.length);

        return new PointsGraphSeries<>(ar);
    }

    private void insertionSort(DataPoint[] tabela, int nElem){
        int i,j;
        DataPoint aux;

        for (i = 1; i < nElem ; i++) {
            aux = tabela[i];

            // Move cada elemento maior do que aux uma posicao adiante
            for (j = i - 1; (j >= 0) && (aux.getX() < tabela[j].getX()); j--) {
                tabela[j + 1] = tabela[j];
            }

            // Posiciono aux na posicao correta
            tabela[j + 1] = aux;
        }
    }


    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public GraphView getGraph() {
        return graph;
    }

    public void setGraph(GraphView graph) {
        this.graph = graph;
    }

    public LineGraphSeries<DataPoint> getSeries_funcao() {
        return series_funcao;
    }

    public void setSeries_funcao(LineGraphSeries<DataPoint> series_funcao) {
        this.series_funcao = series_funcao;
    }

    public PointsGraphSeries<DataPoint> getSeriesX() {
        return series_x;
    }

    public void setSeriesX(PointsGraphSeries<DataPoint> series_x) {
        this.series_x = series_x;
    }

    public PointsGraphSeries<DataPoint> getSeriesRaiz() {
        return series_raiz;
    }

    public void setSeriesRaiz(PointsGraphSeries<DataPoint> series_raiz) {
        this.series_raiz = series_raiz;
    }
}
