package com.example.entrega2_das.InicioRegistro;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.entrega2_das.DataBase.conexionDBDAS;
import com.example.entrega2_das.Principal.MainActivity;
import com.example.entrega2_das.Principal.MenuPrincipal;
import com.example.entrega2_das.R;

public class InicioSesion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(conexionDBDAS.class).build();
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(otwr.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if(workInfo != null && workInfo.getState().isFinished()){
                            //TextView textViewResult = findViewById(R.id.textoResultado);
                            //textViewResult.setText(workInfo.getOutputData().getString("datos"));
                        }
                    }
                });
        WorkManager.getInstance(this).enqueue(otwr);

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

                // Gestion del inicio de sesion

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