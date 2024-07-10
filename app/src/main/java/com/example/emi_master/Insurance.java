package com.example.emi_master;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Insurance extends AppCompatActivity implements DiamondGuideListener {
    TextView datanew;
    TextView titlename;
    ImageView insuranceback;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance);
        datanew = findViewById(R.id.Insurancedata);
        titlename = findViewById(R.id.title);
        insuranceback=findViewById(R.id.insuranceback);
        DiamondGuideListener listener = this;
        if (listener != null) {
            listener.onDataReceived(getIntent().getStringExtra("data"),getIntent().getStringExtra("title"));
        }

        insuranceback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void onDataReceived(String data,String title) {
        datanew.setText(data);
titlename.setText(title);
    }


}