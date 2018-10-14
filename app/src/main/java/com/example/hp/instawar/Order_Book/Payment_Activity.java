package com.example.hp.instawar.Order_Book;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hp.instawar.FirebaseMethods;
import com.example.hp.instawar.R;
import com.example.hp.instawar.modeldatabase.User_account_setting;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.payumoney.core.PayUmoneyConfig;
import com.payumoney.core.PayUmoneyConstants;
import com.payumoney.core.PayUmoneySdkInitializer;
import com.payumoney.core.response.PayUMoneyAPIResponse;
import com.payumoney.core.response.PayumoneyResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class Payment_Activity extends AppCompatActivity {
    private Button startpayment;
    private PayUmoneySdkInitializer.PaymentParam mPaymentParams;
    TextView name, mobileNumber, address, price;
    DataSnapshot dataSnapshot;
    Context mContext;
    private static final String TAG = "Payment_Activity";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthlistener;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mReference;
    FirebaseMethods firebaseMethods;
     String merchantKey,userCredentials;
     PayUmoneySdkInitializer.PaymentParam mPaymentparam;
     PayUmoneyConfig payUmoneyConfig;
     String key="8v0A6mLF";
     String salt="bsFk5tGfOz";
     int amount=150;
     String productinfo="Photo book";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_);
        startpayment = (Button) findViewById(R.id.startpayment);
        name = (TextView) findViewById(R.id.owner_name);
        mobileNumber = (TextView) findViewById(R.id.owner_mobile);
        address = (TextView) findViewById(R.id.owner_address);
        price = (TextView) findViewById(R.id.album_price);
        setDetails();
        mContext = Payment_Activity.this;
        firebaseMethods=new FirebaseMethods(mContext);
        //navigateToBaseActivity();
        startpayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                
            }
        });






    }


    private void setDetails() {
        mAuth=FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        mReference = firebaseDatabase.getReference();


        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                firebaseMethods.getOrderDetails(dataSnapshot,name,address,mobileNumber);
                OrderDetails orderDetails=new OrderDetails();
                Log.d(TAG, "onDataChange: "+orderDetails.toString());


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

}
