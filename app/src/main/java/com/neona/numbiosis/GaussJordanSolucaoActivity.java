package com.neona.numbiosis;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class GaussJordanSolucaoActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gauss_jordan_solucao);

        //recebe os parametros enviados pela tela do Gauss Jordan

        Intent it = getIntent();
        double[] solucao = it.getDoubleArrayExtra("solucao");

        //HORA DO SHOWWWWWW

        int numerodeOperacoes = it.getIntExtra("iteracoes",0);
        //AQUI EU PEGO QUANTAS OPERAÇÕES FORAM FEITAS (QUANTOS PASSOS)

        int linhasA = it.getIntExtra("linhasA",3);
        int colunasA = it.getIntExtra("colunasA",3);
        int linhasB = it.getIntExtra("linhasB",3);
        //TRAGO ALGUNS COMPONENTES IMPORTANTES PARA FAZER GAMBIARRAS

        String passos = it.getStringExtra("passos");
        //PEGO OS PASSOS

        TextView textSistema = findViewById(R.id.text_solução_GJ);

        NumberFormat nf = DecimalFormat.getInstance();
        nf.setMaximumFractionDigits(6);

        String operacoes[] = passos.split("proximaIteracao");
        //DIVIDO OS PASSOS NAS OPERAÇÕES DAS MATRIZES

        String[] matrizesA = new String[(operacoes.length/2)];
        String[] matrizesB = new String[operacoes.length/2];
        //crio as matrizes com base na quantidade de operações necessárias
        //que contém todas as matrizes A e B do passo a passo


        for(int i = 0; i<(numerodeOperacoes*2); i++){
            if(i%2==0){
                matrizesA[i/2] = operacoes[i];
                System.out.println("\n i%2==0 \n"); //quando i for par
            }else if(i%2 != 0){
                matrizesB[i/2] = operacoes[i];
               System.out.println("\n i%2!=0 \n"); //o contrario de par
           }else{
                System.out.println("NENHUM DOS DOIS IFS");
            }
        }

        String normalizado = matrizesA[numerodeOperacoes -1]; //string da matriz A normalizada
        String resultado = matrizesB[numerodeOperacoes -1]; //string da matriz B resposta


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
        //testes para entender o esquema

        sb.append(numerodeOperacoes);
        sb.append("\n");

        sb.append(operacoes.length);
        sb.append("\n");

        sb.append(matrizesA[0]);
        sb.append("\n");

        sb.append(matrizesB[0]);
        sb.append("\n");

        sb.append(resultado);

        TextView textViewA = findViewById(R.id.textView_A);
        textViewA.setText(sb.toString());

    }
}
