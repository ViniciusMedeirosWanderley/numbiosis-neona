package com.neona.numbiosis;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

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

        Button btn_help = findViewById(R.id.btn_help_gauss_jordan);
        btn_help.setOnClickListener(this);
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
                    dimensoesA = txt_dimensoesA.split("x"); //PRECISA GENERALIZAR
                    // PRA QUANDO FOR QUALQUER OUTRA LETRA ELE CAPTURAR SOMENTE OS NUMEROS DOS EXTREMOS

                    int linhaA = Integer.parseInt(dimensoesA[0].trim());
                    int colunaA = Integer.parseInt(dimensoesA[1].trim());

                    if(txt_matrizA.equals(""))
                        txt_matrizA = "2 1 -1\n5 2 2\n3 1 1";

                    //separa a matrizA em linhas
                    txt_matrizA.replaceAll("\n"," ");

                    ma = new String[linhaA*colunaA];
                    ma = txt_matrizA.split("\\s+");

                    matrizA = new double[linhaA][colunaA] ;

                    for(int i = 0,j = 0, a; i < ma.length ; i++){

                        if(i != 0 && (i%linhaA)==0){
                            j++;
                        }

                        a = i % colunaA;

                        matrizA[j][a] = Double.parseDouble(ma[i]);

                    }

                    if(txt_matrizB.equals("")){
                        txt_matrizB = "1 -4 5";
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

                    String sistema = matrizToString(matrizA);
                    String vetorB = vetorToString(matrizB);

                    Matrix A = new Matrix(matrizA);
                    Matrix B = new Matrix(matrizB,linhaA);

                    //acha a solucao
                    com.neona.numbiosis.pacote2.GaussJordan gj = new com.neona.numbiosis.pacote2.GaussJordan(A, B);
                    gj.resolve();
                    String passoApasso = gj.passos; // peganso passo a passo do gj
                    int numeroDeIteracoes = gj.numeroDeIterações;


                    System.out.println("Normaliza");
                    A.print(5, 2);
                    System.out.println(passoApasso);
                    System.out.println("Soluçao");
                    B.print(5, 2);

                    double matriz[] = new double[linhaA];

                    for(int i=0; i<linhaA; i++){
                        matriz[i] = B.get(i,0);
                    }

                    //tela de solucao
                    Intent it = new Intent(this, GaussJordanSolucaoActivity.class);
                    it.putExtra("solucao",matriz);
                    it.putExtra("A",sistema);
                    it.putExtra("B",vetorB);
                    it.putExtra("passos",passoApasso);
                    it.putExtra("iteracoes",numeroDeIteracoes);
                    it.putExtra("linhasA",linhaA );
                    it.putExtra("colunasA",colunaA);
                    it.putExtra("linhasB",linhasB.length);

                    startActivity(it);
                    break;

                }catch (ArithmeticException e){
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Confirme os valores escritos.", Toast.LENGTH_LONG).show();
                }
            case R.id.btn_help_gauss_jordan:
                //startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=C3Tpj2BS46I")));
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://e-tutoring")));
                break;
        }

    }

    private String matrizToString(double[][] ar){
        NumberFormat nf = DecimalFormat.getInstance();
        nf.setMaximumFractionDigits(6);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ar.length; i++) {
            for (int j = 0; j < ar[0].length; j++) {
                //sb.append(ar[i][j]+"  ");
                sb.append(String.format("%1$2s", nf.format(ar[i][j])));
                sb.append("  ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private String vetorToString(double[] ar){
        NumberFormat nf = DecimalFormat.getInstance();
        nf.setMaximumFractionDigits(6);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ar.length; i++) {
            //sb.append(ar[i]+" ");
            sb.append(nf.format(ar[i]));
            sb.append(" ");
        }
        return sb.toString();
    }
}

