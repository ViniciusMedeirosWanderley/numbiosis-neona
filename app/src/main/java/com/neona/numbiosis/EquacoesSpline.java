package com.neona.numbiosis;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.text.DecimalFormat;

import katex.hourglass.in.mathlib.MathView;

public class EquacoesSpline extends AppCompatActivity {
    MathView eq1, eq2, eq3, coeficiente1, coeficiente2, coeficiente3, coeficiente4, texto, formula;
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

        coeficiente1 = (MathView) findViewById(R.id.coeficiente1);
        coeficiente2 = (MathView) findViewById(R.id.coeficiente2);
        coeficiente3 = (MathView) findViewById(R.id.coeficiente3);
        coeficiente4 = (MathView) findViewById(R.id.coeficiente4);

        formula = (MathView) findViewById(R.id.formula_spline);

        texto = (MathView) findViewById(R.id.textoSpline);

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

        String string = "<div style=\"width: 5%;\">";
        string += "$\\text{Onde: }$ \n";
        string += "$h_k = x_k - x_{k-1},\\space\\space g_k = s_k''(x_k) \\space\\space e \\space\\space y_k = f(x_k)$ \n";
        string += "$\\text{Tal que: }$ \n $h_k \\text{ são os }\\Delta x's;$ \n $g_k \\text{ são as derivadas } 2^a \\text{ de cada ponto conhecido.}$";
        string += "</div>";

        texto.setDisplayText(string);

        String s1 = "<div style=\"width: 5%;\">";
        s1 += "$a_k = \\frac{g_k - g_{k-1}}{6h_k}$";
        s1 += "</div>";

        String s2 = "<div style=\"width: 5%;\">";
        s2 += "$b_k = \\frac{g_k}{2}$";
        s2 += "</div>";

        String s3 = "<div style=\"width: 5%;\">";
        s3 += "$c_k = \\frac{y_k - y_{k-1}}{h_k} + \\frac{2h_kg_k+g_{k-1}h_k}{6}$";
        s3 += "</div>";

        String s4 = "<div style=\"width: 5%;\">";
        s4 += "$d_k = y_k$";
        s4 += "</div>";

        coeficiente1.setDisplayText(s1);
        coeficiente2.setDisplayText(s2);
        coeficiente3.setDisplayText(s3);
        coeficiente4.setDisplayText(s4);

        String f = "<div style=\"width: 5%;\">";
        f += "$s_k(k) = a_k(x - x_k)^3 + b_k(x - x_k)^2 + c_k(x - x_k) + d_k \\space\\space k = 1, 2, ..., n-1$";
        f += "</div>";

        formula.setDisplayText(f);

    }
}
