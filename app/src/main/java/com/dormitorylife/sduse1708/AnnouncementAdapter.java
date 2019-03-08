package com.dormitorylife.sduse1708;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class AnnouncementAdapter extends ArrayAdapter<Announcement> {

    private int resourceId;
    private TextView tv_announcement ,tv_username;
    private  TextView tv2;
    public AnnouncementAdapter(Context context, int textViewResourseId, List<Announcement> obiescts) {
        super(context, textViewResourseId, obiescts);
        resourceId = textViewResourseId;
    }
    public View getView(int position, View convertView, ViewGroup viewGroup)
    {
        Announcement announcement = getItem(position);
        //优化listview的运行效率
        View view;

        if(convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, viewGroup, false);
        }
        else
        {
            view = convertView;
        }
        tv_announcement = (TextView)view.findViewById(R.id.tv_notice);
        tv_username= (TextView)view.findViewById(R.id.tv_username);
        //将arraylist中的数据写入listview
        tv_announcement.setText("公告:"+ announcement.getAnnouncement());
        tv_username.setText("用户:"+ announcement.getUsername());



        return view;
    }

}
