package com.caregiver.gocares.views.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;

import com.caregiver.gocares.R;
import com.caregiver.gocares.utils.CekSession;
import com.caregiver.gocares.utils.SessionLog;

public class SplashActivity extends AppCompatActivity {
    Window window;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash2);

        int SPLASH_SCREEN_TIME = 550;
         Log.d("xx", "in: " + SessionLog.GetFcm(this));


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               {
                    if(new CekSession().getSession(getBaseContext())){
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        SplashActivity.this.startActivity(intent);
                        SplashActivity.this.finish();
                    }else {
                        Intent intent = new Intent(SplashActivity.this, WelcomeActivity.class);
                        SplashActivity.this.startActivity(intent);
                        SplashActivity.this.finish();
                    }
                }
            }
        }, SPLASH_SCREEN_TIME);

    }
}