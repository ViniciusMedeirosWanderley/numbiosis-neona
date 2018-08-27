package com.neona.numbiosis;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.neona.numbiosis.metodos.Raiz;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}
