package com.example.taras_podolchak_fem_p2.service;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.example.taras_podolchak_fem_p2.pojo.Example;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherFiveDaysService extends AsyncTask<ListView, Void, Example> {

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected Example doInBackground(ListView... listViews) {

        HttpURLConnection httpURLConnection = null;
        Example example = new Example();

        try {
            URL url = new URL("https://api.openweathermap.org/data/2.5/forecast?q=Madrid&units=metric&lang=es&appid=a9cf0f7a3cc84a884d84d4df48f057c2");
            httpURLConnection = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            Gson gson = new Gson();
            example = gson.fromJson(bufferedReader, Example.class);
            bufferedReader.close();
        } catch (Exception e) {
            Log.e("ERROR", "Error loading data");
        } finally {
            if (httpURLConnection != null) httpURLConnection.disconnect();
        }
        return example;
    }

    @Override
    protected void onPostExecute(Example result) {
        super.onPostExecute(result);
    }
}

