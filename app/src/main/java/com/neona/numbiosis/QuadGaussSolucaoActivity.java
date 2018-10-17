package com.neona.numbiosis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import katex.hourglass.in.mathlib.MathView;

public class QuadGaussSolucaoActivity extends AppCompatActivity {

    private int a, b;
    private double resultado;
    private String funcao;
    private String variavel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quad_gauss_solucao);

        Intent it = getIntent();
        a = it.getIntExtra("A", -1);
        b = it.getIntExtra("B", 1);
        resultado = it.getDoubleExtra("solucao", Double.NaN);
        funcao = it.getStringExtra("funcao");
        variavel = descobreVariavel(funcao);


        MathView mv = findViewById(R.id.mv_QG_solucao);
        String katex = katexIntegral(a,b,funcao,variavel,resultado);
        mv.setDisplayText(katex);

        mv.getLayoutParams().width = katex.length()*18;


        //Log.d("func",katexIntegral(a,b,funcao,variavel,resultado));
    }

    private String katexIntegral(int inf, int sup, String f, String var, double result){
        f = f.replaceAll("\\(","{(");
        f = f.replaceAll("\\)",")}");
        f = f.replaceAll("\\*","\\\\\\cdot ");

        NumberFormat nf = DecimalFormat.getInstance();
        nf.setMaximumFractionDigits(8);

        return "$$\\large\\int_{"+inf+"}^{"+sup+"}{"+f+"}\\space d"+var+" = +"+nf.format(result)+"$$";
    }

    private String descobreVariavel(String f){
        String var = ""+f.replaceAll("[^a-z]", "")
                .replaceAll("e", "")
                .replaceAll("pi","").charAt(0);
        return var;
    }
}
