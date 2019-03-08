package com.dormitorylife.sduse1708;

import android.app.Application;

import cn.bmob.v3.Bmob;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this, "f25fe6dad5bca9d0bb090072ea1e3c65");
    }
}
