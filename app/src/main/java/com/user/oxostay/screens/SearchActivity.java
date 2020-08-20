package com.user.oxostay.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.philliphsu.bottomsheetpickers.time.BottomSheetTimePickerDialog;
import com.philliphsu.bottomsheetpickers.time.grid.GridTimePickerDialog;
import com.user.oxostay.R;

import java.util.Calendar;

public class SearchActivity extends AppCompatActivity implements BottomSheetTimePickerDialog.OnTimeSetListener{

    BottomSheetBehavior bottomSheetBehavior;
    TextView tv_another_day,tv_time;
    RelativeLayout rl_search_now,rl_time_picker;
    ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initView();
    }

    public void initView(){
        LinearLayout bottomSheetLayout = findViewById(R.id.bottom_sheet);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        tv_another_day = (TextView) findViewById(R.id.tv_another_day);
        tv_time = (TextView) findViewById(R.id.tv_time);
        rl_search_now = (RelativeLayout) findViewById(R.id.rl_search_now);
        rl_time_picker = (RelativeLayout) findViewById(R.id.rl_time_picker);
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
        rl_time_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                GridTimePickerDialog grid = new GridTimePickerDialog.Builder(
                        SearchActivity.this,
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        DateFormat.is24HourFormat(SearchActivity.this))
                        /* ... Set additional options ... */
                        .build();
                        grid.show(getSupportFragmentManager(), "TAG");
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

    @Override
    public void onTimeSet(ViewGroup viewGroup, int hourOfDay, int minute) {
        Calendar cal = new java.util.GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
        cal.set(Calendar.MINUTE, minute);
        tv_time.setText(DateFormat.getTimeFormat(this).format(cal.getTime()));
//        Toast.makeText(this, "Time set: " + DateFormat.getTimeFormat(this).format(cal.getTime()), Toast.LENGTH_SHORT).show();
    }
}