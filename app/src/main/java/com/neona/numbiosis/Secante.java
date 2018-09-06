package com.neona.numbiosis;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;

public class Secante extends AppCompatActivity implements  View.OnClickListener {
    double raiz;
    EditText função , x0,x1,tol,n;
    String funçãoS,x0S,x1S,tolS,nS;//variaveis para captação dos dados introduzidos pelo usuário
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secante);//tela que estamos usando

        Button btn_calcular_secante = (Button) findViewById(R.id.btn_calcular_secante);//instanciamos o botão da tela
        btn_calcular_secante.setOnClickListener((View.OnClickListener ) this); //colocamos ele pra ser "escutado"
    }

    @Override
    public void onClick(View view) {

        função = (EditText) findViewById(R.id.editText_função_secante); //instanciamos as caixas de texto da tela
        x0 = (EditText) findViewById(R.id.editText_x0_secante);
        x1 = (EditText) findViewById(R.id.editText_x1_secante);
        tol = (EditText) findViewById(R.id.editText_tol_secante);
        n = (EditText) findViewById(R.id.editText_n_secante);

        if(função.getText().toString().equals("")){
            função.setText("x^2 + x - 6");
        }
        if(x0.getText().toString().equals("")){
            x0.setText("1.5");
        }
        if(x1.getText().toString().equals("")){
            x1.setText("1.7");
        }
        if(tol.getText().toString().equals("")){
            tol.setText(getText(R.string.txt_hint_ERRO));
        }
        if(n.getText().toString().equals("")) {
            n.setText(R.string.txt_hint_N);
        }

        switch(view.getId()){
            case R.id.btn_calcular_secante://caso o click seja no botão calcular

                funçãoS = função.getText().toString();   //capturamos o que foi digitado na caixa de texto da funcao
                x0S = x0.getText().toString();   //capturamos o que foi digitado na caixa de texto da funcao
                x1S = x1.getText().toString();   //capturamos o que foi digitado na caixa de texto da funcao
                tolS = tol.getText().toString();   //capturamos o que foi digitado na caixa de texto da funcao
                nS = n.getText().toString();

                double x0, x1, tol;
                int n;
                n = Integer.parseInt(nS);
                x0 = Double.parseDouble(x0S);
                x1 = Double.parseDouble(x1S);
                tol = Double.parseDouble(tolS);

                try{
                    raiz = Raiz.secante(funçãoS,x0,x1,tol,n);

                    Intent intent = new Intent(this,PlotagemActivity.class);
                    intent.putExtra("funcao", funçãoS);
                    intent.putExtra("raiz", raiz);
                    startActivity(intent);
                }catch (ArithmeticException ex){
                    Toast.makeText(getApplicationContext(),"Raiz não encontrada.\nTente outro intervalo.",Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(this,PlotagemActivity.class);
                    intent.putExtra("funcao", funçãoS);
                    intent.putExtra("raiz_ok", false);
                    startActivity(intent);
                }

        }
    }
}
