package com.dormitorylife.sduse1708;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FixActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_back_fix,btn_true_fix,btn_add_fix;
    private EditText edit_user_fix,edit_describe_fix;
    private Button call_fix;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fix);
        //按钮注册
        findtopbutton();
        //exitview中文字的提取
        findview();

    }
    //按钮注册
    private void findtopbutton()

    {
        btn_true_fix = (Button)findViewById(R.id.btn_true_fix);
        btn_back_fix = (Button)findViewById(R.id.btn_back_fix);
        btn_add_fix = (Button)findViewById(R.id.btn_add_fix);
        call_fix = (Button)findViewById(R.id.call_fix);
        call_fix.setOnClickListener(this);
        btn_back_fix.setOnClickListener(this);
        btn_true_fix.setOnClickListener(this);
        btn_add_fix.setOnClickListener(this);
    }

    //点击事件
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_true_fix:
                //将得到的数据传到维修人员处
                Toast.makeText(FixActivity.this, "您的请求已传到维修人员处", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_back_fix:

                finish();
                //返回
                break;
            case R.id.btn_add_fix:
                //将得到的数据传到维修人员处
                Toast.makeText(FixActivity.this, "您的请求已传到维修人员处", Toast.LENGTH_SHORT).show();
                break;
            case R.id.call_fix:
                //拨打电话
                call("10010");

                break;

        }

    }
    private void findview()
    {
        //获取到用户报修的内容
        edit_user_fix = (EditText)findViewById(R.id.edit_user_fix);
        edit_describe_fix = (EditText)findViewById(R.id.edit_describe_fix);
        String Fix_Describe = edit_describe_fix.getText().toString();
        String Fix_dor = edit_user_fix.getText().toString();

    }
    private void call(String phone)
    {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

}
