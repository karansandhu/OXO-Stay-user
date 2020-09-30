package com.user.oxostay.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.user.oxostay.R;
import com.user.oxostay.adapter.HotelListAdapter;
import com.user.oxostay.adapter.LocationAdapter;
import com.user.oxostay.models.ApprovedModel;
import com.user.oxostay.models.Location;

import java.util.ArrayList;

public class HotelListActivity extends AppCompatActivity {

    RecyclerView recyclerView_hotels;
    HotelListAdapter hotelListAdapter;
    ArrayList<ApprovedModel> hotelList;
    ImageView iv_back;
    String time,location,date;
    FirebaseDatabase database;
    DatabaseReference ref;
    TextView tv_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_list);

        initView();
    }

    public void initView(){

        database = FirebaseDatabase.getInstance();
        ref = database.getReference().child("oxostaypartner").child("hotelsapproved");

        hotelList = new ArrayList<>();
        Intent intent = getIntent();
        time = intent.getStringExtra("time");
        date = intent.getStringExtra("date");
        recyclerView_hotels = (RecyclerView) findViewById(R.id.recyclerView_hotels);
        tv_result = (TextView) findViewById(R.id.tv_result);
        recyclerView_hotels.setHasFixedSize(true);
        recyclerView_hotels.setLayoutManager(new LinearLayoutManager(this));
        location = intent.getStringExtra("location");
        Log.e("checkEveryData",">>" + time + ">>" + date + ">>" + location);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
//        PutData();

//        Query query = ref.orderByChild("city_name").startAt(date.toString()).endAt(date.toString() + "\uf8ff");
        Log.e("checkQuery","Hotel List>>" + ref);
        hotelListAdapter = new HotelListAdapter(hotelList,this);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                hotelList.clear();
                try {

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                        ApprovedModel upload = postSnapshot.getValue(ApprovedModel.class);
                        hotelList.add(upload);
                        Log.e("checkQuery","11>>" + postSnapshot.toString());
                    }
                    hotelListAdapter = new HotelListAdapter(hotelList,getApplicationContext());
                    tv_result.setText("Showing " + hotelList.size() + " results");
                    Log.e("checkQuery","final>>" + hotelList.toString());
                    recyclerView_hotels.setAdapter(hotelListAdapter);
                    hotelListAdapter.notifyDataSetChanged();

                }catch (Exception e){

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//        recyclerView_hotels.setAdapter(hotelListAdapter);
    }

}