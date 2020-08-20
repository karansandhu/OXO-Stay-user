package com.user.oxostay.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.user.oxostay.R;
import com.user.oxostay.adapter.HotelListAdapter;
import com.user.oxostay.adapter.LocationAdapter;

import java.util.ArrayList;

public class HotelListActivity extends AppCompatActivity {

    RecyclerView recyclerView_hotels;
    HotelListAdapter hotelListAdapter;
    ArrayList<String> hotelList;
    ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_list);

        initView();
    }

    public void initView(){

        recyclerView_hotels = (RecyclerView) findViewById(R.id.recyclerView_hotels);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
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