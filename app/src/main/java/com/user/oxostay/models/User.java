package com.user.oxostay.models;

public class User {

    String name,mobile_no,profile_pic,ref_code;

    public User(){

    }

    public User(String name, String mobile_no, String profile_pic, String ref_code) {
        this.name = name;
        this.mobile_no = mobile_no;
        this.profile_pic = profile_pic;
        this.ref_code = ref_code;
    }

    public String getRef_code() {
        return ref_code;
    }

    public void setRef_code(String ref_code) {
        this.ref_code = ref_code;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }
}
