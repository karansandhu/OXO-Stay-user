package com.user.oxostay.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.user.oxostay.R;

public class OffersActivity extends AppCompatActivity {

    ImageView iv_offers_poster,iv_back;
    DatabaseReference referRef;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);

        initView();
    }

    public void initView(){

        iv_offers_poster = (ImageView) findViewById(R.id.iv_offers_poster);


        database = FirebaseDatabase.getInstance();

        referRef = database.getReference().child("appImages").child("offer_poster");
        referRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String image = dataSnapshot.getValue().toString();
                Log.e("checkCovidImg",">>" + dataSnapshot.toString() + ">." + image);
                Glide.with(getApplicationContext()).load(image).into(iv_offers_poster);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

}