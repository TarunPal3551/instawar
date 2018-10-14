package com.example.hp.instawar.Order_Book;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.hp.instawar.FirebaseMethods;
import com.example.hp.instawar.R;

public class OrderActivity extends AppCompatActivity {
    EditText albumNameEditText,fullNameedittext,addressedittext,mobileNumberedittext;
    Spinner sizespinner,albumtype;
     Button order;
     FirebaseMethods firebaseMethods;
     Context mContext;
     String size;
     String albumspinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        albumNameEditText=(EditText)findViewById(R.id.albumname);
        fullNameedittext=(EditText)findViewById(R.id.fullnameedittext);
        addressedittext=(EditText)findViewById(R.id.addressedittext);
        mobileNumberedittext=(EditText)findViewById(R.id.mobilenumberedittext);
        sizespinner=(Spinner)findViewById(R.id.sizespinner);
        albumtype=(Spinner)findViewById(R.id.albumtypespinner);
        order=(Button)findViewById(R.id.ordercomplete);
        mContext=OrderActivity.this;
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String albumname=albumNameEditText.getText().toString();
                String fullname=fullNameedittext.getText().toString();
                String address=addressedittext.getText().toString();
                String mobilenumber=mobileNumberedittext.getText().toString();
                if(size==null||albumspinner==null){

                    Toast.makeText(mContext,"select size",Toast.LENGTH_SHORT).show();

                }
                firebaseMethods=new FirebaseMethods(mContext);
                firebaseMethods.orderDetail(fullname,address,mobilenumber,size,albumspinner,null,albumname);
                Intent intent=new Intent(mContext, Payment_Activity.class);
                intent.putExtra("albumname",albumname);
                intent.putExtra("fullname",fullname);
                intent.putExtra("address",address);
                intent.putExtra("mobilenumber",mobilenumber);
                startActivity(intent);
            }
        });
        sizespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                size=sizespinner.getSelectedItem().toString();
                Toast.makeText(mContext,size,Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        albumtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                albumspinner=albumtype.getSelectedItem().toString();
                Toast.makeText(mContext,albumspinner,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




    }
}
