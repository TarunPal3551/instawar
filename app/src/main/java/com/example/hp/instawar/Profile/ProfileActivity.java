package com.example.hp.instawar.Profile;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.instawar.R;
import com.mikhaellopez.circularimageview.CircularImageView;

public class ProfileActivity extends AppCompatActivity {
    private static final String TAG = "ProfileActivity";
    private Context mContext;
    ProgressBar progressBar;
    private CircularImageView profileimage;
    private TextView editprofile;
    private TextView Username;
    private TextView profilename, post;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Log.d(TAG, "on create");
        TextView editprofile = (TextView) findViewById(R.id.textEditprofile);
        mContext = ProfileActivity.this;
        getProfileFragment();
//        setupBottomNavigationView();


    }

     public void getProfileFragment() {
             Log.d(TAG, "getProfileFragment: inflanting profile");
             FragmentProfile fragmentProfile = new FragmentProfile();
             FragmentTransaction transaction = ProfileActivity.this.getSupportFragmentManager().beginTransaction();
             transaction.replace(R.id.container, fragmentProfile);
             transaction.addToBackStack("Fragment_Profile");
             transaction.commit();





    }
//    private void setupBottomNavigationView(){
//
//        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_icon);
//        BottomNavigationHelper.disableShiftMode(bottomNavigationView);
//        BottomNavigationHelper.enableNavigationClick(mContext,bottomNavigationView);
//        Menu menu=bottomNavigationView.getMenu();
//        MenuItem menuItem=menu.getItem(1);
//        menuItem.setChecked(true);
//
//
//
//    }

  /*

    private void setupProfiletoolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.profiletoolbar);
        setSupportActionBar(toolbar);
        ImageView profilemenu = (ImageView) findViewById(R.id.profilemenu);
        profilemenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onclick:navigate to account setting");
                Intent intent = new Intent(mContext, Account_setting.class);
                startActivity(intent);
            }
        });


    }

    private void setProfileImage() {


        String imgUrls = "www.androidcentral.com/sites/androidcentral.com/files/styles/xlarge/public/article_images/2016/08/ac-lloyd.jpg?itok=bb72IeLf";
        UniversalImageLoader.setImage(imgUrls, profileimage, progressBar, "https://");

    }

    private void setupActivityWidget() {
        progressBar = (ProgressBar) findViewById(R.id.profileprogressbar);
        progressBar.setVisibility(View.GONE);
        profileimage = (CircularImageView) findViewById(R.id.profileimage);


    }

    private void setupImageGrid(ArrayList<String> imgUrls) {

        GridView gridView = (GridView) findViewById(R.id.gridview);
        int gridWidth = getResources().getDisplayMetrics().widthPixels;
        int imageWidth = gridWidth / 2;
        gridView.setColumnWidth(imageWidth);
        GridImageAdapter adapter = new GridImageAdapter(mContext, R.layout.layout_grid_imageview, "", imgUrls);
        gridView.setAdapter(adapter);


    }

    private void tempGridSetup() {

        ArrayList<String> imgUrls = new ArrayList<>();
        imgUrls.add("https://images.unsplash.com/photo-1513166460605-c6363b37f83e?auto=format&fit=crop&w=1055&q=80");
        imgUrls.add("https://images.unsplash.com/photo-1474376700777-eb547d9bed2f?auto=format&fit=crop&w=1050&q=80");
        imgUrls.add("https://images.unsplash.com/photo-1474575981580-1ec7944df3b2?auto=format&fit=crop&w=1103&q=80");
        imgUrls.add("https://images.unsplash.com/photo-1514041181368-bca62cceffcd?auto=format&fit=crop&w=1190&q=80");
        imgUrls.add("https://images.unsplash.com/photo-1497298332258-e43b236a8e30?auto=format&fit=crop&w=1850&q=80");
        imgUrls.add("https://images.unsplash.com/photo-1514043370531-a00dbd95c42e?auto=format&fit=crop&w=1189&q=80");
        imgUrls.add("https://images.unsplash.com/photo-1513053508821-1c2e73e3ecc7?auto=format&fit=crop&w=1050&q=80");
        imgUrls.add("https://images.unsplash.com/photo-1494225157324-2feadbc1382a?auto=format&fit=crop&w=1050&q=80");
        imgUrls.add("https://images.unsplash.com/photo-1470207261933-4f02fa6ab2ad?auto=format&fit=crop&w=334&q=80");
        imgUrls.add("https://images.unsplash.com/photo-1464038293999-0fb8953380d9?auto=format&fit=crop&w=1053&q=80");
        imgUrls.add("https://images.unsplash.com/photo-1491672329368-82e6606bce41?auto=format&fit=crop&w=697&q=80");
        imgUrls.add("https://images.unsplash.com/photo-1467073070072-6b91b7f4c848?auto=format&fit=crop&w=1050&q=80");
        setupImageGrid(imgUrls);

    }
}*/

}
