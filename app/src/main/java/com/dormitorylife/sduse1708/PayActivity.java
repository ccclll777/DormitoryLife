package com.dormitorylife.sduse1708;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class PayActivity extends AppCompatActivity implements View.OnClickListener {

    private Button pay_electricity;
    private Button pay_water;
    private Button pay_intent;
    private Button pay_card;
    private Button pay_fees;
    private WebView pay_web_view;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        findview();
    }
    private void findview()
    {
        pay_electricity = (Button)findViewById(R.id.pay_electricity);
        pay_water = (Button)findViewById(R.id.pay_water);
        pay_intent = (Button)findViewById(R.id.pay_intent);
        pay_card = (Button)findViewById(R.id.pay_card);
        pay_fees = (Button)findViewById(R.id.pay_fees);
        pay_fees.setOnClickListener(this);
        pay_electricity.setOnClickListener(this);
        pay_water.setOnClickListener(this);
        pay_intent.setOnClickListener(this);
        pay_card.setOnClickListener(this);
    }

    @Override



    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pay_electricity:
                Intent intent2 = new Intent(this, PayElectricityActivity.class);
                startActivity(intent2);



                break;
            case R.id.pay_water:


                break;
            case R.id.pay_fees:
                Intent intent1 = new Intent(this, PayWebActivity.class);
                startActivity(intent1);
                break;
            case R.id.pay_intent:


                break;
            case R.id.pay_card:


                break;


        }
    }
}
