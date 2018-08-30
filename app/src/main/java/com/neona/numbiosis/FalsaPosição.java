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

public class FalsaPosição extends AppCompatActivity implements  View.OnClickListener {
    EditText função , a,b,tol,x,n;
    String funçãoS,aS,bS,tolS,xS,nS;//variaveis para captação dos dados introduzidos pelo usuário
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_falsa_posicao);//tela que estamos usando

        Button button = (Button) findViewById(R.id.button_falsa);//instanciamos o botão da tela
        button.setOnClickListener((View.OnClickListener ) this); //colocamos ele pra ser "escutado"

        função = (EditText) findViewById(R.id.editText); //instanciamos as caixas de texto da tela
        a = (EditText) findViewById(R.id.editText2);
        b = (EditText) findViewById(R.id.editText3);
        tol = (EditText) findViewById(R.id.editText4);
        x = (EditText) findViewById(R.id.editText5);
        n = (EditText) findViewById(R.id.editText6);

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.button_falsa://caso o click seja no botão calcular

                funçãoS = função.getText().toString();   //capturamos o que foi digitado na caixa de texto da função
                System.out.println(funçãoS);//printamos o calor capturado no console
                GraphView graph = (GraphView) findViewById(R.id.graph3); // instanciamos o gráfico

                graph.removeAllSeries(); //limpa o gráfico contendo funções já plotadas.

                DataPoint[] data = new DataPoint[200];
                double x = -10,y=0;
                for(int i =0; i < 200; i++){
                    y = x*x;
                    data[i] = new DataPoint(x,y);
                    x += 0.2;
                }
                LineGraphSeries<DataPoint> series = new LineGraphSeries<>(data);
                series.setColor(Color.RED);
                graph.addSeries(series);
                graph.getViewport().setScalable(true);
                graph.getViewport().setScrollable(true);

        }
    }
}
