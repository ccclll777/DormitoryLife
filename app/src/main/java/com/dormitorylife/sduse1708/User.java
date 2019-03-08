package com.dormitorylife.sduse1708;

public class User {
    private String username;
//    private double distance;
    private  String dormitory;



    public User(String username,String dormitory){
        this.username=username;
//        this.distance = distance;
        this.dormitory = dormitory;

    }

    public String getusername(){
        return this.username;
    }

    public String getdormitory(){
        return this.dormitory;
    }


//    public double getDistance() {
//        return distance;
//    }
}
