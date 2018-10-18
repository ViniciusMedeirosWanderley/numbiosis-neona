package com.neona.numbiosis;

import android.content.Intent;
import android.net.Uri;
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

        Button btn_help = findViewById(R.id.btn_help_newton);
        btn_help.setOnClickListener(this);
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
                        return;
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
                        return;
                    }
                }

                if(sErro.length < 2)
                    erro[1] = erro[0];

                NewtonNaoLinear nt = new NewtonNaoLinear(funcoes, variaveis, x0, erro[0], erro[1]);

                // acha a solucao
                double[] raiz;
                try {
                    raiz = nt.resolve();
                }catch (Exception ex){
                    Toast.makeText(getApplicationContext(),"Ocorreu um erro durante o cálculo.\nVerifique se o sistema está correto.", Toast.LENGTH_LONG).show();
                    //Log.d("error", );
                    ex.printStackTrace();
                    return;
                }

                NumberFormat nf = NumberFormat.getInstance();
                nf.setMaximumFractionDigits(10);
                System.out.println("Soluçao:");
                for (double r:raiz) {
                    System.out.println(nf.format(r));
                }

                // chamo a tela de solucao
                Intent it = new Intent(this, NewtonSolucaoActivity.class);
                it.putExtra("solucao", raiz);
                it.putExtra("sistema", txtSistema);

                putExtraTodos(it, nt);

                startActivity(it);
                break;
            case R.id.btn_help_newton:
                //startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=jO-P1RgFziU")));
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://e-tutoring")));
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

    private void putExtraTodos(Intent it, NewtonNaoLinear nt){
        double eps1 = nt.getEPSILON_1();
        double eps2 = nt.getEPSILON_2();

        it.putExtra("eps1",eps1);
        it.putExtra("eps2",eps2);

        String[] funcoes = nt.getFuncoes();
        String[][] variaveis = nt.getVariaveis();

        it.putExtra("funcoes",funcoes);
        it.putExtra("variaveis", variaveis);

        double[] k_normaFx = nt.getK_normaFx();
        double[][] k_fx = nt.getK_Fx();
        double[][][] k_jx = nt.getK_Jx();
        double[] k_norma = nt.getK_norma();
        double[][] k_s = nt.getK_s();
        double[][] k_xk = nt.getK_xk();
        int k = nt.getnIteracoes();

        it.putExtra("fx",k_fx);
        it.putExtra("jx",k_jx);
        it.putExtra("normasFx",k_normaFx);
        it.putExtra("normas",k_norma);
        it.putExtra("sk",k_s);
        it.putExtra("xk",k_xk);
        it.putExtra("k",k);
    }
}
