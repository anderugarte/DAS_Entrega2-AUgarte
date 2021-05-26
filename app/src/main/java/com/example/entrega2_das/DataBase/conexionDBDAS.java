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
import java.net.URL;

public class conexionDBDAS extends Worker {

    public conexionDBDAS(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

//        String username = getInputData().getString("username");
//        String password = getInputData().getString("password");
//
//        String direccion = "http://ec2-54-242-79-204.compute-1.amazonaws.com/augarte059/WEB/conexionBD.php";
//        String result = "";
//        Data resultados = null;
//        HttpURLConnection urlConnection = null;
//
//        try {
//            URL destino = new URL(direccion);
//            urlConnection = (HttpURLConnection) destino.openConnection();
//            urlConnection.setRequestMethod("POST");
//            urlConnection.setConnectTimeout(5000);
//            urlConnection.setReadTimeout(5000);
//            urlConnection.setDoOutput(true);
//
//            JSONObject params = new JSONObject();
//            params.put("username",username);
//            params.put("password",password);
//
//            urlConnection.setRequestProperty("Content-Type","application/json");
//
//            PrintWriter out = new PrintWriter(urlConnection.getOutputStream());
//            out.print(params.toJSONString());
//            out.close();
//
//            int statusCode = urlConnection.getResponseCode();
//
//            if (statusCode == 200) {
//
//                BufferedInputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
//                String line = "";
//                while ((line = bufferedReader.readLine()) != null) {
//                    result += line;
//                }
//                inputStream.close();
//
//                resultados = new Data.Builder()
//                        .putString("result",result)
//                        .build();
//
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return Result.success(resultados);
//    }

        String txtUsuario = getInputData().getString("username");
        String txtContrasena = getInputData().getString("password");

        String direccion = "http://ec2-54-242-79-204.compute-1.amazonaws.com/augarte059/WEB/conexionBD.php";
        String result = "";
        Data resultados = null;
        HttpURLConnection urlConnection = null;
           try {
            URL destino = new URL(direccion);
            urlConnection = (HttpURLConnection) destino.openConnection();
            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(5000);
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            JSONObject parametrosJSON = new JSONObject();
            parametrosJSON.put("username", txtUsuario);
            parametrosJSON.put("password", txtContrasena);

            urlConnection.setRequestProperty("Content-Type","application/json");
            PrintWriter out = new PrintWriter(urlConnection.getOutputStream());
            out.print(parametrosJSON.toJSONString());
            out.close();

            int statusCode = urlConnection.getResponseCode();

            if (statusCode == 200) {
                BufferedInputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                inputStream.close();

                resultados = new Data.Builder()
                        .putString("resultado", result)
                        .build();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
            return Result.success(resultados);
    }

}
