package com.user.oxostay.screens;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.user.oxostay.BuildConfig;
import com.user.oxostay.R;

public class ReferralActivity extends AppCompatActivity {

    RelativeLayout rl_refer;
    DatabaseReference referRef;
    FirebaseDatabase database;
    ImageView iv_login_head;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referral);

        initView();
    }
    public void initView(){
        rl_refer = (RelativeLayout) findViewById(R.id.rl_refer);

        database = FirebaseDatabase.getInstance();
        iv_login_head = (ImageView) findViewById(R.id.iv_login_head);

        referRef = database.getReference().child("appImages").child("refer_poster");
        referRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String image = dataSnapshot.getValue().toString();
                Log.e("checkCovidImg",">>" + dataSnapshot.toString() + ">." + image);
                Glide.with(getApplicationContext()).load(image).into(iv_login_head);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        rl_refer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedpreferences = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
                String ref_code = sharedpreferences.getString("ref_code","");
                Log.e("checkRefCode",">>" + ref_code);
                Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                /*This will be the actual content you wish you share.*/
                String shareBody = "Download OXOStay app from Playstore and use this code for OXOStay app " + " < " + ref_code + " > ";
                /*The type of the content is text, obviously.*/
                intent.setType("text/plain");
                /*Applying information Subject and Body.*/
//                intent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.share_subject));
                intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                /*Fire!*/
                startActivity(Intent.createChooser(intent, getString(R.string.share_using)));
            }
        });

    }
}