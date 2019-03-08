package com.dormitorylife.sduse1708;

import cn.bmob.v3.BmobObject;

public class Notice  extends BmobObject{
    String student_name;
    String notice;

    public Notice(String student_name,String notice){
        this.student_name=student_name;
        this.notice=notice;
    }

    public String getStudent_name() {
        return student_name;
    }

    public String getNotice() {
        return notice;
    }
}
