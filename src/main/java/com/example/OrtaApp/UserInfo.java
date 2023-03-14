package com.example.OrtaApp;

public class UserInfo {

    boolean isOrganizer = false;
    String city = "";
    String gender = "";

    public UserInfo(boolean isOrganizerUser, String cityUser, String genderUser) {
        isOrganizer = isOrganizerUser;
        city = cityUser;
        gender = genderUser;
    }

    @Override
    public String toString() {
        return "UserInfo [isOrganizer=" + isOrganizer + ", city=" + city + ", gender=" + gender + "]";
    }

}
