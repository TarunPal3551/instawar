package com.example.hp.instawar.Profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.hp.instawar.BottomNavigationHelper;
import com.example.hp.instawar.Home.SectionStatePagerAdapter;
import com.example.hp.instawar.R;

import java.util.ArrayList;


public class Account_setting extends AppCompatActivity {
    SectionStatePagerAdapter sectionStatePagerAdapter;
    RelativeLayout relativeLayout;
    ViewPager viewPager;
    BottomNavigationView bottomNavigationView;

    private static final String TAG = "Account_setting";
    private Context mContext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_accountsetting);
       bottomNavigationView=(BottomNavigationView)findViewById(R.id.bottom_navigation_icon);

        viewPager=(ViewPager)findViewById(R.id.container);
        relativeLayout=(RelativeLayout)findViewById(R.id.relLayout1);
       Log.d(TAG,"oncreate:started");
       mContext=Account_setting.this;
       setupSettingList();
        ImageView backarrow=(ImageView)findViewById(R.id.backarrow);
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext,ProfileActivity.class);
                startActivity(intent);
            }
        });
        setupFragment();

       setupBottomNavigationView();
    }

    private void setupBottomNavigationView(){
        BottomNavigationHelper.enableNavigationClick(mContext,bottomNavigationView);
        Menu menu=bottomNavigationView.getMenu();
        MenuItem menuItem=menu.getItem(1);
        menuItem.setChecked(true);



    }
    private void setupSettingList(){
    Log.d(TAG,"setupsettinglist: initializing 'AccountSetting' list");
        ListView listView=(ListView)findViewById(R.id.listview);
        ArrayList<String> option =new ArrayList<>();
        option.add(getString(R.string.editprofile));
        option.add(getString(R.string.Singout));
        ArrayAdapter adapter=new ArrayAdapter(mContext,android.R.layout.simple_list_item_1,option);
        listView.setAdapter(adapter);
          listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
              @Override
              public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                  setViewPager( position);
              }
          });


    }
    private void setupFragment(){
       sectionStatePagerAdapter=new SectionStatePagerAdapter(getSupportFragmentManager());
       sectionStatePagerAdapter.addFragment(new EditProfileFragment(),"EditProfile");
       sectionStatePagerAdapter.addFragment(new SignOutFragment(),"SignOut");


    }
    private void setViewPager(int fragmentNumber){
        relativeLayout.setVisibility(View.GONE);
        viewPager.setAdapter(sectionStatePagerAdapter);
        viewPager.setCurrentItem(fragmentNumber);





    }
}
