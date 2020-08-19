package com.user.oxostay.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.user.oxostay.R;

public class SearchActivity extends AppCompatActivity {

    BottomSheetBehavior bottomSheetBehavior;
    TextView tv_another_day;
    RelativeLayout rl_search_now;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initView();
    }

    public void initView(){
        LinearLayout bottomSheetLayout = findViewById(R.id.bottom_sheet);
        tv_another_day = (TextView) findViewById(R.id.tv_another_day);
        rl_search_now = (RelativeLayout) findViewById(R.id.rl_search_now);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout);

        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
//                        textViewBottomSheetState.setText("STATE HIDDEN");
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
//                        textViewBottomSheetState.setText("STATE EXPANDED");
                        // update toggle button text
//                        toggleBottomSheet.setText("Expand BottomSheet");
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
//                        textViewBottomSheetState.setText("STATE COLLAPSED");
                        // update collapsed button text
//                        toggleBottomSheet.setText("Collapse BottomSheet");
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
//                        textViewBottomSheetState.setText("STATE DRAGGING");
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
//                        textViewBottomSheetState.setText("STATE SETTLING");
                        break;
                }
            }
            @Override public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });
        rl_search_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchActivity.this,HotelListActivity.class);
                startActivity(intent);
            }
        });
        tv_another_day.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                Log.e("checkBottonSheet", ": " + bottomSheetBehavior.getState());
//                if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//                    toggleBottomSheet.setText("Collapse BottomSheet");
//                } else {
//                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//                    toggleBottomSheet.setText("Expand BottomSheet");
//                }
            }
        });

    }

}