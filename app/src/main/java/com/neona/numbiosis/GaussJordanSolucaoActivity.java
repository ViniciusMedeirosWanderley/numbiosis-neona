package com.neona.numbiosis;

import android.content.Intent;
import android.opengl.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class GaussJordanSolucaoActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gauss_jordan_solucao);

        //recebe os parametros enviados pela tela do Gauss Jordan

        Intent it = getIntent();
        double[] solucao = it.getDoubleArrayExtra("solucao");

        TextView textSistema = findViewById(R.id.text_solução_GJ);

        NumberFormat nf = DecimalFormat.getInstance();
        nf.setMaximumFractionDigits(6);

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0;  i< solucao.length; i++){
            sb.append(nf.format(solucao[i]));
            sb.append(", ");
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        sb.append("]");
        textSistema.setText(sb.toString());

        // escrever a matriz do sistema
        String A = it.getStringExtra("A");
        String B = it.getStringExtra("B");

        sb = new StringBuilder();

        String[] A_linhas = A.split("\\n+");
        String[] B_linhas = B.split("\\s+");

        for (int i = 0; i < A_linhas.length; i++) {
            sb.append(A_linhas[i]);
            sb.append("  |  ");
            sb.append(String.format("%1$2s",B_linhas[i]));
            sb.append("\n");
        }

        TextView textViewA = findViewById(R.id.textView_A);
        textViewA.setText(sb.toString());

    }
}
