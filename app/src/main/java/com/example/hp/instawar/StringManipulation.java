package com.example.hp.instawar;

/**
 * Created by hp on 11-Jan-18.
 */

public class StringManipulation {


    public static String expandUsername(String username){
        return username.replace(" ",".");



    }
    public static String condenseUsername(String username){


        return username.replace("."," ");
    }
}
