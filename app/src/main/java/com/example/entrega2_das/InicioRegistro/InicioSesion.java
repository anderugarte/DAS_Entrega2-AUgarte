package com.example.entrega2_das.InicioRegistro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.entrega2_das.Principal.MenuPrincipal;
import com.example.entrega2_das.R;

public class InicioSesion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        EditText tUsername = (EditText) findViewById(R.id.ptUsername);
        EditText tPassword = (EditText) findViewById(R.id.ptPassword);
        Button bIniSes = (Button) findViewById(R.id.bIniSes);
        Button bRegis = (Button) findViewById(R.id.bRegis);

        // Se realizan las comprobaciones para el inicio de sesion
        bIniSes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Se recogen los campos rellenados por el usuario
                String user = tUsername.getText().toString();
                String pass = tPassword.getText().toString();

                Intent mp = new Intent (getBaseContext(), MenuPrincipal.class);
                startActivity(mp);
                finish();

            }
        });

        // Se redirecciona el usuario a la interfaz de registro
        bRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent re = new Intent (getBaseContext(), Registro.class);
                startActivity(re);
                finish();
            }
        });
    }
}