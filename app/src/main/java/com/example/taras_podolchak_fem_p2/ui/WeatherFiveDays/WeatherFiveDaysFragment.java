package com.example.taras_podolchak_fem_p2.ui.WeatherFiveDays;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.taras_podolchak_fem_p2.R;
import com.example.taras_podolchak_fem_p2.adapter.WeatherFiveDaysAdapter;
import com.example.taras_podolchak_fem_p2.pojo.Example;
import com.example.taras_podolchak_fem_p2.service.WeatherFiveDaysService;

import java.util.concurrent.ExecutionException;

public class WeatherFiveDaysFragment extends Fragment {

    private Context mContext;

    private ListView lvCitiesList;

    private WeatherFiveDaysService tarea;
    private Example example;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_five_days, container, false);
        getWeather5DaysInMadrid(view);
        WeatherFiveDaysAdapter adapter = new WeatherFiveDaysAdapter(mContext, example);
        lvCitiesList.setAdapter(adapter);
        return view;
    }

    private void getWeather5DaysInMadrid(View view) {
        lvCitiesList = view.findViewById(R.id.lvCities);
        tarea = new WeatherFiveDaysService();
        try {
            example = tarea.execute(lvCitiesList).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

}