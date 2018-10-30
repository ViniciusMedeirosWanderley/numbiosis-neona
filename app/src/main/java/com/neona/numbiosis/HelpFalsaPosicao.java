package com.neona.numbiosis;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class HelpFalsaPosicao extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_falsa_posicao);

        Button btn_help_site = (Button) findViewById(R.id.button);
        btn_help_site.setOnClickListener((View.OnClickListener) this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.button:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://numbiosis.ci.ufpb.br/e-tutoring")));
                break;
        }
    }
}
