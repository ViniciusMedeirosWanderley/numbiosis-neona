package com.neona.numbiosis;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;
import com.neona.numbiosis.metodos.Raiz;
import com.neona.numbiosis.util.GraphHandler;

import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    String f = "x^3 - 6*x^2 -x + 30";
    double raiz;
    double  a = -1.5,
            b = 0.5,
            c = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GraphView graph = (GraphView) findViewById(R.id.graph);

        raiz = Raiz.secante(f,a,b,Raiz.TOL,Raiz.N);

        GraphHandler handler = new GraphHandler(f, graph);
        handler.initSerieFuncao();
        handler.initSerieRaizes();
        handler.initSerieRaizFinal(raiz);
        handler.initGraph();


        String resumo = "Raiz: "+raiz+"  MenorX: "+Raiz.getMenorX()+"  MaiorX: "+Raiz.getMaiorX();
        Log.d("resumo",resumo);
        Log.d("resumo","Iteracoes: "+Raiz.getNumeroIteracoes());
    }

}
