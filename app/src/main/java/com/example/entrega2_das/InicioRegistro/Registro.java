package com.example.entrega2_das.InicioRegistro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
        EditText nombreUsu = (EditText) findViewById(R.id.ptUsernameR);
        EditText contrasena = (EditText) findViewById(R.id.ptContrasena);
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
                String u = nombreUsu.getText().toString();
                String p = contrasena.getText().toString();
                String d = mostrarC.getText().toString();

                // Compruebo si algun campo esta vacio
                if (n.length()==0 || a.length()==0 || u.length()==0 || p.length()==0 || d.length()==0) {
                    int tiempo= Toast.LENGTH_SHORT;
                    Toast aviso = Toast.makeText(getApplicationContext(), "Existen campos vacíos", tiempo);
                    aviso.setGravity(Gravity.BOTTOM| Gravity.CENTER, 0, 0);
                    aviso.show();
                } else if (n.length()>0 && a.length()>0 && u.length()>0 && p.length()>0 && d.length()>0) {
                    // Ahora compruebo si los campos introducidos son validos

                }

                Intent rf = new Intent (getBaseContext(), RegistroFoto.class);
                startActivity(rf);

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