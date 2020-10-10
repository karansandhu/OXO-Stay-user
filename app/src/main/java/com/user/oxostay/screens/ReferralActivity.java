package com.user.oxostay.screens;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.user.oxostay.BuildConfig;
import com.user.oxostay.R;

public class ReferralActivity extends AppCompatActivity {

    RelativeLayout rl_refer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referral);

        initView();
    }
    public void initView(){
        rl_refer = (RelativeLayout) findViewById(R.id.rl_refer);

        rl_refer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedpreferences = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
                String ref_code = sharedpreferences.getString("ref_code","");
                Log.e("checkRefCode",">>" + ref_code);
//                Intent intent = new Intent(android.content.Intent.ACTION_SEND);
//                /*This will be the actual content you wish you share.*/
//                String shareBody = "Here is the share content body";
//                /*The type of the content is text, obviously.*/
//                intent.setType("text/plain");
//                /*Applying information Subject and Body.*/
////                intent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.share_subject));
//                intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
//                /*Fire!*/
//                startActivity(Intent.createChooser(intent, getString(R.string.share_using)));
            }
        });

    }
}