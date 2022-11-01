package com.example.taras_podolchak_fem_p2.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.taras_podolchak_fem_p2.R;
import com.example.taras_podolchak_fem_p2.meteorologia.pojo.Cities;
import com.example.taras_podolchak_fem_p2.meteorologia.service.Weather5DaysInMadridService;
import com.example.taras_podolchak_fem_p2.meteorologia.service.WeatherCityService;
import com.example.taras_podolchak_fem_p2.meteorologia.service.WeatherNearbyAreasServise;

import java.util.concurrent.ExecutionException;

public class HomeFragment extends Fragment {

    private TextView lblCurrent;
    private TextView lblfeels_like;
    private TextView lblMin;
    private TextView lblMax;
    private TextView lbl_humedad;
    private TextView lbl_presión;
    private TextView lbl_velocidad_viento;
    private TextView lbl_dirección_viento;
    private TextView lbl_nubes;
    private TextView lbl_visibilidad;


    Cities citiesList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        lblCurrent = view.findViewById(R.id.lblCurrent);
        lblfeels_like = view.findViewById(R.id.lblfeels_like);
        lblMin = view.findViewById(R.id.lblMin);
        lblMax = view.findViewById(R.id.lblMax);
        lbl_humedad = view.findViewById(R.id.lbl_humedad);
        lbl_presión = view.findViewById(R.id.lbl_presión);
        lbl_velocidad_viento = view.findViewById(R.id.lbl_velocidad_viento);
        lbl_dirección_viento = view.findViewById(R.id.lbl_direccion_viento);
        lbl_nubes = view.findViewById(R.id.lbl_nubes);
        lbl_visibilidad = view.findViewById(R.id.lbl_visibilidad);

       // getWeatherNearbyAreas();
       // getWeatherCity();
        getWeather5DaysInMadrid();

        return view;
    }
    private void getWeatherNearbyAreas() {
        try {
            citiesList = new WeatherNearbyAreasServise().execute(new ListView[0]).get();
            // citiesList.getList().ge
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
    private void getWeatherCity() {
        new WeatherCityService().requestWeatherData("Madrid", "ES", (isNetworkError, statusCode, root) -> {
            if (!isNetworkError) {
                if (statusCode == 200) {
                   // showWeatherInfo(root);
                } else {
                    Toast.makeText(getContext(), "Weather Service error", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "Weather Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getWeather5DaysInMadrid() {
        new Weather5DaysInMadridService().requestWeatherData( (isNetworkError, statusCode, root) -> {
            if (!isNetworkError) {
                if (statusCode == 200) {
                    showWeather5DaysInMadrid(root);
                } else {
                    Toast.makeText(getContext(), "Weather Service error", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "Weather Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showWeather5DaysInMadrid(Cities cities) {
       // String temp = String.valueOf(cities.getList().get(1).getMain().getTemp());
      //  String feels_like = String.valueOf(cities.getMain().getFeelsLike());
     /*   String tempMin = String.valueOf(cities.getMain().getTempMin());
        String tempMax = String.valueOf(cities.getMain().getTempMax());
        String humedad = String.valueOf(cities.getMain().getHumidity());
        String presion = String.valueOf(cities.getMain().getPressure());
        String velocidad_viento = String.valueOf(cities.getWind().getSpeed());
        String dirección_viento = String.valueOf(cities.getWind().getDeg());
        String nubes = String.valueOf(cities.getClouds().getAll());
      //  String visibilidad = String.valueOf(cities.getVisibility());



        lblCurrent.setText(getString(R.string.current) + " " + temp + " °C");
     //   lblfeels_like.setText(getString(R.string.se_siente_como) + " " + feels_like + " °C");
        lblMin.setText(getString(R.string.minimum) + " " + tempMin + " °C");
        lblMax.setText(getString(R.string.maximum) + " " + tempMax + " °C");
        lbl_humedad.setText(getString(R.string.humedad) + " " + humedad + " %");
        lbl_presión.setText(getString(R.string.presion) + " " + presion + " hPa");
        lbl_velocidad_viento.setText(getString(R.string.velocidad) + " " + velocidad_viento + " km/h");
        lbl_dirección_viento.setText(getString(R.string.dirección) + " " + dirección_viento + "°");
        lbl_nubes.setText(getString(R.string.nubes) + " " + nubes + " %");
   */  //   lbl_visibilidad.setText(getString(R.string.visibilidad) + " " + visibilidad + " %");
    }




}