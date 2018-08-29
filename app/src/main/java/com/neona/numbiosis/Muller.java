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
    EditText função , x0,x1,x2,tol,n;
    String funçãoS,x0S,x1S,x2S,tolS,nS;//variaveis para captação dos dados introduzidos pelo usuário
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muller);//tela que estamos usando

        Button btn_calcular_muller = (Button) findViewById(R.id.btn_calcular_muller);//instanciamos o botão da tela
        btn_calcular_muller.setOnClickListener((View.OnClickListener ) this); //colocamos ele pra ser "escutado"

        função = (EditText) findViewById(R.id.editText_função_muller); //instanciamos as caixas de texto da tela
        x0 = (EditText) findViewById(R.id.editText_x0_muller);
        x1 = (EditText) findViewById(R.id.editText_x1_muller);
        x2 = (EditText) findViewById(R.id.editText_x2_muller);
        tol = (EditText) findViewById(R.id.editText_tol_muller);
        n = (EditText) findViewById(R.id.editText_n_muller);



    }

    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_calcular_muller://caso o click seja no botão calcular

                funçãoS = função.getText().toString();   //capturamos o que foi digitado na caixa de texto da função
                System.out.println(funçãoS);//printamos o calor capturado no console

                GraphView graph = (GraphView) findViewById(R.id.graph1); // instanciamos o gráfico

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
