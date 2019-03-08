package com.dormitorylife.sduse1708;

public class Announcement {

    private String username;

    private  String announcement;



    public Announcement(String username, String announcement){
        this.username=username;
        this.announcement  =announcement;


    }

    public String getUsername(){
        return this.username;
    }





    public String getAnnouncement(){return this.announcement; }
}
