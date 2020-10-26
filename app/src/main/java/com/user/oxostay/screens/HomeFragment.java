package com.user.oxostay.screens;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.user.oxostay.R;

public class HomeFragment extends Fragment {

    RelativeLayout rl_Search;
    ImageView iv_latest_offer,iv_covid,iv_tv_refer_earn;
    EditText et_searchh;
    FirebaseDatabase database;
    DatabaseReference covidRef,offerRef,referRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        database = FirebaseDatabase.getInstance();
        covidRef = database.getReference().child("appImages").child("covid_banner");
        offerRef = database.getReference().child("appImages").child("latest_offers");
        referRef = database.getReference().child("appImages").child("refer_earn");
        rl_Search = (RelativeLayout) view.findViewById(R.id.rl_Search);
        et_searchh = (EditText) view.findViewById(R.id.et_searchh);
        iv_latest_offer = (ImageView) view.findViewById(R.id.iv_latest_offer);
        iv_tv_refer_earn = (ImageView) view.findViewById(R.id.iv_tv_refer_earn);
        iv_covid = (ImageView) view.findViewById(R.id.iv_covid);

        covidRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String image = dataSnapshot.getValue().toString();
                Log.e("checkCovidImg",">>" + dataSnapshot.toString() + ">." + image);
                Glide.with(getActivity()).load(image).into(iv_covid);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        offerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String image = dataSnapshot.getValue().toString();
                Log.e("checkCovidImg",">>" + dataSnapshot.toString() + ">." + image);
                Glide.with(getActivity()).load(image).into(iv_latest_offer);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        referRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String image = dataSnapshot.getValue().toString();
                Log.e("checkCovidImg",">>" + dataSnapshot.toString() + ">." + image);
                Glide.with(getActivity()).load(image).into(iv_tv_refer_earn);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        et_searchh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(),LocationActivity.class);
                startActivity(intent);
            }
        });
        iv_latest_offer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(),OffersActivity.class);
                startActivity(intent);
            }
        });
        iv_covid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(),CovidGuidelinesActivity.class);
                startActivity(intent);
            }
        });
        iv_tv_refer_earn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(),ReferralActivity.class);
                startActivity(intent);
            }
        });
    }
}
