package com.example.hp.instawar;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Album_Detail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album__detail);


        Intent intent=getIntent();
        if(intent.hasExtra("one")) {


            FragmentOne fragmentOne = new FragmentOne();
            FragmentTransaction fragmentTransaction = Album_Detail.this.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.container, fragmentOne);
            fragmentTransaction.addToBackStack("Fragment One");
            fragmentTransaction.commit();
        }
        else if(intent.hasExtra("Two")){




        }
    }
}
