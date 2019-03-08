package com.dormitorylife.sduse1708;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Personal_InformationActivity extends AppCompatActivity implements View.OnClickListener {

    public TextView tv_name,tv_university,tv_area,tv_school,tv_major,tv_class,tv_stuID,tv_dormBuilding,tv_dormRoom;
    private TextView btn_Back_p;
    private Context context;
    public String[] info;
    public String name,university,area,school,major,grade,stuID,dormBuilding,dormRoom;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal__informatin);
        Intent intent=getIntent();
        stuID=intent.getStringExtra("stuID");
        context=Personal_InformationActivity.this;
        findView();
        getInfo();
    }
    private void findView()
    {
        tv_name=(TextView)findViewById(R.id.tv_myInfo_name);
        tv_university=(TextView)findViewById(R.id.tv_myInfo_university);
        tv_area=(TextView)findViewById(R.id.tv_myInfo_area);
        tv_school=(TextView)findViewById(R.id.tv_myInfo_school);
        tv_major=(TextView)findViewById(R.id.tv_myInfo_major);
        tv_class=(TextView)findViewById(R.id.tv_myInfo_class);
        tv_stuID=(TextView)findViewById(R.id.tv_myInfo_stuID);
        tv_dormBuilding=(TextView)findViewById(R.id.tv_myInfo_dormBuilding);
        tv_dormRoom=(TextView)findViewById(R.id.tv_myInfo_dormRoom);
        btn_Back_p = (TextView) findViewById(R.id.btn_Back_p);
        btn_Back_p.setOnClickListener(this);
    }
    private void updateView(final Context context){
        ((Personal_InformationActivity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv_name.setText(name);
                tv_university.setText(university);
                tv_area.setText(area);
                tv_school.setText(school);
                tv_major.setText(major);
                tv_class.setText(grade);
                tv_stuID.setText(stuID);
                tv_dormBuilding.setText(dormBuilding);
                tv_dormRoom.setText(dormRoom);
            }
        });
    }

    private void getInfo(){
        new Thread(runnable).start();
    }
    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            LinkServer linkServer=new LinkServer();
            info=linkServer.student(stuID);
            stuID=info[0];
            name=info[1];
            university=info[2];
            area=info[3];
            school=info[4];
            major=info[5];
            grade=info[6];
            dormBuilding=info[7];
            dormRoom=info[8];
            updateView(context);
        }

    };

    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_Back_p:
               finish();
                break;
        }
    }
}
