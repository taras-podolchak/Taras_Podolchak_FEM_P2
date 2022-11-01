package com.example.taras_podolchak_fem_p2.meteorologia.service;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.example.taras_podolchak_fem_p2.meteorologia.pojo.Cities;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherNearbyAreasServise extends AsyncTask<ListView, Void, Cities> {
    final String URL_RECURSO = "https://api.openweathermap.org/data/2.5/find?lat=40.39354&lon=-3.662&cnt=20&APPID=add7afd148b08ad9e0c06da452f061d5";

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected Cities doInBackground(ListView... listViews) {
        Cities citiesList = new Cities();

        HttpURLConnection con = null;

        try {
            // Establecer conexión remota
            con = (HttpURLConnection) new URL(URL_RECURSO).openConnection();
            BufferedReader fin = new BufferedReader(
                    new InputStreamReader(
                            con.getInputStream()
                    )
            );

            // Deserializando el JSON via librería gson
            Gson gson = new Gson();
            citiesList = gson.fromJson(fin, Cities.class);
            fin.close();

        } catch (Exception e) {
            Log.e("ERROR", "Error loading data");
        } finally {
            if (con != null) con.disconnect();
        }
        return citiesList;
    }

    @Override
    protected void onPostExecute(Cities result) {
        super.onPostExecute(result);
    }

}
