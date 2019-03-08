package com.dormitorylife.sduse1708;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class DNoticeAdapter extends ArrayAdapter<DNotice> {
    private int resourceId;
    private TextView tv_notice ;

    public DNoticeAdapter(Context context, int textViewResourseId, List<DNotice> objescts) {
        super(context, textViewResourseId, objescts);
        resourceId = textViewResourseId;
    }
    public View getView(int position, View convertView, ViewGroup parent)
    {
        DNotice notice = getItem(position);
        //优化listview的运行效率
        View view;

        if(convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        }
        else
        {
            view = convertView;
        }
        tv_notice = (TextView)view.findViewById(R.id.tv_dor_notice);

        //将arraylist中的数据写入listview
        tv_notice.setText("公告:"+notice.getDnotice());


        return view;
    }
}
