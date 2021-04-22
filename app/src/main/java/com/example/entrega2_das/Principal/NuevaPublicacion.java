package com.example.entrega2_das.Principal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.entrega2_das.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NuevaPublicacion extends AppCompatActivity {

    ImageView foto;
    Boolean ubi = false, fto = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_publicacion);

        Button bF = (Button) findViewById(R.id.bCamara);
        Button bG = (Button) findViewById(R.id.bGaleria);
        Button bU = (Button) findViewById(R.id.bUbicacion);
        Button bP = (Button) findViewById(R.id.bPublicar);

        // Obtener una imagen desde la camara
        bF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, 6666);
                }
            }
        });

        // Obtener una imagen de la galería
        bG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent elIntentGal = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(elIntentGal, 7777);
            }
        });

        // Agregar la ubicacion de la imagen
        bU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ubi = true;
            }
        });

        // Publicar la imagen junto a su ubicación
        bP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Se comprueba si se ha cargado una foto y su ubicación
                if (!ubi || !fto) {
                    if (!fto) {
                        int tiempo= Toast.LENGTH_SHORT;
                        Toast aviso = Toast.makeText(getApplicationContext(), "Imagen no añadida", tiempo);
                        aviso.setGravity(Gravity.BOTTOM| Gravity.CENTER, 0, 0);
                        aviso.show();
                    } else if (!ubi) {
                        int tiempo= Toast.LENGTH_SHORT;
                        Toast aviso = Toast.makeText(getApplicationContext(), "Ubicación no añadida", tiempo);
                        aviso.setGravity(Gravity.BOTTOM| Gravity.CENTER, 0, 0);
                        aviso.show();
                    }
                } else {
                    // Anadimos la nueva publicación al foro

                    Intent mp = new Intent (getBaseContext(), MenuPrincipal.class);
                    startActivity(mp);
                    finish();
                }

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        foto = (ImageView) findViewById(R.id.foto);

        // Imagen de la camara
        if (requestCode == 6666 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap laminiatura = (Bitmap) extras.get("data");
            foto.setImageBitmap(laminiatura);
            File eldirectorio = this.getFilesDir();
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
            String nombrefichero = "IMG_" + timeStamp + "_";
            File imagenFich = new File(eldirectorio, nombrefichero + ".jpg");
            OutputStream os;
            try {
                os = new FileOutputStream(imagenFich);
                laminiatura.compress(Bitmap.CompressFormat.JPEG, 100, os);
                fto = true;
                os.flush();
                os.close();
            } catch (Exception e) {

            }
        }

        // Imagen de la galeria
        if (requestCode == 7777 && resultCode == RESULT_OK) {
            Uri imagenSeleccionada = data.getData();
            foto.setImageURI(imagenSeleccionada);
            fto = true;
        }

    }

}