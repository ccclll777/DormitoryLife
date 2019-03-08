package com.dormitorylife.sduse1708;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobRealTimeData;
import cn.bmob.v3.listener.ValueEventListener;

public class AnnouncementActivity extends AppCompatActivity {

   private ListView Listview_announcement;
    private List<Announcement> announcements = new ArrayList<>();
    private Button btn_add;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String TAG = AnnouncementActivity.class.getSimpleName();
    private LinearLayout linearLayout;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anoucement);
        //连接数据库
        setBmobRealTimeData();
        //实现listview的更新
        findListView();
        //实现下拉刷新
        swipe();




    }
    //设置消息监听器
    public void setBmobRealTimeData(){
        final BmobRealTimeData bmobRealTimeData=new BmobRealTimeData();
        bmobRealTimeData.start(new ValueEventListener() {
            @Override
            public void onConnectCompleted(Exception e) {
                if(e==null){
                    Log.i(TAG,"连接情况：" + (bmobRealTimeData.isConnected() ? "已连接" : "未连接"));
                    if(bmobRealTimeData.isConnected()){
                        bmobRealTimeData.subTableUpdate("DNotice");
                        //导出历史信息的最新10条




                    }else {
                        Log.e(TAG,"监听失败:"+e.getMessage());
                    }
                }
            }

            @Override
            public void onDataChange(JSONObject jsonObject) {
                if (BmobRealTimeData.ACTION_UPDATETABLE.equals(jsonObject.optString("action"))) {
                    JSONObject data = jsonObject.optJSONObject("data");
                    Log.i(TAG, "更新返回内容是：" + data.toString());
                    Announcement announcement =new Announcement(data.optString("student_name"),data.optString("dnotice"));
                    announcements.add(announcement);
                    TextView tv=new TextView(AnnouncementActivity.this);
                    tv.setText("收到更新："+ announcement.getAnnouncement());
                    tv.setTextSize(15);
                    linearLayout.addView(tv);
                }
            }
        });
    }


    private void swipe()
    {
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //重新在服务端查询数据
                refreshNotice();
            }
        });
    }
    private void refreshNotice()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    public void run() {
                        //在服务端查询是否有新的数据生成
                        findListView();
                        swipeRefreshLayout.setRefreshing(false);
                        }
                });
            }
        }).start();
    }

    //notive 的listview的注册
    private void findListView()
    {
        AnnouncementAdapter adapter = new AnnouncementAdapter(this,R.layout.list_announcement, announcements);
        Listview_announcement = (ListView)findViewById(R.id.listview_announcement);
        Listview_announcement.setAdapter(adapter);
        Listview_announcement.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            //listview 的 点击事件
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Announcement announcement = announcements.get(i);



            }
        });
        //顶部添加公告按钮
        btn_add = (Button)findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AnnouncementActivity.this,AddAnnouncementActivity.class);
                startActivity(intent);
            }
        });

    }
    //将数据库中的公告及其宿舍 username 写入arraylist ，现在先模拟数据库，写入几组数据
//    private void getNotice()
//    {
//        Announcement n1 = new Announcement("超超弟弟","木头哥哥该回宿舍了");
//        announcements.add(n1);
//        Announcement n2 = new Announcement("木头哥哥","6666666666666666");
//        announcements.add(n2);
//
//    }
}
