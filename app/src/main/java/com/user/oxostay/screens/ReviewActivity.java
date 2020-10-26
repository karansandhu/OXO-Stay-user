package com.user.oxostay.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.user.oxostay.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class ReviewActivity extends AppCompatActivity {

    RelativeLayout rl_continue_booking;
    TextView tv_hotel_name,tv_day_date,tv_time_chosen,tv_user_name,tv_user_no,tv_user_email,tv_final_amt,tv_no_rooms,tv_no_users;
    EditText et_promo_code;
    CheckBox checkbox;
    RelativeLayout rl_add_rooms;
    Button btn_edit_rooms,btn_minus_rooms,btn_add_rooms;
    ImageView iv_back;
    int hotel_room_no = 1;
    int hotel_room_rate = 0;
    int new_rate = 0;
    private String final_date;
    private String selected_time,hotel_email,hotel_name,check_in_time,check_in_date,hotel_address,hotel_phnno,hotel_id,hotel_room,hotel_rate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        initView();
    }

    public void initView(){
        SharedPreferences sharedpreferences = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        rl_add_rooms = (RelativeLayout) findViewById(R.id.rl_add_rooms);
        rl_continue_booking = (RelativeLayout) findViewById(R.id.rl_continue_booking);
        btn_edit_rooms = (Button) findViewById(R.id.btn_edit_rooms);
        btn_add_rooms = (Button) findViewById(R.id.btn_add_rooms);
        btn_minus_rooms = (Button) findViewById(R.id.btn_minus_rooms);
        tv_no_users = (TextView) findViewById(R.id.tv_no_users);
        tv_no_rooms = (TextView) findViewById(R.id.tv_no_rooms);
        tv_hotel_name = (TextView) findViewById(R.id.tv_hotel_name);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_day_date = (TextView) findViewById(R.id.tv_day_date);
        tv_time_chosen = (TextView) findViewById(R.id.tv_time_chosen);
        tv_user_name = (TextView) findViewById(R.id.tv_user_name);
        tv_user_no = (TextView) findViewById(R.id.tv_user_no);
        tv_user_email = (TextView) findViewById(R.id.tv_user_email);
        tv_final_amt = (TextView) findViewById(R.id.tv_final_amt);
        et_promo_code = (EditText) findViewById(R.id.et_promo_code);
        checkbox = (CheckBox) findViewById(R.id.checkbox);

        final Calendar myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String month = myCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());

                tv_day_date.setText(month + " " + dayOfMonth + "," + " " + year);
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

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        tv_day_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(ReviewActivity.this, date2, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        Intent intent = getIntent();
        selected_time = intent.getStringExtra("time");
        hotel_name = intent.getStringExtra("hotel_name");
        check_in_time = intent.getStringExtra("check_in_time");
        check_in_date = intent.getStringExtra("check_in_date");
        hotel_address = intent.getStringExtra("hotel_address");
        hotel_email = intent.getStringExtra("hotel_email");
        hotel_phnno = intent.getStringExtra("hotel_phnno");
        hotel_id = intent.getStringExtra("hotel_id");
        hotel_room = intent.getStringExtra("hotel_room");
        hotel_rate = intent.getStringExtra("hotel_rate");
        Log.e("checkcheck_in_date",">>" + check_in_date);
//        new_rate =
        btn_edit_rooms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rl_add_rooms.isShown()){
                    rl_add_rooms.setVisibility(View.GONE);

                }else{
                    rl_add_rooms.setVisibility(View.VISIBLE);

                }
            }
        });

        btn_add_rooms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rl_add_rooms.isShown()){
                    hotel_room_no++;
                    hotel_room_rate = Integer.parseInt(hotel_rate);

                    new_rate = hotel_room_rate + Integer.parseInt(tv_final_amt.getText().toString());
                    tv_no_rooms.setText(String.valueOf(hotel_room_no));
                    tv_final_amt.setText(String.valueOf(new_rate));
                    tv_no_users.setText("Total Rooms - " + hotel_room_no);
                    Log.e("checkFinalAmount",">>" + String.valueOf(new_rate));
                }

            }
        });

        btn_minus_rooms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rl_add_rooms.isShown()){
                    if (hotel_room_no != 0){
                        hotel_room_no--;
                        hotel_room_rate = Integer.parseInt(hotel_rate);

                        tv_no_rooms.setText(String.valueOf(hotel_room_no));
                        new_rate = hotel_room_rate - Integer.parseInt(tv_final_amt.getText().toString());
                        if (new_rate != 0){
                        tv_no_rooms.setText(String.valueOf(hotel_room_no));
                        tv_final_amt.setText(String.valueOf(new_rate));
                        }
                        Log.e("checkFinalAmount",">>" + String.valueOf(new_rate));
                    }
                }

            }
        });

        String username = sharedpreferences.getString("user_name","");
        String user_no = sharedpreferences.getString("user_no","");
        tv_user_name.setText(username);
        tv_user_no.setText(user_no);
        tv_hotel_name.setText(hotel_name);

//        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy  hh:mm a");
//        String date = format.format(Date.parse(check_in_date));
        try {
            tv_day_date.setText(parseDateToddMMyyyy(check_in_date));// see again
            tv_time_chosen.setText(check_in_time);
            tv_final_amt.setText(hotel_rate);
            tv_user_email.setText(hotel_email);
            et_promo_code.setText("");

        }catch (Exception e){

        }

        Log.e("checkFinalData","review>>" + selected_time + ">" + hotel_name + ">" + check_in_time + ">" + check_in_date + ">"
                + hotel_address + ">" + hotel_email + ">" + hotel_phnno + ">" + hotel_id + ">" + hotel_room);


        rl_continue_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                hotel_room = String.valueOf(hotel_room_no);

                Intent intent = new Intent(ReviewActivity.this,PaymentActivity.class);
                intent.putExtra("time",selected_time);
                intent.putExtra("hotel_name",hotel_name);
                intent.putExtra("check_in_time",check_in_time);
                intent.putExtra("check_in_date",check_in_date);
                intent.putExtra("hotel_address",hotel_address);
                intent.putExtra("hotel_email",hotel_email);
                intent.putExtra("hotel_phnno",hotel_phnno);
                intent.putExtra("hotel_id",hotel_id);
                intent.putExtra("hotel_room",hotel_room);
                startActivity(intent);
            }
        });

    }
    public String parseDateToddMMyyyy(String time) {
        String inputPattern = "yyyy-MM-dd";
        String outputPattern = "dd-MMM-yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }
}