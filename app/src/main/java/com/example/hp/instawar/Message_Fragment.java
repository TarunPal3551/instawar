package com.example.hp.instawar;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

/**
 * Created by hp on 20-Jan-18.
 */

public class Message_Fragment extends Fragment {
    public static String FACEBOOK_URL = "https://www.facebook.com/earningo";
    public static String FACEBOOK_PAGE_ID = "EarningGo";
    ImageView share;
    private AdView mAdView;
    private static final String TAG = "Message_Fragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        share=(ImageView)view.findViewById(R.id.share);
        mAdView = (AdView)view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);






        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT,"Download Photobook : Create your own photoalbum.....https://play.google.com/store/apps/details?id=com.example.hp.instawar");
                startActivity(Intent.createChooser(intent,"Share Via"));

            }
        });

        return view;
    }

}
