package com.neona.numbiosis;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import Jama.Matrix;

public class GaussJordanActivity extends AppCompatActivity implements  View.OnClickListener{

    String[] linhasA;
    String[] linhasB;
    String[] dimensoesA;

    String ma[];
    String mb[];

    double matrizA[][];
    double matrizB[];


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
                try{

                    EditText editTextDimensoesA = findViewById(R.id.editText_GJ_dimensoesA);
                    String txt_dimensoesA = editTextDimensoesA.getText().toString().trim();

                    EditText editTextMatrizes = findViewById(R.id.txt_matrizA_GJ);
                    String txt_matrizA = editTextMatrizes.getText().toString().trim();

                    EditText editTextMatrizB = findViewById(R.id.editText_matrizB_GJ);
                    String txt_matrizB = editTextMatrizB.getText().toString().trim();

                    if(txt_dimensoesA.equals(""))
                        txt_dimensoesA = "3x3";

                    //futuramente podemos melhorar essa recepcao das dimensoes de A
                    dimensoesA = txt_dimensoesA.split("x");

                    int linhaA = Integer.parseInt(dimensoesA[0]);
                    int colunaA = Integer.parseInt(dimensoesA[1]);

                    if(txt_matrizA.equals(""))
                        txt_matrizA = "2 1 -1\n5 2 2 \n3 1 1";

                    //separa a matrizA em linhas
                    linhasA = txt_matrizA.split("\\n+");
                    for (int i = 0; i < linhasA.length; i++){
                        linhasA[i] = linhasA[i].trim();
                    }

                    //TODO: Fazer a matriz A a partir das linhas capturadas anteriormente

                    ma = new String[linhaA*colunaA];
                    ma = linhasA[0].split(" ");

                    matrizA = new double[linhaA][colunaA] ;

                    for(int i = 0,j = 0, a; i < ma.length ; i++){

                        if(i != 0 && (i%linhaA)==0){
                            j++;
                        }

                        a = i % colunaA;

                        matrizA[j][a] = Double.parseDouble(ma[i]);

                    }

                    //capturamos o que o usuario colocou nas linhas(matrizB) e transformamos na matriz B
                    linhasB = txt_matrizB.split("\\s+");
                    for (int i = 0; i < linhasB.length; i++){
                        linhasB[i] = linhasB[i].trim();
                    }

                    matrizB = new double[linhaA];

                    for(int i = 0; i < linhasB.length ; i++){

                        matrizB[i] = Double.parseDouble(linhasB[i]);

                    }

                    Matrix A = new Matrix(matrizA);
                    Matrix B = new Matrix(matrizB,linhaA);

                    //acha a solucao
                    com.neona.numbiosis.pacote2.GaussJordan gj = new com.neona.numbiosis.pacote2.GaussJordan(A, B);
                    gj.resolve();

                    System.out.println("Normaliza");
                    A.print(5, 2);
                    System.out.println("Soluçao");
                    B.print(5, 2);

                    double matriz[] = new double[linhaA];

                    for(int i=0; i<linhaA; i++){
                        matriz[i] = B.get(i,0);
                    }

                    //tela de solucao
                    Intent it = new Intent(this, GaussJordanSolucaoActivity.class);
                    it.putExtra("solucao",matriz);
                    startActivity(it);
                    break;

                }catch (NumberFormatException ex){
                    Toast.makeText(getApplicationContext(),"Confirme os valores escritos.", Toast.LENGTH_LONG).show();
                }catch (ArrayIndexOutOfBoundsException ar){
                    Toast.makeText(getApplicationContext(),"Confirme os valores escritos.", Toast.LENGTH_LONG).show();
                }catch (ArithmeticException ue){
                    Toast.makeText(getApplicationContext(),"Confirme os valores escritos.", Toast.LENGTH_LONG).show();
                }
        }

    }
}

