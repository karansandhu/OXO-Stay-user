package com.user.oxostay.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

import com.user.oxostay.R;

import com.shreyaspatil.easyupipayment.EasyUpiPayment;
import com.shreyaspatil.easyupipayment.listener.PaymentStatusListener;
import com.shreyaspatil.easyupipayment.model.PaymentApp;
import com.shreyaspatil.easyupipayment.model.TransactionDetails;

public class PaymentActivity extends AppCompatActivity implements PaymentStatusListener {

    private ImageView imageView;

    private TextView statusView;

    private RelativeLayout payButton;

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
    String method_selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        initViews();

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pay(method_selected);
            }
        });
    }

    private void initViews() {
        imageView = findViewById(R.id.imageView);
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

        rl_cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                method_selected = "Cash";
                Log.e("checkOnClick","rl_cash>>" + radioAppChoice.getCheckedRadioButtonId());
            }
        });
        rl_gpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                method_selected = "UPI";
                Log.e("checkOnClick","rl_gpay>>" + radioAppChoice.getCheckedRadioButtonId());
            }
        });
        rl_paytm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                method_selected = "Paytm";
                Log.e("checkOnClick","rl_paytm>>" + radioAppChoice.getCheckedRadioButtonId());
            }
        });

        String transactionId = "OXO" + System.currentTimeMillis();
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

        PaymentApp paymentApp;

        switch (method_selected) {
            case "Cash":
                //Cash
                paymentApp = PaymentApp.ALL;
                break;
            case "UPI":
                paymentApp = PaymentApp.BHIM_UPI;
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


//    Uncomment this if you have inherited [android.app.Activity] and not [androidx.appcompat.app.AppCompatActivity]
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        easyUpiPayment.removePaymentStatusListener();
//    }
}