package com.neona.numbiosis;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class TelaSistemasActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_sistemas);

        Button bt_spnatural = (Button) findViewById(R.id.SplineButton);
        bt_spnatural.setOnClickListener((View.OnClickListener ) this);

        Button btn_newton = findViewById(R.id.NewtonButton);
        btn_newton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.SplineButton:
                Intent it3 = new Intent(this, Spline.class);
                startActivity(it3);
                break;
            case R.id.NewtonButton:
                Intent it = new Intent(this, NewtonNaoLinearActivity.class);
                startActivity(it);
                break;
        }
    }
}
