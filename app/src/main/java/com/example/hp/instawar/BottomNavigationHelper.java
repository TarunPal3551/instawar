package com.example.hp.instawar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;

import com.example.hp.instawar.Home.Home_activity;
import com.example.hp.instawar.Like.SettingActivity;
import com.example.hp.instawar.Profile.ProfileActivity;

import java.lang.reflect.Field;

public class BottomNavigationHelper {
    @SuppressLint("RestrictedApi")
    public static void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                //noinspection RestrictedApi
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                //noinspection RestrictedApi
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("BNVHelper", "Unable to get shift mode field", e);
        } catch (IllegalAccessException e) {
            Log.e("BNVHelper", "Unable to change value of shift mode", e);
        }
    }
    public static void enableNavigationClick(final Context mContext, BottomNavigationView View){


       View.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {


                    case R.id.icon_home:
                        mContext.startActivity(new Intent(mContext, Home_activity.class));
                        break;


                    case R.id.icon_like:
                        mContext.startActivity(new Intent(mContext,SettingActivity.class));
                        break;

                    case R.id.icon_profile:
                        mContext.startActivity(new Intent(mContext, ProfileActivity.class));
                        break;

                }

                return false;
            }
        });
    }

}
