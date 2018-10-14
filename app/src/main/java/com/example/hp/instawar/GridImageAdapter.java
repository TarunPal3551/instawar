package com.example.hp.instawar;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 10-Jan-18.
 */

public class GridImageAdapter extends ArrayAdapter<String>{
    private Context mContext;
    private LayoutInflater mInflater;
    private int layoutResources;
    private String mAppend;
    private ArrayList<String> imgUrls;

    public GridImageAdapter(Context mContext, int layoutResources, String mAppend, ArrayList<String> imgUrls) {
        super(mContext,layoutResources, (List<String>) imgUrls);

        this.mContext = mContext;
        mInflater=(LayoutInflater)mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        this.layoutResources = layoutResources;
        this.mAppend = mAppend;
        this.imgUrls = imgUrls;
    }
    private static class ViewHolder{

    SquareImageView profileImage;
     ProgressBar mProgressbar;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder viewHolder;
        if(convertView==null)
        {
         convertView=mInflater.inflate(layoutResources,parent,false);
         viewHolder=new ViewHolder();
         viewHolder.mProgressbar=(ProgressBar)convertView.findViewById(R.id.gridImageProgressbar);
         viewHolder.profileImage=(SquareImageView) convertView.findViewById(R.id.gridImageView);
         convertView.setTag(viewHolder);

        }
        else {

            viewHolder=(ViewHolder)convertView.getTag();
        }
        String imgUrls=getItem(position);



        ImageLoader imageLoader=ImageLoader.getInstance();
        imageLoader.displayImage(mAppend +imgUrls, viewHolder.profileImage, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                if(viewHolder.mProgressbar!=null){

                    viewHolder.mProgressbar.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                if(viewHolder.mProgressbar!=null){

                    viewHolder.mProgressbar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                if(viewHolder.mProgressbar!=null){

                    viewHolder.mProgressbar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                if(viewHolder.mProgressbar!=null){

                    viewHolder.mProgressbar.setVisibility(View.VISIBLE);
                }
            }
        });



        return convertView;
    }
}
