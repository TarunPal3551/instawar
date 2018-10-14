package com.example.hp.instawar.Home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.example.hp.instawar.Album_Detail;
import com.example.hp.instawar.R;
import com.example.hp.instawar.modeldatabase.Home_item;


public class Camera_Fragment extends android.support.v4.app.Fragment {
    private static final String TAG = "CameraFragment";
    Context mContext;
    RecyclerView rvitem;
    CardView cvitem;
    public int selectposition;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera, container, false);
        mContext = getActivity();
        rvitem=(RecyclerView)view.findViewById(R.id.recylerviewhome);
        rvitem.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity());
        rvitem.setLayoutManager(layoutManager);
        final Home_item item = new Home_item();

        HomeAdapter homeAdapter=new HomeAdapter(item, new HomeAdapter.ClickListener() {

            @Override
            public int onPositionClicked(int position) {
                Intent mIntent;
                switch (position){

                    case 0:
                        Toast.makeText(mContext,"Welcome",Toast.LENGTH_SHORT).show();
                        mIntent=new Intent(mContext, Album_Detail.class);
                        mIntent.putExtra("one","One");
                        startActivity(mIntent);

                        break;
                    case 1:
                        Toast.makeText(mContext,"Welcome",Toast.LENGTH_SHORT).show();
                        mIntent=new Intent(mContext, Album_Detail.class);
                        mIntent.putExtra("one","One");
                        startActivity(mIntent);

                        break;
                    case 2:
                        Toast.makeText(mContext,"Welcome",Toast.LENGTH_SHORT).show();
                        mIntent=new Intent(mContext, Album_Detail.class);
                        mIntent.putExtra("one","One");
                        startActivity(mIntent);

                        break;
                    case 3:
                        Toast.makeText(mContext,"Welcome "+Home_item.buttonText[position],Toast.LENGTH_SHORT).show();

                        mIntent=new Intent(mContext, Album_Detail.class);
                        mIntent.putExtra("one","One");
                        startActivity(mIntent);

                        break;
                    case 4:
                        Toast.makeText(mContext,"Welcome",Toast.LENGTH_SHORT).show();
                        mIntent=new Intent(mContext, Album_Detail.class);
                        mIntent.putExtra("one","One");
                        startActivity(mIntent);
                        break;



                }
                selectposition=position;
                Log.d(TAG, "onPositionClicked: select position"+position);
                return selectposition;




            }



            @Override
            public void onLongClicked(int position) {

            }
        });


        rvitem.setAdapter(homeAdapter);
        return view ;

    }




    }

