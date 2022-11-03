package com.example.taras_podolchak_fem_p2.ui.weatherOneDay;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.taras_podolchak_fem_p2.R;
import com.example.taras_podolchak_fem_p2.pojo.List;
import com.example.taras_podolchak_fem_p2.service.WeatherOneDayService;

public class WeatherOneDayFragment extends Fragment {

    private TextView tv_Current;
    private TextView tv_Feels_like;
    private TextView tv_Min;
    private TextView tv_Max;
    private TextView tv_Humidity;
    private TextView tv_Pressure;
    private TextView tv_WindSpeed;
    private TextView tv_WindDirection;
    private TextView tv_Clouds;
    private TextView tv_Visibility;

    private ProgressBar pb_Current;
    private ProgressBar pb_Feels_like;
    private ProgressBar pb_Max;
    private ProgressBar pb_Min;
    private ProgressBar pb_Humidity;
    private ProgressBar pb_Pressure;
    private ProgressBar pb_WindSpeed;
    private ImageView iv_WindDirection;
    private ProgressBar pb_Clouds;
    private ProgressBar pb_Visibility;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_one_day, container, false);

        tv_Current = view.findViewById(R.id.tv_Current);
        tv_Feels_like = view.findViewById(R.id.tv_Feels_like);
        tv_Min = view.findViewById(R.id.tv_Min);
        tv_Max = view.findViewById(R.id.tv_Max);
        tv_Humidity = view.findViewById(R.id.tv_Humidity);
        tv_Pressure = view.findViewById(R.id.tv_Pressure);
        tv_WindSpeed = view.findViewById(R.id.tv_WindSpeed);
        tv_WindDirection = view.findViewById(R.id.tv_WindDirection);
        tv_Clouds = view.findViewById(R.id.tv_Clouds);
        tv_Visibility = view.findViewById(R.id.tv_Visibility);

        pb_Current = view.findViewById(R.id.pb_Current);
        pb_Feels_like = view.findViewById(R.id.pb_Feels_like);
        pb_Min = view.findViewById(R.id.pb_Min);
        pb_Max = view.findViewById(R.id.pb_Max);
        pb_Humidity = view.findViewById(R.id.pb_Humidity);
        pb_Pressure = view.findViewById(R.id.pb_Pressure);
        pb_WindSpeed = view.findViewById(R.id.pb_WindSpeed);
        // iv_WindDirection = view.findViewById(R.id.iv_WindDirection);
        pb_Clouds = view.findViewById(R.id.pb_Clouds);
        pb_Visibility = view.findViewById(R.id.pb_Visibility);


        //getWeatherOneDay();


        return view;
    }

    private void getWeatherOneDay() {
        new WeatherOneDayService().requestWeatherOneDay("Madrid", "ES", (isNetworkError, statusCode, weatherList) -> {
            if (!isNetworkError) {
                if (statusCode == 200) {
                    showWeatherInfo(weatherList);
                } else {
                    Toast.makeText(getContext(), "Weather Service error", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "Weather Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("NewApi")
    public void showWeatherInfo(List list) {
        double temp = list.getMain().getTemp();
        double feels_like = list.getMain().getFeelsLike();
        double tempMin = list.getMain().getTempMin();
        double tempMax = list.getMain().getTempMax();
        int humidity = list.getMain().getHumidity();
        int pressure = list.getMain().getPressure();
        double windSpeed = list.getWind().getSpeed();
        int windDirection = list.getWind().getDeg();
        int clouds = list.getClouds().getAll();
        int visibility = (list.getVisibility() / 100);

        tv_Current.setText(getString(R.string.current) + " " + temp + " °C");
        tv_Feels_like.setText(getString(R.string.se_siente_como) + " " + feels_like + " °C");
        tv_Min.setText(getString(R.string.minimum) + " " + tempMin + " °C");
        tv_Max.setText(getString(R.string.maximum) + " " + tempMax + " °C");
        tv_Humidity.setText(getString(R.string.humedad) + " " + humidity + " %");
        tv_Pressure.setText(getString(R.string.presion) + " " + pressure + " hPa" + calcularDiferenciaPrecion(pressure));
        tv_WindSpeed.setText(getString(R.string.velocidad) + " " + windSpeed + " km/h");
        tv_WindDirection.setText(getString(R.string.dirección) + " " + windDirection + "°");
        tv_Clouds.setText(getString(R.string.nubes) + " " + clouds + " %");
        tv_Visibility.setText(getString(R.string.visibilidad) + " " + visibility + " %");

        pb_Current.setProgress(intToProcent(temp));
        pb_Feels_like.setProgress(intToProcent(feels_like));
        pb_Min.setProgress(intToProcent(tempMin));
        pb_Max.setProgress(intToProcent(tempMax));
        pb_Humidity.setProgress(humidity);
        pb_WindSpeed.setProgress((int) windSpeed);
        rotarFlechaDireccion();
        pb_Clouds.setProgress(clouds);
        pb_Visibility.setProgress(visibility);
    }

    private String calcularDiferenciaPrecion(int pressure) {
        int dif;
        if (pressure > 1015.25) {
            dif = (int) (pressure - 1013.25);
            tv_Pressure.setTextColor(ColorStateList.valueOf(Color.RED));
            pb_Pressure.setProgress(50 + (dif * 2));
            pb_Pressure.setProgressTintList(ColorStateList.valueOf(Color.RED));
            return " > " + dif;
        }
        if (pressure < 1011.25) {
            dif = (int) (1013.25 - pressure);
            tv_Pressure.setTextColor(ColorStateList.valueOf(Color.RED));
            pb_Pressure.setProgress(50 - (dif * 2));
            pb_Pressure.setProgressTintList(ColorStateList.valueOf(Color.RED));
            return " < " + dif;
        } else {
            tv_Pressure.setTextColor(ColorStateList.valueOf(Color.GREEN));
            pb_Pressure.setProgress(50);
            pb_Pressure.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
            return "😀";
        }
    }


    private int intToProcent(double temp) {
        int min = -20;
        int max = 50;
        return (int) (temp * 100) / 50;
    }

    private void rotarFlechaDireccion() {
           /*     Matrix matrix = new Matrix();
        matrix.postRotate(90);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(iv_WindDirection.getDrawingCache(),50,50,true);//BitmapOrg- is origanl bitmap
        Bitmap rotatedBitmap = Bitmap.createBitmap(scaledBitmap , 90, 100, scaledBitmap .getWidth(), scaledBitmap .getHeight(), matrix, true);
        iv_WindDirection.setImageBitmap(rotatedBitmap);

        //iv_WindDirection.setRotation(iv_WindDirection.getRotation()+90);
        //iv_WindDirection.setRotationY(iv_WindDirection.getRotationY()+90);
        // iv_WindDirection.setRotationX(iv_WindDirection.getRotationX()+90);
        iv_WindDirection.setPivotX(iv_WindDirection.getWidth()/2);
        iv_WindDirection.setPivotY(iv_WindDirection.getHeight()/2);
        iv_WindDirection.setRotation(45);*/
    }
}