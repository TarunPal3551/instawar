package com.example.hp.instawar.modeldatabase;

import android.net.Uri;

/**
 * Created by hp on 26-Jan-18.
 */

public class Album {
  String imageUrl;
  String image_path;

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public Album() {

    }
    public Album(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "Album{" +
                "imageUrl='" + imageUrl + '\'' +
                ", image_path='" + image_path + '\'' +
                '}';
    }
}
