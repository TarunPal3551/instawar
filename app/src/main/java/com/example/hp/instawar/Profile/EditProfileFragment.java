package com.example.hp.instawar.Profile;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.instawar.FirebaseMethods;
import com.example.hp.instawar.R;
import com.example.hp.instawar.UniversalImageLoader;
import com.example.hp.instawar.modeldatabase.FilePaths;
import com.example.hp.instawar.modeldatabase.User;
import com.example.hp.instawar.modeldatabase.User_account_setting;
import com.example.hp.instawar.modeldatabase.Usersetting;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import static android.app.Activity.RESULT_OK;
import static java.lang.Long.parseLong;

/**
 * Created by hp on 09-Jan-18.
 */

public class EditProfileFragment extends Fragment {


    ImageView imageback, save;
    private CircularImageView profileImage;
    private EditText changeusername, addmobile, addaddress, useremail, userstate;
    private TextView editProfile;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthlistener;
    private static final String TAG = "Edit profile fragment";
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private DatabaseReference mReference;
    private StorageReference mStorageReference;
    private FirebaseMethods firebaseMethods;
    private String userID;
    Usersetting musersetting;
    public Uri imgUri;
    private static final int RESULT_LOAD_IMAGE = 1;
    Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_editprofile, container, false);
        imageback = (ImageView) view.findViewById(R.id.icon_cancel);
        save = (ImageView) view.findViewById(R.id.savedata);
        profileImage = (CircularImageView) view.findViewById(R.id.profileimageedit);
        changeusername = (EditText) view.findViewById(R.id.changename);
        userstate = (EditText) view.findViewById(R.id.state);
        addaddress = (EditText) view.findViewById(R.id.address);
        addmobile = (EditText) view.findViewById(R.id.mobilenumber);
        useremail = (EditText) view.findViewById(R.id.email);
        editProfile = (TextView) view.findViewById(R.id.changephoto);
        mContext=getActivity();


        imageback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        // setProfileImage();
        //initImageLoader();
        firebaseMethods = new FirebaseMethods(getActivity());
        setupFirebaseAuth();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNewProfile();
                Intent intent=new Intent(mContext,ProfileActivity.class);
                startActivity(intent);
            }
        });
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.CATEGORY_APP_GALLERY, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "select images"), RESULT_LOAD_IMAGE);
            }
        });


        return view;


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK) {
            FilePaths filePaths=new FilePaths();
            String imgUrl = data.getDataString();
            imgUri = data.getData();
            profileImage.setImageURI(imgUri);
            userID = mAuth.getCurrentUser().getUid();
            firebaseMethods.updateUserprofile(imgUrl);
            firebaseMethods.uploadNewphoto(getString(R.string.profile_photo),null,0,imgUri);



        }


    }

    private void seteditProfileField(Usersetting usersetting) {
        musersetting = usersetting;
        User_account_setting user_account_setting = usersetting.getSetting();
        changeusername.setText(user_account_setting.getUsername());
        userstate.setText(user_account_setting.getState());
        addaddress.setText(user_account_setting.getAddress());
        useremail.setText(user_account_setting.getEmail());
        addmobile.setText(String.valueOf(user_account_setting.getPhone_number()));
        UniversalImageLoader.setImage(user_account_setting.getProfile_photo(), profileImage, null, "");


    }

    private void saveNewProfile() {
        Log.d(TAG, "saveNewProfile: attempting to save profile");
        final String username = changeusername.getText().toString();
        final String address = addaddress.getText().toString();
        final String state = userstate.getText().toString();
        final String email = useremail.getText().toString();
        final long phone_number = Long.parseLong(addmobile.getText().toString());
        Log.d(TAG, "saveNewProfile: database reference");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = new User();
                User_account_setting setting = new User_account_setting();
                Log.d(TAG, "onDataChange: musersetting");
                if (!musersetting.getSetting().getUsername().equals(username)) {
                    checkIfUsernameExists(username);


                }
                if (!(musersetting.getSetting().getPhone_number() == phone_number)) {
                    firebaseMethods.updateUserAccountSetting(null, null, phone_number, null, null, null);


                }
                if (!musersetting.getSetting().getAddress().equals(address)) {
                    firebaseMethods.updateUserAccountSetting(null, null, 0, address, null, null);


                }
                if (!musersetting.getSetting().getEmail().equals(email)) {
                    firebaseMethods.updateUserAccountSetting(null, email, 0, null, null, null);


                }
                if (!musersetting.getSetting().getState().equals(state)) {
                    firebaseMethods.updateUserAccountSetting(null, null, 0, null, state, null);

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void checkIfUsernameExists(final String username) {

        Log.d(TAG, "checkusernameIfexists: checkingif" + username + "already exists");
        firebaseDatabase = FirebaseDatabase.getInstance();
        Log.d(TAG, "checkIfUsernameExists: firebase database");
        databaseReference = firebaseDatabase.getReference();
        Query query = databaseReference.child(getString(R.string.user))
                .orderByChild(getString(R.string.dataaccountsetting))
                .equalTo(username);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {


                    Log.d(TAG, "onDataChange: SAVED username");
                    firebaseMethods.updateUsername(username);

                }
                for (DataSnapshot singleDatasnapshot : dataSnapshot.getChildren()) {

                    if (singleDatasnapshot.exists()) {
                        Log.d(TAG, "onDataChange: found a match user name already exists" + singleDatasnapshot.getValue(User.class));
                        Toast.makeText(getActivity(), "That username already exists", Toast.LENGTH_SHORT).show();

                    }


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


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
                seteditProfileField(firebaseMethods.getUser_settings(dataSnapshot));
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


//    private void setProfileImage(){
//    String imgUrl="www.androidcentral.com/sites/androidcentral.com/files/styles/xlarge/public/article_images/2016/08/ac-lloyd.jpg?itok=bb72IeLf";
//    UniversalImageLoader.setImage(imgUrl,profileImage,null,"https://");
//
//
//    }
//     private void initImageLoader(){
//    UniversalImageLoader universalImageLoader=new UniversalImageLoader(getActivity());
//    ImageLoader.getInstance().init(universalImageLoader.getConfig());
//
//
// }

}

