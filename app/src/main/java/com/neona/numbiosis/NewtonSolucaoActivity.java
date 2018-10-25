package com.neona.numbiosis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.neona.numbiosis.pacote3.ConverteKatex;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import katex.hourglass.in.mathlib.MathView;

public class NewtonSolucaoActivity extends AppCompatActivity {

    double EPSILON = 1e-5;
    ConverteKatex ktx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newton_solucao);

        ktx = new ConverteKatex();

        katexSistema();
        katexSolucao();
        katexPassoAPasso();
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
        Intent it = getIntent();
        double[] solucao = it.getDoubleArrayExtra("solucao");

        NumberFormat nf = DecimalFormat.getInstance();
        nf.setMaximumFractionDigits(10);
        StringBuilder sb = new StringBuilder();
        int i = 0;
        sb.append("<span style=\"float:left\" class=\"latexEle\">");
        sb.append("$$");
        sb.append("\\begin{aligned}");
        for (double x:solucao) {
            switch (i){
                case 0:
                    sb.append("\\bold{x} &=");
                    break;
                case 1:
                    sb.append("\\bold{y} &=");
                    break;
                case 2:
                    sb.append("\\bold{z} &=");
                    break;
                case 3:
                    sb.append("\\bold{w} &=");
                    break;
            }
            if(Math.abs(x) <= EPSILON)
                x = 0;

            sb.append("\\bold{"+nf.format(x)+"}\\\\");
            i++;
        }
        sb.append("\\end{aligned}");
        sb.append("$$");
        sb.append("</span>");

        MathView mv = findViewById(R.id.mv_NL_solucao);
        mv.setDisplayText(sb.toString());
        mv.getLayoutParams().width = sb.toString().length()*8;
    }

    private void katexPassoAPasso(){
        /*Dados: o vetor inicial x (0) ; ε1 , ε2 > 0:
        Passo I: computar F(xk) e J(xk)
        Passo II: se ||F(xk)|| menor que ε1 , x ∗ = xk e pare;
        senão
        Passo III: obter s(k) , solução do sistema J*s(k) = −F
        Passo IV: atualizar x(k+1) = xk + s(k)
        Passo V: se ||x(k+1) − xk || menor que ε2, x ∗ = x(k+1) e pare;
        senão
        Passo VI: atualizar k = k + 1*/

       Intent it = getIntent();
       double eps1 = it.getDoubleExtra("eps1", Double.NaN);
       double eps2 = it.getDoubleExtra("eps1", Double.NaN);
       double[] fx = it.getDoubleArrayExtra("fx");
       double[][] jx = (double[][]) it.getSerializableExtra("jx");

       String[] funcoes = it.getStringArrayExtra("funcoes");
       String[][] variaveis = (String[][]) it.getSerializableExtra("variaveis");

       NumberFormat nf = DecimalFormat.getInstance();
       nf.setMaximumFractionDigits(4);

       double[] k_normaFx = it.getDoubleArrayExtra("normasFx");
       double[][] k_fx = (double[][]) it.getSerializableExtra("fx");
       double[][][] k_jx = (double[][][]) it.getSerializableExtra("jx");
       double[] k_norma = it.getDoubleArrayExtra("normas");
       double[][] k_s = (double[][]) it.getSerializableExtra("sk");
       double[][] k_xk = (double[][]) it.getSerializableExtra("xk");
       int nIteracoes = it.getIntExtra("k", -1);

       String katex = "";// "$$";
       katex += "<div style=\"width: 5%;\">";
       for (int k = 0; k < nIteracoes; k++) {
           //katex += "<div>";
           katex += "<div \">$";
           katex += "k = "+(k+1)+"\\text{:}";
           katex += "$</div>";

           katex += "<div>$";
           // F(x_k)
           katex += "F(x_{"+(k+1)+"})=";
           katex += "\\begin{bmatrix}";
           for (int i = 0; i < k_fx[k].length; i++) {
               katex += nf.format(k_fx[k][i]);
               if(i < k_fx[k].length - 1) {
                   katex += "\\\\";
               }
           }
           katex += "\\end{bmatrix}";
           katex += "$</div>";

           katex += "<div>$";
           //Passo II: se ||F(xk)|| menor que ε1 , x ∗ = xk e pare;
           katex += "\\|F(x_"+(k+1)+")\\|_{\\infty} = "+String.format("%2.4E",k_normaFx[k]);
           katex += (k_normaFx[k]) < eps1 ? "<": ">";
           katex += eps1;
           katex += "$</div>";

           // Continuo Passo I
           katex += "<div>$";

           // J(x_k)
           katex += "J(x_{"+(k+1)+"})=";
           katex += "\\begin{bmatrix}";
           for (int i = 0; i < k_jx[k].length; i++) {
               for (int j = 0; j < k_jx[k][i].length; j++) {
                   katex += nf.format(k_jx[k][i][j]);
                   if(j < k_jx[k][j].length - 1)
                       katex += "&";
               }
               if(i < k_jx[k].length - 1)
                   katex += "\\\\";
           }
           katex += "\\end{bmatrix}";
           katex += "$</div>";

           katex += "<div>$";
           //Passo III: obter s(k) , solução do sistema J*s(k) = −F
           katex += "J(x_"+(k+1)+")\\cdot S_"+(k+1)+" = -F(x_"+(k+1)+")";
           katex += "$</div>";

           katex += "<div>$";
           katex += "S_"+(k+1)+"=";
           katex += "\\begin{bmatrix}";
           for (int i = 0; i < k_s[k].length; i++) {

               if(Math.abs(k_s[k][i]) < EPSILON) // tirar o -0.0
                   k_s[k][i] = 0;

               katex += nf.format(k_s[k][i]);
               if(i < k_s[k].length - 1)
                   katex += "\\\\";
           }
           katex += "\\end{bmatrix}";
           katex += "$</div>";

           katex += "<div>$";
           //Passo IV: atualizar x(k+1) = xk + s(k)
           katex += "x_"+(k+2)+" = x_"+(k+1)+" + S_"+(k+1);
           katex += " = ";
           katex += "\\begin{bmatrix}";
           for (int i = 0; i < k_xk[k].length; i++) {

               if(Math.abs(k_xk[k][i]) < EPSILON) // tirar o -0.0
                   k_xk[k][i] = 0;

               katex += nf.format(k_xk[k][i]);
               if(i < k_xk[k].length - 1)
                   katex += "\\\\";
           }
           katex += "\\end{bmatrix}";
           katex += "$</div>";

           katex += "<div>$";
           //Passo V: se ||x(k+1) − xk || menor que ε2, x ∗ = x(k+1) e pare;
           katex += "\\|x_"+(k+2)+" - x_"+(k+1)+"\\|_{\\infty} = "+String.format("%2.4E",k_norma[k]);
           katex += (k_norma[k]) < eps2 ? "<": ">";
           katex += eps2;
           katex += "$</div>";

           if(k_norma[k] < eps2){
               katex += "<div>$";
               katex += "\\bold{x_"+(k+2)+"\\text{ é solução.}}";
               katex += "$</div><br>";
               break;
           }
           //katex += "</div>";
           katex += "<br>";
       }
       //katex += "$$";
       katex += "</div>";

       MathView mv = findViewById(R.id.mv_NL_passo1);
       mv.setDisplayText(katex);
       mv.getLayoutParams().width = katex.length();
    }
}
