package com.example.entrega2_das.Principal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.entrega2_das.InicioRegistro.InicioSesion;
import com.example.entrega2_das.InicioRegistro.Registro;
import com.example.entrega2_das.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Actividad Principal de la aplicacion
        Button bIS = (Button) findViewById(R.id.bIS);
        Button bR =  (Button) findViewById(R.id.bR);

        // A traves de este boton se accedera a la interfaz para iniciar sesion
        bIS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent is = new Intent (getBaseContext(), InicioSesion.class);
                startActivity(is);
            }
        });

        // A traves de este boton se accedera a la interfaz para registrarse
        bR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent r = new Intent (getBaseContext(), Registro.class);
                startActivity(r);
            }
        });

        // No coviene realizar finish() ya que es posible que el usuario desee volver a esta interfaz
    }
}