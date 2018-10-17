package com.neona.numbiosis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        passoApasso();
    }

    private String katexIntegral(int inf, int sup, String f, String var, double result){
        f = f.replaceAll("\\(","{(");
        f = f.replaceAll("\\)",")}");
        f = f.replaceAll("\\*","\\\\\\cdot ");

        NumberFormat nf = DecimalFormat.getInstance();
        nf.setMaximumFractionDigits(8);

        return "$$\\large\\int_{"+inf+"}^{"+sup+"}{"+f+"}\\space d"+var+" = \\bold{"+nf.format(result)+"}$$";
    }

    private String descobreVariavel(String f){
        String var = ""+f.replaceAll("[^a-z]", "")
                .replaceAll("e", "")
                .replaceAll("pi","").charAt(0);
        return var;
    }

    private String passoApasso(){
        MathView mv = findViewById(R.id.mv_QG_explica1);
        String katex = "";

        katex += "$$\\text{Detalhes}$$";
        katex += "$$\\int_{-1}^{1}f(x)dx\\approx\\sum_{i=0}^nA_if(x_i)$$";
        katex += "$$A_i:\\text{Pesos de Gauss-Legendre}$$";
        katex = pulaLinha(katex);

        katex += "$$\\text{Para } [a,b]\\ne{[-1,1]}:$$";
        katex += "$$\\int_{a}^{b}f(x)dx\\Rightarrow\\int_{-1}^{1}F(t)dt$$";

        katex += "$$\\text{Tal que:}$$";
        katex += "$$x = \\frac{t\\cdot(b-a) + (b+a)}{2}$$";

        katex += "$$\\downdownarrows$$";

        katex += "$$\\int_{-1}^{1}F(t)dt\\approx\\sum_{i=0}^nA_iF(t_i)$$";

        mv.setDisplayText(katex);
        mv.getLayoutParams().width = katex.length()*4;

        return katex;
    }

    private String pulaLinha(String s){
        return s+"$$$$";
    }
}
