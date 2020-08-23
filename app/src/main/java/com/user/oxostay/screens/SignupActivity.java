package com.user.oxostay.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.user.oxostay.R;
import com.user.oxostay.common.BaseActivity;

import java.util.concurrent.TimeUnit;

public class SignupActivity extends AppCompatActivity {

    RelativeLayout rl_signup;
    BaseActivity baseActivity;
    private FirebaseAuth mAuth;
    String mVerificationId;
    String status;
    EditText et_mobile,et_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initView();
    }

    public void initView(){
        baseActivity = new BaseActivity();
//        baseActivity.dismissLoader();
        rl_signup = (RelativeLayout) findViewById(R.id.rl_signup);
        et_mobile = (EditText) findViewById(R.id.et_mobile);
        et_name = (EditText) findViewById(R.id.et_name);

        mAuth = FirebaseAuth.getInstance();
        Intent intent = getIntent();
        status = intent.getStringExtra("status");
        if (status!=null){
            if (status.equals("ok")){
                Log.e("checkStatus","IF>>" + status);
            }else{
                Log.e("checkStatus","ELSE>>" + status);
            }

        }

        rl_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                baseActivity.showLoader(SignupActivity.this);
                Log.e("checkOTPFlow","onClick>>" + et_mobile.getText().toString());
                sendVerificationCode(et_mobile.getText().toString());
            }
        });
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
                Intent intent = new Intent(SignupActivity.this,OTPActivity.class);
                intent.putExtra("mobile",et_mobile.getText().toString());
                intent.putExtra("mVerificationId",mVerificationId);
                intent.putExtra("code",code);
                intent.putExtra("name",et_name.getText().toString());
                intent.putExtra("act","signup");
                startActivity(intent);
//                editTextCode.setText(code);
                //verifying the code
//                verifyVerificationCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Log.e("checkOTPFlow","onVerificationFailed>>" +  e.getMessage());
            Toast.makeText(SignupActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
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