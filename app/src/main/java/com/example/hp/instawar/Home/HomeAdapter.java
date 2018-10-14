package com.example.hp.instawar.Home;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hp.instawar.R;
import com.example.hp.instawar.modeldatabase.Home_item;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by hp on 25-Jan-18.
 */


public class HomeAdapter extends RecyclerView.Adapter {
    private final ClickListener listener;
    private List<Home_item> itemsList;
    public int clickedPosition;

    public HomeAdapter(Home_item itemlist, ClickListener listener) {
        this.listener = listener;
        this.itemsList =  itemsList;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item,parent,false);

        return new HomeViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((HomeViewHolder)holder).BindViewHolder(position);

    }

    @Override
    public int getItemCount() {
        return Home_item.picturepath.length;
    }
    private class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView itemimage;
        private Button select;
        private WeakReference<ClickListener> listenerRef;
        public HomeViewHolder(View itemView){


            super(itemView);
            itemimage=(ImageView)itemView.findViewById(R.id.homeitemimage);
            select=(Button)itemView.findViewById(R.id.homebutton);
            itemView.setOnClickListener(this);
            select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "upload photos ", Toast.LENGTH_SHORT).show();
                    clickedPosition = getAdapterPosition();
                    listener.onPositionClicked(clickedPosition);




                }
            });
        }
        public void BindViewHolder(int postion){

            itemimage.setImageResource(Home_item.picturepath[postion]);
            select.setText(Home_item.buttonText[postion]);
        }


        @Override
        public void onClick(View v) {
            Toast.makeText(v.getContext(), "Click Button to upload photos ", Toast.LENGTH_SHORT).show();




        }
    }
    public interface ClickListener {

        int onPositionClicked(int position);

        void onLongClicked(int position);
    }

}
