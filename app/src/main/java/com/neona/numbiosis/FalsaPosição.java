package com.neona.numbiosis;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.awt.font.NumericShaper;

public class FalsaPosição extends AppCompatActivity implements  View.OnClickListener {
    double raiz;
    EditText função , a,b,tol,n;
    String funçãoS,aS,bS,tolS,nS;//variaveis para captação dos dados introduzidos pelo usuário
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_falsa_posicao);//tela que estamos usando

        Button button = (Button) findViewById(R.id.button_falsaPosição);//instanciamos o botão da tela
        button.setOnClickListener((View.OnClickListener ) this); //colocamos ele pra ser "escutado"

    }

    @Override
    public void onClick(View view) {

        função = (EditText) findViewById(R.id.editText_função_falsaPosição); //instanciamos as caixas de texto da tela
        a = (EditText) findViewById(R.id.editText_a_falsaPosição);
        b = (EditText) findViewById(R.id.editText_b_falsaPosição);
        tol = (EditText) findViewById(R.id.editText_tol_falsaPosição);
        n = (EditText) findViewById(R.id.editText_n_falsPosição);

        if(função.getText().toString().equals("")){
            função.setText("x^2 + x - 6");
        }
        if(a.getText().toString().equals("")){
            a.setText("1.5");
        }
        if(b.getText().toString().equals("")){
            b.setText("1.7");
        }
        if(tol.getText().toString().equals("")){
            tol.setText(getText(R.string.txt_hint_ERRO));
        }
        if(n.getText().toString().equals("")) {
            n.setText(R.string.txt_hint_N);
        }

        switch(view.getId()){
            case R.id.button_falsaPosição://caso o click seja no botão calcular
            try {
                funçãoS = função.getText().toString();   //capturamos o que foi digitado na caixa de texto da funcao
                aS = a.getText().toString();   //capturamos o que foi digitado na caixa de texto da funcao
                bS = b.getText().toString();   //capturamos o que foi digitado na caixa de texto da funcao
                tolS = tol.getText().toString();   //capturamos o que foi digitado na caixa de texto da funcao
                nS = n.getText().toString();

                char[] compara = funçãoS.toCharArray(); //Array de char`s criado para comparar a função digitada e saber se está correta
                int x = 0;
                for(int i =0; i<compara.length; i++){
                    if(Character.isLetter(compara[i]) && !(compara[i] == 'x' || compara[i] == 'X') )//caso seja uma letra e não seja x, então a função está errada
                        x = 1;
                }

                double a, b, tol;
                int n;
                n = Integer.parseInt(nS);
                a = Double.parseDouble(aS);
                b = Double.parseDouble(bS);
                tol = Double.parseDouble(tolS);

                if(n >= 0) {
                    if (x == 0) {
                        try {
                            raiz = Raiz.falsaPosicao(funçãoS, a, b, tol, n);

                            Intent intent = new Intent(this, PlotagemActivity.class);
                            intent.putExtra("funcao", funçãoS);
                            intent.putExtra("raiz", raiz);
                            startActivity(intent);
                        } catch (ArithmeticException ex) {
                            Toast.makeText(getApplicationContext(), "Raiz não encontrada.\nTente outro intervalo.", Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(this, PlotagemActivity.class);
                            intent.putExtra("funcao", funçãoS);
                            intent.putExtra("raiz_ok", false);
                            startActivity(intent);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Use X como variável independente.", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Máximo de Iterações deve ser positivo.", Toast.LENGTH_LONG).show();
                }
            }catch (NumberFormatException e){
                Toast.makeText(getApplicationContext(),"Erro encontrado.\nConfirme os valores escritos.", Toast.LENGTH_LONG).show();

            }
        }
    }
}
