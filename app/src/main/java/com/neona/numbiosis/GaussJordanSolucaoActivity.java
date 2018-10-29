package com.neona.numbiosis;

import android.content.Intent;
import android.opengl.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import katex.hourglass.in.mathlib.MathView;

public class GaussJordanSolucaoActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gauss_jordan_solucao);

        katexSistema();
        katexPassoAPasso();
    }

    private void katexSistema() {
        Intent it = getIntent();
        double [] solucao = it.getDoubleArrayExtra("solucao");
        double [] B = it.getDoubleArrayExtra("B");
        double [][] A = (double[][])it.getSerializableExtra("A");

        NumberFormat nf = DecimalFormat.getInstance();
        nf.setMaximumFractionDigits(4);

        StringBuilder sb = new StringBuilder();

        sb.append("<div style=\"width: 5%\">$");
        sb.append("\\begin{bmatrix}");

        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[i].length; j++) {

                sb.append(A[i][j]);

                if(j < A[i].length - 1)
                    sb.append("&");
                else
                    sb.append("&:&" + nf.format(B[i]));
            }
            if(i < A[i].length - 1)
                sb.append("\\\\");

        }
        sb.append("\\end{bmatrix}");
        sb.append("$</div>");


        String katex = sb.toString();

        MathView mv = findViewById(R.id.mv_GJ_sistema);
        mv.setDisplayText(katex);
        mv.getLayoutParams().width = katex.length()*8;



        sb = new StringBuilder();
        sb.append("<div style=\"width: 5%;\">$");
        sb.append("\\begin{bmatrix}");
        for (int i = 0; i < solucao.length; i++) {
            sb.append(nf.format(solucao[i]));
            if(i < solucao.length - 1)
                sb.append("\\\\");
        }
        sb.append("\\end{bmatrix}");
        sb.append("$</div>");

        katex = sb.toString();

        mv = findViewById(R.id.mv_GJ_solucao);
        mv.setDisplayText(katex);
        mv.getLayoutParams().width = katex.length()*5;

    }

    private void katexPassoAPasso(){
        Intent it = getIntent();
        double[][][] kA = (double[][][]) it.getSerializableExtra("Aiter");
        double[][][] kB = (double[][][]) it.getSerializableExtra("Biter");
        double[][] bFinal = (double[][]) it.getSerializableExtra("Bfinal");

        NumberFormat nf = DecimalFormat.getInstance();
        nf.setMaximumFractionDigits(4);

        int nIteracoes = kA.length;

        String katex = "";
        katex += "<div style=\"width: 5%; margin-left: 40px\">";

        // sistema inicial
        double [] B = it.getDoubleArrayExtra("B");
        double [][] A = (double[][])it.getSerializableExtra("A");

        StringBuilder sb = new StringBuilder();
        sb.append("<div style=\"margin-left: 60px\">$\\text{Detalhes}$</div>");
        sb.append("<div style=\"width: 5%\">$");
        sb.append("\\begin{bmatrix}");

        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[i].length; j++) {

                sb.append(A[i][j]);

                if(j < A[i].length - 1)
                    sb.append("&");
                else
                    sb.append("&:&" + nf.format(B[i]));
            }
            if(i < A[i].length - 1)
                sb.append("\\\\");

        }
        sb.append("\\end{bmatrix}");
        sb.append("$</div>");
        katex += sb.toString();
        katex += "<div>$\\Downarrow$</div>";

        for (int k = 0; k < nIteracoes; k++) {

            // Imprimo a matriz A
            katex += "<div>$";
            katex += "\\begin{bmatrix}";
            for (int i = 0; i < kA[k].length; i++) {
                for (int j = 0; j < kA[k][i].length; j++) {
                    katex += nf.format(kA[k][i][j]);
                    if(j < kA[k][i].length - 1)
                        katex += "&";
                    else
                        katex += "&:&" + nf.format(kB[k][i][0]);
                }
                if(i < kA[k].length - 1)
                    katex += " \\\\";

            }
            katex+= "\\end{bmatrix}";
            katex += "$</div>";

            // Se for a ultima iteracao
            if(k == nIteracoes - 1){

                katex += "<div style=\"margin-top: 50px\">$";
                katex += "\\text{Normalizamos cada Linha:}";
                katex += "$</div>";

                katex += "<div>$";
                katex += "L_i = \\frac{1}{a_{ii}}{L_i}";
                katex += "$</div>";

                // ultima matriz
                katex += "<div>$";
                katex+= "\\begin{bmatrix}";

                for (int i = 0; i < kA[k].length; i++) {
                    for (int j = 0; j < kA[k][i].length; j++) {
                        if(i == j)
                            katex += "1";
                        else
                            katex += "0";

                        if(j < kA[k][i].length - 1)
                            katex += "&";
                        else
                            katex += "&:&\\bold{" + nf.format(bFinal[i][0])+"}";
                    }
                    if(i < kA[k].length - 1)
                        katex += " \\\\";

                }
                katex+= "\\end{bmatrix}";
                katex += "$</div>";
            }else{
                katex += "<div>$\\Downarrow$</div>";
            }

        }
        katex += "</div>";

        MathView mv = findViewById(R.id.mv_GJ_passo1);
        mv.setDisplayText(katex);
        mv.getLayoutParams().width = katex.length()*3;
    }
}
