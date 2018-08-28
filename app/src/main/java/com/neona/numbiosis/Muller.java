package com.neona.numbiosis;

import android.content.Intent;
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
    EditText text_user;
    String teste;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muller);

        Button btnCalcularMuller = (Button) findViewById(R.id.btn_calcular);
        btnCalcularMuller.setOnClickListener((View.OnClickListener ) this);

        text_user = (EditText) findViewById(R.id.texto_muller);

        GraphView graph = (GraphView) findViewById(R.id.graph1);

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

    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_calcular:
                //MapsActivity.userinfo = text_user.getText().toString(); // ALTERA A VAR DA OUTRA CLASSE C/ AS INFO DIGITADAS
                //Intent inter = new Intent(this, MapsActivity.class);
                //startActivity(inter);
                teste = text_user.getText().toString();
                System.out.println(teste);
        }

    }
}
