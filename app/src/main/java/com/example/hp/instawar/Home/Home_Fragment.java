package com.example.hp.instawar.Home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.hp.instawar.R;
import com.example.hp.instawar.Upload.UploadActivity;
import com.example.hp.instawar.Upload.Upload_review_activity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Home_Fragment extends android.support.v4.app.Fragment {


    private static final String TAG = "HomeFragment";
    private StorageReference mStorage;
    Button uploadphoto,uploadpost,review;
    private AdView mAdView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mStorage = FirebaseStorage.getInstance().getReference();
        uploadphoto = (Button) view.findViewById(R.id.uploadimages);
        uploadpost=(Button)view.findViewById(R.id.uploadpost);
        review=(Button)view.findViewById(R.id.review_upload_album);
        uploadphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UploadActivity.class);
                startActivity(intent);
            }
        });
        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),Upload_review_activity.class);
                startActivity(intent);
            }
        });
        mAdView = (AdView)view.findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        return view;

    }


}
