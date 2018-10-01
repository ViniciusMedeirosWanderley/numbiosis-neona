package com.neona.numbiosis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.neona.numbiosis.pacote2.NewtonNaoLinear;

import java.text.NumberFormat;
import java.util.Arrays;

public class NewtonNaoLinearActivity extends AppCompatActivity  implements View.OnClickListener{

    String[] funcoes;
    String[][] variaveis;
    double[] x0;
    Double[] erro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newton_nao_linear);

        Button btn_resolver = findViewById(R.id.buttonResolver);
        btn_resolver.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonResolver:
                //////  pego as funçoes
                EditText textViewSistema = findViewById(R.id.txtSistema);
                String txtSistema = textViewSistema.getText().toString().trim();

                if(txtSistema.equals(""))
                    txtSistema = "x + y - 3 \nx^2 + y^2 - 9";

                funcoes = txtSistema.split("\\n+");
                for (int i = 0; i < funcoes.length; i++){
                    funcoes[i] = funcoes[i].trim();
                }

                preencheVariaveis(funcoes);

                ////// Estimativas Iniciais (x0)
                String txtEstimativas = ((TextView)findViewById(R.id.txtEstimativas)).getText().toString().trim();

                if(txtEstimativas.equals(""))
                    txtEstimativas = "1.0   5.0";

                String[] sX0 = txtEstimativas.split("\\s+");
                x0 = new double[sX0.length];
                for (int i = 0; i < sX0.length; i++){
                    try {
                        x0[i] = Double.parseDouble(sX0[i]);
                    }catch (NumberFormatException ex){
                        Toast.makeText(getApplicationContext(),"Erro com suas estimativas iniciais.\nConfirme os valores escritos.", Toast.LENGTH_LONG).show();
                    }
                }

                ////// Erro minimo
                String txtErroMin = ((TextView)findViewById(R.id.txtErroMin)).getText().toString().trim();

                if(txtErroMin.equals(""))
                    txtErroMin = "1e-5";

                String[] sErro = txtErroMin.split("\\s+");
                erro = new Double[2];
                for (int i = 0; (i < sErro.length) && (i < 2); i++){
                    try {
                        erro[i] = Double.parseDouble(sErro[i]);
                    }catch (NumberFormatException ex){
                        Toast.makeText(getApplicationContext(),"Erro com seu valor de tolerancia minima.\nConfirme os valores escritos.", Toast.LENGTH_LONG).show();
                    }
                }

                if(sErro.length < 2)
                    erro[1] = erro[0];

                NewtonNaoLinear nt = new NewtonNaoLinear(funcoes, variaveis, x0, erro[0], erro[1]);

                // acha a solucao
                double[] raiz = nt.resolve();

                NumberFormat nf = NumberFormat.getInstance();
                nf.setMaximumFractionDigits(10);
                System.out.println("Soluçao:");
                for (double r:raiz) {
                    System.out.println(nf.format(r));
                    //System.out.println(r);
                }

                // chamo a tela de solucao
                Intent it = new Intent(this, NewtonSolucaoActivity.class);
                it.putExtra("solucao", raiz);
                it.putExtra("sistema", txtSistema);
                startActivity(it);
                break;
        }
    }

    private void preencheVariaveis(String[] sistema){
        variaveis = new String[sistema.length][];
        int index;

        for (int i = 0; i < sistema.length; i++){
            index = 0;
            String[] vars = new String[4];

            if(sistema[i].contains("x")) {
                vars[index] = "x";
                index++;
            }
            if(sistema[i].contains("y")) {
                vars[index] = "y";
                index++;
            }
            if(sistema[i].contains("z")) {
                vars[index] = "z";
                index++;
            }
            if(sistema[i].contains("w")) {
                vars[index] = "w";
                index++;
            }

            variaveis[i] = Arrays.copyOfRange(vars, 0, index);
        }
    }
}
