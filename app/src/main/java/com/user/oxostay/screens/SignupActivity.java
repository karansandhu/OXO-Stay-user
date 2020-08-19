package com.user.oxostay.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.user.oxostay.R;

public class SignupActivity extends AppCompatActivity {

    RelativeLayout rl_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initView();
    }

    public void initView(){
        rl_signup = (RelativeLayout) findViewById(R.id.rl_signup);
        rl_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SignupActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

}