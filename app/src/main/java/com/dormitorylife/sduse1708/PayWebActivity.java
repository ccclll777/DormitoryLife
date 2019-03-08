package com.dormitorylife.sduse1708;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.URL;

public class PayWebActivity extends AppCompatActivity {


    private WebView pay_web_view;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_web);
        //可以在数据库根据不同的学校进入各自学校的交费界面

        String url = "http://pay.sdu.edu.cn";
        FindWebView(url);

    }
    public void FindWebView(String url)
    {
        pay_web_view = (WebView)findViewById(R.id.pay_web_view);
        pay_web_view.getSettings().setJavaScriptEnabled(true);
        pay_web_view.getSettings().setUseWideViewPort(true);
        pay_web_view.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        pay_web_view.getSettings().setLoadWithOverviewMode(true);
        pay_web_view.setWebViewClient(new WebViewClient());
        //可以在数据库根据不同的学校进入各自学校的交费界面
        pay_web_view.loadUrl(url);
    }

}
