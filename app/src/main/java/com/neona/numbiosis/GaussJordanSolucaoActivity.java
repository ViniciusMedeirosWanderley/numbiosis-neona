package com.neona.numbiosis;

import android.content.Intent;
import android.opengl.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class GaussJordanSolucaoActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gauss_jordan_solucao);

        //recebe os parametros enviados pela tela do Gauss Jordan

        Intent it = getIntent();
        double[] solucao = it.getDoubleArrayExtra("solucao");

        TextView textSistema = findViewById(R.id.text_solução_GJ);

        StringBuilder sb = new StringBuilder();
        for (int i = 0;  i< solucao.length; i++){
            sb.append(Double.toString(solucao[i]));
            sb.append("\n");
        }
        textSistema.setText(sb.toString());

    }
}
