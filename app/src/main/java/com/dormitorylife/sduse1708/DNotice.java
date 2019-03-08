package com.dormitorylife.sduse1708;

import cn.bmob.v3.BmobObject;

public class DNotice extends BmobObject {
    String student_name;
    String dnotice;

    public DNotice(String student_name,String notice){
        this.student_name=student_name;
        this.dnotice=notice;
    }

    public String getStudent_name() {
        return student_name;
    }

    public String getDnotice() {
        return dnotice;
    }
}
