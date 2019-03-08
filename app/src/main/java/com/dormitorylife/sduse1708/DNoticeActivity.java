package com.dormitorylife.sduse1708;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobRealTimeData;
import cn.bmob.v3.listener.ValueEventListener;

public class DNoticeActivity extends AppCompatActivity {
    private String TAG = DNoticeActivity.class.getSimpleName();
    private ListView lv_dor_notice;
    private TextView tv_history;
    private List<DNotice> DNotices = new ArrayList<>();
    private DNoticeAdapter Adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout linearLayout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dor_notice);
        setBmobRealTimeData();
        findListView();
        //实现下拉刷新
        swipe();
        //查询消息记录
        findHistory();
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
                    DNotice dNotice=new DNotice(data.optString("student_name"),data.optString("dnotice"));
                    DNotices.add(dNotice);
                    TextView tv=new TextView(DNoticeActivity.this);
                    tv.setText("收到更新："+dNotice.getDnotice());
                    tv.setTextSize(15);
                    linearLayout.addView(tv);
                }
            }
        });
    }



    //查询历史消息
    private void findHistory() {
        tv_history = (TextView)findViewById(R.id.tv_history);
        tv_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    private void swipe() {
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe2);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //重新在服务端查询数据
                refreshNotice();
            }
        });
    }

    private void refreshNotice() {
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
    private void findListView() {
        Adapter = new DNoticeAdapter(DNoticeActivity.this,R.layout.list_dor_notice,DNotices);
        lv_dor_notice = (ListView)findViewById(R.id.lv_dor_notice);
        lv_dor_notice.setAdapter(Adapter);
        lv_dor_notice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            //listview 的 点击事件
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DNotice notice = DNotices.get(i);
                }
        });


    }



}
