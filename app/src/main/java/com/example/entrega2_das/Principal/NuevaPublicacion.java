package com.example.entrega2_das.Principal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.entrega2_das.DataBase.numPublicacionesDBDAS;
import com.example.entrega2_das.DataBase.registroFotoDBDAS;
import com.example.entrega2_das.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NuevaPublicacion extends AppCompatActivity {

    ImageView foto;
    Boolean ubi = false, fto = false;
    private AlertDialog.Builder alertDialogBuilder;
    private Context context;
    private String usuarioN;
    int n = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_publicacion);

        context = this;

        Button bF = (Button) findViewById(R.id.bCamara);
        Button bG = (Button) findViewById(R.id.bGaleria);
        Button bU = (Button) findViewById(R.id.bUbicacion);
        Button bP = (Button) findViewById(R.id.bPublicar);

        // Recibimos el username del usuario
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            usuarioN = extras.getString("userNo");
        }

        foto = (ImageView) findViewById(R.id.foto);

        // Establecemos la foto predefinida
        foto.setImageResource(R.drawable.landscape);

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
                    anadirPublicacion(usuarioN);

                    Intent mp = new Intent (getBaseContext(), MenuPrincipal.class);
                    startActivity(mp);
                    finish();
                }

            }
        });

    }

    private void anadirPublicacion(String usuar) {

        // ------------------------ Almacenaje de la foto de perfil ------------------------ //

        // Obtener numero de publicaciones del usuario
        int num = obtenerNumPublicaciones();

        //Instancia de FireBase
        FirebaseStorage storage = FirebaseStorage.getInstance();
        // Crear una storage reference de nuestra app
        StorageReference storageRef = storage.getReference();
        // Crear una referencia a "fotoUser.jpg" siendo User el nombre de usuario
        String ref = "Publicaciones/foto" + usuar + "_" + num + ".jpg"; // Gestionar varias publicaciones
        StorageReference fotoRef = storageRef.child(ref);

        //Transformar el ImageView a bytes
        BitmapDrawable bitmapDrawablefto = (BitmapDrawable) foto.getDrawable();
        Bitmap bitmapFto = bitmapDrawablefto.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmapFto.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] data = stream.toByteArray();

        UploadTask uploadTask = fotoRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Hacer algo cuando ocurra un error en la subida
                int tiempo= Toast.LENGTH_SHORT;
                String texto = "Ha ocurrido un error al guardar la publicación";
                Toast aviso = Toast.makeText(getApplicationContext(), texto, tiempo);
                aviso.setGravity(Gravity.BOTTOM| Gravity.CENTER, 0, 0);
                aviso.show();
            }
        });

    }

    private int obtenerNumPublicaciones() {

        Data resultadosRF = new Data.Builder()
                .putString("username",usuarioN)
                .build();

        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(numPublicacionesDBDAS.class)
                .setInputData(resultadosRF).build();

        WorkManager.getInstance(getApplicationContext()).getWorkInfoByIdLiveData(otwr.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo status) {
                        if (status != null && status.getState().isFinished()) {
                            if (status.getOutputData().getString("resultado").equals("true")) {

                                // Se obtienen los datos del usuario
                                n = status.getOutputData().getInt("numP",0);

                            } else {
                                // Registro incorrecto
                                int tiempo= Toast.LENGTH_SHORT;
                                Toast aviso = Toast.makeText(getApplicationContext(), "Error", tiempo);
                                aviso.setGravity(Gravity.BOTTOM| Gravity.CENTER, 0, 0);
                                aviso.show();
                            }
                        }
                    }
                });
        WorkManager.getInstance(getApplicationContext()).enqueue(otwr);

        return n;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

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

    // Gestiona la acciona cuando el usuario pulsa el boton 'Atras' de su dispositivo
    @Override
    public void onBackPressed(){
        alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("WIT?");
        alertDialogBuilder.setMessage("¿Descartar publicación?")
                .setCancelable(false)
                .setPositiveButton("Descartar", (dialog, which) -> {
                    // Descartar la nueva publicacion
                    Intent mp = new Intent(context, MenuPrincipal.class);
                    mp.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(mp);
                    finish();
                })
                .setNegativeButton("Continuar", (dialog, which) -> dialog.cancel()).create().show();

    }

}