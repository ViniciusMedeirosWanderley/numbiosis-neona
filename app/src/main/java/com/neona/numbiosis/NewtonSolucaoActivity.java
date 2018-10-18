package com.neona.numbiosis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.neona.numbiosis.pacote3.ConverteKatex;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import katex.hourglass.in.mathlib.MathView;

public class NewtonSolucaoActivity extends AppCompatActivity {

    double EPSILON = 1e-10;
    ConverteKatex ktx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newton_solucao);

        ktx = new ConverteKatex();

        Intent it = getIntent();
        double[] solucao = it.getDoubleArrayExtra("solucao");
        String sistema = it.getStringExtra("sistema");

        TextView textSistema = findViewById(R.id.textSistema);
        textSistema.setText(sistema);

        TextView textSolucao = findViewById(R.id.textSolucao);
        NumberFormat nf = DecimalFormat.getInstance();
        nf.setMaximumFractionDigits(10);
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (double x:solucao) {
            switch (i){
                case 0:
                    sb.append("x = ");
                    break;
                case 1:
                    sb.append("y = ");
                    break;
                case 2:
                    sb.append("z = ");
                    break;
                case 3:
                    sb.append("w = ");
                    break;
            }
            if(Math.abs(x) <= EPSILON)
                x = 0;

            sb.append(String.format("%1$2s", nf.format(x)));
            sb.append("\n");
            i++;
        }
        textSolucao.setText(sb.toString());

        katexSistema();
        katexSolucao();
    }

    private void katexSistema(){
        Intent it = getIntent();

        String[] funcoes = it.getStringArrayExtra("funcoes");
        String sistemaKatex = ktx.toSistemaKatex(funcoes);
        MathView mv = findViewById(R.id.mv_NL_sistema);
        mv.setDisplayText(sistemaKatex);
        mv.getLayoutParams().width = sistemaKatex.length()*10;
    }

    private void katexSolucao(){
        //\begin{aligned}
        //   a&=b+c \\
        //   d+e&=f
        //\end{aligned}

        Intent it = getIntent();
        double[] solucao = it.getDoubleArrayExtra("solucao");

        NumberFormat nf = DecimalFormat.getInstance();
        nf.setMaximumFractionDigits(10);
        StringBuilder sb = new StringBuilder();
        int i = 0;
        sb.append("$$");
        sb.append("\\begin{aligned}");
        for (double x:solucao) {
            //sb.append("$$");
            switch (i){
                case 0:
                    sb.append("x &=");
                    break;
                case 1:
                    sb.append("y &=");
                    break;
                case 2:
                    sb.append("z &=");
                    break;
                case 3:
                    sb.append("w &=");
                    break;
            }
            if(Math.abs(x) <= EPSILON)
                x = 0;

            sb.append(nf.format(x)+"\\\\");
            i++;
        }
        sb.append("\\end{aligned}$$");

        MathView mv = findViewById(R.id.mv_NL_solucao);
        mv.setDisplayText(sb.toString());
        mv.getLayoutParams().width = sb.toString().length()*5;
    }
}
