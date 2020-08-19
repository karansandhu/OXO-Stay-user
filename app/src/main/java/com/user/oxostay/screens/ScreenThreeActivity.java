package com.user.oxostay.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.user.oxostay.R;

public class ScreenThreeActivity extends AppCompatActivity {

    ImageView iv_skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_three);

        initView();
    }

    public void initView(){
        iv_skip = (ImageView) findViewById(R.id.iv_skip);
        iv_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScreenThreeActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}