package com.neona.numbiosis;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.neona.numbiosis.pacote3.QuadraturaGaussiana;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class QuadGaussActivity extends AppCompatActivity implements View.OnClickListener {

    private String link_video = "https://www.youtube.com";
    private String default_func = "2*e^(-2*x^2)";
    private int default_N = 0;
    private int default_A = -1;
    private int default_B = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quad_gauss);

        Button calcular = (Button) findViewById(R.id.btn_QG_calcular);
        calcular.setOnClickListener(this);

        Button btn_help = findViewById(R.id.btn_help_QG);
        btn_help.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_QG_calcular:
                int n, a, b;
                String funcao;

                try {
                    funcao = ((EditText) findViewById(R.id.txt_QG_Funcao)).getText().toString().trim();
                    if (funcao.isEmpty())
                        funcao = default_func;

                    String n_string = ((EditText) findViewById(R.id.txt_QG_N)).getText().toString().trim();
                    if (n_string.isEmpty())
                        n = default_N;
                    else
                        n = Integer.parseInt(n_string);

                    String txt_intervalo = ((EditText) findViewById(R.id.txt_QG_Intervalo)).getText().toString().trim();
                    if (txt_intervalo.isEmpty()) {
                        a = default_A;
                        b = default_B;
                    } else {
                        String[] intervalo = txt_intervalo.split("\\s+");
                        a = Integer.parseInt(intervalo[0]);
                        b = Integer.parseInt(intervalo[1]);
                    }

                    QuadraturaGaussiana QG = new QuadraturaGaussiana(funcao, n, a, b);
                    double quadratura = QG.calcular();

                    exibirSolucao(quadratura);
                    //Toast.makeText(getApplicationContext(), "Quadratura = " + quadratura, Toast.LENGTH_LONG).show();

                }catch (IllegalArgumentException e){
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }catch(Exception e){
                    Toast.makeText(getApplicationContext(), "Ocorreu um erro." +
                                    "\nConfirme os valores escritos.", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btn_help_QG:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link_video)));
                break;
        }
    }

    private void exibirSolucao(double quadratura){
        NumberFormat nf = DecimalFormat.getInstance();
        nf.setMaximumFractionDigits(8);

        String txt = String.format("Quadratura = %1$2s", nf.format(quadratura));

        TextView viewSolucao = findViewById(R.id.txt_QG_viewSolucao);
        TextView solucao = findViewById(R.id.txt_QG_Solucao);
        solucao.setText(txt);
        if(solucao.getVisibility() == TextView.INVISIBLE)
            solucao.setVisibility(TextView.VISIBLE);
        if(viewSolucao.getVisibility() == TextView.INVISIBLE)
            viewSolucao.setVisibility(TextView.VISIBLE);
    }
}
