package com.example.entrega2_das.Principal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.entrega2_das.InicioRegistro.InicioSesion;
import com.example.entrega2_das.R;

public class MenuPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        Button bNP = (Button) findViewById(R.id.bNP);
        Button bMP = (Button) findViewById(R.id.bMP);

        bNP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent np = new Intent (getBaseContext(), NuevaPublicacion.class);
                startActivity(np);
            }
        });
    }
}