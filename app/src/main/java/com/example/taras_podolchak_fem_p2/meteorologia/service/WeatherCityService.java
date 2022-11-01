package com.example.taras_podolchak_fem_p2.meteorologia.service;

import com.example.taras_podolchak_fem_p2.meteorologia.pojo.WeatherList;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.URL;

import cafsoft.foundation.HTTPURLResponse;
import cafsoft.foundation.URLComponents;
import cafsoft.foundation.URLQueryItem;
import cafsoft.foundation.URLSession;


public class WeatherCityService {

    public WeatherCityService() {
    }

    public void requestWeatherData(String cityName, String countryISOCode, OnDataResponse delegate) {
        URL url = null;
        URLComponents components = new URLComponents();
        components.setScheme("https");
        components.setHost("api.openweathermap.org");
        components.setPath("/data/2.5/weather");
        String theAPIKey = "a9cf0f7a3cc84a884d84d4df48f057c2";
        components.setQueryItems(new URLQueryItem[]{
                new URLQueryItem("appid", theAPIKey),
                new URLQueryItem("units", "metric"),
                new URLQueryItem("lang", "es"),
                new URLQueryItem("q", cityName + "," + countryISOCode)
        });

        URLSession.getShared().dataTask(components.getURL(), (data, response, error) -> {
            HTTPURLResponse resp = (HTTPURLResponse) response;

            WeatherList weatherList = null;
            int statusCode = -1;
            if (error == null && resp.getStatusCode() == 200) {
                String text = data.toText();
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
                Gson gson = gsonBuilder.create();
                weatherList = gson.fromJson(text, WeatherList.class);
                statusCode = resp.getStatusCode();
            }
            if (delegate != null) {
                delegate.onChange(error != null, statusCode, weatherList);
            }
        }).resume();
    }

    public interface OnDataResponse {
        public abstract void onChange(boolean isNetworkError, int statusCode, WeatherList root);
    }
}
