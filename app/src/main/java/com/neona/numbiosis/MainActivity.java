package com.neona.numbiosis;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.neona.numbiosis.metodos.Raiz;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnTeste = (Button) findViewById(R.id.btn_teste);
        btnTeste.setOnClickListener((View.OnClickListener ) this);

        GraphView graph = (GraphView) findViewById(R.id.graph);

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

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_teste:
                Intent it = new Intent(this, TelaInicial.class);
                startActivity(it);
                break;
        }
    }
}
