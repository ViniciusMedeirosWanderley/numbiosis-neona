package com.neona.numbiosis;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class GaussJordan extends AppCompatActivity implements  View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gauss_jordan);

        Button calcular = (Button) findViewById(R.id.GJ_botao_calcular); //instanciamos o botão da tela
        calcular.setOnClickListener((View.OnClickListener) this); //colocamos ele pra ser "escutado"

    }

    @Override
    public void onClick(View view) {

    }
}
