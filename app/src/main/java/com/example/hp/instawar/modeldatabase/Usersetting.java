package com.example.hp.instawar.modeldatabase;

/**
 * Created by hp on 13-Jan-18.
 */

public class Usersetting {
    private User user;
    private User_account_setting setting;

    public Usersetting(User user, User_account_setting setting) {
        this.user = user;
        this.setting = setting;
    }
    public Usersetting() {

    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User_account_setting getSetting() {
        return setting;
    }

    public void setSetting(User_account_setting setting) {
        this.setting = setting;
    }

    @Override
    public String toString() {
        return "Usersetting{" +
                "user=" + user +
                ", setting=" + setting +
                '}';
    }
}
