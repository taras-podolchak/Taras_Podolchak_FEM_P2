package com.example.taras_podolchak_fem_p2.service;

import android.util.Log;

import com.example.taras_podolchak_fem_p2.pojo.Example;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.URL;

import cafsoft.foundation.HTTPURLResponse;
import cafsoft.foundation.URLSession;


public class WeatherFiveDaysService_dontWork {


    public WeatherFiveDaysService_dontWork() {
    }

    public void requestWeatherFiveDays(OnDataResponse delegate) {
        try {
            String url = "https://api.openweathermap.org/data/2.5/forecast?q=Madrid&appid=a9cf0f7a3cc84a884d84d4df48f057c2";
            URLSession.getShared().dataTask(new URL(url), (data, response, error) -> {
                HTTPURLResponse resp = (HTTPURLResponse) response;

                Example example = null;
                int statusCode = -1;
                if (error == null && resp.getStatusCode() == 200) {
                    String text = data.toText();
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
                    Gson gson = gsonBuilder.create();
                    example = gson.fromJson(text, Example.class);
                    statusCode = resp.getStatusCode();
                }
                if (delegate != null) {
                    delegate.onChange(error != null, statusCode, example);
                }
            }).resume();
        } catch (Exception e) {
            Log.e("ERROR", "Error loading data");
        }
    }

    public interface OnDataResponse {
        public abstract void onChange(boolean isNetworkError, int statusCode, Example example);
    }
}
