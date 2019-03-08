package com.dormitorylife.sduse1708;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG="ChatActivity";


    private String myName="",toWho="";

    private Button button;
    private EditText editText;
    private TextView textView,bt;
    private LinearLayout linearLayout;
    private Button btn_chat_back;
    private TextView tv_user;
    private TextView btn_Chatrecord;





    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_chat);
        Intent intent=getIntent();
        myName=intent.getStringExtra("myName");
        toWho=intent.getStringExtra("toWho");

        findView();
    }
    private void findView() {

        linearLayout=(LinearLayout)findViewById(R.id.line);
        btn_chat_back=(Button)findViewById(R.id.btn_chat_back);
        tv_user = (TextView)findViewById(R.id.tv_user);
        btn_Chatrecord= (TextView)findViewById(R.id.btn_Chatrecord);
        button=(Button)findViewById(R.id.txt_send);
        editText=(EditText)findViewById(R.id.edit_context);
        //发送按钮的点击事件
        btn_chat_back.setOnClickListener(this);

        btn_Chatrecord.setOnClickListener(this);
        button.setOnClickListener(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!editText.getText().toString().equals("")) {
                    TextView tv = new TextView(ChatActivity.this);
                    tv.setText("     我说：" + editText.getText().toString());
                    tv.setTextSize(15);
                    linearLayout.addView(tv);
                }
            }
        });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_chat_back:
               finish();
                break;
            case R.id.btn_Chatrecord:


                //跳入聊天记录页面
                break;


        }
    }
//连接websocket的点击事件
//    private void connect(){
//        try{
//            mConnect.connect(wsurl,new WebSocketHandler(){
//                @Override
//                public void onOpen() {
//                    sendUsername();
//                }
//                @Override
//                public void onTextMessage(final String payload) {
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            final String[] message=payload.split("@",2);
//                            server add=new test();
//                            if(message[0].equals("404")){
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        TextView tv=new TextView(ChatActivity.this);
//                                        tv.setText("     我说："+message[1]+"\n");
//                                        tv.setTextSize(15);
//                                        linearLayout.addView(tv);
//                                    }
//                                });
//                                add.addMessage(myName,toWho,"我说："+message[1]);
//                                add.addMessage(toWho,myName,myName+"说："+message[1]);
//                            }else{
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        TextView tv=new TextView(ChatActivity.this);
//                                        tv.setText("     "+message[1]+"\n");
//                                        tv.setTextSize(15);
//                                        linearLayout.addView(tv);
//                                    }
//                                });
//                                add.addMessage(myName,toWho,message[1]);
//                            }
//                        }
//                    }).start();
//                }
//                @Override
//                public void onClose(int code, String reason) {
//
//                }
//            });
//        }catch (WebSocketException e){
//            e.printStackTrace();
//        }
//    }

//    private void sendUsername(){
//        mConnect.sendTextMessage(myName);
//    }
//
//    private void sendMessage(String message){
//        if (mConnect.isConnected()) {
//            mConnect.sendTextMessage(message);
//        } else {
//            Toast.makeText(getApplicationContext(), "no connection!", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    @Override
//    protected void onDestroy(){
//        super.onDestroy();
//        mConnect.disconnect();
//    }
}
