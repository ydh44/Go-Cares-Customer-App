package com.example.escort;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragprofile);

        SessionLog sessionLog = new SessionLog();
        if(sessionLog.GetEmail(this) == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }

    }
}