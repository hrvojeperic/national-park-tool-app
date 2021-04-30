package com.codingproject.nationalparktool.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.codingproject.nationalparktool.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread background = new Thread() {
            public void run() {
                try {
                    // sleep
                    sleep(3000);
                    // start login screen
                    Intent i=new Intent(getBaseContext(), LoginActivity.class);
                    startActivity(i);
                    finish();
                } catch (Exception e) {
                    Log.i("SplashActivity.java", "Error: Splash screen not working correctly.");
                }
            }
        };

        // start thread
        background.start();

    }
}