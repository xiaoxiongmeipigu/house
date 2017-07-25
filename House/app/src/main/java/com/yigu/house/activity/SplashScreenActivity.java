package com.yigu.house.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yigu.house.base.BaseActivity;
import com.yigu.house.util.ControllerUtil;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreenActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timer timer=new Timer();
        TimerTask task=new TimerTask()
        {
            @Override
            public void run(){
                if (!getversionCode().equals(userSP.getUserGuide())) {
                    ControllerUtil.go2Guide();
                    finish();
                }else{
                    ControllerUtil.go2Main();
                    finish();
                }

            }
        };
        timer.schedule(task,2*1000);
    }
}
