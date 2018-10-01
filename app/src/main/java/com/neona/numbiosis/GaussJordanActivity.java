package com.neona.numbiosis;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import Jama.Matrix;

public class GaussJordanActivity extends AppCompatActivity implements  View.OnClickListener{

    String[] matrizes;
    String[] dimensoesA;
    String[] dimensoesB;

    double matrizA[][];
    double matrizB[][];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gauss_jordan);

        Button calcular = (Button) findViewById(R.id.GJ_botao_calcular); //instanciamos o botão da tela
        calcular.setOnClickListener((View.OnClickListener) this); //colocamos ele pra ser "escutado"

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.GJ_botao_calcular:
                EditText editTextMatrizes = findViewById(R.id.txt_matrizes);
                String txt_matrizes = editTextMatrizes.getText().toString().trim();

                //capturar dimensões das matrizes nas caixas de texto

                EditText editTextDimensoesA = findViewById(R.id.editText_GJ_dimensoesA);
                EditText editTextDimensoesB = findViewById(R.id.editText_GJ_dimensoesB);

                String txt_dimensoesA = editTextDimensoesA.getText().toString().trim();
                String txt_dimensoesB = editTextDimensoesB.getText().toString().trim();

                if(txt_dimensoesA.equals(""))
                    txt_dimensoesA = "3x3";

                if(txt_dimensoesB.equals(""))
                    txt_dimensoesB = "1x3";

                dimensoesA = txt_dimensoesA.split("x");
                dimensoesB = txt_dimensoesB.split("x");

                /*
                    As dimensões estão como String
                 */

                if(txt_matrizes.equals(""))
                    txt_matrizes = "2 1 -1 5 2 2 3 1 1 \n1 -4 5";

                matrizes = txt_matrizes.split("\\n+");
                for (int i = 0; i < matrizes.length; i++){
                    matrizes[i] = matrizes[i].trim();
                }

                //TODO: precisa pegar os valores das matrizes transformar para double

                matrizA = new double[][]{{2, 1, -1},
                                         {5, 2, 2},
                                         {3, 1, 1}};

                matrizB = new double[][] {{1},{-4},{5}};

                Matrix A = new Matrix(matrizA);
                Matrix B = new Matrix(matrizB);

                //acha a solucao
                com.neona.numbiosis.pacote2.GaussJordan gj = new com.neona.numbiosis.pacote2.GaussJordan(A, B);
                gj.resolve();

                System.out.println("Normaliza");
                A.print(5, 2);
                System.out.println("Soluçao");
                B.print(5, 2);

                //tela de solucao
                Intent it = new Intent(this, GaussJordanSolucaoActivity.class);
                //it.putExtra("solucao", raiz); ENVIA PARA A TELA DA SOLUCAO
                //it.putExtra("sistema", txtSistema);
                startActivity(it);
                break;


        }
    }
}
