package com.example.emi_master;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class Privacy_police extends AppCompatActivity {

    ExtendedFloatingActionButton BTN_accept, BTN_decline;
    CheckBox chk_privacy;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_police);


          // Initialize shared preferences
            sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

            // Check if the user has already agreed to the terms and conditions
            boolean agreed = sharedPreferences.getBoolean("agreed", false);
            if (agreed) {
                // If agreed, directly navigate to the next activity
                startActivity(new Intent(Privacy_police.this, StartActivity.class));
                finish(); // Finish this activity to prevent going back to it
                return; // Return from onCreate
            }

            setContentView(R.layout.activity_privacy_police);

            chk_privacy = findViewById(R.id.chk_privacy);
            BTN_accept = findViewById(R.id.BTN_accept);
            BTN_decline = findViewById(R.id.BTN_decline);

            BTN_decline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            BTN_accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (chk_privacy.isChecked()) {
                        // If terms accepted, save the agreement status in shared preferences
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("agreed", true);
                        editor.apply();

                        // Proceed to the next activity (corrected typo for BottomNavActivity)
                        startActivity(new Intent(Privacy_police.this, StartActivity.class));
                        finish();
                    } else {
                        Toast.makeText(Privacy_police.this, "Please accept terms & conditions", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        @SuppressLint("MissingSuperCall")
        @Override
        public void onBackPressed() {
            finish();
        }

}