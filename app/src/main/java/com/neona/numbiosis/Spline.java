package com.neona.numbiosis;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.neona.numbiosis.pacote2.SplineNatural;

import java.util.Arrays;

public class Spline extends AppCompatActivity implements  View.OnClickListener {
    EditText x0, x1, x2, x3, x4, fx0, fx1, fx2, fx3, fx4;
    String x0S, x1S, x2S, x3S, x4S, fx0S, fx1S, fx2S, fx3S, fx4S;//variaveis para captação dos dados introduzidos pelo usuário
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spline);//tela que estamos usando

        Button button = (Button) findViewById(R.id.btn_calcular_spline);//instanciamos o botão da tela
        button.setOnClickListener((View.OnClickListener ) this); //colocamos ele pra ser "escutado"

        Button btn_help_spline = (Button) findViewById(R.id.btn_help_spline);
        btn_help_spline.setOnClickListener((View.OnClickListener) this);

    }

    @Override
    public void onClick(View view) {
        x0 = (EditText) findViewById(R.id.editText_x0_spline);
        x1 = (EditText) findViewById(R.id.editText_x1_spline);
        x2 = (EditText) findViewById(R.id.editText_x2_spline);
        x3 = (EditText) findViewById(R.id.editText_x3_spline);
        x4 = (EditText) findViewById(R.id.editText_x4_spline);
        fx0 = (EditText) findViewById(R.id.editText_fx0_spline);
        fx1 = (EditText) findViewById(R.id.editText_fx1_spline);
        fx2 = (EditText) findViewById(R.id.editText_fx2_spline);
        fx3 = (EditText) findViewById(R.id.editText_fx3_spline);
        fx4 = (EditText) findViewById(R.id.editText_fx4_spline);

        switch(view.getId()){
            case R.id.btn_calcular_spline://caso o click seja no botão calcular

                x0S = x0.getText().toString();   //capturamos o que foi digitado na caixa de texto de x0
                fx0S = fx0.getText().toString();   //capturamos o que foi digitado na caixa de texto de fx0
                x1S = x1.getText().toString();   //capturamos o que foi digitado na caixa de texto de x0
                fx1S = fx1.getText().toString();   //capturamos o que foi digitado na caixa de texto de fx0
                x2S = x2.getText().toString();   //capturamos o que foi digitado na caixa de texto de x0
                fx2S = fx2.getText().toString();   //capturamos o que foi digitado na caixa de texto de fx0
                x3S = x3.getText().toString();   //capturamos o que foi digitado na caixa de texto de x0
                fx3S = fx3.getText().toString();   //capturamos o que foi digitado na caixa de texto de fx0
                x4S = x4.getText().toString();   //capturamos o que foi digitado na caixa de texto de x0
                fx4S = fx4.getText().toString();   //capturamos o que foi digitado na caixa de texto de fx0

                if (x0S.isEmpty() && x1S.isEmpty() && x2S.isEmpty() && x3S.isEmpty() && x4S.isEmpty()
                    && fx0S.isEmpty() && fx1S.isEmpty() && fx2S.isEmpty() && fx3S.isEmpty() && fx4S.isEmpty()) {
                    x0.setText("0");
                    x0S = "0";
                    fx0.setText("3");
                    fx0S = "3";

                    x1.setText("0.5");
                    x1S = "0.5";
                    fx1.setText("1.8616");
                    fx1S = "1.8616";

                    x2.setText("1.0");
                    x2S = "1.0";
                    fx2.setText("-0.5571");
                    fx2S = "-0.5571";

                    x3.setText("1.5");
                    x3S = "1.5";
                    fx3.setText("-4.1987");
                    fx3S = "-4.1987";

                    x4.setText("2.0");
                    x4S = "2.0";
                    fx4.setText("-9.0536");
                    fx4S = "-9.0536";
                }

                /*if(x0S.isEmpty()){
                    x0.setText("0");
                    x0S = "0";
                }
                if(fx0S.isEmpty()){
                    fx0.setText("3");
                    fx0S = "3";
                }
                if(x1S.isEmpty()){
                    x1.setText("0.5");
                    x1S = "0.5";
                }
                if(fx1S.isEmpty()){
                    fx1.setText("1.8616");
                    fx1S = "1.8616";
                }
                if(x2S.isEmpty()){
                    x2.setText("1.0");
                    x2S = "1.0";
                }
                if(fx2S.isEmpty()){
                    fx2.setText("-0.5571");
                    fx2S = "-0.5571";
                }
                if(x3S.isEmpty()){
                    x3.setText("1.5");
                    x3S = "1.5";
                }
                if(fx3S.isEmpty()){
                    fx3.setText("-4.1987");
                    fx3S = "-4.1987";
                }
                if(x4S.isEmpty()){
                    x4.setText("2.0");
                    x4S = "2.0";
                }
                if(fx4S.isEmpty()){
                    fx4.setText("-9.0536");
                    fx4S = "-9.0536";
                }
                */


                float[] x = new float[5];
                float[] y = new float[5];
                float[] resultado;
                int cont = 0;

                    try{
                    if(!x0S.isEmpty()){
                        if(!fx0S.isEmpty()) {
                            x[cont] = Float.parseFloat(x0S);
                            y[cont] = Float.parseFloat(fx0S);
                            cont++;
                        }else{
                            Toast.makeText(getApplicationContext(),"Campo f(x) deve ser preenchido.",Toast.LENGTH_LONG).show();
                            return;
                        }
                    }

                    if(!x1S.isEmpty()){
                        if(!fx1S.isEmpty()) {
                            x[cont] = Float.parseFloat(x1S);
                            y[cont] = Float.parseFloat(fx1S);
                            cont++;
                        }else{
                            Toast.makeText(getApplicationContext(),"Campo f(x) deve ser preenchido.",Toast.LENGTH_LONG).show();
                            return;
                        }
                    }

                    if(!x2S.isEmpty()){
                        if(!fx2S.isEmpty()) {
                            x[cont] = Float.parseFloat(x2S);
                            y[cont] = Float.parseFloat(fx2S);
                            cont++;
                        }else{
                            Toast.makeText(getApplicationContext(),"Campo f(x) deve ser preenchido.",Toast.LENGTH_LONG).show();
                            return;
                        }
                    }

                    if(!x3S.isEmpty()){
                        if(!fx3S.isEmpty()) {
                            x[cont] = Float.parseFloat(x3S);
                            y[cont] = Float.parseFloat(fx3S);
                            cont++;
                        }else{
                            Toast.makeText(getApplicationContext(),"Campo f(x) deve ser preenchido.",Toast.LENGTH_LONG).show();
                            return;
                        }
                    }

                    if(!x4S.isEmpty()){
                        if(!fx4S.isEmpty()) {
                            x[cont] = Float.parseFloat(x4S);
                            y[cont] = Float.parseFloat(fx4S);
                            cont++;
                        }else{
                            Toast.makeText(getApplicationContext(),"Campo f(x) deve ser preenchido.",Toast.LENGTH_LONG).show();
                            return;
                        }
                    }


                    x = Arrays.copyOfRange(x,0,cont);

                }catch(NumberFormatException ex){
                    Toast.makeText(getApplicationContext(),"Campos com caracteres inválidos.",Toast.LENGTH_LONG).show();
                    return;
                }

                SplineNatural sp = new SplineNatural();
                try {
                    resultado = sp.resolve(x, y);
                }catch(IllegalArgumentException ex){
                    Toast.makeText(getApplicationContext(),"O número mínimo de pontos deve ser 3.",Toast.LENGTH_LONG).show();
                    return;
                }

                Intent intent = new Intent(this, EquacoesSpline.class);
                intent.putExtra("resposta", resultado);
                intent.putExtra("x", x);
                startActivity(intent);
                break;

            case R.id.btn_help_spline:
                Intent intent1 = new Intent(this, HelpSpline.class);
                startActivity(intent1);
                break;
        }
    }
}
