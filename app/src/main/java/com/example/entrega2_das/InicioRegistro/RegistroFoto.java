package com.example.entrega2_das.InicioRegistro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

//import com.example.entrega2_das.DataBase.conexionDBDAS;
//import com.example.entrega2_das.DataBase.conexionTokenDBDAS;
//import com.example.entrega2_das.DataBase.registroFotoDBDAS;
//import com.example.entrega2_das.Principal.MenuPrincipal;
import com.example.entrega2_das.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
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

public class RegistroFoto extends AppCompatActivity {

    ImageView fp;
    Boolean cambiado = false;
    String username,nom,ape,con,cum = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_foto);

        fp = (ImageView) findViewById(R.id.fotoPerfil);

        Button bTF = (Button) findViewById(R.id.bTomarFoto);
        Button bTG = (Button) findViewById(R.id.bTomarGaleria);
        Button bC = (Button) findViewById(R.id.bContinuar);

        // Establecemos la foto de perfil por defecto
        fp.setImageResource(R.drawable.perfil);

        // Recibimos el nombre de usuario del usuario que se ha registrado al igual que el resto de sus datos
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            username = extras.getString("username");
            nom = extras.getString("nombre");
            ape = extras.getString("apellidos");
            con = extras.getString("contrasena");
            cum = extras.getString("cumple");
        }

        // Modificar el campo de la fecha para introducirla en la base de datos
        String[] d = cum.split(" / ");
        cum = d[2] + "-" + d[1] + "-" + d[0];

        // Gestionar sacar una foto con la camara
        bTF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
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

        // Registrarse
        bC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cambiado) {
                    // Se genera un dialogo para preguntar si el usuario esta seguro de no anadir una foto de perfil
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegistroFoto.this);
                    String txt1 = "Aceptar";
                    String txt2 = "Cancelar";
                    builder.setTitle("Añadir foto de perfil");
                    builder.setMessage("¿Está seguro de no añadir ninguna foto de perfil?");
                    builder.setCancelable(true);

                    // Acepta
                    builder.setPositiveButton(txt1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // Crear cuenta sin foto de perfil
                            //gestionarRegistroFoto(username, nom, ape, con, cum);
                        }
                    });

                    // Cancela
                    builder.setNegativeButton(txt2, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });

                    builder.show();

                } else {
                    // Crear cuenta con foto de perfil
                    //gestionarRegistroFoto(username, nom, ape, con, cum);
                }
            }
        });
    }

    // Metodo que gestionara el registro de un nuevo usuario
//    private void gestionarRegistroFoto(String user, String nom, String ape, String con, String cum) {
//
//        // ------------------------ Almacenaje de la foto de perfil ------------------------ //
//
//        //Instancia de FireBase
//        FirebaseStorage storage = FirebaseStorage.getInstance();
//        // Crear una storage reference de nuestra app
//        StorageReference storageRef = storage.getReference();
//        // Crear una referencia a "fotoUser.jpg" siendo User el nombre de usuario
//        String ref = "FotosPerfil/foto" + username + ".jpg";
//        StorageReference fotoRef = storageRef.child(ref);
//
//        //Transformar el ImageView a bytes
//        BitmapDrawable bitmapDrawablefto = (BitmapDrawable) fp.getDrawable();
//        Bitmap bitmapFto = bitmapDrawablefto.getBitmap();
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        bitmapFto.compress(Bitmap.CompressFormat.PNG, 100, stream);
//        byte[] data = stream.toByteArray();
//
//        UploadTask uploadTask = fotoRef.putBytes(data);
//        uploadTask.addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                // Hacer algo cuando ocurra un error en la subida
//                int tiempo= Toast.LENGTH_SHORT;
//                String texto = "Ha ocurrido un error al guardar la foto de perfil";
//                Toast aviso = Toast.makeText(getApplicationContext(), texto, tiempo);
//                aviso.setGravity(Gravity.BOTTOM| Gravity.CENTER, 0, 0);
//                aviso.show();
//            }
//        });
//
//        // ------------------------ Registro del usuario ------------------------ //
//
//        Data resultadosRF = new Data.Builder()
//                .putString("username",user)
//                .putString("nombre",nom)
//                .putString("apellidos",ape)
//                .putString("password",con)
//                .putString("cumpleanos",cum)
//                .build();
//
//        OneTimeWorkRequest trabajoPuntualRF = new OneTimeWorkRequest.Builder(registroFotoDBDAS.class)
//                .setInputData(resultadosRF).build();
//
//        WorkManager.getInstance(getApplicationContext()).getWorkInfoByIdLiveData(trabajoPuntualRF.getId())
//                .observe(this, new Observer<WorkInfo>() {
//                    @Override
//                    public void onChanged(WorkInfo status) {
//                        if (status != null && status.getState().isFinished()) {
//                            if (status.getOutputData().getString("resultado").equals("true")) {
//                                // Registro correcto
//                                // Almacenar la imagen en Firebase
//                                Intent mp = new Intent (getBaseContext(), MenuPrincipal.class);
//                                mp.putExtra("username", user);
//                                startActivity(mp);
//                                finish();
//                                // Obtener el token del dispositivo y guardarlo en la BD
//                                FirebaseMessaging.getInstance().getToken()
//                                        .addOnCompleteListener(new OnCompleteListener<String>() {
//                                            @Override
//                                            public void onComplete(@NonNull Task<String> task) {
//                                                if (!task.isSuccessful()) {
//                                                    return;
//                                                }
//                                                String token = task.getResult();
//                                                registrarToken(token);
//                                            }
//                                        });
//                            } else {
//                                // Registro incorrecto
//                                int tiempo= Toast.LENGTH_SHORT;
//                                Toast aviso = Toast.makeText(getApplicationContext(), "Error", tiempo);
//                                aviso.setGravity(Gravity.BOTTOM| Gravity.CENTER, 0, 0);
//                                aviso.show();
//                            }
//                        }
//                    }
//                });
//        WorkManager.getInstance(getApplicationContext()).enqueue(trabajoPuntualRF);
//
//    }

//    private void registrarToken(String token) {
//
//        Data datos = new Data.Builder()
//                .putString("username",username)
//                .putString("token",token).build();
//
//        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(conexionTokenDBDAS.class)
//                .setInputData(datos).build();
//
//        WorkManager.getInstance(getApplicationContext()).enqueue(oneTimeWorkRequest);
//
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Imagen de la camara
        if (requestCode == 8888 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap laminiatura = (Bitmap) extras.get("data");
            File eldirectorio = this.getFilesDir();
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
            String nombrefichero = "IMG_" + timeStamp + "_";
            File imagenFich = new File(eldirectorio, nombrefichero + ".jpg");
            OutputStream os;
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            laminiatura.compress(Bitmap.CompressFormat.PNG, 100, stream);
//            byte[] fototransformada = stream.toByteArray();
//            String fotoen64 = Base64.encodeToString(fototransformada,Base64.DEFAULT);
//            Uri.Builder builder = new Uri.Builder()
//                    .appendQueryParameter("identificador", username)
//                    .appendQueryParameter("imagen", fotoen64);
//            String parametrosURL = builder.build().getEncodedQuery();
            try {
                fp.setImageBitmap(laminiatura);
                os = new FileOutputStream(imagenFich);
                laminiatura.compress(Bitmap.CompressFormat.JPEG, 100, os);
                cambiado = true;
                os.flush();
                os.close();
            } catch (Exception e) {

            }
        }

        // Imagen de la galeria
        if (requestCode == 9999 && resultCode == RESULT_OK) {
            Uri imagenSeleccionada = data.getData();
            fp.setImageURI(imagenSeleccionada);
            cambiado = true;
        }

    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, 8888);
        }
    }
}