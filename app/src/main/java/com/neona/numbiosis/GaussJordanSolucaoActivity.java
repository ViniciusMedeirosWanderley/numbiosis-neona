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
        Matrix solucao = it.clone();

        System.out.println(solucao);


        //TextView textSistema = findViewById(R.id.text_solução_GJ);
        //textSistema.setText(solucao.toString());





    }
}
