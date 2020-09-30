package com.user.oxostay.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.user.oxostay.R;
import com.user.oxostay.adapter.LocationAdapter;
import com.user.oxostay.common.BaseActivity;
import com.user.oxostay.models.Location;

import java.util.ArrayList;

public class LocationActivity extends AppCompatActivity {

    RecyclerView recyclerView_locations;
    LocationAdapter locationAdapter;
    EditText et_city;
    ArrayList<String> citiesList;
    ImageView iv_back;
    BaseActivity baseActivity;
    FirebaseDatabase database;
    DatabaseReference ref;
    ArrayList<Location> approvedModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        initView();
    }

    public void initView(){
        baseActivity = new BaseActivity();
        baseActivity.showLoader(LocationActivity.this);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference().child("oxostaypartner").child("cities");
        approvedModels = new ArrayList<>();
        recyclerView_locations = (RecyclerView) findViewById(R.id.recyclerView_locations);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        et_city = (EditText) findViewById(R.id.et_city);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        citiesList = new ArrayList<>();
        getDataFirebase();
//        PutData();
//        locationAdapter = new LocationAdapter(citiesList,this);
        recyclerView_locations.setHasFixedSize(true);
        recyclerView_locations.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_locations.setAdapter(locationAdapter);

        et_city.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

                DatabaseReference dateRef = rootRef.child("oxostaypartner").child("cities");
                Query query = dateRef.orderByChild("city_name").startAt(charSequence.toString()).endAt(charSequence.toString() + "\uf8ff");
                Log.e("checkQuery",">>" + dateRef);

                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        approvedModels.clear();
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                            Location upload = postSnapshot.getValue(Location.class);
                            approvedModels.add(upload);
                            Log.e("checkQuery","11>>" + postSnapshot.toString());
                        }
                        locationAdapter = new LocationAdapter(approvedModels,getApplicationContext());

                        Log.e("checkQuery","final>>" + approvedModels.toString());
                        recyclerView_locations.setAdapter(locationAdapter);
                        locationAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

//                query.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                            Log.e("checkQuery","00>>" + postSnapshot.toString());
//
//                            Location upload = postSnapshot.getValue(Location.class);
//                            approvedModels.add(upload);
//                            Log.e("checkQuery","11>>" + approvedModels.size() + "><><" + postSnapshot.toString());
//                        }
//                        locationAdapter = new LocationAdapter(approvedModels,getApplicationContext());
//
//                        recyclerView_locations.setAdapter(locationAdapter);
//                        locationAdapter.notifyDataSetChanged();
////                        mprogress.setVisibility(View.INVISIBLE);
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                        Toast.makeText(LocationActivity.this, databaseError.getMessage(),Toast.LENGTH_SHORT).show();
////                        mprogress.setVisibility(View.INVISIBLE);
//                    }
//                });
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void SearchCity(){

    }

    public void getDataFirebase(){

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    Location approvedModel = dataSnapshot1.getValue(Location.class);
                    approvedModels.add(approvedModel);
//                    approvedModelsIds.add(dataSnapshot1.getKey());
                }
                Log.e("checkKey",">>" + approvedModels.toString());

                locationAdapter = new LocationAdapter(approvedModels,getApplicationContext());
                recyclerView_locations.setAdapter(locationAdapter);
                locationAdapter.notifyDataSetChanged();
                baseActivity.dismissLoader();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}