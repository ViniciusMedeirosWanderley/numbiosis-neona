package com.neona.numbiosis;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.text.DecimalFormat;

public class EquacoesSpline extends AppCompatActivity {
    TextView eq1, eq2, eq3;
    String eq1S, eq2S, eq3S;
    float[] equações;
    float[] x;
    DecimalFormat decimal = new DecimalFormat( "0.000" );
    //System.out.printf("s[%d] = %.5f(x-%.2f)^3 + %.5f(x-%.2f)^2 + %.5f(x-%.2f) + %.5f \n", i, a1[i], x[i], b[i], x[i], c[i], x[i], y[i]);
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.equacoes_spline);//tela que estamos usando

        Intent intent = getIntent();

        eq1  = (TextView) findViewById(R.id.equacao1);
        eq2  = (TextView) findViewById(R.id.equacao2);
        eq3  = (TextView) findViewById(R.id.equacao3);

        equações = intent.getExtras().getFloatArray("resposta");
        x = intent.getExtras().getFloatArray("x");
        eq1S = "s[1] = "+ decimal.format(equações[0]) +"(x-"+ x[1] +")^3 + " + decimal.format(equações[3]) +"(x-"+ x[1] +")^2 + "+ decimal.format(equações[6]) +"(x-"+ x[1] +") + " + decimal.format(equações[9]);
        eq2S = "s[2] = "+ decimal.format(equações[1]) +"(x-"+ x[2] +")^3 + " + decimal.format(equações[4]) +"(x-"+ x[2] +")^2 + "+ decimal.format(equações[7]) +"(x-"+ x[2] +") + " + decimal.format(equações[10]);
        eq3S = "s[3] = "+ decimal.format(equações[2]) +"(x-"+ x[3] +")^3 + " + decimal.format(equações[5]) +"(x-"+ x[3] +")^2 + "+ decimal.format(equações[8]) +"(x-"+ x[3] +") + " + decimal.format(equações[11]);

        eq1.setText(eq1S);
        eq2.setText(eq2S);
        eq3.setText(eq3S);

    }
}
