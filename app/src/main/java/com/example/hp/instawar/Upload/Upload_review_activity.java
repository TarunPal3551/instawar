package com.example.hp.instawar.Upload;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.instawar.GridImageAdapter;
import com.example.hp.instawar.Order_Book.OrderActivity;
import com.example.hp.instawar.R;
import com.example.hp.instawar.modeldatabase.Album;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class Upload_review_activity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthlistener;
    private Context mContext;
    private static String userID;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mReference;
    private StorageReference mStorageReference;
    GridView review_grid_view;
    private static final String TAG = "Upload_review_activity";
    private ImageView orderButton;
    final ArrayList<Album> albums= new ArrayList<>();
    final ArrayList<String> imgUrls = new ArrayList<String>();
    StorageReference storageReference;
    FirebaseStorage firebaseStorage;
    int lenght=0;
    Button backbutton;
    TextView textView;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_review_activity);

        review_grid_view = (GridView) findViewById(R.id.review_grid);
        orderButton=(ImageView)findViewById(R.id.orderbutton);
         textView=(TextView)findViewById(R.id.adnewphototextview);
        textView.setVisibility(View.GONE);
        progressBar=(ProgressBar)findViewById(R.id.datashowing);
        progressBar.setVisibility(View.GONE);
        backbutton=(Button)findViewById(R.id.backbutton);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, UploadActivity.class);
                startActivity(intent);
            }
        });
        mContext = Upload_review_activity.this;


        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext,OrderActivity.class);
                startActivity(intent);
            }
        });

        setReview_grid_view();
        setupImageGrid();











    }






        private void setReview_grid_view(){

            review_grid_view.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                    reference.removeEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {
                            remove(position);
                            lenght--;


                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    remove(position);
                    int gridWidth = getResources().getDisplayMetrics().widthPixels;
                    int imageWidth = gridWidth / 4;
                    review_grid_view.setColumnWidth(imageWidth);
                    imgUrls.remove(imgUrls.get(position));
                    review_grid_view.deferNotifyDataSetChanged();
                    GridImageAdapter adapter = new GridImageAdapter(mContext, R.layout.layout_grid_imageview, "", imgUrls);
                    review_grid_view.setAdapter(adapter);
                    lenght--;
                    Toast.makeText(mContext,"lenght is"+lenght,Toast.LENGTH_SHORT).show();
                    if(lenght==0){


                        progressBar.setVisibility(View.VISIBLE);
                        textView.setVisibility(View.VISIBLE);
                    }






                    return false;
                }
            });



        }
    private void remove(int position){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("album").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(albums.get(position).getImage_path()).removeValue();



    }

    private void setupImageGrid() {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
            Query query = reference.child("album").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        albums.add(ds.getValue(Album.class));

                    }
                    if(albums.isEmpty()){


                        textView.setVisibility(View.VISIBLE);
                    }
                    else {

                        int gridWidth = getResources().getDisplayMetrics().widthPixels;
                        int imageWidth = gridWidth / 4;
                        review_grid_view.setColumnWidth(imageWidth);
                        for (int i = 0; i < albums.size(); i++) {
                            imgUrls.add(albums.get(i).getImageUrl());
                            lenght++;
                            Toast.makeText(mContext, "lenght is" + lenght, Toast.LENGTH_SHORT).show();


                        }
                        GridImageAdapter adapter = new GridImageAdapter(mContext, R.layout.layout_grid_imageview, "", imgUrls);
                        review_grid_view.setAdapter(adapter);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    progressBar.setVisibility(View.VISIBLE);



                }


            });


        }









}
