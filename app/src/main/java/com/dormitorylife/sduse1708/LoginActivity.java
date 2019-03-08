package com.dormitorylife.sduse1708;

import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {


    private Button btLogin;
    private EditText etUsername,etPassword;
    static private Boolean isTrue=false;
    private String id=null,password=null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getViewID();
        setListener();
    }
    //登录 按钮
    public void getViewID() {
        btLogin=(Button)findViewById(R.id.btn_login);
        etUsername=(EditText)findViewById(R.id.et_1);
        etPassword=(EditText)findViewById(R.id.et_2);
    }
    //获取用户名和密码
    private void setListener() {

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id=etUsername.getText().toString();
                password=etPassword.getText().toString();
                new Thread(runnable).start();
            }
        });



    }
    //开启线程  实现登录
    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            Looper.prepare();
            LinkServer linkServer=new LinkServer();
            if(linkServer.login(id,password)==1){
                String[] info=linkServer.student(id);
                String name=info[1];
                Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("id",id);
                intent.putExtra("name",name);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }else{
                Toast.makeText(LoginActivity.this,"用户名不存在或密码错误",Toast.LENGTH_SHORT).show();
            }
            Looper.loop();
        }
    };

}
