package com.example.entrega2_das.Principal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MiPerfil extends AppCompatActivity {

    private String usuName;
    private String nBD;
    private String aBD;
    private String cBD;

    ImageView fpe;
    EditText nombreBD, apellidosBD, cumpleBD;
    TextView nomUsu, contrasenaBD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_perfil);

        Button bCa = (Button) findViewById(R.id.bC);
        nomUsu = (TextView) findViewById(R.id.tvNUsuBD);
        nombreBD = (EditText) findViewById(R.id.etNombreBD);
        apellidosBD = (EditText) findViewById(R.id.etApellidosBD);
        contrasenaBD = (TextView) findViewById(R.id.etContrasenaBD);
        cumpleBD = (EditText) findViewById(R.id.etCumpleBD);
        fpe = (ImageView) findViewById(R.id.imageView);

        // Recibimos el username del usuario
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            usuName = extras.getString("userN");
        }

        // Escribimos el nombre de usuario
        nomUsu.setText(usuName);
        
        // Recogemos los datos del usuario
        recogerDatos(usuName);

        // Boton cancelar
        bCa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mp = new Intent (getBaseContext(), MenuPrincipal.class);
                mp.putExtra("usename",usuName);
                startActivity(mp);
                finish();
            }
        });

        // Cambiar contrasena
        contrasenaBD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void recogerDatos(String usuName) {

        // ------------------------ Descarga de la foto de perfil ------------------------ //

        //Instancia de FireBase
        FirebaseStorage storage = FirebaseStorage.getInstance();
        // Crear una storage reference de nuestra app
        StorageReference storageRef = storage.getReference();
        // Crear una referencia a "fotoUser.jpg" siendo User el nombre de usuario
        String ref = "FotosPerfil/foto" + usuName + ".jpg";
        StorageReference fotoRef = storageRef.child(ref);

        final long ONE_MEGABYBTE = 1024 * 1024;
        fotoRef.getBytes(ONE_MEGABYBTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                //Se ha devuelto la foto
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                fpe.setImageBitmap(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Ha ocurrido un error
                int tiempo= Toast.LENGTH_SHORT;
                String texto = "No se ha podido cargar la foto de perfil";
                Toast aviso = Toast.makeText(getApplicationContext(), texto, tiempo);
                aviso.setGravity(Gravity.BOTTOM| Gravity.CENTER, 0, 0);
                aviso.show();
            }
        });

        // ------------------------ Recogida de los datos del usuario ------------------------ //

        Data resultadosMiP = new Data.Builder()
                .putString("username",usuName)
                .build();

        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(obtenerDatosDBDAS.class)
                .setInputData(resultadosMiP).build();

        WorkManager.getInstance(getApplicationContext()).getWorkInfoByIdLiveData(otwr.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onChanged(WorkInfo status) {
                        if (status != null && status.getState().isFinished()) {
                            // Se obtienen los datos del usuario
                            nBD = status.getOutputData().getString("nom");
                            aBD = status.getOutputData().getString("ape");
                            cBD = status.getOutputData().getString("cum");

                            // Escribimos los datos del usuario
                            nombreBD.setText(nBD);
                            apellidosBD.setText(aBD);
                            cumpleBD.setText(cBD);
                        }
                    }
                });
        WorkManager.getInstance(getApplicationContext()).enqueue(otwr);
    }

}