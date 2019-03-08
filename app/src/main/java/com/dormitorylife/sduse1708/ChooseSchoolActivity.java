package com.dormitorylife.sduse1708;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

public class ChooseSchoolActivity extends AppCompatActivity {
private Spinner spinner;
    ArrayAdapter<String> arrayAdapter;
    String[] str = { "山东大学","数控库中只有山东大学可以选择" };
private ImageView ib1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_school);

        FindSpinner();
        ib1 = (ImageView)findViewById(R.id.ib1);
        ib1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChooseSchoolActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
    private void FindSpinner()
    {
        spinner = (Spinner) findViewById(R.id.spinner);

        arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, str);


//        spinner.setPromptId(R.string.spinner_id);

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);



        spinner.setAdapter(arrayAdapter);
        //设置默认值
        spinner.setVisibility(View.VISIBLE);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String val = spinner.getItemAtPosition(i).toString();

            }
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }
}
