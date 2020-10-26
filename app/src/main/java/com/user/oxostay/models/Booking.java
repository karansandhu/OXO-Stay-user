package com.user.oxostay.models;

public class Booking {

    String hotel_name,hotel_address,fcm_token,hotel_email,hotel_phnno,hotel_id,user_id,selected_room,check_in_time,transaction_id,
    payment_type,rooms_booked,check_in_date,user_phnno,username,booking_Status,total_amount,check_out_time;

    public Booking(){

    }

    public Booking(String hotel_name, String hotel_address, String fcm_token, String hotel_email, String hotel_phnno, String hotel_id, String user_id, String selected_room, String check_in_time, String transaction_id, String payment_type, String rooms_booked, String check_in_date, String user_phnno, String username, String booking_Status, String total_amount, String check_out_time) {
        this.hotel_name = hotel_name;
        this.hotel_address = hotel_address;
        this.fcm_token = fcm_token;
        this.hotel_email = hotel_email;
        this.hotel_phnno = hotel_phnno;
        this.hotel_id = hotel_id;
        this.user_id = user_id;
        this.selected_room = selected_room;
        this.check_in_time = check_in_time;
        this.transaction_id = transaction_id;
        this.payment_type = payment_type;
        this.rooms_booked = rooms_booked;
        this.check_in_date = check_in_date;
        this.user_phnno = user_phnno;
        this.username = username;
        this.booking_Status = booking_Status;
        this.total_amount = total_amount;
        this.check_out_time = check_out_time;
    }

    public String getHotel_name() {
        return hotel_name;
    }

    public void setHotel_name(String hotel_name) {
        this.hotel_name = hotel_name;
    }

    public String getHotel_address() {
        return hotel_address;
    }

    public void setHotel_address(String hotel_address) {
        this.hotel_address = hotel_address;
    }

    public String getFcm_token() {
        return fcm_token;
    }

    public void setFcm_token(String fcm_token) {
        this.fcm_token = fcm_token;
    }

    public String getHotel_email() {
        return hotel_email;
    }

    public void setHotel_email(String hotel_email) {
        this.hotel_email = hotel_email;
    }

    public String getHotel_phnno() {
        return hotel_phnno;
    }

    public void setHotel_phnno(String hotel_phnno) {
        this.hotel_phnno = hotel_phnno;
    }

    public String getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(String hotel_id) {
        this.hotel_id = hotel_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getSelected_room() {
        return selected_room;
    }

    public void setSelected_room(String selected_room) {
        this.selected_room = selected_room;
    }

    public String getCheck_in_time() {
        return check_in_time;
    }

    public void setCheck_in_time(String check_in_time) {
        this.check_in_time = check_in_time;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getRooms_booked() {
        return rooms_booked;
    }

    public void setRooms_booked(String rooms_booked) {
        this.rooms_booked = rooms_booked;
    }

    public String getCheck_in_date() {
        return check_in_date;
    }

    public void setCheck_in_date(String check_in_date) {
        this.check_in_date = check_in_date;
    }

    public String getUser_phnno() {
        return user_phnno;
    }

    public void setUser_phnno(String user_phnno) {
        this.user_phnno = user_phnno;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBooking_Status() {
        return booking_Status;
    }

    public void setBooking_Status(String booking_Status) {
        this.booking_Status = booking_Status;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getCheck_out_time() {
        return check_out_time;
    }

    public void setCheck_out_time(String check_out_time) {
        this.check_out_time = check_out_time;
    }
}
