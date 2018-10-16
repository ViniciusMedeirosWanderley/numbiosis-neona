package com.neona.numbiosis;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.text.DecimalFormat;

import katex.hourglass.in.mathlib.MathView;

public class EquacoesSpline extends AppCompatActivity {
    MathView eq1, eq2, eq3;
    float[] equações;
    float[] x;
    DecimalFormat decimal = new DecimalFormat( "0.000" );

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.equacoes_spline);//tela que estamos usando

        Intent intent = getIntent();

        eq1  = (MathView) findViewById(R.id.equacao1);
        eq2  = (MathView) findViewById(R.id.equacao2);
        eq3  = (MathView) findViewById(R.id.equacao3);

        equações = intent.getExtras().getFloatArray("resposta");
        x = intent.getExtras().getFloatArray("x");

        int qtd = equações.length/4;
        Log.d("debug", ""+qtd);
        for (int i=0; i < qtd; i++){
            String s = "$s["+(i+1)+"] = "+ decimal.format(equações[i]) +"(x-"+ x[i+1] +")^3 + " + decimal.format(equações[i+qtd]) +"(x-"+ x[i+1] +")^2 + "+ decimal.format(equações[i+2*qtd]) +"(x-"+ x[i+1] +") + " + decimal.format(equações[i+3*qtd]) + "$";
            switch (i){
                case 0:
                    eq1.setDisplayText(s);
                    eq1.setMinimumWidth(s.length()*60);
                    break;
                case 1:
                    eq2.setDisplayText(s);
                    eq2.setMinimumWidth(s.length()*18);
                    break;
                case 2:
                    eq3.setDisplayText(s);
                    eq3.setMinimumWidth(s.length()*18);
                    break;
            }
        }

    }
}
