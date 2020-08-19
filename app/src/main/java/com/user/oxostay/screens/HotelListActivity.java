package com.user.oxostay.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.user.oxostay.R;
import com.user.oxostay.adapter.HotelListAdapter;
import com.user.oxostay.adapter.LocationAdapter;

import java.util.ArrayList;

public class HotelListActivity extends AppCompatActivity {

    RecyclerView recyclerView_hotels;
    HotelListAdapter hotelListAdapter;
    ArrayList<String> hotelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_list);

        initView();
    }

    public void initView(){

        recyclerView_hotels = (RecyclerView) findViewById(R.id.recyclerView_hotels);
        hotelList = new ArrayList<>();
        PutData();
        hotelListAdapter = new HotelListAdapter(hotelList,this);
        recyclerView_hotels.setHasFixedSize(true);
        recyclerView_hotels.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_hotels.setAdapter(hotelListAdapter);
    }

    public void PutData(){
        hotelList.add("JW Marriott Hotel");
        hotelList.add("Sunrise Hotel");
        hotelList.add("Lalit Hotel");
        hotelList.add("Lemontree Hotel");
        hotelList.add("Hotel sundance");
        hotelList.add("Sunrise Hotel");
    }


}