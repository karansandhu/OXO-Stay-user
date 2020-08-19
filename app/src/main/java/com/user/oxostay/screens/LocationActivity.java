package com.user.oxostay.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.user.oxostay.R;
import com.user.oxostay.adapter.LocationAdapter;

import java.util.ArrayList;

public class LocationActivity extends AppCompatActivity {

    RecyclerView recyclerView_locations;
    LocationAdapter locationAdapter;
    ArrayList<String> citiesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        initView();
    }

    public void initView(){
        recyclerView_locations = (RecyclerView) findViewById(R.id.recyclerView_locations);
        citiesList = new ArrayList<>();
        PutData();
        locationAdapter = new LocationAdapter(citiesList,this);
        recyclerView_locations.setHasFixedSize(true);
        recyclerView_locations.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_locations.setAdapter(locationAdapter);
    }

    public void PutData(){
        citiesList.add("Delhi");
        citiesList.add("Mumbai");
        citiesList.add("Gurugram");
        citiesList.add("Punjab");
        citiesList.add("Chandigarh");
        citiesList.add("Gujrat");
        citiesList.add("Delhi");
        citiesList.add("Surat");
        citiesList.add("Faridabad");
    }

}