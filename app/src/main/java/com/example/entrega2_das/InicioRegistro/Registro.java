package com.example.entrega2_das.InicioRegistro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.entrega2_das.DataBase.conexionDBDAS;
import com.example.entrega2_das.DataBase.registroDBDAS;
import com.example.entrega2_das.Principal.ClaseDialogoFecha;
import com.example.entrega2_das.Principal.MenuPrincipal;
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
                    int tiempo = Toast.LENGTH_SHORT;
                    Toast aviso = Toast.makeText(getApplicationContext(), "Existen campos vacíos", tiempo);
                    aviso.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 0);
                    aviso.show();
                } else if (p.length()<=4) { // La contrasena debe de tener mas de 4 caracteres
                    int tiempo = Toast.LENGTH_SHORT;
                    Toast aviso = Toast.makeText(getApplicationContext(), "Contraseña demasiado corta", tiempo);
                    aviso.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 0);
                    aviso.show();
                } else if (n.length()>0 && a.length()>0 && u.length()>0 && p.length()>0 && d.length()>0) {
                    // Ahora compruebo si los campos introducidos son validos
                    if (containsDigit(n) || containsDigit(a)){
                        int tiempo = Toast.LENGTH_SHORT;
                        Toast aviso = Toast.makeText(getApplicationContext(), "El nombre o los apellidos contiene números", tiempo);
                        aviso.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 0);
                        aviso.show();
                    } else {
                        gestionarRegistro(n,a,u,p,d);
                    }
                }

            }
        });
    }

    // Comprueba si el String contiene numeros o no
    public final boolean containsDigit(String s) {
        boolean containsDigit = false;

        if (s != null && !s.isEmpty()) {
            for (char c : s.toCharArray()) {
                if (containsDigit = Character.isDigit(c)) {
                    break;
                }
            }
        }

        return containsDigit;
    }

    private void gestionarRegistro(String n, String a, String u, String p, String d) {

        Data resultadosR = new Data.Builder()
                .putString("username",u)
                .build();

        OneTimeWorkRequest trabajoPuntualR = new OneTimeWorkRequest.Builder(registroDBDAS.class)
                .setInputData(resultadosR)
                .build();

        WorkManager.getInstance(getApplicationContext()).getWorkInfoByIdLiveData(trabajoPuntualR.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo status) {
                        if (status != null && status.getState().isFinished()) {
                            if (status.getOutputData().getString("resultado").equals("true")) {
                                // Todos los campos son correctos asi que se traslada al usuario a la interfaz correspondiente a la foto de perfil
                                // Registro correcto (no existe ningun usuario con dicho username)
                                Intent rf = new Intent (getBaseContext(), RegistroFoto.class);
                                rf.putExtra("username", u);
                                rf.putExtra("nombre",n);
                                rf.putExtra("apellidos",a);
                                rf.putExtra("contrasena",p);
                                rf.putExtra("cumple",d);
                                startActivity(rf);
                                finish();
                            } else {
                                // Registro incorrecto (existe un usuario con dicho username)
                                int tiempo= Toast.LENGTH_SHORT;
                                Toast aviso = Toast.makeText(getApplicationContext(), "Nombre de usuario ya en uso", tiempo);
                                aviso.setGravity(Gravity.BOTTOM| Gravity.CENTER, 0, 0);
                                aviso.show();
                            }
                        }
                    }
                });
        WorkManager.getInstance(getApplicationContext()).enqueue(trabajoPuntualR);
    }

    // Este metodo nos ayuda a desplegar el dialogo para la seleccion de la fecha de nacimiento y nos permite enviar
    // el EditText por parametro para una vez obtenida la fecha poder realizar un .setText("fecha") en el
    private void showDatePickerDialog(final EditText editText) {
        ClaseDialogoFecha dialogoCumpleanos = new ClaseDialogoFecha(editText);
        dialogoCumpleanos.show(getSupportFragmentManager(),"cumple");
    }

}