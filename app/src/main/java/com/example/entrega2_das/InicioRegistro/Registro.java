package com.example.entrega2_das.InicioRegistro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.entrega2_das.Principal.ClaseDialogoFecha;
import com.example.entrega2_das.R;

public class Registro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        // A traves de esta clase se gestionara el registro de un nuevo usuario
        EditText nombre = (EditText) findViewById(R.id.ptNombre);
        EditText apellidos = (EditText) findViewById(R.id.ptApellidos);
        EditText username = (EditText) findViewById(R.id.ptUsername);
        EditText contrasena = (EditText) findViewById(R.id.ptPassword);
        EditText mostrarC = (EditText) findViewById(R.id.ptCumple);
        Button bRegistro = (Button) findViewById(R.id.bReg);

        // Al pulsar este EditText, desplegaremos un dialogo donde se podra seleccionar la fecha de nacimientop del usuario
        mostrarC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.ptCumple:
                        showDatePickerDialog(mostrarC);
                        break;
                }
            }
        });

        // Al pulsar este boton, se gestionara el registro del usuario
        bRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Obtenemos los campos introducidos por el usuario
                String n = nombre.getText().toString();
                String a = apellidos.getText().toString();
                String u = username.getText().toString();
                String p = contrasena.getText().toString();
                String d = mostrarC.getText().toString();

            }
        });
    }

    // Este metodo nos ayuda a desplegar el dialogo para la seleccion de la fecha de nacimiento y nos permite enviar
    // el EditText por parametro para una vez obtenida la fecha poder realizar un .setText("fecha") en el
    private void showDatePickerDialog(final EditText editText) {
        ClaseDialogoFecha dialogoCumpleanos = new ClaseDialogoFecha(editText);
        dialogoCumpleanos.show(getSupportFragmentManager(),"cumple");
    }

}