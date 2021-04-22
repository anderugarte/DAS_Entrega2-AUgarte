package com.example.entrega2_das.Principal;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.entrega2_das.InicioRegistro.InicioSesion;
import com.example.entrega2_das.InicioRegistro.RegistroFoto;
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

        bMP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mp = new Intent (getBaseContext(), MiPerfil.class);
                startActivity(mp);
            }
        });
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        AlertDialog.Builder builder = new AlertDialog.Builder(MenuPrincipal.this);
        Configuration configuration = getBaseContext().getResources().getConfiguration();
        String l = configuration.getLocales().toString();
        String txt1 = "Aceptar";
        String txt2 = "Cancelar";
        builder.setTitle("WIT?");
        builder.setMessage("¿Desea cerrar la sesión?");

        builder.setPositiveButton(txt1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Cerrar sesión
                Intent ma = new Intent(getBaseContext(), MainActivity.class);
                ma.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(ma);
                finish();
            }
        });

        builder.setNegativeButton(txt2, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

}