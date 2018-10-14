package com.example.hp.instawar.Order_Book;

/**
 * Created by hp on 10-Mar-18.
 */

public class OrderDetails {
    String name;
    String address;
    String size;
    String price;
    String albumtype;
    String mobilenumber;
    String albumname;

    public OrderDetails(){


    }

    public OrderDetails(String name, String address, String size, String price, String albumtype, String mobilenumber,String albumname) {
        this.name = name;
        this.address = address;
        this.size = size;
        this.price = price;
        this.albumtype = albumtype;
        this.mobilenumber = mobilenumber;
        this.albumname=albumname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAlbumtype() {
        return albumtype;
    }

    public void setAlbumtype(String albumtype) {
        this.albumtype = albumtype;
    }

    public String getMobilenumber() {
        return mobilenumber;
    }

    public void setMobilenumber(String mobilenumber) {
        this.mobilenumber = mobilenumber;
    }

    public String getAlbumname() {
        return albumname;
    }

    public void setAlbumname(String albumname) {
        this.albumname = albumname;
    }

    @Override
    public String toString() {
        return "OrderDetails{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", size='" + size + '\'' +
                ", price='" + price + '\'' +
                ", albumtype='" + albumtype + '\'' +
                ", mobilenumber='" + mobilenumber + '\'' +
                ", albumname='" + albumname + '\'' +
                '}';
    }
}
