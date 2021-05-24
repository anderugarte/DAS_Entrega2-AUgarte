package com.example.entrega2_das.DataBase;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.json.simple.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class registroDBDAS extends Worker {

    Data resultadosR = new Data.Builder().build();

    public registroDBDAS(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String direccion = "http://ec2-54-242-79-204.compute-1.amazonaws.com/augarte059/WEB/registroBD.php";
        String result = "";
        HttpURLConnection urlConnection = null;

        String username = getInputData().getString("username");

        try {
            URL destino = new URL(direccion);
            urlConnection = (HttpURLConnection) destino.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(5000);
            urlConnection.setDoOutput(true);

            JSONObject params = new JSONObject();
            params.put("username",username);

            urlConnection.setRequestProperty("Content-Type","application/json");

            PrintWriter out = new PrintWriter(urlConnection.getOutputStream());
            out.print(params.toJSONString());
            out.close();

            int statusCode = urlConnection.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_OK) {

                BufferedInputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                inputStream.close();

                resultadosR = new Data.Builder().putString("resultado",result).build();

            }
        } catch (MalformedURLException e) {e.printStackTrace();} catch (IOException e) {e.printStackTrace();}
        urlConnection.disconnect();

        return Result.success(resultadosR);
    }
}