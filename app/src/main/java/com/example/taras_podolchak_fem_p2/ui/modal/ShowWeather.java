package com.example.taras_podolchak_fem_p2.ui.modal;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.example.taras_podolchak_fem_p2.R;
import com.example.taras_podolchak_fem_p2.pojo.Lists;
import com.google.firebase.firestore.FirebaseFirestore;

public class ShowWeather extends DialogFragment {

    private TextView tv_Current_mod;
    private TextView tv_Feels_like_mod;
    private TextView tv_Min_mod;
    private TextView tv_Max_mod;
    private TextView tv_Humidity_mod;
    private TextView tv_Pressure_mod;
    private TextView tv_WindSpeed_mod;
    private TextView tv_WindDirection_mod;
    private TextView tv_Clouds_mod;
    private TextView tv_Visibility_mod;

    private ProgressBar pb_Current_mod;
    private ProgressBar pb_Feels_like_mod;
    private ProgressBar pb_Max_mod;
    private ProgressBar pb_Min_mod;
    private ProgressBar pb_Humidity_mod;
    private ProgressBar pb_Pressure_mod;
    private ProgressBar pb_WindSpeed_mod;
    private ImageView iv_WindDirection_mod;
    private ProgressBar pb_Clouds_mod;
    private ProgressBar pb_Visibility_mod;
    private Button btn_quitar_de_evento_mod;


    private FirebaseFirestore fbf = FirebaseFirestore.getInstance();


    private Lists listsEnProceso;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            listsEnProceso = (Lists) getArguments().getSerializable("listsEnProceso");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_weather_modal, container, false);

        tv_Current_mod = view.findViewById(R.id.tv_Current_mod);
        tv_Feels_like_mod = view.findViewById(R.id.tv_Feels_like_mod);
        tv_Min_mod = view.findViewById(R.id.tv_Min_mod);
        tv_Max_mod = view.findViewById(R.id.tv_Max_mod);
        tv_Humidity_mod = view.findViewById(R.id.tv_Humidity_mod);
        tv_Pressure_mod = view.findViewById(R.id.tv_Pressure_mod);
        tv_WindSpeed_mod = view.findViewById(R.id.tv_WindSpeed_mod);
        tv_WindDirection_mod = view.findViewById(R.id.tv_WindDirection_mod);
        tv_Clouds_mod = view.findViewById(R.id.tv_Clouds_mod);
        tv_Visibility_mod = view.findViewById(R.id.tv_Visibility_mod);

        pb_Current_mod = view.findViewById(R.id.pb_Current_mod);
        pb_Feels_like_mod = view.findViewById(R.id.pb_Feels_like_mod);
        pb_Min_mod = view.findViewById(R.id.pb_Min_mod);
        pb_Max_mod = view.findViewById(R.id.pb_Max_mod);
        pb_Humidity_mod = view.findViewById(R.id.pb_Humidity_mod);
        pb_Pressure_mod = view.findViewById(R.id.pb_Pressure_mod);
        pb_WindSpeed_mod = view.findViewById(R.id.pb_WindSpeed_mod);
        // iv_WindDirection = view.findViewById(R.id.iv_WindDirection);
        pb_Clouds_mod = view.findViewById(R.id.pb_Clouds_mod);
        pb_Visibility_mod = view.findViewById(R.id.pb_Visibility_mod);
        btn_quitar_de_evento_mod = view.findViewById(R.id.btn_quitar_de_evento_mod);

        rellenacionDeLosCamposDeAAddEve_act(listsEnProceso);

        btn_quitar_de_evento_mod.setOnClickListener(view12 -> ShowWeather.this.getDialog().cancel());

        return view;
    }


    private void rellenacionDeLosCamposDeAAddEve_act(Lists lists) {
        double temp = lists.getMain().getTemp();
        double feels_like = lists.getMain().getFeelsLike();
        double tempMin = lists.getMain().getTempMin();
        double tempMax = lists.getMain().getTempMax();
        int humidity = lists.getMain().getHumidity();
        int pressure = lists.getMain().getPressure();
        double windSpeed = lists.getWind().getSpeed();
        int windDirection = lists.getWind().getDeg();
        int clouds = lists.getClouds().getAll();
        int visibility = (lists.getVisibility() / 100);

        tv_Current_mod.setText(getString(R.string.current) + " " + temp + " °C");
        tv_Feels_like_mod.setText(getString(R.string.se_siente_como) + " " + feels_like + " °C");
        tv_Min_mod.setText(getString(R.string.minimum) + " " + tempMin + " °C");
        tv_Max_mod.setText(getString(R.string.maximum) + " " + tempMax + " °C");
        tv_Humidity_mod.setText(getString(R.string.humedad) + " " + humidity + " %");
        tv_Pressure_mod.setText(getString(R.string.presion) + " " + pressure + " hPa" + calcularDiferenciaPrecion(pressure));
        tv_WindSpeed_mod.setText(getString(R.string.velocidad) + " " + windSpeed + " km/h");
        tv_WindDirection_mod.setText(getString(R.string.dirección) + " " + windDirection + "°");
        tv_Clouds_mod.setText(getString(R.string.nubes) + " " + clouds + " %");
        tv_Visibility_mod.setText(getString(R.string.visibilidad) + " " + visibility + " %");

        pb_Current_mod.setProgress(intToProcent(temp));
        pb_Feels_like_mod.setProgress(intToProcent(feels_like));
        pb_Min_mod.setProgress(intToProcent(tempMin));
        pb_Max_mod.setProgress(intToProcent(tempMax));
        pb_Humidity_mod.setProgress(humidity);
        pb_WindSpeed_mod.setProgress((int) windSpeed);
        pb_Clouds_mod.setProgress(clouds);
        pb_Visibility_mod.setProgress(visibility);
    }

    private String calcularDiferenciaPrecion(int pressure) {
        int dif;
        if (pressure > 1015.25) {
            dif = (int) (pressure - 1013.25);
            tv_Pressure_mod.setTextColor(ColorStateList.valueOf(Color.RED));
            pb_Pressure_mod.setProgress(50 + (dif * 2));
            pb_Pressure_mod.setProgressTintList(ColorStateList.valueOf(Color.RED));
            return " > " + dif;
        }
        if (pressure < 1011.25) {
            dif = (int) (1013.25 - pressure);
            tv_Pressure_mod.setTextColor(ColorStateList.valueOf(Color.RED));
            pb_Pressure_mod.setProgress(50 - (dif * 2));
            pb_Pressure_mod.setProgressTintList(ColorStateList.valueOf(Color.RED));
            return " < " + dif;
        } else {
            tv_Pressure_mod.setTextColor(ColorStateList.valueOf(Color.GREEN));
            pb_Pressure_mod.setProgress(50);
            pb_Pressure_mod.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
            return "😀";
        }
    }

    private int intToProcent(double temp) {
        int min = -20;
        int max = 50;
        return (int) (temp * 100) / 50;
    }
}