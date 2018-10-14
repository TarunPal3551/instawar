package com.example.hp.instawar.Upload;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hp.instawar.FirebaseMethods;
import com.example.hp.instawar.Home.Home_activity;
import com.example.hp.instawar.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Uploadpost extends AppCompatActivity {
    private static final String TAG = "Uploadpost";
    ImageView cancel, sharepost;
    ImageButton selectphoto;
    EditText caption;
    private static final int RESULT_LOAD_IMAGE = 1;
    public Uri imgUri;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthlistener;
    private Context mContext;
    private static String userID;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mReference;
    public int count=0;
    private FirebaseMethods firebaseMethods;
    String imageUrl;
    public String imgUrl;



    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_post);
        mContext= Uploadpost.this;
        cancel = (ImageView) findViewById(R.id.icon_cancel);
        sharepost = (ImageView) findViewById(R.id.sharepost);
        selectphoto = (ImageButton) findViewById(R.id.imagepost);
        caption = (EditText) findViewById(R.id.description);
        selectphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.CATEGORY_APP_GALLERY, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "select images"), RESULT_LOAD_IMAGE);
            }
        });
        firebaseMethods=new FirebaseMethods(mContext);
        firebaseDatabase = FirebaseDatabase.getInstance();
        mReference = firebaseDatabase.getReference();
        setupFirebaseAuth();
        sharepost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mCaption=caption.getText().toString();
                Toast.makeText(mContext,"attempting to share photo",Toast.LENGTH_SHORT).show();
                firebaseMethods.uploadNewphoto(getString(R.string.new_photo),mCaption,count,imgUri);
                Intent intent=new Intent(mContext,Home_activity.class);
                startActivity(intent);

            }
        });



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK) {

             imgUri = data.getData();
             imgUrl=imgUri.getPath();
             selectphoto.setImageURI(imgUri);


        }


    }
    private void setupFirebaseAuth() {
        Log.d(TAG, "setupFirebaseAuth:setting up firebase auth.");
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        mReference = firebaseDatabase.getReference();
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
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               count=firebaseMethods.getPostCount(dataSnapshot);
                Log.d(TAG, "onDataChange: image count:"+count);
                //firebaseMethods.UpdatePost(count);

                
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


}
