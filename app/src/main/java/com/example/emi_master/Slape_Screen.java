package com.example.emi_master;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.TextView;

public class Slape_Screen extends AppCompatActivity {
    private TextView timeTextView;
    private static final long TIME_OUT = 6000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slape_screen);
        timeTextView = findViewById(R.id.time);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Finish the activity after the timeout
                finish();
            }
        }, TIME_OUT);
        new CountDownTimer(TIME_OUT, 1000) {

            public void onTick(long millisUntilFinished) {
                // Update the TextView with the remaining time
                timeTextView.setText("" + millisUntilFinished / 1000 + "");
            }

            public void onFinish() {
                startNextActivity();
                finish();
            }
        }.start();
    }
    private void startNextActivity() {
        Intent intent = new Intent(Slape_Screen.this, Privacy_police.class); // Replace NextActivity with your actual next activity
        startActivity(intent);
        finish(); // Finish current activity
    }
}