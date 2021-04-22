package com.example.entrega2_das.InicioRegistro;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.entrega2_das.Principal.MenuPrincipal;
import com.example.entrega2_das.R;

public class RegistroFoto extends AppCompatActivity {

    ImageView fp;
    Boolean cambiado = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_foto);

        Button bTF = (Button) findViewById(R.id.bTomarFoto);
        Button bTG = (Button) findViewById(R.id.bTomarGaleria);
        Button bC = (Button) findViewById(R.id.bContinuar);

        // Gestionar sacar una foto con la camara
        bTF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, 8888);
                }
            }
        });

        // Gestionar recoger una foto de la galeria
        bTG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent elIntentGal = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(elIntentGal, 9999);
            }
        });

        bC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cambiado) {
                    // Se genera un dialogo para preguntar si el usuario esta seguro de no anadir una foto de perfil
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegistroFoto.this);
                    Configuration configuration = getBaseContext().getResources().getConfiguration();
                    String l = configuration.getLocales().toString();
                    String txt1 = "Aceptar";
                    String txt2 = "Cancelar";
                    builder.setTitle("Añadir foto de perfil");
                    builder.setMessage("¿Está seguro de no añadir ninguna foto de perfil?");

                    builder.setPositiveButton(txt1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // Crear cuenta
                            Intent mp = new Intent(getBaseContext(), MenuPrincipal.class);
                            startActivity(mp);
                            finish();
                        }
                    });

                    builder.setNegativeButton(txt2, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });

                    builder.show();

                } else {
                    // Crear cuenta
                    Intent mp = new Intent(getBaseContext(), MenuPrincipal.class);
                    startActivity(mp);
                    finish();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        fp = (ImageView) findViewById(R.id.fotoPerfil);

        // Imagen de la camara
        if (requestCode == 8888 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap laminiatura = (Bitmap) extras.get("data");
            fp.setImageBitmap(laminiatura);
        }

        // Imagen de la galeria
        if (requestCode == 9999 && resultCode == RESULT_OK) {
            Uri imagenSeleccionada = data.getData();
            fp.setImageURI(imagenSeleccionada);
        }
        cambiado = true;
    }
}