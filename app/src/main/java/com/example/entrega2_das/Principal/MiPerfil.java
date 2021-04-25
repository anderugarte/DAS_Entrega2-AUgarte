package com.example.entrega2_das.Principal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.entrega2_das.R;

public class MiPerfil extends AppCompatActivity {

    private String usuName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_perfil);

        Button bAc = (Button) findViewById(R.id.bA);
        Button bCa = (Button) findViewById(R.id.bC);
        TextView nomUsu = (TextView) findViewById(R.id.tvNUsuBD);
        EditText nombreBD = (EditText) findViewById(R.id.etNombreBD);
        EditText apellidosBD = (EditText) findViewById(R.id.etApellidosBD);
        EditText contrasenaBD = (EditText) findViewById(R.id.etContrasenaBD);
        EditText cumpleBD = (EditText) findViewById(R.id.etCumpleBD);

        // Recibimos el username del usuario
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            usuName = extras.getString("userN");
        }

        // Escribimos los datos del usuario
        nomUsu.setText(usuName);

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