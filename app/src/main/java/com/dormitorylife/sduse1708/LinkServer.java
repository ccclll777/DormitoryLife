package com.dormitorylife.sduse1708;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LinkServer {
    final static int SUCCESS=1;
    final static int FAILED=0;
    OkHttpClient client=new OkHttpClient();

    //登录
    public int login(String student_id,String password){
        Request request=new Request.Builder()
                .url("http://39.105.43.226/DormitoryLife/Login.php?student_id="+student_id)
                .build();
        try {
            Response response=client.newCall(request).execute();
            String responseData=response.body().string();
            JSONArray jsonArray=new JSONArray(responseData);
            JSONObject jsonObject=jsonArray.getJSONObject(0);
            String passwordServer=jsonObject.getString("PASSWORD");
            if(passwordServer.equals(password))
                return SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
        }
        return FAILED;
    }

    //个人信息
    public String[] student(String student_id){
        Request request=new Request.Builder()
                .url("http://39.105.43.226/DormitoryLife/Student.php?student_id="+student_id)
                .build();
        try{
            Response response=client.newCall(request).execute();
            String responseData=response.body().string();
            JSONParser jsonParser=new JSONParser(responseData);
            String []info=jsonParser.parseSingle();
            return info;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //全员信息
    public ArrayList<String[]> student_all(String dorm_building,String dorm_room){
        Request request=new Request.Builder()
                .url("http://39.105.43.226/DormitoryLife/Room.php?dorm_building="+dorm_building+
                "&dorm_room="+dorm_room)
                .build();
        try{
            Response response=client.newCall(request).execute();
            String responseData=response.body().string();
            JSONParser jsonParser=new JSONParser(responseData);
            ArrayList<String[]> info=jsonParser.parseALL();
            return info;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //更新位置
    public void updateLocation(String student_id,String latitude,String longitude){
        Request request=new Request.Builder()
                .url("http://39.105.43.226/DormitoryLife/updateLocation.php?student_id="+
                        student_id+"&latitude="+latitude+"&longitude="+longitude)
                .build();
        try{
            client.newCall(request).execute();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //查询洗衣机微波炉使用状况
    public ArrayList<String[]> queryMachineStatus(String dorm_id){
        Request request=new Request.Builder()
                .url("http://39.105.43.226/DormitoryLife/queryMachineSituation.php?dorm_id"+dorm_id)
                .build();
        try{
            Response response=client.newCall(request).execute();
            String responseData=response.body().string();
            JSONParser jsonParser=new JSONParser(responseData);
            ArrayList<String[]> info=jsonParser.parseMachine();
            return info;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //
}
