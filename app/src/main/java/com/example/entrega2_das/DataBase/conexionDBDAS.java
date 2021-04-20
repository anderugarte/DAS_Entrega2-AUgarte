package com.example.entrega2_das.DataBase;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.net.HttpURLConnection;
import java.net.URL;

public class conexionDBDAS extends Worker {
    String direccion = "localhost";

    public conexionDBDAS(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        return null;
    }
    //HttpURLConnection urlConnection = null;
    //try {
      //  URL destino = new URL(direccion);
        //urlConnection = (HttpURLConnection) destino.openConnection();
        //urlConnection.setConnectTimeout(5000);
        //urlConnection.setReadTimeout(5000);
    //} catch {}
}
