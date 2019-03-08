package com.dormitorylife.sduse1708;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NoticeActivity extends AppCompatActivity {
    private String TAG = NoticeActivity.class.getSimpleName();
    private ListView listView_notice;

    private List<Notice> Notices = new ArrayList<>();
    private NoticeAdapter NoticeAdapter;
    private LinearLayout linearLayout;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        getNotice();
        findListView();
    }
    //notive 的listview的注册
    private void findListView()
    {

        NoticeAdapter = new NoticeAdapter(NoticeActivity.this,R.layout.list_notice, Notices);
        listView_notice = (ListView)findViewById(R.id.listview_notice);
        listView_notice.setAdapter(NoticeAdapter);
        listView_notice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            //listview 的 点击事件
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Notice notice = Notices.get(i);



            }
        });
        //顶部添加公告按钮


    }
        private void getNotice()
    {
       Notice n1 = new Notice("超超弟弟","木头哥哥该回宿舍了");
        Notices.add(n1);
        Notice n2 = new  Notice("木头哥哥","6666666666666666");
        Notices.add(n2);

    }
}
