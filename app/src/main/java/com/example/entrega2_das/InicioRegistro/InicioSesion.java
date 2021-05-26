package com.example.entrega2_das.InicioRegistro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.entrega2_das.DataBase.conexionDBDAS;
import com.example.entrega2_das.Principal.MenuPrincipal;
import com.example.entrega2_das.R;

public class InicioSesion extends AppCompatActivity {

    EditText tUsername;
    EditText tPassword;
    String user, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        tUsername = (EditText) findViewById(R.id.ptUsername);
        tPassword = (EditText) findViewById(R.id.ptPassword);

        Button bIniSes = (Button) findViewById(R.id.bIniSes);
        Button bRegis = (Button) findViewById(R.id.bRegis);

        // Se realizan las comprobaciones para el inicio de sesion
        bIniSes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Se recogen los campos rellenados por el usuario
                user = tUsername.getText().toString();
                pass = tPassword.getText().toString();

                // Gestion del inicio de sesion
                // Primero, se comprueba si existen campos vacios
                if (user.length()==0 || pass.length()==0) {
                    if (user.length()==0) { // Campo username vacio
                        int tiempo= Toast.LENGTH_SHORT;
                        Toast aviso = Toast.makeText(getApplicationContext(), "Nombre de usuario vacío", tiempo);
                        aviso.setGravity(Gravity.BOTTOM| Gravity.CENTER, 0, 0);
                        aviso.show();
                    } else { // Campo contrasena vacia
                        int tiempo= Toast.LENGTH_SHORT;
                        Toast aviso = Toast.makeText(getApplicationContext(), "Contraseña vacía", tiempo);
                        aviso.setGravity(Gravity.BOTTOM| Gravity.CENTER, 0, 0);
                        aviso.show();
                    }
                } else { // No existe campos vacios, se comprobara si los campos introducidos por el usuario son correctos
                    // Se accede al metodo que gestiona la conexion e inicio de sesion
                    // Si existe se hara el inicio de sesión
                    // Si no aparecerá un Toast
                    gestionarConexion();
                }

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

    // Comprobaremos en la BD si existe un usuario con ese username
    private void gestionarConexion() {

        Data resultados = new Data.Builder()
                .putString("username",user)
                .putString("password",pass)
                .build();

        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(conexionDBDAS.class)
                .setInputData(resultados).build();

        WorkManager.getInstance(getApplicationContext()).getWorkInfoByIdLiveData(otwr.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo status) {
                        if (status != null && status.getState().isFinished()) {
                            if (status.getOutputData().getString("resultado").equals("logOK")) {
                                // Login correcto (existe un usuario con dichas credenciales asi que se inicia sesion)
                                Intent mp = new Intent(getBaseContext(), MenuPrincipal.class);
                                mp.putExtra("username", user);
                                startActivity(mp);
                                finish();
                            } else {
                                // Login incorrecto (no existe ningun usuario con dichas credenciales)
                                int tiempo= Toast.LENGTH_SHORT;
                                Toast aviso = Toast.makeText(getApplicationContext(), "Error / Campos incorrectos", tiempo);
                                aviso.setGravity(Gravity.BOTTOM| Gravity.CENTER, 0, 0);
                                aviso.show();
                            }
                        }
                    }
                });
        WorkManager.getInstance(getApplicationContext()).enqueue(otwr);

    }
}