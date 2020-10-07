package com.user.oxostay.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.user.oxostay.R;
import com.user.oxostay.adapter.LocationAdapter;
import com.user.oxostay.common.BaseActivity;
import com.user.oxostay.models.Location;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

public class LoginActivity extends AppCompatActivity {

    RelativeLayout rl_login;
    TextView tv_password,tv_login_btn,rl_login_signup;
    RelativeLayout rl_pass;
    EditText et_login_no;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    ArrayList<String> mobileList;
    DatabaseReference ref;
    String mVerificationId;
    String status;
    BaseActivity baseActivity;
    String mobile_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    public void initView(){
        database = FirebaseDatabase.getInstance();
        ref = database.getReference().child("oxostayuser").child("users");
        baseActivity = new BaseActivity();
        mobileList = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        try {
            baseActivity.dismissLoader();
        }catch (Exception e){

        }
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if (user != null) {
//            // User is signed in
//            Intent i = new Intent(LoginActivity.this, MainActivity.class);
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(i);
//        } else {
//            // User is signed out
//            Log.e("LOGIN", "onAuthStateChanged:signed_out");
//        }

        Intent intent = getIntent();
            status = intent.getStringExtra("status");
        if (status!=null){
            if (status.equals("ok")){
                Log.e("checkStatus","IF>>" + status);
            }else{
                Log.e("checkStatus","ELSE>>" + status);
            }

        }
        et_login_no = (EditText) findViewById(R.id.et_login_no);
        rl_login = (RelativeLayout) findViewById(R.id.rl_login);
        rl_login_signup = (TextView) findViewById(R.id.rl_login_signup);
        rl_pass = (RelativeLayout) findViewById(R.id.rl_pass);
        tv_password = (TextView) findViewById(R.id.tv_password);
        tv_login_btn = (TextView) findViewById(R.id.tv_login_btn);

        tv_login_btn.setText(R.string.send_otp);

        rl_pass.setVisibility(View.GONE);
        tv_password.setVisibility(View.GONE);

        rl_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                baseActivity.showLoader(LoginActivity.this);
                Log.e("checkOTPFlow","onClick>>" + et_login_no.getText().toString());

//                ref.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
////                    approvedModelsIds.add(dataSnapshot1.getKey());
//                            mobile_no = dataSnapshot1.child("mobile_no").getValue(String.class);
//                            mobileList.add(mobile_no);
//                            if (et_login_no.getText().toString().equals(mobile_no)){
////                                sendVerificationCode(et_login_no.getText().toString());
//                            }else{
//                                baseActivity.dismissLoader();
//                                Toast.makeText(LoginActivity.this, "Please register first", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//
//                        if (mobileList.contains(mobile_no)){
//
//                            Log.e("checkKeyNames","IF>>" + mobile_no);
//                        }
//                        mobile_no = "";
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });


//                if (et_login_no.getText().toString())

                sendVerificationCode(et_login_no.getText().toString());

            }
        });
        rl_login_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                baseActivity.showLoader(LoginActivity.this);
                Intent intent = new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(intent);

//                Log.e("checkOTPFlow","onClick>>" + et_login_no.getText().toString());
//                sendVerificationCode(et_login_no.getText().toString());

            }
        });
    }

    public void getDataFirebase(){


    }

    private void sendVerificationCode(String mobile) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + mobile,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);
        Log.e("checkOTPFlow","sendVerificationCode>>" + mobile);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            //Getting the code sent by SMS
            String code = phoneAuthCredential.getSmsCode();

            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            if (code != null) {
                baseActivity.dismissLoader();
                Log.e("checkOTPFlow","onVerificationCompleted>>" + code);
                Intent intent = new Intent(LoginActivity.this,OTPActivity.class);
                intent.putExtra("mobile",et_login_no.getText().toString());
                intent.putExtra("mVerificationId",mVerificationId);
                intent.putExtra("code",code);
                intent.putExtra("act","login");
                startActivity(intent);
//                editTextCode.setText(code);
                //verifying the code
//                verifyVerificationCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Log.e("checkOTPFlow","onVerificationFailed>>" +  e.getMessage());
            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            baseActivity.dismissLoader();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            Log.e("checkOTPFlow","onCodeSent>>" +  mVerificationId);
            //storing the verification id that is sent to the user
            mVerificationId = s;
        }
    };


}