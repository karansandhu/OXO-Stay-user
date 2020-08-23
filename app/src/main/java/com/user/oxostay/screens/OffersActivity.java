package com.user.oxostay.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.user.oxostay.R;

public class OffersActivity extends AppCompatActivity {

    ImageView iv_offers_poster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);

        initView();
    }

    public void initView(){
        iv_offers_poster = (ImageView) findViewById(R.id.iv_offers_poster);
        Glide.with(getApplicationContext()).load(R.drawable.offer_poster).into(iv_offers_poster);
    }

}