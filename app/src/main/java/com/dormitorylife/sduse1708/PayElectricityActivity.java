package com.dormitorylife.sduse1708;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class PayElectricityActivity extends AppCompatActivity {

    private WebView pay_electricity_web;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_electricity);
        //可以在数据库根据不同的学校进入各自学校的交费界面
        String url  = "http://www.stuzf.sdu.edu.cn/wxapp/api/user/loginOut";
        FindWebView(url);

    }
    private void FindWebView(String url)
    {
        pay_electricity_web= (WebView)findViewById(R.id.pay_electricity_web);
        pay_electricity_web.getSettings().setJavaScriptEnabled(true);
        pay_electricity_web.getSettings().setUseWideViewPort(true);
        pay_electricity_web.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        pay_electricity_web.getSettings().setLoadWithOverviewMode(true);
        pay_electricity_web.setWebViewClient(new WebViewClient());
        //可以在数据库根据不同的学校进入各自学校的交费界面
        pay_electricity_web.loadUrl(url);
    }
}
