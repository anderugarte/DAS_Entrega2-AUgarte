package com.example.entrega2_das.Principal;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.entrega2_das.InicioRegistro.InicioSesion;
import com.example.entrega2_das.InicioRegistro.RegistroFoto;
import com.example.entrega2_das.R;

public class MenuPrincipal extends AppCompatActivity {

    private Context context;
    private AlertDialog.Builder alertDialogBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        context = this;

        Button bNP = (Button) findViewById(R.id.bNP);
        Button bMP = (Button) findViewById(R.id.bMP);
        Button bAc = (Button) findViewById(R.id.bAc);

        bNP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent np = new Intent (context, NuevaPublicacion.class);
                startActivity(np);
            }
        });

        bMP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mp = new Intent (context, MiPerfil.class);
                startActivity(mp);
            }
        });

        bAc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mp = new Intent (context, MenuPrincipal.class);
                startActivity(mp);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed(){
        alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("WIT?");
        alertDialogBuilder.setMessage("¿Desea cerrar la sesión?")
                .setCancelable(false)
                .setPositiveButton("Aceptar", (dialog, which) -> {
                    // Cerrar sesión
                    Intent ma = new Intent(context, MainActivity.class);
                    ma.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(ma);
                    finish();
                })
                .setNegativeButton("Cancelar", (dialog, which) -> dialog.cancel()).create().show();

    }

}