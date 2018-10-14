package com.example.hp.instawar.modeldatabase;

/**
 * Created by hp on 11-Jan-18.
 */

public class User_account_setting {
    String address;
    String display_name;
    long post;
    String profile_photo;
    String state;
    String email;
   long phone_number;
   String username;





    public User_account_setting(String address, String display_name, long post, String profile_photo, String state, String email, long phone_number, String username) {
        this.address = address;
        this.display_name = display_name;
        this.post = post;
        this.profile_photo = profile_photo;
        this.state = state;
        this.email = email;
        this.phone_number = phone_number;
        this.username = username;
    }

    public User_account_setting() {

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public long getPost() {
        return post;
    }

    public void setPost(long post) {
        this.post = post;
    }

    public String getProfile_photo() {
        return profile_photo;
    }

    public void setProfile_photo(String profile_photo) {
        this.profile_photo = profile_photo;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(long phone_number) {
        this.phone_number = phone_number;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User_account_setting{" +
                "address='" + address + '\'' +
                ", display_name='" + display_name + '\'' +
                ", post=" + post +
                ", profile_photo='" + profile_photo + '\'' +
                ", state='" + state + '\'' +
                ", email='" + email + '\'' +
                ", phone_number=" + phone_number +
                ", username='" + username + '\'' +
                '}';
    }
}
