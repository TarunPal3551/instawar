package com.example.hp.instawar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.instawar.modeldatabase.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Register_activity extends AppCompatActivity {
    private static final String TAG="Register_Activity";
    Button registerbutton;
    EditText input_signup_email,input_signup_password,input_username_name;
    Context mContext;
    private TextView pleasewait;
    private String email,password,username;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthlistener;
    private ProgressBar progressBar;
    private FirebaseMethods firebaseMethods;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mReference;
    private String append;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_activity);
        input_signup_email=(EditText) findViewById(R.id.input_signup_email);
        input_signup_password=(EditText) findViewById(R.id.input_signup_password);
        input_username_name=(EditText) findViewById(R.id.input_signup_name);
        registerbutton=(Button) findViewById(R.id.button_signup);
          mContext=Register_activity.this;
        final ProgressDialog progressDialog = new ProgressDialog(Register_activity.this,
                R.style.Theme_AppCompat_DayNight_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.dismiss();
          firebaseMethods=new FirebaseMethods(mContext);
          setupFirebaseAuth();

        Log.d(TAG,"onCreate:started.");
        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email=input_signup_email.getText().toString();
                password=input_signup_password.getText().toString();
                username=input_username_name.getText().toString();
                if(checkInput(email,password,username)){
                   progressDialog.show();
                    firebaseMethods.registerNewemail(email,password,username);




                }
            }
        });




    }
    private boolean checkInput(String email,String password,String username){
    Log.d(TAG,"checking input: input for null values");
    if(email.equals("")||password.equals("")||username.equals("")){


        Toast.makeText(mContext,"All filled are required",Toast.LENGTH_SHORT).show();
        return false;
    }
    else
        return true;
    }

    private void checkUser(FirebaseUser user){
        if(user!=null){


            startActivity(new Intent(mContext,login_Activity.class));
        }


       }

    private void setupFirebaseAuth(){
        Log.d(TAG,"setupFirebaseAuth:setting up firebase auth.");
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mReference=mFirebaseDatabase.getReference();
        email=input_signup_email.getText().toString();
        password=input_signup_password.getText().toString();
        username=input_username_name.getText().toString();

        mAuthlistener=new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                Log.d(TAG,"checking user ");
                FirebaseUser user=firebaseAuth.getCurrentUser();
                checkUser(user);

                if(user!=null) {
                    Log.d(TAG,"checking user is logged in or not");

                    Log.d(TAG,"onAuthStateChanged:signed_in:" +user.getUid());
                    mReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                          if(firebaseMethods.checkIfUsernameExists(username,dataSnapshot)){
                              append=mReference.getKey();
                              Log.d(TAG,"username already exists:apeending random string");


                          }
                          firebaseMethods.addNewUser(email,username,0,password,"","","",0);
                              Toast.makeText(mContext,"Signup Succesfull:Sending Verification email",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
                else
                    {
                    Log.d(TAG,"onAuthStateChanged:signed_out");


                }
            }
        };



    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if User is signed in (non-null) and update UI accordingly
        mAuth.addAuthStateListener(mAuthlistener);
    }
    public void onStop(){

        super.onStop();
        if(mAuthlistener!=null){


            mAuth.removeAuthStateListener(mAuthlistener);
        }

    }


}
