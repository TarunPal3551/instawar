package com.example.hp.instawar.Upload;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.instawar.FirebaseMethods;
import com.example.hp.instawar.Home.Camera_Fragment;
import com.example.hp.instawar.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class UploadActivity extends AppCompatActivity {
    private static final String TAG = "UploadActivity";
    private Button selectbutton, uploadpost;
    private RecyclerView uploadlist;
    private static final int RESULT_LOAD_IMAGE = 1;
    private List<String> fileNamelist;
    private List<String> fileDonelist;
    private List<String> imageuri;
    private ImageView imageView;
    private UploadAdapter uploadAdapter;
    private StorageReference mStorageReference;
    private static String userID;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthlistener;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mReference;
    Context mContext;
    Uri fileUri;
    TextView textView;
    public int countalbum=0;
    Button uploadedphotos;
    String filename;
    int countimages;
    RelativeLayout relativeLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        selectbutton = (Button) findViewById(R.id.selectbutton);
        uploadlist = (RecyclerView) findViewById(R.id.recylerview);
        imageView = (ImageView) findViewById(R.id.imageview);
        uploadedphotos=(Button)findViewById(R.id.reviewuploaded);
       relativeLayout=(RelativeLayout)findViewById(R.id.relrecyler);
        relativeLayout.setVisibility(View.GONE);
        uploadedphotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext,Upload_review_activity.class);
                startActivity(intent);
            }
        });
        mAuth = FirebaseAuth.getInstance();

        mReference = FirebaseDatabase.getInstance().getReference().child("albums");
        Camera_Fragment fragment=new Camera_Fragment();
        Log.d(TAG, "onCreate: button text position"+fragment.selectposition);
        mContext = UploadActivity.this;

        mStorageReference = FirebaseStorage.getInstance().getReference();
        fileNamelist = new ArrayList<>();
        fileDonelist = new ArrayList<>();
        imageuri = new ArrayList<>();
        uploadAdapter = new UploadAdapter(mContext, fileNamelist, fileDonelist, imageuri);
        uploadlist.setLayoutManager(new LinearLayoutManager(this));
        uploadlist.setHasFixedSize(true);
        uploadlist.setAdapter(uploadAdapter);
        setupFirebaseAuth();

        selectbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "select images"), RESULT_LOAD_IMAGE);
            }
        });
        checkview(countimages);








    }
    private void checkview(int images){

        if(images!=0){
            relativeLayout.setVisibility(View.GONE);

        }
        else if(images==0) {

            relativeLayout.setVisibility(View.VISIBLE);



        }
    }

    @SuppressLint("NewApi")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK) {

            if (data.getClipData() != null) {

                final int totalitemselected = data.getClipData().getItemCount();
                for (int i = 0; i < totalitemselected; i++) {
                    fileUri = data.getClipData().getItemAt(i).getUri();

                   filename = String.valueOf(getFileName(fileUri));
                    fileNamelist.add(filename);
                    fileDonelist.add("uploading");
                    countimages++;
                    checkview(countimages);
                    Toast.makeText(mContext, " number of images is"+countimages, Toast.LENGTH_SHORT).show();


                    uploadAdapter.notifyDataSetChanged();
                    userID = mAuth.getCurrentUser().getUid();

                    final StorageReference filerefrence = mStorageReference.child("uploaded_album").child(userID).child(filename);
                    final  DatabaseReference reference=mReference.child("uploaded_album").child(userID);
                    final int finalI = i;
                    final int finalI1 = i;
                    filerefrence.putFile(fileUri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Toast.makeText(mContext, " file uploaded" + finalI1, Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "onSuccess: getting download url");
                                    fileDonelist.remove(finalI);
                                    fileDonelist.add(finalI, "Done");
                                    Log.d(TAG, "onSuccess: notifiy data is changed");
                                    uploadAdapter.notifyDataSetChanged();
                                        String url= String.valueOf(taskSnapshot.getDownloadUrl());
                                        FirebaseMethods firebaseMethods=new FirebaseMethods(mContext);
                                        firebaseMethods.uploadAlbum(url);





                                }})
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {


                                }
                            });





                }

                Toast.makeText(UploadActivity.this, "select multiple image", Toast.LENGTH_SHORT).show();
            } else if (data.getData() != null) {

                Toast.makeText(UploadActivity.this, "select single file", Toast.LENGTH_SHORT).show();
            }


        }




    }


    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap map = MimeTypeMap.getSingleton();
        return map.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public String getFileName(Uri uri) {

        String result = null;
        if (uri.getScheme().equals("content")) {

            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {

                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));

                }
            } finally {
                cursor.close();
            }


        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {

                result = result.substring(cut + 1);

            }


        }
        return result;


    }
    private void setupFirebaseAuth() {
        Log.d(TAG, "setupFirebaseAuth:setting up firebase auth.");
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        mReference = firebaseDatabase.getReference();
        userID = mAuth.getCurrentUser().getUid();
        final FirebaseMethods firebaseMethods=new FirebaseMethods(mContext);

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
                 countalbum = firebaseMethods.getAlbumCount(dataSnapshot);
                Log.d(TAG, "onDataChange: album count:"+countalbum);
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
