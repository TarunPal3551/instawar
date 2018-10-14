package com.example.hp.instawar.Profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.hp.instawar.R;
import com.example.hp.instawar.login_Activity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by hp on 09-Jan-18.
 */

public class SignOutFragment extends Fragment {
    private static  final String TAG="SignoutFragment";
    Context mContext;
    private ProgressBar progressBar;
    private TextView signout,pleasewaitsignout;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener  mAuthlistener;
    private Button signoutbutton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_sign_out,container,false);
         signout=(TextView)view.findViewById(R.id.signouttextview);
        pleasewaitsignout=(TextView)view.findViewById(R.id.textviewpleasewaitsignout);
         signoutbutton=(Button)view.findViewById(R.id.signoutbutton);
         progressBar=(ProgressBar)view.findViewById(R.id.progressBarSignout);
         setupFirebaseAuth();
         progressBar.setVisibility(View.GONE);
         pleasewaitsignout.setVisibility(View.GONE);
             signoutbutton.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     progressBar.setVisibility(View.VISIBLE);
                     pleasewaitsignout.setVisibility(View.VISIBLE);
                     mAuth.signOut();
                     getActivity().finish();
                 }
             });
        return view;
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
                    Log.d(TAG,"Navigate back to login screen");
                    Intent intent=new Intent(getActivity(),login_Activity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                     startActivity(intent);

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
