package com.user.oxostay.screens;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.user.oxostay.R;

import com.shreyaspatil.easyupipayment.EasyUpiPayment;
import com.shreyaspatil.easyupipayment.listener.PaymentStatusListener;
import com.shreyaspatil.easyupipayment.model.PaymentApp;
import com.shreyaspatil.easyupipayment.model.TransactionDetails;
import com.user.oxostay.models.Booking;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class PaymentActivity extends AppCompatActivity implements PaymentStatusListener {

    private ImageView imageView,iv_back;

    private TextView statusView;

    private RelativeLayout payButton;
    private String transactionId;
    private RadioGroup radioAppChoice;
    private RadioButton app_default,app_google_pay,app_paytm;

    private EditText fieldPayeeVpa;
    private EditText fieldPayeeName;
    private EditText fieldTransactionId;
    private EditText fieldTransactionRefId;
    private EditText fieldDescription;
    private EditText fieldAmount;
    private RelativeLayout rl_cash,rl_gpay,rl_paytm;

    private EasyUpiPayment easyUpiPayment;
    private String method_selected;

    private FirebaseDatabase database;
    private DatabaseReference ref,refPartner;
    private FirebaseAuth mAuth;
    String check_out_time;
    String selected_time,hotel_email,hotel_name,check_in_time,check_in_date,hotel_address,hotel_phnno,hotel_id,hotel_room;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        initViews();

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                pay(method_selected);
//                MakeABooking();
            }
        });
    }

    private void initViews() {
        database = FirebaseDatabase.getInstance();
        ref = database.getReference().child("bookings");
        refPartner = database.getReference().child("bookingsPartner");
        mAuth = FirebaseAuth.getInstance();

        imageView = findViewById(R.id.imageView);
        iv_back = findViewById(R.id.iv_back);
        statusView = findViewById(R.id.textView_status);
        payButton = findViewById(R.id.rl_continue_booking);

        fieldPayeeVpa = findViewById(R.id.field_vpa);
        app_default = findViewById(R.id.app_default);
        app_google_pay = findViewById(R.id.app_google_pay);
        app_paytm = findViewById(R.id.app_paytm);
        rl_cash = findViewById(R.id.rl_cash);
        rl_gpay = findViewById(R.id.rl_gpay);
        rl_paytm = findViewById(R.id.rl_paytm);
        fieldPayeeName = findViewById(R.id.field_name);
        fieldTransactionId = findViewById(R.id.field_transaction_id);
        fieldTransactionRefId = findViewById(R.id.field_transaction_ref_id);
        fieldDescription = findViewById(R.id.field_description);
        fieldAmount = findViewById(R.id.field_amount);
        radioAppChoice = findViewById(R.id.radioAppChoice);

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
        Log.e("checkFinalData",">>" + selected_time + ">" + hotel_name + ">" + check_in_time + ">" + check_in_date + ">"
                + hotel_address + ">" + hotel_email + ">" + hotel_phnno + ">" + hotel_id + ">" + hotel_room);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        rl_cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                method_selected = "Cash";
                pay(method_selected);
                Log.e("checkOnClick","rl_cash>>" + radioAppChoice.getCheckedRadioButtonId());
            }
        });
        rl_gpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                method_selected = "UPI";
                pay(method_selected);
                Log.e("checkOnClick","rl_gpay>>" + radioAppChoice.getCheckedRadioButtonId());
            }
        });
        rl_paytm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                method_selected = "Paytm";
                pay(method_selected);
                Log.e("checkOnClick","rl_paytm>>" + radioAppChoice.getCheckedRadioButtonId());
            }
        });

        transactionId = "OXO" + System.currentTimeMillis();
        Log.e("checkTID",">>" + transactionId);
        fieldTransactionId.setText(transactionId);
        fieldTransactionRefId.setText(transactionId);

    }

    private void pay(String method_selected) {
        String payeeVpa = fieldPayeeVpa.getText().toString();
        String payeeName = fieldPayeeName.getText().toString();
        String transactionId = fieldTransactionId.getText().toString();
        String transactionRefId = fieldTransactionRefId.getText().toString();
        String description = fieldDescription.getText().toString();
        String amount = fieldAmount.getText().toString();
        RadioButton paymentAppChoice = findViewById(radioAppChoice.getCheckedRadioButtonId());
        MakeABooking();
        PaymentApp paymentApp;

        switch (method_selected) {
            case "Cash":
                //Cash
                paymentApp = PaymentApp.ALL;
//                MakeABooking();
                break;
            case "UPI":
                paymentApp = PaymentApp.BHIM_UPI;
//                MakeABooking();
                break;
            case "Paytm":
                paymentApp = PaymentApp.PAYTM;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + paymentAppChoice.getId());
        }
//        switch (paymentAppChoice.getId()) {
//            case R.id.app_default:
//                //Cash
//                paymentApp = PaymentApp.ALL;
//                break;
//            case R.id.app_bhim_upi:
//                paymentApp = PaymentApp.BHIM_UPI;
//                break;
//            case R.id.app_google_pay:
//                paymentApp = PaymentApp.GOOGLE_PAY;
//                break;
//            case R.id.app_paytm:
//                paymentApp = PaymentApp.PAYTM;
//                break;
//            default:
//                throw new IllegalStateException("Unexpected value: " + paymentAppChoice.getId());
//        }


        // START PAYMENT INITIALIZATION
        EasyUpiPayment.Builder builder = new EasyUpiPayment.Builder(this)
                .with(paymentApp)
                .setPayeeVpa("payeeVpa")
                .setPayeeName("payeeName")
                .setTransactionId(transactionId)
                .setTransactionRefId(transactionRefId)
                .setDescription("Hotel Booking")
                .setAmount("1");
        // END INITIALIZATION

        try {
            // Build instance
            easyUpiPayment = builder.build();

            // Register Listener for Events
            easyUpiPayment.setPaymentStatusListener(this);

            // Start payment / transaction
            easyUpiPayment.startPayment();
        } catch (Exception exception) {
            exception.printStackTrace();
            toast("Error: " + exception.getMessage());
        }
    }

    @Override
    public void onTransactionCompleted(TransactionDetails transactionDetails) {
        // Transaction Completed
        Log.e("TransactionDetails", transactionDetails.toString());
        statusView.setText(transactionDetails.toString());

        switch (transactionDetails.getTransactionStatus()) {
            case SUCCESS:
                onTransactionSuccess();
                break;
            case FAILURE:
                onTransactionFailed();
                break;
            case SUBMITTED:
                onTransactionSubmitted();
                break;
        }
    }

    @Override
    public void onTransactionCancelled() {
        // Payment Cancelled by User
        toast("Cancelled by user");
        imageView.setImageResource(R.drawable.black_dot);
    }

    private void onTransactionSuccess() {
        // Payment Success
        toast("Success");
        imageView.setImageResource(R.drawable.black_dot);
    }

    private void onTransactionSubmitted() {
        // Payment Pending
        toast("Pending | Submitted");
        imageView.setImageResource(R.drawable.black_dot);
    }

    private void onTransactionFailed() {
        // Payment Failed
        toast("Failed");
        imageView.setImageResource(R.drawable.black_dot);
    }

    private void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public String addTime(int hour, int minute, int minutesToAdd) {
        Calendar calendar = new GregorianCalendar(1990, 1, 1, hour, minute);
        calendar.add(Calendar.MINUTE, minutesToAdd);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String date = sdf.format(calendar.getTime());
        return date;
    }

    public void ShowAlertDialog(){
        new AlertDialog.Builder(PaymentActivity.this)
                .setTitle("Success")
                .setMessage("Congrats. Booking has been made!")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(PaymentActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
//                .setNegativeButton(android.R.string.no, null)
//                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void MakeABooking(){
        SharedPreferences sharedpreferences = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);

        try {

            String[] timee = check_in_time.split(":");
            String[] ti = timee[1].split(" ");
            Log.e("checkLogTime",">>" + addTime(Integer.parseInt(timee[0]),Integer.parseInt(ti[0]),180));
            Log.e("checkLogTime",">2>" + selected_time);

            if (selected_time.equals("3 Hours")){

                check_out_time = addTime(Integer.parseInt(timee[0]),Integer.parseInt(ti[0]),180);
                Log.e("checkFinaltime",">>" + check_out_time);
            }
            if (selected_time.equals("6 Hours")){

                check_out_time = addTime(Integer.parseInt(timee[0]),Integer.parseInt(ti[0]),360);
                Log.e("checkFinaltime",">>" + check_out_time);
            }
            if (selected_time.equals("12 Hours")){

                check_out_time = addTime(Integer.parseInt(timee[0]),Integer.parseInt(ti[0]),720);
                Log.e("checkFinaltime",">>" + check_out_time);
            }

        }catch (Exception e){

        }
        String username = sharedpreferences.getString("user_name","");
        String user_no = sharedpreferences.getString("user_no","");
        final Booking booking = new Booking();
        booking.setHotel_name(hotel_name);
        booking.setCheck_in_date(check_in_date);
        booking.setCheck_in_time(selected_time);
        booking.setHotel_address(hotel_address);
        booking.setFcm_token("fcm token");
        booking.setHotel_email(hotel_email);
        booking.setHotel_phnno(hotel_phnno);
        booking.setHotel_id(hotel_id);
        booking.setUser_id(mAuth.getCurrentUser().getUid());
        booking.setSelected_room(hotel_room);
        booking.setTransaction_id(transactionId);
        booking.setPayment_type(method_selected);
        booking.setRooms_booked("1");
        booking.setUsername(username);
        booking.setBooking_Status("1");
        booking.setUser_phnno(user_no);
        booking.setTotal_amount("500");
        booking.setCheck_out_time(check_out_time);

        Log.e("checkFinalBooking",">>" + check_out_time);

        ref.child(mAuth.getCurrentUser().getUid()).push().setValue(booking);
//        refPartner.child(hotel_id).push().setValue(booking);
        ShowAlertDialog();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                refPartner.child(hotel_id).push().setValue(booking);
            }
        }, 500);

    }

//    Uncomment this if you have inherited [android.app.Activity] and not [androidx.appcompat.app.AppCompatActivity]
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        easyUpiPayment.removePaymentStatusListener();
//    }
}