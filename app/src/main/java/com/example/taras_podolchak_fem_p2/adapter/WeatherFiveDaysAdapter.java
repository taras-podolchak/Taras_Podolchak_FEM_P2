package com.example.taras_podolchak_fem_p2.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.taras_podolchak_fem_p2.R;
import com.example.taras_podolchak_fem_p2.pojo.Example;

public class WeatherFiveDaysAdapter extends BaseAdapter {

    private Context context;
    private Example example;

    public WeatherFiveDaysAdapter(Context context, Example example) {
        this.context = context;
        this.example = example;

    }

    public static class ViewHolder {
        TextView textViewId01;
        TextView textViewId02;
        TextView textViewCity;
        ProgressBar textViewCitssy01;
        ProgressBar textViewCitssy02;
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        ViewHolder vh;
        if (convertView == null) {
            vh = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.fragment_weather_five_days_item, parent, false);
            vh.textViewId01 = view.findViewById(R.id.textViewId01);
            vh.textViewId02 = view.findViewById(R.id.textViewId02);
            vh.textViewCitssy01 = view.findViewById(R.id.textViewCitssy01);
            vh.textViewCitssy02 = view.findViewById(R.id.textViewCitssy02);
            vh.textViewCity = view.findViewById(R.id.textViewCity);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        vh.textViewCity.setText(example.getList().get(position).getDtTxt());
        double dTemp = example.getList().get(position).getMain().getTemp() - 273.15;
        int dTempRoundOff = (int) (Math.round(dTemp * 100) / 100);
        Integer oiHumidPerc = example.getList().get(position).getMain().getHumidity();

        vh.textViewId01.setText("Temp: " + dTempRoundOff + "°C");
        vh.textViewId02.setText("Humed: " + oiHumidPerc.intValue() + "%");
        vh.textViewCitssy01.setProgress(dTempRoundOff);
        vh.textViewCitssy02.setProgress(oiHumidPerc);
        vh.textViewCitssy01.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
        vh.textViewCitssy02.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
        if (dTempRoundOff < 20)
            vh.textViewCitssy01.setProgressTintList(ColorStateList.valueOf(Color.BLUE));
        if (dTempRoundOff > 35)
            vh.textViewCitssy01.setProgressTintList(ColorStateList.valueOf(Color.RED));
        if (oiHumidPerc > 50 || oiHumidPerc < 30)
            vh.textViewCitssy02.setProgressTintList(ColorStateList.valueOf(Color.RED));
        return view;
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
}