package com.example.githubauthentication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity
{

    TextView tvLoadEmail;
    Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        tvLoadEmail = findViewById(R.id.txt);
        btnLogout = findViewById(R.id.btnlogout);

        String githubEmail = getIntent().getStringExtra("githubname");
        tvLoadEmail.setText(githubEmail);
    }
}