package com.example.entrega2_das.Principal;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class ClaseDialogoFecha extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    // Empleamos esta clase para generar el dialogo que nos devuelva un calendario

    EditText cumple;

    public ClaseDialogoFecha (EditText et) {
        cumple = et;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        Calendar calendario = Calendar.getInstance();
        int anyo = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog eldialogo = new DatePickerDialog(getActivity(),this, anyo,mes,dia);
        return eldialogo;
    }

    // Ahora establecemos la fecha seleccionada por el usuario en el EditText
    @Override
    public void onDateSet(DatePicker datePicker, int anyo, int mes, int dia) {
        final String selectedDate = twoDigits(dia) + " / " + twoDigits(mes+1) + " / " + anyo;
        cumple.setText(selectedDate);
    }

    // Este metodo resulta de gran ayuda para escribir los digitos de un solo valor con un 0 por
    // delante, es decir, si el usuario nacio un dia 7, este metodo nos guardara dicho dia como 07
    private String twoDigits(int x) {return (x<10) ? ("0"+x) : String.valueOf(x);}

}