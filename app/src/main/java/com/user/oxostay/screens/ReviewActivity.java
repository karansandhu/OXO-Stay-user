package com.user.oxostay.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.user.oxostay.R;

public class ReviewActivity extends AppCompatActivity {

    RelativeLayout rl_continue_booking;
    TextView tv_hotel_name,tv_day_date,tv_time_chosen,tv_user_name,tv_user_no,tv_user_email,tv_final_amt;
    EditText et_promo_code;
    CheckBox checkbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        initView();
    }

    public void initView(){
        SharedPreferences sharedpreferences = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        rl_continue_booking = (RelativeLayout) findViewById(R.id.rl_continue_booking);
        tv_hotel_name = (TextView) findViewById(R.id.tv_hotel_name);
        tv_day_date = (TextView) findViewById(R.id.tv_day_date);
        tv_time_chosen = (TextView) findViewById(R.id.tv_time_chosen);
        tv_user_name = (TextView) findViewById(R.id.tv_user_name);
        tv_user_no = (TextView) findViewById(R.id.tv_user_no);
        tv_user_email = (TextView) findViewById(R.id.tv_user_email);
        tv_final_amt = (TextView) findViewById(R.id.tv_final_amt);
        et_promo_code = (EditText) findViewById(R.id.et_promo_code);
        checkbox = (CheckBox) findViewById(R.id.checkbox);

        String username = sharedpreferences.getString("user_name","");
        String user_no = sharedpreferences.getString("user_no","");

        tv_user_name.setText(username);
        tv_user_no.setText(user_no);

        rl_continue_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ReviewActivity.this,PaymentActivity.class);
                startActivity(intent);
            }
        });

    }

}