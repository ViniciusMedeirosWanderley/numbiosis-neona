package com.neona.numbiosis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class SobreActivity extends AppCompatActivity implements View.OnClickListener  {

    private String link_numbiosis = "http://numbiosis.ci.ufpb.br";
    private String link_numis = "http://appmmc.ci.ufpb.br";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sobre);
        try {
            getSupportActionBar().hide();
        }catch(NullPointerException ex){/*...*/}

        Button btn_numbiosis = findViewById(R.id.numbiosis_button);
        btn_numbiosis.setOnClickListener(this);

        Button but_numis = findViewById(R.id.numis_button);
        but_numis.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.numbiosis_button:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link_numbiosis)));
                break;

            case R.id.numis_button:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link_numis)));
                break;

        }
    }

}
