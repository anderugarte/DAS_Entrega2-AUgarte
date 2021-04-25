package com.example.entrega2_das.DataBase;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class Tarea extends Worker {

    public Tarea(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String vUser = getInputData().getString("username");
        String vPass = getInputData().getString("password");

        Data resultados = new Data.Builder()
                .putString("username",vUser)
                .putString("password",vPass)
                .build();

        return Result.success(resultados);
    }
}
