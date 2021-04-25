package com.example.entrega2_das.Principal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.entrega2_das.DataBase.conexionDBDAS;
import com.example.entrega2_das.DataBase.obtenerDatosDBDAS;
import com.example.entrega2_das.R;

public class MiPerfil extends AppCompatActivity {

    private String usuName;
    private String nBD;
    private String aBD;
    private String pBD;
    private String cBD;
    private String fBD;
    ImageView fpe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_perfil);

        Button bCa = (Button) findViewById(R.id.bC);
        TextView nomUsu = (TextView) findViewById(R.id.tvNUsuBD);
        EditText nombreBD = (EditText) findViewById(R.id.etNombreBD);
        EditText apellidosBD = (EditText) findViewById(R.id.etApellidosBD);
        EditText contrasenaBD = (EditText) findViewById(R.id.etContrasenaBD);
        EditText cumpleBD = (EditText) findViewById(R.id.etCumpleBD);
        fpe = (ImageView) findViewById(R.id.imageView);

        // Recibimos el username del usuario
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            usuName = extras.getString("userN");
        }

        Data resultadosMiP = new Data.Builder()
                .putString("username",usuName)
                .build();

        OneTimeWorkRequest trabajoPuntualMiP = new OneTimeWorkRequest.Builder(obtenerDatosDBDAS.class)
                .setInputData(resultadosMiP)
                .build();

        WorkManager.getInstance(getApplicationContext()).getWorkInfoByIdLiveData(trabajoPuntualMiP.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onChanged(WorkInfo status) {
                        if (status != null && status.getState().isFinished()) {
                            // Se obtienen los datos del usuario
                            nBD = status.getOutputData().getString("nom");
                            aBD = status.getOutputData().getString("ape");
                            pBD = status.getOutputData().getString("pas");
                            cBD = status.getOutputData().getString("cum");
                            fBD = status.getOutputData().getString("fot");

                            // No se el porque pero aparecen en color transparente
                            nombreBD.setText(nBD);
                            apellidosBD.setText(aBD);
                            contrasenaBD.setText(pBD);
                            cumpleBD.setText(cBD);
                            fpe.setImageURI(Uri.parse(fBD));
                        }
                    }
                });
        WorkManager.getInstance(getApplicationContext()).enqueue(trabajoPuntualMiP);

        // Escribimos los datos del usuario
        nomUsu.setText(usuName);

        bCa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mp = new Intent (getBaseContext(), MenuPrincipal.class);
                mp.putExtra("usename",usuName);
                startActivity(mp);
                finish();
            }
        });
    }

}