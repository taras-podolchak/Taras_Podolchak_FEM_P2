package com.example.taras_podolchak_fem_p2.adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taras_podolchak_fem_p2.R;
import com.example.taras_podolchak_fem_p2.pojo.Lists;
import com.example.taras_podolchak_fem_p2.ui.modal.ShowWeather;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class WeatherFiveDaysArchivedAdapter extends RecyclerView.Adapter<WeatherFiveDaysArchivedAdapter.ViewHolder> {

    private final List<Lists> lists;
    private final Context context;
    private FirebaseFirestore fbf = FirebaseFirestore.getInstance();


    public WeatherFiveDaysArchivedAdapter(List<Lists> lists, Context context) {
        this.lists = lists;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.fragment_weather_five_days_archived_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_Date.setText(formateDateFromstring(lists.get(position).getDtTxt()));
        holder.tv_Max_ar.setText("Dia\n" + Math.round(lists.get(position).getMain().getTempMax()) + " °C");
        holder.tv_Min_ar.setText("Noche\n" + Math.round(lists.get(position).getMain().getTempMin()) + " °C");
        holder.tv_WindSpeed.setText("Viento\n" + lists.get(position).getWind().getSpeed() + " km/h");
        holder.tv_Clouds.setText("Nubes\n" + lists.get(position).getClouds().getAll() + " %");
        holder.tv_Humidity.setText("Humedad\n" + lists.get(position).getMain().getHumidity() + " %");
        holder.tv_Description_ar.setText(lists.get(position).getWeather().get(0).getDescription());
        holder.cv_weather_Five_Days_item_ar.setOnClickListener(v1 -> {
            FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
            Bundle bundle = new Bundle();
            bundle.putSerializable("listsEnProceso", lists.get(position));
            DialogFragment a_update_act_modal = new ShowWeather();
            a_update_act_modal.setArguments(bundle);
            a_update_act_modal.show(manager, "dialog");
        });

        holder.cv_weather_Five_Days_item_ar.setOnLongClickListener(view -> {
            AlertDialog dialogo = new AlertDialog
                    .Builder(view.getContext())
                    .setPositiveButton("Eliminar", (dialog, which) -> {
                        fbf.collection("Weather_Five_Days").document(lists.get(position).getDtTxt())
                                .delete()
                                .addOnSuccessListener(aVoid -> Toast.makeText(view.getContext(), "Registro eliminado con éxito!", Toast.LENGTH_SHORT).show())
                                .addOnFailureListener(e -> Toast.makeText(view.getContext(), "Ha ocurrido un error eliminando el registro", Toast.LENGTH_SHORT).show());
                    })
                    .setNegativeButton("Volver", null)
                    .setTitle("Confirmar")
                    .setMessage("¿Deseas Eliminar el registro de la BBDD?")
                    .create();
            dialogo.show();
            return true;
        });
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_Date;
        private TextView tv_Max_ar;
        private TextView tv_Min_ar;
        private TextView tv_WindSpeed;
        private TextView tv_Clouds;
        private TextView tv_Humidity;
        private TextView tv_Description_ar;
        private CardView cv_weather_Five_Days_item_ar;

        public ViewHolder(View view) {
            super(view);
            this.tv_Date = view.findViewById(R.id.tv_Date_ar);
            this.tv_Max_ar = view.findViewById(R.id.tv_Max_ar);
            this.tv_Min_ar = view.findViewById(R.id.tv_Min_ar);
            this.tv_WindSpeed = view.findViewById(R.id.tv_WindSpeed_ar);
            this.tv_Clouds = view.findViewById(R.id.tv_Clouds_ar);
            this.tv_Humidity = view.findViewById(R.id.tv_Humidity_ar);
            this.tv_Description_ar = view.findViewById(R.id.tv_Description_ar);
            this.cv_weather_Five_Days_item_ar = view.findViewById(R.id.cv_weather_Five_Days_item_ar);
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