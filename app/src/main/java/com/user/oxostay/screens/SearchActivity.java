package com.user.oxostay.screens;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.philliphsu.bottomsheetpickers.time.BottomSheetTimePickerDialog;
import com.philliphsu.bottomsheetpickers.time.grid.GridTimePickerDialog;
import com.user.oxostay.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class SearchActivity extends AppCompatActivity implements BottomSheetTimePickerDialog.OnTimeSetListener {

    private static final String TAG = "SearchActivity";
    private TextView tv_another_day, tv_time, tv_today;
    private RelativeLayout rl_search_now, rl_time_picker;
    private ImageView iv_back, iv_edit;
    private EditText et_city_name;
    private String location, location_id, today_date, final_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initView();
    }

    public void initView() {
        Intent intent = getIntent();
        location = intent.getStringExtra("location");
        location_id = intent.getStringExtra("location_id");
        LinearLayout bottomSheetLayout = findViewById(R.id.bottom_sheet);
        et_city_name = (EditText) findViewById(R.id.et_city_name);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_edit = (ImageView) findViewById(R.id.iv_edit);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        tv_another_day = (TextView) findViewById(R.id.tv_another_day);
        tv_today = (TextView) findViewById(R.id.tv_today);
        tv_time = (TextView) findViewById(R.id.tv_time);
        rl_search_now = (RelativeLayout) findViewById(R.id.rl_search_now);
        et_city_name.setText(location);
        rl_time_picker = (RelativeLayout) findViewById(R.id.rl_time_picker);

        iv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(SearchActivity.this, LocationActivity.class);
                startActivity(intent1);
                finish();
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
                Intent intent = new Intent(SearchActivity.this, HotelListActivity.class);
                intent.putExtra("date", final_date);
                intent.putExtra("location", location);
                intent.putExtra("time", tv_time.getText().toString());
                startActivity(intent);
            }
        });

        final Calendar myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String month = myCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());

                tv_another_day.setText(month + " " + dayOfMonth + "," + " " + year);
//                final_date =
                Calendar calendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);
                calendar.getTimeInMillis();
                monthOfYear = monthOfYear + 1;
                String monthOfYearwithzero = String.valueOf(monthOfYear);
                String dayOfTheMonthwithzero = String.valueOf(dayOfMonth);
                if (String.valueOf(monthOfYear).length() == 1) {
                    monthOfYearwithzero = "0" + monthOfYear;
                }

                if(String.valueOf(dayOfMonth).length() == 1)
                {
                    dayOfTheMonthwithzero = "0" + dayOfMonth;
                }
                Log.e("SearchActivity", "onDateSet: " + year + "-" + monthOfYearwithzero + "-" + dayOfTheMonthwithzero);
                final_date = year + "-" + monthOfYearwithzero + "-" + dayOfTheMonthwithzero;

            }
        };

        tv_today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date today = new Date();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
                today_date = format.format(today);
                Log.e("checkTodaysDate", ">>" + today_date);
                final_date = today_date;

                if (tv_today.getBackground().equals(R.color.colorAppLightYellow)) {

                    tv_today.setBackgroundResource(R.color.colorWhite);
                    tv_another_day.setBackgroundResource(R.color.colorAppLightYellow);
                } else {
                    tv_today.setBackgroundResource(R.color.colorAppLightYellow);
                    tv_another_day.setBackgroundResource(R.color.colorWhite);
                }
            }
        });
        tv_another_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(SearchActivity.this, date2, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                if (tv_another_day.getBackground().equals(R.color.colorAppLightYellow)) {

                    tv_another_day.setBackgroundResource(R.color.colorWhite);
                    tv_today.setBackgroundResource(R.color.colorAppLightYellow);
                } else {
                    tv_another_day.setBackgroundResource(R.color.colorAppLightYellow);
                    tv_today.setBackgroundResource(R.color.colorWhite);
                }
            }
        });

    }


    @Override
    public void onTimeSet(ViewGroup viewGroup, int hourOfDay, int minute) {
        Calendar cal = new java.util.GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
        cal.set(Calendar.MINUTE, minute);
        tv_time.setText(DateFormat.getTimeFormat(this).format(cal.getTime()));
        Log.e(TAG, "onTimeSet: "+cal.getTime());
//        Toast.makeText(this, "Time set: " + DateFormat.getTimeFormat(this).format(cal.getTime()), Toast.LENGTH_SHORT).show();
    }
}