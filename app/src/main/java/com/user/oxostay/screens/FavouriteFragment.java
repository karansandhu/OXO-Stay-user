package com.user.oxostay.screens;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.user.oxostay.R;
import com.user.oxostay.adapter.FavouriteAdapter;
import com.user.oxostay.adapter.GridViewAdapter;
import com.user.oxostay.adapter.HotelListAdapter;
import com.user.oxostay.common.BaseActivity;
import com.user.oxostay.models.ApprovedModel;
import com.user.oxostay.models.Favourites;

import java.util.ArrayList;

public class FavouriteFragment extends Fragment {

    RelativeLayout rl_Search;
    FavouriteAdapter favouriteAdapter;
    RecyclerView recyclerView_fav;
    ArrayList<Favourites> hotelListIds;
    ArrayList<String> hotelList,hotelListIdsNew;
    EditText et_fav_search;
    FirebaseDatabase database;
    TextView tv_fav_result,tv_zero_results;
    DatabaseReference ref,hotelRef;
    private FirebaseAuth mAuth;
    ArrayList<ApprovedModel> approvedModels;
//    private ArrayList<String> hotelListIds;
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
        mAuth = FirebaseAuth.getInstance();
        recyclerView_fav = (RecyclerView) view.findViewById(R.id.recyclerView_fav);
        et_fav_search = (EditText) view.findViewById(R.id.et_fav_search);
        tv_fav_result = (TextView) view.findViewById(R.id.tv_fav_result);
        tv_zero_results = (TextView) view.findViewById(R.id.tv_zero_results);
        hotelList = new ArrayList<>();
        hotelListIdsNew = new ArrayList<>();
        hotelListIds = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        ref = database.getReference().child("oxostayuser").child("favourites");
        hotelRef = database.getReference().child("oxostaypartner").child("hotelsapproved");
        approvedModels = new ArrayList<>();
        recyclerView_fav.setHasFixedSize(true);
        recyclerView_fav.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView_fav.setAdapter(favouriteAdapter);


        rl_Search = (RelativeLayout) view.findViewById(R.id.rl_Search);

//        et_fav_search.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
//
//                DatabaseReference dateRef = rootRef.child("oxostaypartner").child("hotelsapproved");
//                Query query = dateRef.orderByChild("hotel_name").startAt(charSequence.toString()).endAt(charSequence.toString() + "\uf8ff");
//                Log.e("checkQuery",">>" + dateRef);
//
//                query.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        approvedModels.clear();
//                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//
//                            ApprovedModel upload = postSnapshot.getValue(ApprovedModel.class);
//                            approvedModels.add(upload);
//                            Log.e("checkQuery","11>>" + postSnapshot.toString());
//                        }
//                        favouriteAdapter = new FavouriteAdapter(approvedModels,getActivity());
//
//                        Log.e("checkQuery","final>>" + approvedModels.toString());
//                        recyclerView_fav.setAdapter(favouriteAdapter);
//                        favouriteAdapter.notifyDataSetChanged();
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });

    }

    @Override
    public void onResume(){
        super.onResume();
        //OnResume Fragment
        baseActivity.showLoader(getActivity());
        hotelList.clear();
        approvedModels.clear();
        hotelListIds.clear();
        hotelListIdsNew.clear();
        getFirebaseKeys();
    }

    public void getFirebaseKeys(){
        try {
            Query query = ref.child(mAuth.getCurrentUser().getUid());

            Log.e("checkFLowISsue","00>>");
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Log.e("checkfavKey","00>>" + dataSnapshot.toString());
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                        Favourites favourites = dataSnapshot1.getValue(Favourites.class);
                        Log.e("checkfavKey","11>>" + favourites);
                        hotelListIds.add(favourites);
                    }
                    Log.e("checkfavKey","22>>" + hotelListIds.toString());

                    for (int i = 0;i<hotelListIds.size();i++){
                        Favourites favourites = hotelListIds.get(i);
                        hotelListIdsNew.add(favourites.getHotel_id());
                    }

                    Log.e("checkQuery","11Favourites>>" + hotelListIdsNew.toString());
                    if (hotelListIdsNew.size() == 0){
                        baseActivity.dismissLoader();
                        tv_zero_results.setVisibility(View.VISIBLE);
                    }else{
                        tv_zero_results.setVisibility(View.GONE);
                        getDataFirebase();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }catch (Exception e){

        }
    }

    public void getDataFirebase(){

        try {
            Log.e("checkforloop","00>>" + hotelListIdsNew);
            hotelRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try {

                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            Log.e("checkforloop","11>>" + postSnapshot.toString());
                            if (hotelListIdsNew.contains(postSnapshot.getKey())){

                                Log.e("checkforloop","22>>" + postSnapshot.getKey());
                                ApprovedModel upload = postSnapshot.getValue(ApprovedModel.class);
                                approvedModels.add(upload);
//                                approvedModels.add(upload);
                            }
                        }

                        favouriteAdapter = new FavouriteAdapter(hotelListIdsNew,approvedModels,getActivity());
                        tv_fav_result.setText("Showing " + approvedModels.size() + " results");
                        if (approvedModels.size() == 0){
                            tv_zero_results.setVisibility(View.VISIBLE);
                        }else{
                            tv_zero_results.setVisibility(View.GONE);
                        }
                        Log.e("checkQueryNew","final>>" + approvedModels.toString());
                        recyclerView_fav.setAdapter(favouriteAdapter);
                        favouriteAdapter.notifyDataSetChanged();
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
