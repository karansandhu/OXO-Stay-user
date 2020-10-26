package com.user.oxostay.screens;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.user.oxostay.R;
import com.user.oxostay.adapter.LocationAdapter;
import com.user.oxostay.common.BaseActivity;
import com.user.oxostay.models.Location;
import com.user.oxostay.models.User;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

public class LoginActivity extends AppCompatActivity {

    RelativeLayout rl_login;
    TextView tv_password,tv_login_btn,rl_login_signup,rl_login_forgot_pass;
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
    ArrayList<User> userList;
    ArrayList<String> userNoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    public void initView(){
        userList= new ArrayList<>();
        userNoList= new ArrayList<>();
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
        rl_login_forgot_pass = (TextView) findViewById(R.id.rl_login_forgot_pass);

        tv_login_btn.setText(R.string.send_otp);

        rl_pass.setVisibility(View.GONE);
        tv_password.setVisibility(View.GONE);

        rl_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                baseActivity.showLoader(LoginActivity.this);
                Log.e("checkOTPFlow","onClick>>" + et_login_no.getText().toString());
                checkFirebaseUser();


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
        rl_login_forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                baseActivity.showLoader(LoginActivity.this);
                Intent intent = new Intent(LoginActivity.this,ForgotActivity.class);
                startActivity(intent);


            }
        });
    }

    public void checkFirebaseUser() {

//        btLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.show();
//                phNumberstr = etPhNumber.getText().toString().trim();
//                passwordstr = etPassword.getText().toString().trim();
//
//                if (!TextUtils.isEmpty(phNumberstr) && !TextUtils.isEmpty(passwordstr)) {
//                    dbRefLogin.orderByChild("username").equalTo(phNumberstr).addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            if (dataSnapshot.hasChildren()) {
//                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                                    LoginDto loginDto = dataSnapshot1.getValue(LoginDto.class);
//                                    if (loginDto.getPassword().equals(passwordstr)) {
//                                        editor.putBoolean(Constants.LOGGED_IN, true);
//                                        editor.putString(Constants.USER_KEY, dataSnapshot1.getKey());
//                                        editor.apply();
//                                        Intent n = new Intent(LoginActivity.this, NavHomeActivity.class);
//                                        startActivity(n);
//                                        finish();
//                                    } else {
//                                        makeToast("Password is wrong");
//
//                                    }
//
//                                }
//
//
//                            } else {
//                                makeToast("No Data found from this phone number");
//
//                            }
//
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });
//                } else {
//                    makeToast("Please fill both the fields to login");
//                }
//                dialog.dismiss();
//
//            }
//        });

        Log.e("checkOTPFlow","onDataChange>00>" + et_login_no.getText().toString());
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.e("checkOTPFlow","onDataChange>11>" + dataSnapshot.toString());
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Log.e("checkOTPFlow", "onDataChange>2>" + dataSnapshot1.toString());
                    String loginDto = dataSnapshot1.getValue(String.class);
                    userNoList.add(loginDto);
                    Log.e("checkOTPFlow", "loginDto>2>" + loginDto);
//                    if (loginDto.equals(et_login_no.getText().toString())) {
//                        Log.e("checkLatestFlow", "if>equals>");
//                        sendVerificationCode(et_login_no.getText().toString());
//                    } else {
//                        Log.e("checkLatestFlow", "else>>");
//                        baseActivity.dismissLoader();
//                        if (!((Activity) LoginActivity.this).isFinishing()) {
//                            ShowAlertDialog();
//
//                            return;
//                        }

//                    }
                }
                if (userNoList.contains(et_login_no.getText().toString())){

                    Log.e("checkLatestFlow", "if>equals>");
                    sendVerificationCode(et_login_no.getText().toString());
                }else{

                    Log.e("checkLatestFlow", "else>>");
                    baseActivity.dismissLoader();
                    if (!((Activity) LoginActivity.this).isFinishing()) {
                        ShowAlertDialog();

                        return;
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Log.e("checkKeyUsers", "00>>" + dataSnapshot.toString());
//                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                    Log.e("checkKeyUsers", "11>>" + dataSnapshot1.toString());
//                    User approvedModel = dataSnapshot1.getValue(User.class);
//                    userList.add(approvedModel);
//                }
//                Log.e("checkKeyUsers", ">>" + userList.toString());
//
//                for (int i = 0; i < userList.size(); i++) {
//                    Log.e("checkKeyUsers","for loop>>" + userList.get(i).getMobile_no());
//                    if (userList.get(i).getMobile_no().equals(mobile_no)){
//                        sendVerificationCode(et_login_no.getText().toString());
//
//                    }else{
//                        baseActivity.dismissLoader();
//                        if(!((Activity) LoginActivity.this).isFinishing())
//                        {
//                            ShowAlertDialog();
//
//                            return;
//                        }
////                        Toast.makeText(LoginActivity.this, "Account doesn't exists please Signup first", Toast.LENGTH_LONG).show();
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

    }


//            Log.e("checkOTPFlow","onDataChange>00>" + et_login_no.getText().toString());
//        ref.orderByChild("mobile_no").equalTo(et_login_no.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
//        @Override
//        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//            Log.e("checkOTPFlow","onDataChange>444>" + dataSnapshot.toString());
//            if (dataSnapshot.hasChildren()) {
//                Log.e("checkOTPFlow","onDataChange>>" + dataSnapshot.toString());
//                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                    Log.e("checkOTPFlow","onDataChange>2>" + dataSnapshot1.toString());
//                    User loginDto = dataSnapshot1.getValue(User.class);
//                    if (loginDto.getMobile_no().equals(et_login_no.getText().toString())) {
//                        Log.e("checkLatestFlow","if>equals>");
//                        sendVerificationCode(et_login_no.getText().toString());
//                    } else {
//                        Log.e("checkLatestFlow","else>>");
//                        baseActivity.dismissLoader();
//                        if(!((Activity) LoginActivity.this).isFinishing())
//                        {
//                            ShowAlertDialog();
//
//                            return;
//                        }
//
//                    }
////                            makeToast("Password is wrong");
//
//                }
//
//            }
//
//        }
//
//        @Override
//        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            Log.e("checkOTPFlow","onCancelled>1>" + databaseError.toString());
//        }
//    });



    public void ShowAlertDialog(){
        new AlertDialog.Builder(LoginActivity.this)
                .setTitle("Login Alert")
                .setMessage("Account doesn't exists please Signup first. Do you want to Signup now?")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(LoginActivity.this,SignupActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
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