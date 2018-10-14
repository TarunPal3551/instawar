package com.example.hp.instawar.modeldatabase;

/**
 * Created by hp on 20-Jan-18.
 */

public class Profile {
    private String caption;
    private String data_created;
    private String image_path;
    private String user_id;
    private String tag;
    String photo_id;

    public Profile(String photo_id) {
        this.photo_id = photo_id;
    }

    public String getPhoto_id() {
        return photo_id;
    }

    public void setPhoto_id(String photo_id) {
        this.photo_id = photo_id;
    }

    public Profile() {

    }

    public Profile(String caption, String data_created, String image_path, String user_id, String tag) {
        this.caption = caption;
        this.data_created = data_created;
        this.image_path = image_path;
        this.user_id = user_id;
        this.tag = tag;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getData_created() {
        return data_created;
    }

    public void setData_created(String data_created) {
        this.data_created = data_created;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "caption='" + caption + '\'' +
                ", data_created='" + data_created + '\'' +
                ", image_path='" + image_path + '\'' +
                ", user_id='" + user_id + '\'' +
                ", tag='" + tag + '\'' +
                ", photo_id='" + photo_id + '\'' +
                '}';
    }
}
