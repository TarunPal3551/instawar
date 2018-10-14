package com.example.hp.instawar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.hp.instawar.Home.Home_activity;
import com.example.hp.instawar.Upload.UploadActivity;
import com.example.hp.instawar.modeldatabase.ViewPagerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by hp on 09-Mar-18.
 */

public class FragmentOne extends Fragment {
    ViewPager viewPager;
    RelativeLayout size8,siz12;
    FirebaseMethods firebaseMethods;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    private static final String TAG = "FragmentOne";
   ImageView backhome;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
           View view=inflater.inflate(R.layout.fragment_one,container,false);
            viewPager=(ViewPager)view.findViewById(R.id.imageviewpager);
            backhome=(ImageView)view.findViewById(R.id.backtomain);
            backhome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(getContext(), Home_activity.class);
                    startActivity(intent);
                }
            });

            ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getContext());
        viewPager.setAdapter(viewPagerAdapter);
        size8=(RelativeLayout)view.findViewById(R.id.relativeLayout8by8);
        siz12=(RelativeLayout)view.findViewById(R.id.relativeLayout12by12);
        size8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),UploadActivity.class);
                startActivity(intent);
                FirebaseMethods firebaseMethods=new FirebaseMethods(getContext());
                firebaseMethods.orderDetail(null,null,null,"8 by 8","Yari-PhotoBook","100",null);
                Log.d(TAG, "onClick: uploading details");
            }
        });
        siz12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),UploadActivity.class);
                startActivity(intent);
                FirebaseMethods firebaseMethods=new FirebaseMethods(getContext());
                firebaseMethods.orderDetail(null,null,null,"12 by 12","Yari","100",null);

            }
        });
        return view;
    }
}
