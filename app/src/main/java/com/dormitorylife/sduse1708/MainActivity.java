package com.dormitorylife.sduse1708;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private final int A = 1;
    private final int B = 2;
    private final int C = 3;
    private int nowFragment = 1;
    private Button btn_A;
    private Button btn_B;
    private Button btn_C;
    private Button test;
    private AFragment AFragment;
    private BFragment BFragment;
    private CFragment CFragment;
    private static MainActivity instance;
    private int place=1;
    List<String> permissionList;
    private TextView AA;
    private TextView BB;
    private TextView CC;
    static public String student_id;
    static public String name;
    private NavigationView navigationView;
    private TextView name_tv;
    private TextView stuID_tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent=getIntent();
        student_id=intent.getStringExtra("id");
        name=intent.getStringExtra("name");
        permissionRequest();
        //按钮的注册
        findButton();
        //碎片的管理
        managerFragment();
        //抽屉的管理
        ToolbarManager();
    }

    //碎片的管理
    private void managerFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.frg_left,0);
        if (AFragment == null) {
            AFragment = new AFragment();
            transaction.add(R.id.fragment_layout, AFragment);
        } else {
            transaction.show(AFragment);
        }
        transaction.commit();
    }
    private void showFragment(int x,int last){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragment(transaction);
        switch (x){
            case A:
                if (last == 2 || last == 3){
                    transaction.setCustomAnimations(R.anim.frg_left,0);
                }
                if (AFragment == null){
                    AFragment = new AFragment();
                    transaction.add(R.id.fragment_layout, AFragment);
                } else {
                    transaction.show(AFragment);
                }
                break;
            case B:
                if (last == 1){
                    transaction.setCustomAnimations(R.anim.frg_right,0);
                }
                if (last == 3){
                    transaction.setCustomAnimations(R.anim.frg_left,0);
                }
                if (BFragment == null){
                    BFragment = new BFragment();
                    transaction.add(R.id.fragment_layout, BFragment);
                }else {
                    transaction.show(BFragment);
                }
                break;
            case C:
                if (last == 2 || last == 1){
                    transaction.setCustomAnimations(R.anim.frg_right,0);
                }
                if (CFragment == null){
                    CFragment = new CFragment();
                    transaction.add(R.id.fragment_layout, CFragment);
                }else {
                    transaction.show(CFragment);
                }
                break;
        }
        transaction.commit();
        nowFragment = x;

    }
    private void hideFragment(FragmentTransaction transaction){
        if(AFragment!=null){
            transaction.hide(AFragment);
        }
        if (BFragment!=null){
            transaction.hide(BFragment);
        }
        if (CFragment!=null){
            transaction.hide(CFragment);
        }
    }
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //按钮的注册
    private void findButton(){
    AA = (TextView)this.findViewById(R.id.A);
    BB = (TextView)this.findViewById(R.id.B);
    CC = (TextView)this.findViewById(R.id.C);
    AA.setOnClickListener(this);
    BB.setOnClickListener(this);
    CC.setOnClickListener(this);

    }
    //点击事件
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.A:
                if (nowFragment == A) {
                    return;
                }
                showFragment(A, place);
                place = 1;
                break;
            case R.id.B:
                if (nowFragment == B) {
                    return;
                }

                showFragment(B, place);
                place = 2;
                break;
            case R.id.C:
                if (nowFragment == C) {
                    return;
                }
                showFragment(C, place);
                place = 3;
                break;
        }
    }

    //导航栏
    private void ToolbarManager() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //侧滑更新信息
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        View view= navigationView.getHeaderView(0);
        name_tv = (TextView)view.findViewById(R.id.name_tv);
        stuID_tv = (TextView)view.findViewById(R.id.stuID_tv);
        stuID_tv.setText(student_id);
        name_tv.setText(name);
        navigationView.setNavigationItemSelectedListener(this);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_personal) {
            Intent intent = new Intent(MainActivity.this,Personal_InformationActivity.class);
            intent.putExtra("stuID",student_id);
            startActivity(intent);
        } else if (id == R.id.nav_update) {

        } else if (id == R.id.nav_back) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    //定位权限请求
    public void permissionRequest(){
        permissionList=new ArrayList<>();
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if(!permissionList.isEmpty()){
            String[]permissions=permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(this,permissions,1);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults){
        switch (requestCode){
            case 1:
                if(grantResults.length>0){
                    for(int result:grantResults){
                        if (result!= PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(this,"必须同意所有权限才能使用本程序", Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                }else{
                    Toast.makeText(this,"发生未知错误", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    //退出
    @Override
    protected void onDestroy() {
        BFragment.locationManager.removeUpdates(BFragment.locationListener);
        super.onDestroy();
    }
}
