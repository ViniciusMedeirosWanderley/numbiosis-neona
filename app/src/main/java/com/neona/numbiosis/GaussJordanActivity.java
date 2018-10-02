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

    String[] matrizes;
    String[] dimensoesA;
    String[] dimensoesB;

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
                    EditText editTextMatrizes = findViewById(R.id.txt_matrizes);
                    String txt_matrizes = editTextMatrizes.getText().toString().trim();

                    EditText editTextDimensoesA = findViewById(R.id.editText_GJ_dimensoesA);

                    String txt_dimensoesA = editTextDimensoesA.getText().toString().trim();

                    if(txt_dimensoesA.equals(""))
                        txt_dimensoesA = "3x3";

                    dimensoesA = txt_dimensoesA.split("x");


                    int linhaA = Integer.parseInt(dimensoesA[0]);
                    int colunaA = Integer.parseInt(dimensoesA[1]);


                    if(txt_matrizes.equals(""))
                        txt_matrizes = "2 1 -1 5 2 2 3 1 1 \n1 -4 5";

                    matrizes = txt_matrizes.split("\\n+");
                    for (int i = 0; i < matrizes.length; i++){
                        matrizes[i] = matrizes[i].trim();
                    }

                    ma = new String[linhaA*colunaA];
                    ma = matrizes[0].split(" ");

                    matrizA = new double[linhaA][colunaA] ;

                    for(int i = 0,j = 0, a; i < ma.length ; i++){

                        if(i != 0 && (i%linhaA)==0){
                            j++;
                        }

                        a = i % colunaA;

                        matrizA[j][a] = Double.parseDouble(ma[i]);

                    }

                    mb = new String[linhaA];
                    mb = matrizes[1].split(" ");

                    matrizB = new double[linhaA];

                    for(int i = 0; i < mb.length ; i++){

                        matrizB[i] = Double.parseDouble(mb[i]);

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

