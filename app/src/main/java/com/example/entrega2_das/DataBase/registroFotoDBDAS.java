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

public class registroFotoDBDAS extends Worker {

    Data resultadosRF = new Data.Builder().build();

    public registroFotoDBDAS(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String direccion = "http://ec2-54-167-31-169.compute-1.amazonaws.com/augarte059/WEB/registroFotoBD.php";
        String result = "";
        HttpURLConnection urlConnection = null;

        String username = getInputData().getString("username");
        String nombre = getInputData().getString("nombre");
        String apellidos = getInputData().getString("apellidos");
        String password = getInputData().getString("password");
        String cumpleanos = getInputData().getString("cumpleanos");
        String fotoperfil = getInputData().getString("fotoperfil");

        try {
            URL destino = new URL(direccion);
            urlConnection = (HttpURLConnection) destino.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(5000);
            urlConnection.setDoOutput(true);

            JSONObject params = new JSONObject();
            params.put("username",username);
            params.put("nombre",nombre);
            params.put("apellidos",apellidos);
            params.put("password",password);
            params.put("cumpleanos",cumpleanos);
            params.put("fotoperfil",fotoperfil);

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

                resultadosRF = new Data.Builder().putString("resultado",result).build();

            }
        } catch (MalformedURLException e) {e.printStackTrace();} catch (IOException e) {e.printStackTrace();}
        urlConnection.disconnect();

        return Result.success(resultadosRF);
    }
}