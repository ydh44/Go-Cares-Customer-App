package com.example.escort;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;



public class LoginActivity extends AppCompatActivity {
    Button buttonReg;
    private Object Intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        buttonReg = (Button) findViewById(R.id.buttonreg);

        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent = new Intent (LoginActivity.this, RegActivity.class);
                startActivity((android.content.Intent) Intent);
                finish();
            }
        });
    }
}