package com.user.oxostay.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ApprovedModel {

    String aadhaarCard, address, fullName, gstCert, panCard, phNumber, fcm_token, hotel_address, hotel_desc, hotel_email, hotel_name, hotel_pictures,
            hotel_rating, hotel_secondary_email, manager_added, room_12h_first_checkin, room_3h_first_checkin, room_6h_first_checkin,
            room_12h_last_checkin, room_3h_last_checkin, room_6h_last_checkin, room_rate_12_hour, room_rate_3_hour, room_rate_6_hour, rooms_available, date_from, date_to;
    boolean approvedOrNot;
    ArrayList<String> hotel_images,amenities;

    public ApprovedModel(ArrayList<String> hotel_images,ArrayList<String> amenities, String aadhaarCard, String address, String fullName, String gstCert, String panCard, String phNumber, String fcm_token, String hotel_address, String hotel_desc, String hotel_email, String hotel_name, String hotel_pictures, String hotel_rating, String hotel_secondary_email, String manager_added, String room_12h_first_checkin, String room_3h_first_checkin, String room_6h_first_checkin, String room_12h_last_checkin, String room_3h_last_checkin, String room_6h_last_checkin, String room_rate_12_hour, String room_rate_3_hour, String room_rate_6_hour, String rooms_available, boolean approvedOrNot, String date_from, String date_to) {
        this.amenities = amenities;
        this.hotel_images = hotel_images;
        this.aadhaarCard = aadhaarCard;
        this.address = address;
        this.fullName = fullName;
        this.gstCert = gstCert;
        this.panCard = panCard;
        this.phNumber = phNumber;
        this.fcm_token = fcm_token;
        this.hotel_address = hotel_address;
        this.hotel_desc = hotel_desc;
        this.hotel_email = hotel_email;
        this.hotel_name = hotel_name;
        this.hotel_pictures = hotel_pictures;
        this.hotel_rating = hotel_rating;
        this.hotel_secondary_email = hotel_secondary_email;
        this.manager_added = manager_added;
        this.room_12h_first_checkin = room_12h_first_checkin;
        this.room_3h_first_checkin = room_3h_first_checkin;
        this.room_6h_first_checkin = room_6h_first_checkin;
        this.room_12h_last_checkin = room_12h_last_checkin;
        this.room_3h_last_checkin = room_3h_last_checkin;
        this.room_6h_last_checkin = room_6h_last_checkin;
        this.room_rate_12_hour = room_rate_12_hour;
        this.room_rate_3_hour = room_rate_3_hour;
        this.room_rate_6_hour = room_rate_6_hour;
        this.rooms_available = rooms_available;
        this.approvedOrNot = approvedOrNot;
        this.date_from = date_from;
        this.date_to = date_to;
    }

    public ApprovedModel() {

    }

    public String getDate_from() {
        return date_from;
    }

    public void setDate_from(String date_from) {
        this.date_from = date_from;
    }

    public String getDate_to() {
        return date_to;
    }

    public void setDate_to(String date_to) {
        this.date_to = date_to;
    }

    public ArrayList<String> getAmenities() {
        return amenities;
    }

    public void setAmenities(ArrayList<String> amenities) {
        this.amenities = amenities;
    }

    public ArrayList<String> getHotel_images() {
        return hotel_images;
    }

    public void setHotel_images(ArrayList<String> hotel_images) {
        this.hotel_images = hotel_images;
    }

    public String getAadhaarCard() {
        return aadhaarCard;
    }

    public void setAadhaarCard(String aadhaarCard) {
        this.aadhaarCard = aadhaarCard;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean getApprovedOrNot() {
        return approvedOrNot;
    }

    public void setApprovedOrNot(boolean approvedOrNot) {
        this.approvedOrNot = approvedOrNot;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGstCert() {
        return gstCert;
    }

    public void setGstCert(String gstCert) {
        this.gstCert = gstCert;
    }

    public String getPanCard() {
        return panCard;
    }

    public void setPanCard(String panCard) {
        this.panCard = panCard;
    }

    public String getPhNumber() {
        return phNumber;
    }

    public void setPhNumber(String phNumber) {
        this.phNumber = phNumber;
    }

    public String getFcm_token() {
        return fcm_token;
    }

    public void setFcm_token(String fcm_token) {
        this.fcm_token = fcm_token;
    }

    public String getHotel_address() {
        return hotel_address;
    }

    public void setHotel_address(String hotel_address) {
        this.hotel_address = hotel_address;
    }

    public String getHotel_desc() {
        return hotel_desc;
    }

    public void setHotel_desc(String hotel_desc) {
        this.hotel_desc = hotel_desc;
    }

    public String getHotel_email() {
        return hotel_email;
    }

    public void setHotel_email(String hotel_email) {
        this.hotel_email = hotel_email;
    }

    public String getHotel_name() {
        return hotel_name;
    }

    public void setHotel_name(String hotel_name) {
        this.hotel_name = hotel_name;
    }

    public String getHotel_pictures() {
        return hotel_pictures;
    }

    public void setHotel_pictures(String hotel_pictures) {
        this.hotel_pictures = hotel_pictures;
    }

    public String getHotel_rating() {
        return hotel_rating;
    }

    public void setHotel_rating(String hotel_rating) {
        this.hotel_rating = hotel_rating;
    }

    public String getHotel_secondary_email() {
        return hotel_secondary_email;
    }

    public void setHotel_secondary_email(String hotel_secondary_email) {
        this.hotel_secondary_email = hotel_secondary_email;
    }

    public String getManager_added() {
        return manager_added;
    }

    public void setManager_added(String manager_added) {
        this.manager_added = manager_added;
    }

    public String getRoom_12h_first_checkin() {
        return room_12h_first_checkin;
    }

    public void setRoom_12h_first_checkin(String room_12h_first_checkin) {
        this.room_12h_first_checkin = room_12h_first_checkin;
    }

    public String getRoom_3h_first_checkin() {
        return room_3h_first_checkin;
    }

    public void setRoom_3h_first_checkin(String room_3h_first_checkin) {
        this.room_3h_first_checkin = room_3h_first_checkin;
    }

    public String getRoom_6h_first_checkin() {
        return room_6h_first_checkin;
    }

    public void setRoom_6h_first_checkin(String room_6h_first_checkin) {
        this.room_6h_first_checkin = room_6h_first_checkin;
    }

    public String getRoom_12h_last_checkin() {
        return room_12h_last_checkin;
    }

    public void setRoom_12h_last_checkin(String room_12h_last_checkin) {
        this.room_12h_last_checkin = room_12h_last_checkin;
    }

    public String getRoom_3h_last_checkin() {
        return room_3h_last_checkin;
    }

    public void setRoom_3h_last_checkin(String room_3h_last_checkin) {
        this.room_3h_last_checkin = room_3h_last_checkin;
    }

    public String getRoom_6h_last_checkin() {
        return room_6h_last_checkin;
    }

    public void setRoom_6h_last_checkin(String room_6h_last_checkin) {
        this.room_6h_last_checkin = room_6h_last_checkin;
    }

    public String getRoom_rate_12_hour() {
        return room_rate_12_hour;
    }

    public void setRoom_rate_12_hour(String room_rate_12_hour) {
        this.room_rate_12_hour = room_rate_12_hour;
    }

    public String getRoom_rate_3_hour() {
        return room_rate_3_hour;
    }

    public void setRoom_rate_3_hour(String room_rate_3_hour) {
        this.room_rate_3_hour = room_rate_3_hour;
    }

    public String getRoom_rate_6_hour() {
        return room_rate_6_hour;
    }

    public void setRoom_rate_6_hour(String room_rate_6_hour) {
        this.room_rate_6_hour = room_rate_6_hour;
    }

    public String getRooms_available() {
        return rooms_available;
    }

    public void setRooms_available(String rooms_available) {
        this.rooms_available = rooms_available;
    }
}