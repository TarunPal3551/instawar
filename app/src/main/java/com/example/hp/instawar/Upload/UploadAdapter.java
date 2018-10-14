package com.example.hp.instawar.Upload;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.instawar.R;

import java.util.List;

public class UploadAdapter extends RecyclerView.Adapter<UploadAdapter.ViewHolder>{

    public List<String> fileNamelist;
    public List<String> fileDonelist;
    public List<String> image;
    Context mContext;




    public UploadAdapter(Context mContext,List<String> fileNamelist, List<String>fileDonelist,List<String> image){
        this.fileNamelist=fileNamelist;
        this.fileDonelist=fileDonelist;
        this.image=image;
        this.mContext=mContext;


    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.uploaditem,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String filenames=fileNamelist.get(position);
        holder.fileNameView.setText(filenames);
        String filedone=fileDonelist.get(position);
            if (filedone.equals("uploading")) {


                holder.filedoneview.setImageResource(R.drawable.upload);
            } else if(filedone.equals("Done")){


                holder.filedoneview.setImageResource(R.drawable.uploaddone);
            }


    }

    @Override
    public int getItemCount() {
        return fileNamelist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        View mView;
        public TextView fileNameView;
        public ImageView filedoneview;
        public ImageView imageView;



        public ViewHolder(View itemView) {
            super(itemView);

            mView=itemView;
            imageView=(ImageView)mView.findViewById(R.id.imageview);
            fileNameView=(TextView)mView.findViewById(R.id.filename);
            filedoneview=(ImageView)mView.findViewById(R.id.upload_loading);



        }
    }

}


