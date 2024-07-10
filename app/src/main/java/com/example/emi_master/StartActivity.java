package com.example.emi_master;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class StartActivity extends AppCompatActivity {
    CardView calculation;
    LinearLayout  rate, share, privacy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        calculation = findViewById(R.id.calculation);
        rate = findViewById(R.id.rate);
        share = findViewById(R.id.share);
        privacy = findViewById(R.id.privacy);

        calculation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareApp();
            }
        });
        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.freeprivacypolicy.com/live/cb1225b8-eb72-4959-83dc-e3e3e0e1a640")));

            }
        });
        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Replace "your_package_name" with your app's package name
                String packageName = getPackageName();

                // Uri to open your app in the Play Store
                Uri uri = Uri.parse("market://details?id=" + packageName);

                // Create an Intent with the action view
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);

                // Set the flag to open the Play Store in a new task
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                // Check if there is an app that can handle this intent
                if (intent.resolveActivity(getPackageManager()) != null) {
                    // Open the Play Store
                    startActivity(intent);
                } else {
                    // If Play Store is not available, open in browser
                    intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + packageName));
                    startActivity(intent);
                }
            }
        });

    }
    private void shareApp() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Check out this awesome app on Play Store: https://play.google.com/store/apps/details?id=" + getPackageName());
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }
}