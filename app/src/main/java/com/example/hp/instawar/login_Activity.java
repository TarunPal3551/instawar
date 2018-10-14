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

import com.example.hp.instawar.Home.Home_activity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login_Activity extends AppCompatActivity {
     private Button loginbutton;
     private EditText inputemail,inputpassword;
     private TextView pleasewait,linksignup;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthlistener;
    private Context mContext;
    private ProgressBar mprogressbar;
    private static final String TAG="LoginActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);
        loginbutton=(Button)findViewById(R.id.button_login);
        linksignup=(TextView)findViewById(R.id.link_signup);
        inputemail=(EditText)findViewById(R.id.input_email);
        inputpassword=(EditText)findViewById(R.id.input_password);
        mContext=login_Activity.this;
        mAuth=FirebaseAuth.getInstance();
        Log.d(TAG,"onCreate:started.");
//        mprogressbar.setVisibility(View.GONE);
//        pleasewait.setVisibility(View.GONE);
        final ProgressDialog progressDialog = new ProgressDialog(login_Activity.this,
                R.style.Theme_AppCompat_DayNight_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.dismiss();


        setupFirebaseAuth();




        linksignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent=new Intent(mContext,Register_activity.class);
               startActivity(intent);
               finish();





            }
        });


         loginbutton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Log.d(TAG,"onClick:  attempting to login.");
                 progressDialog.show();
                 String email=inputemail.getText().toString();
                 String password=inputpassword.getText().toString();
                 if(email.isEmpty()||password.isEmpty()){

                     Toast.makeText(mContext,"Fill all detail",Toast.LENGTH_SHORT).show();
                     progressDialog.dismiss();
                 }
                 else {
                     //mprogressbar.setVisibility(View.VISIBLE);
                     //pleasewait.setVisibility(View.VISIBLE);
                     progressDialog.show();

                     mAuth.signInWithEmailAndPassword(email, password)
                             .addOnCompleteListener(login_Activity.this, new OnCompleteListener<AuthResult>() {
                                 @Override
                                 public void onComplete(@NonNull Task<AuthResult> task) {
                                     FirebaseUser user=mAuth.getCurrentUser();
                                     try {
                                         if(user.isEmailVerified()){
                                             Log.d(TAG,"Navigate to home activity");
                                             Intent intent=new Intent(login_Activity.this,Home_activity.class);
                                             startActivity(intent);
                                             progressDialog.dismiss();


                                         }
                                         else {
                                             Toast.makeText(mContext, "Email is not verified", Toast.LENGTH_SHORT).show();
                                             //mprogressbar.setVisibility(View.GONE);
                                             progressDialog.dismiss();
                                         }

                                     }
                                     catch (NullPointerException e){


                                         Log.d(TAG,"onComplete:NullPointerException:" +e.getMessage());
                                     }
                                     if (task.isSuccessful()) {
                                         // Sign in success, update UI with the signed-in User's information
                                         Log.d(TAG, "signInWithEmail:success");
                                         Toast.makeText(login_Activity.this,getString(R.string.authenticationsucces),Toast.LENGTH_SHORT).show();
                                        // mprogressbar.setVisibility(View.GONE);
                                         //pleasewait.setVisibility(View.GONE);
                                         progressDialog.dismiss();
                                     }
                                     else {
                                         Log.d(TAG, "signInWithEmail:",task.getException());
                                         // If sign in fails, display a message to the User.
                                         Toast.makeText(login_Activity.this,getString(R.string.authenticationfailed),Toast.LENGTH_SHORT).show();

                                        // mprogressbar.setVisibility(View.GONE);
                                        // pleasewait.setVisibility(View.GONE);
                                         progressDialog.dismiss();
                                         Intent signup=new Intent(mContext,Register_activity.class);
                                         startActivity(signup);
                                     }

                                     // ...
                                 }
                             });


                 }


             }
         });
         if(mAuth.getCurrentUser()!=null){

             Intent intent=new Intent(login_Activity.this,Home_activity.class);
             startActivity(intent);
             finish();


         }
    }


    private void setupFirebaseAuth(){
        Log.d(TAG,"setupFirebaseAuth:setting up firebase auth.");
        mAuth = FirebaseAuth.getInstance();
        mAuthlistener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=firebaseAuth.getCurrentUser();

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
    }
    public void onStop(){

        super.onStop();
        if(mAuthlistener!=null){


            mAuth.removeAuthStateListener(mAuthlistener);
        }

    }

}
