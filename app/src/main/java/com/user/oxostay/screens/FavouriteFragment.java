package com.user.oxostay.screens;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.user.oxostay.R;
import com.user.oxostay.adapter.FavouriteAdapter;
import com.user.oxostay.common.BaseActivity;
import com.user.oxostay.models.ApprovedModel;

import java.util.ArrayList;

public class FavouriteFragment extends Fragment {

    RelativeLayout rl_Search;
    FavouriteAdapter favouriteAdapter;
    RecyclerView recyclerView_fav;
    ArrayList<String> hotelList;
    EditText et_fav_search;
    FirebaseDatabase database;
    TextView tv_fav_result;
    DatabaseReference ref;
    ArrayList<ApprovedModel> approvedModels;
    ArrayList<String> citiesList;
    BaseActivity baseActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favourite_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);

    }

    public void initView(View view){

        baseActivity = new BaseActivity();
        recyclerView_fav = (RecyclerView) view.findViewById(R.id.recyclerView_fav);
        et_fav_search = (EditText) view.findViewById(R.id.et_fav_search);
        tv_fav_result = (TextView) view.findViewById(R.id.tv_fav_result);
        hotelList = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        ref = database.getReference().child("oxostaypartner").child("hotelsapproved");
        approvedModels = new ArrayList<>();
        getDataFirebase();
        recyclerView_fav.setHasFixedSize(true);
        recyclerView_fav.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView_fav.setAdapter(favouriteAdapter);


        rl_Search = (RelativeLayout) view.findViewById(R.id.rl_Search);
//        rl_Search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent(getContext(),LocationActivity.class);
//                startActivity(intent);
//            }
//        });

        et_fav_search.addTextChangedListener(new TextWatcher() {
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
                        approvedModels.clear();
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                            ApprovedModel upload = postSnapshot.getValue(ApprovedModel.class);
                            approvedModels.add(upload);
                            Log.e("checkQuery","11>>" + postSnapshot.toString());
                        }
                        favouriteAdapter = new FavouriteAdapter(approvedModels,getActivity());

                        Log.e("checkQuery","final>>" + approvedModels.toString());
                        recyclerView_fav.setAdapter(favouriteAdapter);
                        favouriteAdapter.notifyDataSetChanged();
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

    }

    public void getDataFirebase(){

        try {

            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    try {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                            ApprovedModel approvedModel = dataSnapshot1.getValue(ApprovedModel.class);
                            approvedModels.add(approvedModel);
//                    approvedModelsIds.add(dataSnapshot1.getKey());
                        }
                        Log.e("checkfavKey",">>" + approvedModels.toString());
                        tv_fav_result.setText("Showing " + approvedModels.size() + " results");
                        favouriteAdapter = new FavouriteAdapter(approvedModels,getActivity());
                        recyclerView_fav.setAdapter(favouriteAdapter);
                        favouriteAdapter.notifyDataSetChanged();
                    }catch (Exception e){
                        Log.e("checkfavKey",">>" + e.getMessage());
                    }

//                baseActivity.dismissLoader();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }catch (Exception e){

        }
    }

}
