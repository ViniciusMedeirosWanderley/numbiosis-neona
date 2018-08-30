package com.neona.numbiosis;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class Muller extends AppCompatActivity implements View.OnClickListener{
    double raiz;
    GraphHandler handler;
    EditText função , x0,x1,x2,tol,n;
    String funçãoS,x0S,x1S,x2S,tolS,nS;//variaveis para captação dos dados introduzidos pelo usuário
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muller);//tela que estamos usando

        Button btn_calcular_muller = (Button) findViewById(R.id.btn_calcular_muller);//instanciamos o botão da tela
        btn_calcular_muller.setOnClickListener((View.OnClickListener ) this); //colocamos ele pra ser "escutado"

    }

    public void onClick(View view) {

        função = (EditText) findViewById(R.id.editText_função_muller); //instanciamos as caixas de texto da tela
        x0 = (EditText) findViewById(R.id.editText_x0_muller);
        x1 = (EditText) findViewById(R.id.editText_x1_muller);
        x2 = (EditText) findViewById(R.id.editText_x2_muller);
        tol = (EditText) findViewById(R.id.editText_tol_muller);
        n = (EditText) findViewById(R.id.editText_n_muller);

        switch(view.getId()){
            case R.id.btn_calcular_muller://caso o click seja no botão calcular

                GraphView graph1 = (GraphView) findViewById(R.id.graph1); // instanciamos o gráfico

                graph1.removeAllSeries(); //limpa o gráfico contendo funções já plotadas.

                funçãoS = função.getText().toString();   //capturamos o que foi digitado na caixa de texto da função
                x0S = x0.getText().toString();   //capturamos o que foi digitado na caixa de texto da função
                x1S = x1.getText().toString();   //capturamos o que foi digitado na caixa de texto da função
                x2S = x2.getText().toString();   //capturamos o que foi digitado na caixa de texto da função
                tolS = tol.getText().toString();   //capturamos o que foi digitado na caixa de texto da função
                nS = n.getText().toString();

                double x0, x1,x2, tol;
                int n;

                n = Integer.parseInt(nS);
                x0 = Double.parseDouble(x0S);
                x1 = Double.parseDouble(x1S);
                x2 = Double.parseDouble(x2S);
                tol = Double.parseDouble(tolS);

                raiz = Raiz.muller(funçãoS,x0,x1,x2,tol,n);

                handler = new GraphHandler(funçãoS, graph1);
                handler.initSerieFuncao();
                handler.initSerieRaizes();
                handler.initSerieRaizFinal(raiz);
                handler.initGraph();
        }

    }
}
