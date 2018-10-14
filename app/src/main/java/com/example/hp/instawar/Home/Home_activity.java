package com.example.hp.instawar.Home;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.hp.instawar.BottomNavigationHelper;
import com.example.hp.instawar.Message_Fragment;
import com.example.hp.instawar.R;
import com.example.hp.instawar.UniversalImageLoader;
import com.example.hp.instawar.login_Activity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nostra13.universalimageloader.core.ImageLoader;

import static android.app.PendingIntent.getActivity;

public class Home_activity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthlistener;
    private static final String TAG="Home_Activity";
    private Context mContext;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_activity);
        Log.d(TAG,"onCreate:starting.");
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_icon);
        mAuth = FirebaseAuth.getInstance();
        mContext=Home_activity.this;
        initImageLoader();
        setViewPager();
        setupFirebaseAuth();
        setupBottomNavigationView();

    }
    private void setupBottomNavigationView(){

       BottomNavigationHelper.disableShiftMode(bottomNavigationView);
        BottomNavigationHelper.enableNavigationClick(mContext,bottomNavigationView);
        Menu menu=bottomNavigationView.getMenu();
        MenuItem menuItem=menu.getItem(0);
        menuItem.setChecked(true);



    }
    private void initImageLoader(){

        UniversalImageLoader universalImageLoader=new UniversalImageLoader(mContext);
        ImageLoader.getInstance().init(universalImageLoader.getConfig());
    }
    public void setViewPager(){
        SectionPagerAdapter sectionPagerAdapter=new SectionPagerAdapter(getSupportFragmentManager());
        sectionPagerAdapter.addFragment(new Camera_Fragment());
        sectionPagerAdapter.addFragment(new Home_Fragment());
        sectionPagerAdapter.addFragment(new Message_Fragment());
        ViewPager viewPager=(ViewPager)findViewById(R.id.container);
        viewPager.setAdapter(sectionPagerAdapter);

        TabLayout tabLayout=(TabLayout)findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(1).setIcon(R.drawable.ic_camera);
        tabLayout.getTabAt(0).setIcon(R.drawable.fragmenticon);
        tabLayout.getTabAt(2).setIcon(R.drawable.icon_message);



    }




    private void checkCurrentUser(FirebaseUser user){
        Log.d(TAG,"checkCurrentUser:checking if User is logged in.");
        if(user==null){
           startActivity(new Intent(mContext,login_Activity.class));
        }



    }

    private void setupFirebaseAuth(){
        Log.d(TAG,"setupFirebaseAuth:setting up firebase auth.");
        mAuth = FirebaseAuth.getInstance();
         mAuthlistener=new FirebaseAuth.AuthStateListener() {
             @Override
             public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                 FirebaseUser user=firebaseAuth.getCurrentUser();
                 checkCurrentUser(user);

                 if(user!=null){

                    Log.d(TAG,"onAuthStateChanged:signed_in:" +user.getUid());

                 }
                 else {
                Log.d(TAG,"onAuthStateChanged:signed_out");


                 }
             }
         };


    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if User is signed in (non-null) and update UI accordingly.
       mAuth.addAuthStateListener(mAuthlistener);
       checkCurrentUser(mAuth.getCurrentUser());
    }
    public void onStop(){

        super.onStop();
        if(mAuthlistener!=null){


            mAuth.removeAuthStateListener(mAuthlistener);
        }

    }


}
