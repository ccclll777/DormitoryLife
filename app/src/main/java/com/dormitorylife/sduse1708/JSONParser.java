package com.dormitorylife.sduse1708;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONParser {
    String responseData;

    //构造函数
    public JSONParser(String responseData){
        this.responseData=responseData;
    }

    //处理个人信息
    public String[] parseSingle(){
        String[] info=new String[11];
        try {
            JSONArray jsonArray=new JSONArray(responseData);
            JSONObject jsonObject=jsonArray.getJSONObject(0);
            String student_id=jsonObject.getString("STUDENT_ID");
            String student_name=jsonObject.getString("STUDENT_NAME");
            String university=jsonObject.getString("UNIVERSITY");
            String area=jsonObject.getString("AREA");
            String school=jsonObject.getString("SCHOOL");
            String major=jsonObject.getString("MAJOR");
            String grade=jsonObject.getString("CLASS");
            String dormBuilding=jsonObject.getString("DORM_BUILDING");
            String dormRoom=jsonObject.getString("DORM_ROOM");
            String latitude=jsonObject.getString("LATITUDE");
            String longitude=jsonObject.getString("LONGITUDE");
            info[0]=student_id;
            info[1]=student_name;
            info[2]=university;
            info[3]=area;
            info[4]=school;
            info[5]=major;
            info[6]=grade;
            info[7]=dormBuilding;
            info[8]=dormRoom;
            info[9]=latitude;
            info[10]=longitude;
        }catch (Exception e){
            e.printStackTrace();
        }
        return info;
    }

    //处理组员信息
    public ArrayList<String[]> parseALL(){
        ArrayList<String[]>arrayList=new ArrayList<>();
        try {
            JSONArray jsonArray=new JSONArray(responseData);
            for(int i=0;i<jsonArray.length();i++){
                String info[]=new String[11];
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                String student_id=jsonObject.getString("STUDENT_ID");
                String student_name=jsonObject.getString("STUDENT_NAME");
                String university=jsonObject.getString("UNIVERSITY");
                String area=jsonObject.getString("AREA");
                String school=jsonObject.getString("SCHOOL");
                String major=jsonObject.getString("MAJOR");
                String grade=jsonObject.getString("CLASS");
                String dormBuilding=jsonObject.getString("DORM_BUILDING");
                String dormRoom=jsonObject.getString("DORM_ROOM");
                String latitude=jsonObject.getString("LATITUDE");
                String longitude=jsonObject.getString("LONGITUDE");
                info[0]=student_id;
                info[1]=student_name;
                info[2]=university;
                info[3]=area;
                info[4]=school;
                info[5]=major;
                info[6]=grade;
                info[7]=dormBuilding;
                info[8]=dormRoom;
                info[9]=latitude;
                info[10]=longitude;
                arrayList.add(info);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return arrayList;
    }

    //处理洗衣机微波炉情况
    public ArrayList<String[]> parseMachine(){
        ArrayList<String[]>arrayList=new ArrayList<>();
        try{
            JSONArray jsonArray=new JSONArray(responseData);
            for(int i=0;i<jsonArray.length();i++){
                String[] info=new String[2];
                JSONObject jsonObject=jsonArray.getJSONObject(0);
                String Machine=jsonObject.getString("WASHER_ID");
                String Status=jsonObject.getString("STATUS");
                info[0]=Machine;
                info[1]=Status;
                arrayList.add(info);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return arrayList;
    }
}
