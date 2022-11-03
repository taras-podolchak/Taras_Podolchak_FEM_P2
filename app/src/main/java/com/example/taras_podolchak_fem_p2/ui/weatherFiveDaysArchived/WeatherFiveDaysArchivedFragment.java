package com.example.taras_podolchak_fem_p2.ui.weatherFiveDaysArchived;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taras_podolchak_fem_p2.R;
import com.example.taras_podolchak_fem_p2.adapter.WeatherFiveDaysArchivedAdapter;
import com.example.taras_podolchak_fem_p2.pojo.Lists;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class WeatherFiveDaysArchivedFragment extends Fragment {

    private FirebaseFirestore fbf = FirebaseFirestore.getInstance();
    private final List<Lists> lists = new ArrayList<>();
    private WeatherFiveDaysArchivedAdapter weatherFiveDaysArchivedAdapter;
    private Context mContext;
    private RecyclerView rcv_weather_five_days_archived;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_five_days_archived, container, false);


        this.rcv_weather_five_days_archived = view.findViewById(R.id.rcv_weather_five_days_archived);
        this.rcv_weather_five_days_archived.setHasFixedSize(true);
        this.rcv_weather_five_days_archived.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, true));
        cargarRegistros();


        return view;
    }

    private void cargarRegistros() {
        fbf.collection("Weather_Five_Days")
                .addSnapshotListener((value, e) -> {
                    lists.clear();
                    for (QueryDocumentSnapshot doc : value) {
                        lists.add(doc.toObject(Lists.class));
                    }
                    weatherFiveDaysArchivedAdapter = new WeatherFiveDaysArchivedAdapter(lists, mContext);
                    rcv_weather_five_days_archived.setAdapter(weatherFiveDaysArchivedAdapter);
                    weatherFiveDaysArchivedAdapter.notifyDataSetChanged();
                });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }
}