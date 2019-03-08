package com.dormitorylife.sduse1708;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddAnnouncementActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText edit_title,edit_user,edit_describe;
    private Button btn_back,btn_true;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_announcement);
        findview();

    }


    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_add:
                geteditview();
                SetToSql();
                break;
            case R.id.btn_back:

                finish();
                //返回查看公告页面
                break;
        }

    }
    private void findview()
    {
        edit_title = (EditText)findViewById(R.id.edit_title);
        edit_user = (EditText)findViewById(R.id.edit_user);
        edit_describe = (EditText)findViewById(R.id.edit_describe);
        btn_back =(Button)findViewById(R.id.btn_back);
        btn_true = (Button)findViewById(R.id.btn_true);
        btn_true.setOnClickListener(this);
        btn_back.setOnClickListener(this);

    }
    //获取文字内容 将notice写入对应的user
    public void geteditview()
    {
        String Describe = edit_describe.getText().toString();
        String Username = edit_user.getText().toString();
        String Title = edit_title.getText().toString();
        String Notice = Username + "   "+Title +":"  +  Describe;
    }
    //将notice写入数据库
    public void SetToSql()
    {


    }




}
