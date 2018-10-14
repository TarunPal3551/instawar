package com.example.hp.instawar.Like;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.hp.instawar.AboutUs;
import com.example.hp.instawar.BottomNavigationHelper;
import com.example.hp.instawar.Contactus;
import com.example.hp.instawar.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

public class SettingActivity extends AppCompatActivity implements RewardedVideoAdListener {
    private static final String TAG="SettingActivity";
    private Context mContext;
    BottomNavigationView bottomNavigationView;
    private InterstitialAd interstitialAd;
    private AdView mAdViewtop,adViewbottom;
    TextView feedback,about,contactus,changepassword,help;
    private RewardedVideoAd mAd;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);


        mContext=SettingActivity.this;
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_icon);
        setupBottomNavigationView();

        MobileAds.initialize(this,"ca-app-pub-1357841146860335~8929400120");
        interstitialAd=new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        //interstitialAd.setAdUnitId("ca-app-pub-1357841146860335/7666706761");
        interstitialAd.loadAd(new AdRequest.Builder().build());
       help=(TextView) findViewById(R.id.showadd);
       about=(TextView)findViewById(R.id.aboutus);
       contactus=(TextView)findViewById(R.id.contactus);
       changepassword=(TextView)findViewById(R.id.changepaasword);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
            }
        });
        mAdViewtop= (AdView)findViewById(R.id.adView3);
        adViewbottom= (AdView)findViewById(R.id.adView4);
        AdRequest adRequest = new AdRequest.Builder().build();
        AdRequest adRequest2 = new AdRequest.Builder().build();
        mAdViewtop.loadAd(adRequest);
        adViewbottom.loadAd(adRequest2);
        feedback=(TextView) findViewById(R.id.feedback);
        mAd= MobileAds.getRewardedVideoAdInstance(this);
        loadedvedioadd();
        mAd.setRewardedVideoAdListener(this);
       feedback.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(mAd.isLoaded()){


                   mAd.show();
               }
           }
       });
       about.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(mContext, AboutUs.class);
               startActivity(intent);

           }
       });
       contactus.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(mContext, Contactus.class);
               startActivity(intent);

           }
       });


    }

    private void setupBottomNavigationView(){
        BottomNavigationHelper.disableShiftMode(bottomNavigationView);
        BottomNavigationHelper.enableNavigationClick(mContext,bottomNavigationView);
        Menu menu=bottomNavigationView.getMenu();
        MenuItem menuItem=menu.getItem(2);
        menuItem.setChecked(true);



    }
    private void loadedvedioadd(){
        if(!mAd.isLoaded()) {
            mAd.loadAd("ca-app-pub-3940256099942544/5224354917", new AdRequest.Builder().build());
        }

    }


    @Override
    public void onRewardedVideoAdLoaded() {

    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {
        loadedvedioadd();

    }

    @Override
    public void onRewarded(RewardItem rewardItem) {

    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {
        loadedvedioadd();

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onResume() {
        mAd.resume(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        mAd.pause(this);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mAd.destroy(this);
        super.onDestroy();
    }


    }





