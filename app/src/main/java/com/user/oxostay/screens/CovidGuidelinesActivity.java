package com.user.oxostay.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.user.oxostay.R;

public class CovidGuidelinesActivity extends AppCompatActivity {

    DatabaseReference referRef;
    FirebaseDatabase database;
    ImageView iv_covid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_guidelines);

        database = FirebaseDatabase.getInstance();
        iv_covid = (ImageView) findViewById(R.id.iv_covid);

        referRef = database.getReference().child("appImages").child("corona_poster");
        referRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String image = dataSnapshot.getValue().toString();
                Log.e("checkCovidImg",">>" + dataSnapshot.toString() + ">." + image);
                Glide.with(getApplicationContext()).load(image).into(iv_covid);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}