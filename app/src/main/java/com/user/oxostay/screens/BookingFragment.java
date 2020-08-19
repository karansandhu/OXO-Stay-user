package com.user.oxostay.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.user.oxostay.R;
import com.user.oxostay.adapter.FavouriteAdapter;

import java.util.ArrayList;

public class BookingFragment extends Fragment {

    RelativeLayout rl_Search;
    FavouriteAdapter favouriteAdapter;
    RecyclerView recyclerView_fav;
    ArrayList<String> hotelList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        initView(view);

    }

    public void initView(View view){

        recyclerView_fav = (RecyclerView) view.findViewById(R.id.recyclerView_fav);
        hotelList = new ArrayList<>();
        PutData();
        favouriteAdapter = new FavouriteAdapter(hotelList,getActivity());
        recyclerView_fav.setHasFixedSize(true);
        recyclerView_fav.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView_fav.setAdapter(favouriteAdapter);


        rl_Search = (RelativeLayout) view.findViewById(R.id.rl_Search);
        rl_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(),LocationActivity.class);
                startActivity(intent);
            }
        });
    }

    public void PutData(){
        hotelList.add("JW Marriott Hotel");
        hotelList.add("Sunrise Hotel");
        hotelList.add("Lalit Hotel");
        hotelList.add("Lemontree Hotel");
        hotelList.add("Hotel sundance");
        hotelList.add("Sunrise Hotel");
    }

}

