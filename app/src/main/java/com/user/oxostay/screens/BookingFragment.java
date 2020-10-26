package com.user.oxostay.screens;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.user.oxostay.R;
import com.user.oxostay.adapter.BookingAdapter;
import com.user.oxostay.adapter.FavouriteAdapter;
import com.user.oxostay.common.BaseActivity;
import com.user.oxostay.models.ApprovedModel;
import com.user.oxostay.models.Booking;

import java.util.ArrayList;

public class BookingFragment extends Fragment {

    private RelativeLayout rl_Search;
    private BookingAdapter bookingAdapter;
    private  RecyclerView recyclerView_bookings;
    private ArrayList<Booking> hotelList;
    private TextView tv_fav_result,tv_zero_results;
    private ImageView iv_support;
    private BaseActivity baseActivity;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference ref,hotelRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);

    }

    public void initView(View view){
        baseActivity = new BaseActivity();
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        ref = database.getReference().child("bookings").child(mAuth.getCurrentUser().getUid());
//        hotelRef = database.getReference().child("oxostaypartner").child("hotelsapproved");
        recyclerView_bookings = (RecyclerView) view.findViewById(R.id.recyclerView_bookings);
        tv_zero_results = (TextView) view.findViewById(R.id.tv_zero_results);
        tv_fav_result = (TextView) view.findViewById(R.id.tv_fav_result);
        iv_support = (ImageView) view.findViewById(R.id.iv_support);
        hotelList = new ArrayList<>();
//        bookingAdapter = new BookingAdapter(hotelList,getActivity());
        recyclerView_bookings.setHasFixedSize(true);
        recyclerView_bookings.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView_bookings.setAdapter(bookingAdapter);

        iv_support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = "+91 96547 53527";
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                startActivity(intent);
            }
        });
        getDataFirebase();
//        rl_Search = (RelativeLayout) view.findViewById(R.id.rl_Search);
//        rl_Search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent(getContext(),LocationActivity.class);
//                startActivity(intent);
//            }
//        });
    }


    public void getDataFirebase(){

        try {
            Log.e("checkforloop","00>>" + hotelList);
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try {

                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            Log.e("checkBookings","11>>" + postSnapshot.toString());
//                            if (hotelListIdsNew.contains(postSnapshot.getKey())){
//
//                                Log.e("checkforloop","22>>" + postSnapshot.getKey());
                                Booking upload = postSnapshot.getValue(Booking.class);
                                hotelList.add(upload);
//                            }
                        }

                        bookingAdapter = new BookingAdapter(hotelList,getActivity());
                        tv_fav_result.setText("Showing " + hotelList.size() + " results");
                        if (hotelList.size() == 0){
                            tv_zero_results.setVisibility(View.VISIBLE);
                        }else{
                            tv_zero_results.setVisibility(View.GONE);
                        }
                        Log.e("checkQueryNew","final>>" + hotelList.toString());
                        recyclerView_bookings.setAdapter(bookingAdapter);
                        bookingAdapter.notifyDataSetChanged();
                        baseActivity.dismissLoader();
                    }catch (Exception e){

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }catch (Exception e){

        }
    }

}

