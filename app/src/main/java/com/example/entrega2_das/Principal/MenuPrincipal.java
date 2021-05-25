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

    private String nomUsu;
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

        // Recibimos el nombre de usuario del usuario que se ha registrado o ha iniciado sesion
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            nomUsu = extras.getString("username");
        }

        // Acceder a Nueva Publicacion
        bNP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent np = new Intent (context, NuevaPublicacion.class);
                np.putExtra("userNo",nomUsu);
                startActivity(np);
            }
        });

        // Acceder a Mi Perfil
        bMP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mp = new Intent (context, MiPerfil.class);
                mp.putExtra("userN",nomUsu);
                startActivity(mp);
            }
        });

        // Actualizar la interfaz
        bAc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mp = new Intent (context, MenuPrincipal.class);
                startActivity(mp);
                finish();
            }
        });
    }

    // Gestiona la acciona cuando el usuario pulsa el boton 'Atras' de su dispositivo
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