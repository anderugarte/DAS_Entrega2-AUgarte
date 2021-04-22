package com.example.entrega2_das.DataBase;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class conexionDBDAS extends Worker {

    public conexionDBDAS(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);

        //OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(conexionDBDAS.class).build();
        //WorkManager.getInstance(getApplicationContext()).getWorkInfoByIdLiveData(otwr.getId()).observe(this, new Observer<WorkInfo>() {
        //    @Override
        //    public void onChanged(WorkInfo workInfo) {
        //        if(workInfo != null && workInfo.getState().isFinished()){
                    //TextView textViewResult = findViewById(R.id.textoResultado);
                    //textViewResult.setText(workInfo.getOutputData().getString("datos"));
        //        }
        //    }
        //});
        //WorkManager.getInstance(getApplicationContext()).enqueue(otwr);

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
