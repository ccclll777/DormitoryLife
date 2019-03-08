package com.dormitorylife.sduse1708;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class CFragment extends Fragment {

    private Button notive_bt;
    private String context;
    private View view;
    private  Button money_bt;
    private Button service_bt;
    private Button dor_notice_bt;

    public static CFragment newInstance(String param1, String param2) {
        CFragment fragment = new CFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_c, container, false);
        //宿舍通知
        notive_bt = (Button) view.findViewById(R.id.notive_bt);
        notive_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),AnnouncementActivity.class);
                startActivity(intent);

            }
        });
        //费用缴纳
        money_bt = (Button)view.findViewById(R.id.money_bt);
        money_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getActivity(),PayActivity.class);
                startActivity(intent2);
            }
        });
        //故障维修
        service_bt = (Button)view.findViewById(R.id.service_bt);
        service_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(getActivity(),FixActivity.class);
                startActivity(intent3);
            }
        });
        //公寓通知
        dor_notice_bt = (Button)view.findViewById(R.id.dor_notice_bt);
        dor_notice_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4 = new Intent(getActivity(),DNoticeActivity.class);
                startActivity(intent4);
            }
        });

        return view;
    }
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        }


}
