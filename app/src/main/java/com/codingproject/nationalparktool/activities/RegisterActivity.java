package com.codingproject.nationalparktool.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.codingproject.nationalparktool.R;

public class RegisterActivity extends AppCompatActivity {

    // declare gui variables
    private TextView buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // find gui element
        buttonRegister = findViewById(R.id.buttonRegister);

        // event handler for register button
        buttonRegister.setOnClickListener(v -> {
            Intent myIntent = new Intent(this, NationalParkListActivity.class);
            startActivity(myIntent);
        });
    }
}