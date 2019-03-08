package com.dormitorylife.sduse1708;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

public class UserAdapter extends ArrayAdapter<User> {
    private int resourceId;
    private TextView tv1;
    private  TextView tv2;
    public UserAdapter(Context context, int textViewResourseId, List<User> obiescts) {
        super(context, textViewResourseId, obiescts);
        resourceId = textViewResourseId;
    }
    public View getView(int position, View convertView, ViewGroup viewGroup)
    {
        User user = getItem(position);
        //优化listview的运行效率
        View view;

        if(convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, viewGroup, false);
        }
        else
        {
            view = convertView;
        }
        tv1 = (TextView)view.findViewById(R.id.tv_name);
        tv2 = (TextView)view.findViewById(R.id.tv_dormitory);

        //将arraylist中的数据写入listview
        tv1.setText("舍友:"+user.getusername());
        DecimalFormat df1 = new DecimalFormat("###.0");
//        tv2.setText(" 宿舍："+user.getdormitory());


        return view;
    }

}
