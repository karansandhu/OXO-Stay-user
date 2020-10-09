package com.user.oxostay.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.user.oxostay.R;
import com.user.oxostay.adapter.FavouriteAdapter;
import com.user.oxostay.adapter.HotelListAdapter;
import com.user.oxostay.adapter.LocationAdapter;
import com.user.oxostay.models.ApprovedModel;
import com.user.oxostay.models.Location;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class HotelListActivity extends AppCompatActivity {

    private String TAG = "HotelListActivity";
    private RecyclerView recyclerView_hotels;
    private HotelListAdapter hotelListAdapter;
    private ArrayList<ApprovedModel> hotelList;
    private EditText et_location;
    private ImageView iv_back;
    private String time,location,Intentdate;
    private FirebaseDatabase database;
    private DatabaseReference ref;
    private TextView tv_result;
    private String monthOfTheYear;

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
        if(getIntent() != null)
        {
            Intent intent = getIntent();
            time = intent.getStringExtra("time");
            Log.e(TAG, "initView: "+time);
            Intentdate = intent.getStringExtra("date");
            location = intent.getStringExtra("location");

        }

        recyclerView_hotels = (RecyclerView) findViewById(R.id.recyclerView_hotels);
        et_location = (EditText) findViewById(R.id.et_location);
        tv_result = (TextView) findViewById(R.id.tv_result);
        recyclerView_hotels.setHasFixedSize(true);
        recyclerView_hotels.setLayoutManager(new LinearLayoutManager(this));
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        et_location.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

                DatabaseReference dateRef = rootRef.child("oxostaypartner").child("hotelsapproved");
                Query query = dateRef.orderByChild("hotel_name").startAt(charSequence.toString()).endAt(charSequence.toString() + "\uf8ff");
                Log.e("checkQuery",">>" + dateRef);

                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        hotelList.clear();
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                            ApprovedModel upload = postSnapshot.getValue(ApprovedModel.class);
                            hotelList.add(upload);
                            Log.e("checkQuery","11>>" + postSnapshot.toString());
                        }
                        hotelListAdapter = new HotelListAdapter(hotelList,getApplicationContext());

                        Log.e("checkQuery","final>>" + hotelList.toString());
                        tv_result.setText("Showing " + hotelList.size() + " results");
                        recyclerView_hotels.setAdapter(hotelListAdapter);
                        hotelListAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        hotelListAdapter = new HotelListAdapter(hotelList,this);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                hotelList.clear();
                try {

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        ApprovedModel upload = postSnapshot.getValue(ApprovedModel.class);
                        if(!upload.getDate_from().equalsIgnoreCase("Select Date") && !upload.getDate_to().equalsIgnoreCase("Select Date"))
                        {

                        String subMonthFrom = "";
                        String subMonthTo = "";


                        /**Date Comparison**/
                        if(upload.getDate_from().contains(" ")){
                            subMonthFrom = upload.getDate_from().substring(0, upload.getDate_from().indexOf(" "));
                        }
                        if(upload.getDate_to().contains(" "))
                        {
                            subMonthTo = upload.getDate_to().substring(0, upload.getDate_to().indexOf(" "));
                        }

                        String subDateFrom = upload.getDate_from().substring(subMonthFrom.length()+1, upload.getDate_from().indexOf(","));
                        String[] subYearFrom = upload.getDate_from().split(",");

                        if(subDateFrom.length() == 1)
                        {
                            subDateFrom = "0"+subDateFrom;
                        }

                        String datesFrom = subYearFrom[1]+"-"+convertMonthIntoInt(subMonthFrom)+"-"+subDateFrom;
                        SimpleDateFormat formatFrom = new SimpleDateFormat("yyyy-MM-dd");
                        Date dateFrom = formatFrom.parse(datesFrom);

                        String subDateTo = upload.getDate_to().substring(subMonthTo.length()+1, upload.getDate_to().indexOf(","));
                        String[] subYearTo = upload.getDate_to().split(",");
                        if(subDateTo.length() == 1)
                        {
                            subDateTo = "0"+subDateTo;
                        }
                        convertMonthIntoInt(subMonthFrom);

                        String datesTo = subYearTo[1]+"-"+convertMonthIntoInt(subMonthTo)+"-"+subDateTo;
                        SimpleDateFormat formatTo = new SimpleDateFormat("yyyy-MM-dd");
                        Date dateTo = formatTo.parse(datesTo);

                        SimpleDateFormat intentDate = new SimpleDateFormat("yyyy-MM-dd");
                        Date dateIntent = intentDate.parse(Intentdate);

                        if(dateIntent.after(dateFrom) && dateIntent.before(dateTo))
                        {
                            if(Integer.parseInt(upload.getRooms_available()) > 0)
                            {
                                hotelList.add(upload);
                            }
                        }


                        /**End of Date comparison**/
                    }
                    hotelListAdapter = new HotelListAdapter(hotelList,getApplicationContext());
                    tv_result.setText("Showing " + hotelList.size() + " results");
                    recyclerView_hotels.setAdapter(hotelListAdapter);
                    hotelListAdapter.notifyDataSetChanged();

                    }
                }catch (Exception e){
                    e.printStackTrace();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//        recyclerView_hotels.setAdapter(hotelListAdapter);

    }


    private String convertMonthIntoInt(String month)
    {

        if(month.equalsIgnoreCase("january"))
        {
            monthOfTheYear = "01";
        }
        else if(month.equalsIgnoreCase("february"))
        {
            monthOfTheYear = "02";
        }
        else if(month.equalsIgnoreCase("march"))
        {
            monthOfTheYear  = "03";
        }
        else if(month.equalsIgnoreCase("april"))
        {
            monthOfTheYear = "04";
        }
        else if(month.equalsIgnoreCase("may"))
        {
            monthOfTheYear = "05";
        }
        else if(month.equalsIgnoreCase("june"))
        {
            monthOfTheYear = "06";
        }
        else if(month.equalsIgnoreCase("july"))
        {
            monthOfTheYear = "07";
        }
        else if(month.equalsIgnoreCase("august"))
        {
            monthOfTheYear = "08";
        }
        else if(month.equalsIgnoreCase("september"))
        {
            monthOfTheYear = "09";
        }
        else if(month.equalsIgnoreCase("october"))
        {
            monthOfTheYear = "10";
        }
        else if(month.equalsIgnoreCase("november"))
        {
            monthOfTheYear = "11";
        }
        else if(month.equalsIgnoreCase("december"))
        {
            monthOfTheYear = "12";
        }

        Log.e("checkQuery","final>>" + monthOfTheYear);


        return monthOfTheYear;
    }

}