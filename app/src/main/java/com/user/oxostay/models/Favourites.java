package com.user.oxostay.models;

public class Favourites {

    String hotel_id,username;

    public Favourites(){

    }

    public Favourites(String hotel_id, String username) {
        this.hotel_id = hotel_id;
        this.username = username;
    }

    public String getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(String hotel_id) {
        this.hotel_id = hotel_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
