package com.example.entrega2_das.InicioRegistro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.entrega2_das.R;

public class RegistroFoto extends AppCompatActivity {

    ImageView fp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_foto);

        Button bTF = (Button) findViewById(R.id.bTomarFoto);
        Button bTG = (Button) findViewById(R.id.bTomarGaleria);

        bTG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent elIntentGal = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(elIntentGal, 9999);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        fp = (ImageView) findViewById(R.id.fotoPerfil);
        if (requestCode == 9999 && resultCode == RESULT_OK) {
            Uri imagenSeleccionada = data.getData();
            fp.setImageURI(imagenSeleccionada);
        }
    }
}