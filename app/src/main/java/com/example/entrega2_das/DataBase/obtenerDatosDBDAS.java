package com.example.entrega2_das.DataBase;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class obtenerDatosDBDAS extends Worker {

    Data resultadosOD = new Data.Builder().build();

    public obtenerDatosDBDAS(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String direccion = "http://ec2-54-167-31-169.compute-1.amazonaws.com/augarte059/WEB/obtenerDatosBD.php";
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

                JSONParser parser = new JSONParser();
                JSONObject json = (JSONObject) parser.parse(result);
                String nombre = (String) json.get("nombre");
                String apellidos = (String)json.get("apellidos");
                String usuario = (String) json.get("username");
                String password = (String) json.get("password");
                String cumpleanos = (String) json.get("cumpleanos");
                String fotoperfil = (String) json.get("fotoperfil");

                resultadosOD = new Data.Builder()
                        .putString("nom",nombre)
                        .putString("ape",apellidos)
                        .putString("usu",usuario)
                        .putString("pas",password)
                        .putString("cum",cumpleanos)
                        .putString("fot",fotoperfil)
                        .build();

            }
        } catch (MalformedURLException e) {e.printStackTrace();} catch (IOException e) {e.printStackTrace();} catch (ParseException e) {
            e.printStackTrace();
        }
        urlConnection.disconnect();

        return Result.success(resultadosOD);
    }

}
