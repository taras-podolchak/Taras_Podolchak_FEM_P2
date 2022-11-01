package com.example.taras_podolchak_fem_p2.meteorologia.service;

import android.util.Log;

import com.example.taras_podolchak_fem_p2.meteorologia.pojo.Cities;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.URL;

import cafsoft.foundation.HTTPURLResponse;
import cafsoft.foundation.URLSession;


public class Weather5DaysInMadridService {


    public Weather5DaysInMadridService() {
    }

    public void requestWeatherData(OnDataResponse delegate) {
        try {
            String url = "https://api.openweathermap.org/data/2.5/weather?q=Madrid&appid=16ff8b44e221582dbbd992b71f1ac5fb";
            URLSession.getShared().dataTask(new URL(url), (data, response, error) -> {
                HTTPURLResponse resp = (HTTPURLResponse) response;
                Cities cities = null;
                int statusCode = -1;
                if (error == null && resp.getStatusCode() == 200) {
                    String text = data.toText();
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
                    Gson gson = gsonBuilder.create();
                    cities = gson.fromJson(text, Cities.class);
                    statusCode = resp.getStatusCode();
                }
                if (delegate != null) {
                    delegate.onChange(error != null, statusCode, cities);
                }
            }).resume();
        } catch (Exception e) {
            Log.e("ERROR", "Error loading data");
        }

    }

    public interface OnDataResponse {
        public abstract void onChange(boolean isNetworkError, int statusCode, Cities cities);
    }
}
