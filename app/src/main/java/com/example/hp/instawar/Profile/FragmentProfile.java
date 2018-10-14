package com.example.hp.instawar.Profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.hp.instawar.BottomNavigationHelper;
import com.example.hp.instawar.FirebaseMethods;
import com.example.hp.instawar.GridImageAdapter;
import com.example.hp.instawar.R;
import com.example.hp.instawar.UniversalImageLoader;
import com.example.hp.instawar.Upload.Uploadpost;
import com.example.hp.instawar.modeldatabase.Profile;
import com.example.hp.instawar.modeldatabase.User_account_setting;
import com.example.hp.instawar.modeldatabase.Usersetting;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import static com.nostra13.universalimageloader.core.ImageLoader.TAG;


public class FragmentProfile extends Fragment {
    Context mContext;
    ProgressBar progressBar;
    TextView editProfile, mpost, mDisplayName;
    ImageView mProfilemenu;
    CircularImageView mProfileImage;
    Toolbar toolbar;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthlistener;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseMethods firebaseMethods;
    private String userID;
    private BottomNavigationView bottomNavigationView;
    private GridView gridView;
    private int   mPostCount=0;
    Button uploadpost;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        editProfile = (TextView) view.findViewById(R.id.textEditprofile);
        mpost = (TextView) view.findViewById(R.id.post);
        mDisplayName = (TextView) view.findViewById(R.id.display_username);
        mProfilemenu = (ImageView) view.findViewById(R.id.profilemenu);
        firebaseMethods = new FirebaseMethods(getActivity());
        uploadpost=(Button)view.findViewById(R.id.uploadpost);
        bottomNavigationView = (BottomNavigationView) view.findViewById(R.id.bottom_navigation_icon);
        mProfileImage = (CircularImageView) view.findViewById(R.id.profileimage);
        gridView = (GridView) view.findViewById(R.id.gridview);

        mContext = getActivity();
        toolbar = (Toolbar) view.findViewById(R.id.profiletoolbar);
        setupProfiletoolbar();
        setupFirebaseAuth();
        setupImageGrid();
        setupBottomNavigationView();
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, Account_setting.class));
                //if you want to change hindi 42
            }
        });
        uploadpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),Uploadpost.class);
                startActivity(intent);
            }
        });

        //setupActivityWidget();
        //setProfileImage();
        getPostCount();

        return view;
    }

    private void setupBottomNavigationView() {
        BottomNavigationHelper.disableShiftMode(bottomNavigationView);
        BottomNavigationHelper.enableNavigationClick(mContext, bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

    }


    private void setupProfiletoolbar() {
        ((ProfileActivity) getActivity()).setSupportActionBar(toolbar);

        mProfilemenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onclick:navigate to account setting");
                Intent intent = new Intent(mContext, Account_setting.class);
                startActivity(intent);
            }
        });


    }
    private void getPostCount(){
        mPostCount=0;
        DatabaseReference reference2= FirebaseDatabase.getInstance().getReference();
        Query query2=reference2.child("user_photo")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        query2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot singlesnapshot:dataSnapshot.getChildren()){

                    Log.d(TAG, "onDataChange: found Post" +singlesnapshot.getValue());
                    mPostCount++;

                }
                mpost.setText(String.valueOf(mPostCount));


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }


//    private void setProfileImage() {
//
//
//        String imgUrls = "www.androidcentral.com/sites/androidcentral.com/files/styles/xlarge/public/article_images/2016/08/ac-lloyd.jpg?itok=bb72IeLf";
//        UniversalImageLoader.setImage(imgUrls, mProfileImage, progressBar, "https://");
//
//    }

    private void initImageLoader() {
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(mContext);
        ImageLoader.getInstance().init(universalImageLoader.getConfig());

    }

    private void getAlldata(Usersetting usersetting) {
        User_account_setting user_account_setting = usersetting.getSetting();
        UniversalImageLoader.setImage(user_account_setting.getProfile_photo(), mProfileImage, null, "");
        mDisplayName.setText(user_account_setting.getUsername());



    }


    private void setupFirebaseAuth() {
        Log.d(TAG, "setupFirebaseAuth:setting up firebase auth.");
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        userID = mAuth.getCurrentUser().getUid();

        mAuthlistener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {

                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");


                }
            }
        };
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                getAlldata(firebaseMethods.getUser_settings(dataSnapshot));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if User is signed in (non-null) and update UI accordingly.
        mAuth.addAuthStateListener(mAuthlistener);
    }

    public void onStop() {

        super.onStop();
        if (mAuthlistener != null) {


            mAuth.removeAuthStateListener(mAuthlistener);
        }

    }

    private void setupImageGrid() {
        final ArrayList<Profile> profile = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child(getString(R.string.user_photo)).child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    profile.add(ds.getValue(Profile.class));

                }
                int gridWidth = getResources().getDisplayMetrics().widthPixels;
                int imageWidth = gridWidth / 2;
                gridView.setColumnWidth(imageWidth);
                ArrayList<String> imgUrls = new ArrayList<String>();
                for (int i = 0; i < profile.size(); i++) {


                    imgUrls.add(profile.get(i).getImage_path());

                }
                GridImageAdapter adapter = new GridImageAdapter(mContext, R.layout.layout_grid_imageview, "", imgUrls);
                gridView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
//    private void tempGridSetup() {
//
//        ArrayList<String> imgUrls = new ArrayList<>();
//        imgUrls.add("https://images.unsplash.com/photo-1513166460605-c6363b37f83e?auto=format&fit=crop&w=1055&q=80");
//        imgUrls.add("https://images.unsplash.com/photo-1474376700777-eb547d9bed2f?auto=format&fit=crop&w=1050&q=80");
//        imgUrls.add("https://images.unsplash.com/photo-1474575981580-1ec7944df3b2?auto=format&fit=crop&w=1103&q=80");
//        imgUrls.add("https://images.unsplash.com/photo-1514041181368-bca62cceffcd?auto=format&fit=crop&w=1190&q=80");
//        imgUrls.add("https://images.unsplash.com/photo-1497298332258-e43b236a8e30?auto=format&fit=crop&w=1850&q=80");
//        imgUrls.add("https://images.unsplash.com/photo-1514043370531-a00dbd95c42e?auto=format&fit=crop&w=1189&q=80");
//        imgUrls.add("https://images.unsplash.com/photo-1513053508821-1c2e73e3ecc7?auto=format&fit=crop&w=1050&q=80");
//        imgUrls.add("https://images.unsplash.com/photo-1494225157324-2feadbc1382a?auto=format&fit=crop&w=1050&q=80");
//        imgUrls.add("https://images.unsplash.com/photo-1470207261933-4f02fa6ab2ad?auto=format&fit=crop&w=334&q=80");
//        imgUrls.add("https://images.unsplash.com/photo-1464038293999-0fb8953380d9?auto=format&fit=crop&w=1053&q=80");
//        imgUrls.add("https://images.unsplash.com/photo-1491672329368-82e6606bce41?auto=format&fit=crop&w=697&q=80");
//        imgUrls.add("https://images.unsplash.com/photo-1467073070072-6b91b7f4c848?auto=format&fit=crop&w=1050&q=80");
//        setupImageGrid(imgUrls);
//
//    }


}
