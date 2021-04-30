package com.codingproject.nationalparktool.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codingproject.nationalparktool.R;

public class LoginActivity extends AppCompatActivity {

    // constants for sign-in verification
    private static final String USERNAME = "admin@gmail.com";
    private static final String PASSWORD = "admin";

    // declare gui widgets
    private EditText userName;
    private EditText password;
    private TextView signIn;
    private TextView registerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // get gui widgets
        userName = (EditText) findViewById(R.id.username_input);
        password = (EditText) findViewById(R.id.pass);
        signIn = (TextView) findViewById(R.id.buttonSignIn);
        registerTextView = (TextView) findViewById(R.id.registerTextView);

        // event handler for register button
        registerTextView.setOnClickListener(v -> {
            Intent myIntent = new Intent(this, RegisterActivity.class);
            startActivity(myIntent);
        });

        // event handler for sign-in button
        signIn.setOnClickListener(v -> {
            if (userName.getText().toString().equals(USERNAME) && password.getText().toString().equals(PASSWORD)) { // valid credentials
                // start national park list intent
                Intent myIntent = new Intent(this, NationalParkListActivity.class);
                startActivity(myIntent);
            }
            else { // invalid credentials
                Toast.makeText(getApplicationContext(), "Invalid Username or Password", Toast.LENGTH_SHORT).show();
            }
        });
    }
}