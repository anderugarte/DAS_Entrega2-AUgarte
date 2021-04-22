package com.example.entrega2_das.DataBase;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class conexionDBDAS extends Worker {

    public conexionDBDAS(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);

        String direccion = "http://ec2-54-167-31-169.compute-1.amazonaws.com/WEB/conexionBD.php";
        HttpURLConnection urlConnection = null;
        try {
            URL destino = new URL(direccion);
            urlConnection = (HttpURLConnection) destino.openConnection();
            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(5000);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @NonNull
    @Override
    public Result doWork() {
        Data resultados = new Data.Builder().putInt("resultado",42).build();
        return Result.success(resultados);
    }
}
