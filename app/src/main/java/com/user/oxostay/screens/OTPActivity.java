package com.user.oxostay.screens;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.user.oxostay.R;
import com.user.oxostay.adapter.LocationAdapter;
import com.user.oxostay.models.Location;
import com.user.oxostay.models.User;

import java.util.ArrayList;

public class OTPActivity extends AppCompatActivity {

    String mobile_no,mVerificationId,code,name,ref_code,user_ref_code,email;
    private FirebaseAuth mAuth;
    String act;
    FirebaseDatabase database;
    RelativeLayout rl_login;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        ref = database.getReference().child("oxostayuser").child("users");
        rl_login = (RelativeLayout) findViewById(R.id.rl_login);
        Intent intent = getIntent();
//        if (!intent.getStringExtra("user_ref_code").equals("")){
//            user_ref_code = intent.getStringExtra("user_ref_code");
//        }
        act = intent.getStringExtra("act");
        if (act.equals("signup")){
            Log.e("checkOTPAct","signup>>");
            ref_code = intent.getStringExtra("ref_code");
            mobile_no = intent.getStringExtra("mobile");
            mVerificationId = intent.getStringExtra("mVerificationId");
            code = intent.getStringExtra("code");
            name = intent.getStringExtra("name");
            user_ref_code = intent.getStringExtra("user_ref_code");
            email = intent.getStringExtra("email");

            SharedPreferences sharedpreferences = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            // TODO: Save username, refcode and user_no somewhere else as well in sharedprefereces rather than this only activity
            editor.putString("ref_code", ref_code);
            editor.putString("user_name", name);
            editor.putString("user_no", mobile_no);
            editor.putString("user_email", email);
            editor.commit();

            verifyVerificationCode(code);
        }else if (act.equals("login")){

            Log.e("checkOTPAct","login>>");
            mobile_no = intent.getStringExtra("mobile");
            mVerificationId = intent.getStringExtra("mVerificationId");
            code = intent.getStringExtra("code");
            verifyVerificationCode(code);
        }
        rl_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
//        verifyVerificationCode(code);
    }


//    public void checkFirebaseUser() {
//
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
//                        verifyVerificationCode(code);
//                    }else{
//                        Toast.makeText(OTPActivity.this, "Account doesn't exists please Signup first", Toast.LENGTH_LONG).show();
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//    }

    private void verifyVerificationCode(String code) {
        //creating the credential
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);

        //signing the user
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(OTPActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //verification successful we will start the profile activity

                            if (act.equals("login")){
                                Intent intent = new Intent(OTPActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.putExtra("status","ok");
                                startActivity(intent);

                            }else{
                                User user = new User();
                                user.setName(name);
                                user.setMobile_no(mobile_no);
                                user.setProfile_pic("");
                                user.setEmail(email);
                                user.setRef_code(ref_code);
                                Log.e("checkSignupData",">>" + user);
                                SharedPreferences sharedpreferences = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString("ref_code", ref_code);
                                editor.putString("user_name", name);
                                editor.putString("user_no", mobile_no);
                                editor.putString("user_email", email);
                                editor.commit();
                                ref.child(mAuth.getCurrentUser().getUid()).setValue(user);

                                Intent intent = new Intent(OTPActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.putExtra("status","ok");
                                startActivity(intent);

                            }

                        } else {

                            //verification unsuccessful.. display an error message

                            String message = "Somthing is wrong, we will fix it soon...";

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                message = "Invalid code entered...";
                            }

                            Snackbar snackbar = Snackbar.make(findViewById(R.id.parent), message, Snackbar.LENGTH_LONG);
                            snackbar.setAction("Dismiss", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });
                            snackbar.show();
                        }
                    }
                });
    }

}