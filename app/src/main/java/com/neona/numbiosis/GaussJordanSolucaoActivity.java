package com.neona.numbiosis;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class GaussJordanSolucaoActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gauss_jordan_solucao);

        //recebe os parametros enviados pela tela do Gauss Jordan
        Intent it = getIntent();
        //double[] solucao = it.getDoubleArrayExtra("solucao");
        //String sistema = it.getStringExtra("sistema");



    }
}
