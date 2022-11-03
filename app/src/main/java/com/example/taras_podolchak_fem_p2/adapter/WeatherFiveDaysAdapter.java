package com.example.taras_podolchak_fem_p2.adapter;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.taras_podolchak_fem_p2.R;
import com.example.taras_podolchak_fem_p2.pojo.Example;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WeatherFiveDaysAdapter extends BaseAdapter {

    private final Context context;
    private final Example example;

    public WeatherFiveDaysAdapter(Context context, Example example) {
        this.context = context;
        this.example = example;

    }

    public static class ViewHolder {
        TextView tv_Date;
        TextView tv_Current;
        TextView tv_WindSpeed;
        TextView tv_Clouds;
        TextView tv_Humidity;

    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        ViewHolder vh;
        if (convertView == null) {
            vh = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.fragment_weather_five_days_item, parent, false);
            vh.tv_Date = view.findViewById(R.id.tv_Date);
            vh.tv_Current = view.findViewById(R.id.tv_Current);
            vh.tv_WindSpeed = view.findViewById(R.id.tv_WindSpeed);
            vh.tv_Clouds = view.findViewById(R.id.tv_Clouds);
            vh.tv_Humidity = view.findViewById(R.id.tv_Humidity);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        vh.tv_Date.setText(formatDate(example.getList().get(position).getDtTxt()));
        vh.tv_Current.setText(Math.round(example.getList().get(position).getMain().getTemp()) +" °C");
        vh.tv_WindSpeed.setText(example.getList().get(position).getWind().getSpeed() +" km/h");
        vh.tv_Clouds.setText(example.getList().get(position).getClouds().getAll() +" %");
        vh.tv_Humidity.setText(example.getList().get(position).getMain().getHumidity() +" %");

        return view;
    }

    private String formatDate(String time) {
        return formateDateFromstring(time);
    }


    public long getItemId(int position) {
        return position;
    }

    public Object getItem(int position) {
        return position;
    }

    public int getCount() {
        if (example.getCnt() != null) {
            Integer oI = example.getCnt();
            return oI.intValue();
        } else {
            return 0;
        }
    }

    private String formateDateFromstring(String inputDate) {
        String outputDate = "";
        SimpleDateFormat df_input = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", java.util.Locale.getDefault());
        SimpleDateFormat df_output = new SimpleDateFormat("dd-MMM\nhh:mm", java.util.Locale.getDefault());
        try {
            Date parsed = df_input.parse(inputDate);
            outputDate = df_output.format(parsed);
        } catch (ParseException e) {
            Log.e("Date", "ParseException - dateFormat");
        }
        return outputDate;
    }
}