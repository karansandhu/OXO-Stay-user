package com.user.oxostay.models;

public class Location {

    String city_name,city_id;

    public Location(String city_name, String city_id) {
        this.city_name = city_name;
        this.city_id = city_id;
    }

    public Location(){

    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

}
