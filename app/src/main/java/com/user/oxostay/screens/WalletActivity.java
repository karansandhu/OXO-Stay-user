package com.user.oxostay.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.user.oxostay.R;
import com.user.oxostay.adapter.LocationAdapter;
import com.user.oxostay.adapter.WalletAdapter;

import java.util.ArrayList;

public class WalletActivity extends AppCompatActivity {

    RecyclerView recyclerView_wallet;
    WalletAdapter walletAdapter;
    ArrayList<String> citiesList;
    ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        initView();
    }
    public void initView(){
        recyclerView_wallet = (RecyclerView) findViewById(R.id.recyclerView_wallet);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        citiesList = new ArrayList<>();
        PutData();
        walletAdapter = new WalletAdapter(citiesList,this);
        recyclerView_wallet.setHasFixedSize(true);
        recyclerView_wallet.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_wallet.setAdapter(walletAdapter);
    }

    public void PutData(){
        citiesList.add("Rs 200");
        citiesList.add("Rs 581");
        citiesList.add("Rs 456");
        citiesList.add("Rs 696");
        citiesList.add("Rs 985");
        citiesList.add("Rs 1222");
        citiesList.add("Rs 2587");
        citiesList.add("Rs 1455");
        citiesList.add("Rs 658");
    }

}