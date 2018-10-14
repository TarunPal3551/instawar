package com.example.hp.instawar.SplashScreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.instawar.Home.Home_activity;
import com.example.hp.instawar.R;

/**
 * Created by hp on 12-Mar-18.
 */

public class SplashScreen extends AppCompatActivity {
    private TextView textView;
    private ImageView imageView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        textView=(TextView)findViewById(R.id.splashtext);
        imageView=(ImageView)findViewById(R.id.splashlogo);
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.splashscreen);
        textView.startAnimation(animation);
        imageView.startAnimation(animation);
        final Intent intent=new Intent(this, Home_activity.class);

        Thread timer=new Thread(){
            public void run(){
                try{

                    sleep(500);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    startActivity(intent);
                    finish();

                }
            }
        };
        timer.start();
    }
}
