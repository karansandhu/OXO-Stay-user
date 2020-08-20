package com.user.oxostay.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.user.oxostay.R;

public class HomeFragment extends Fragment {

    RelativeLayout rl_Search;
    ImageView iv_latest_offer;
    EditText et_searchh;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rl_Search = (RelativeLayout) view.findViewById(R.id.rl_Search);
        et_searchh = (EditText) view.findViewById(R.id.et_searchh);
        iv_latest_offer = (ImageView) view.findViewById(R.id.iv_latest_offer);
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
    }
}
