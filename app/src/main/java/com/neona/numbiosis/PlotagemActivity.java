package com.neona.numbiosis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;

public class PlotagemActivity extends AppCompatActivity {

    GraphHandler handler;
    String funcao;
    double raiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plotagem);

        Intent intent = getIntent();
        try {
            funcao = intent.getExtras().getString("funcao");

            String titulo = "f(x) = " + funcao;
            setTitle(titulo);

            GraphView graph = (GraphView) findViewById(R.id.graph); // instanciamos o gráfico
            graph.removeAllSeries();
            graph.computeScroll();
            graph.setTitle("");
            graph.getGridLabelRenderer().setLabelVerticalWidth(80);


            // Plota o grafico
            handler = new GraphHandler(funcao, graph);
            handler.initSerieFuncao();
            boolean raiz_encontrada = intent.getBooleanExtra("raiz_ok", true);
            if(raiz_encontrada) {
                raiz = intent.getExtras().getDouble("raiz");
                handler.initSerieRaizes();
                handler.initSerieRaizFinal(raiz);
            }
            handler.initGraph();

        }catch(NullPointerException ex){
            Toast.makeText(getApplicationContext(),"Ocorreu um erro.", Toast.LENGTH_SHORT).show();
        }
    }
}
