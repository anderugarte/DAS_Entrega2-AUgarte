package com.example.entrega2_das.Principal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.entrega2_das.R;

public class MiPerfil extends AppCompatActivity {

    private String usuName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_perfil);

        Button bAc = (Button) findViewById(R.id.bA);
        Button bCa = (Button) findViewById(R.id.bC);

        // Recibimos el username del usuario
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            usuName = extras.getString("userN");
        }

        bAc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        bCa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mp = new Intent (getBaseContext(), MenuPrincipal.class);
                startActivity(mp);
                finish();
            }
        });
    }

}