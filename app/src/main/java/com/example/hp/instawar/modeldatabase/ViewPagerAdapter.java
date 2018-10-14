package com.example.hp.instawar.modeldatabase;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.hp.instawar.R;

/**
 * Created by hp on 09-Mar-18.
 */

public class ViewPagerAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private Integer[] images={R.drawable.image_1,R.drawable.image_5,R.drawable.image21,R.drawable.image_16,R.drawable.image18,R.drawable.image_6};



    public ViewPagerAdapter (Context context){
        this.context=context;

    }
    @Override
    public int getCount() {
        return images.length;
    }
    @Override
    public boolean isViewFromObject(View view,Object object){
        return view==object;

    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.viewpageritem,null);
        ImageView imageView=(ImageView)view.findViewById(R.id.imageviewpager);
        imageView.setImageResource(images[position]);
        ViewPager viewPager=(ViewPager)container;
        viewPager.addView(view,0);
        return view;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager viewPager=(ViewPager)container;
        View view=(View)object;
        viewPager.removeView(view);
    }



}
