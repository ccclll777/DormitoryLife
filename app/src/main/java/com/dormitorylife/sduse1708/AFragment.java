package com.dormitorylife.sduse1708;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class AFragment extends Fragment implements View.OnClickListener {

    private ListView lv;
    private List<User> user = new ArrayList<>();
    private TextView mTextView;
    private String context;
    private View view;
    private SwipeRefreshLayout swipeRefreshLayout_chat;
    private LinearLayout LinearLayout_dor;
    private LinearLayout LinearLayout_announcemenet;
    private LinearLayout LinearLayout_notice;
    private TextView tv_in_notice;
    private TextView tv_in_dor;

    private TextView tv_in_announcement;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_a,container,false);



        return view;

    }
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //注册控件
        findview();
        //listview

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            //listview 的 点击事件
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                User user1 = user.get(i);
                Intent intent = new Intent(getActivity(),ChatActivity.class);
                startActivity(intent);
            }
        });
        //实现下拉刷新
        swipe();


    }
    //注册控件
    private void findview()
    {//listview
        UserAdapter adapter = new UserAdapter(getActivity(),R.layout.list_user,user);
        lv = (ListView)getActivity().findViewById(R.id.lv1);
        lv.setAdapter(adapter);
        //LinearLayout
        LinearLayout_dor = (LinearLayout)getActivity().findViewById(R.id.LinearLayout_dor);
        LinearLayout_notice=(LinearLayout)getActivity().findViewById(R.id.LinearLayout_notice);
        LinearLayout_announcemenet=(LinearLayout)getActivity().findViewById(R.id.LinearLayout_announcement);
        LinearLayout_dor.setOnClickListener(this);
        LinearLayout_announcemenet.setOnClickListener(this);
        LinearLayout_notice.setOnClickListener(this);
        tv_in_dor = (TextView)getActivity().findViewById(R.id.tv_in_dor);
        tv_in_notice = (TextView)getActivity().findViewById(R.id.tv_in_notice);
        tv_in_announcement=(TextView)getActivity().findViewById(R.id.tv_in_announcement);

    }
    //点击事件
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.LinearLayout_dor:
                Intent intent = new Intent(getActivity(),DNoticeActivity.class);
                startActivity(intent);
                break;
            case R.id.LinearLayout_notice:
                Intent intent2 = new Intent(getActivity(),NoticeActivity.class);
                startActivity(intent2);
                break;
            case R.id.LinearLayout_announcement:
                Intent intent1 = new Intent(getActivity(),AnnouncementActivity.class);
                startActivity(intent1);
                break;

        }
    }
    //实现下拉刷新
    private void swipe()
    {
        swipeRefreshLayout_chat = (SwipeRefreshLayout)getActivity().findViewById(R.id.swipe_chat);
        swipeRefreshLayout_chat.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout_chat.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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
               getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        //在服务端查询是否有新的数据生成

                        swipeRefreshLayout_chat.setRefreshing(false);
                    }
                });
            }
        }).start();
    }


}
