package com.example.emi_master;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {

    LinearLayout emi, gst, vat, swp,breakevenpoint,sale,profit,margin,inflation,simple,fix,reccuring,
            compound,ppf,roi,advancesip,sip,interest,lumpsum,equity,intraday,
            delivery,futures,option,compare,advnce,interestrate,loan,loantenure,
            moratorium,cash,currancy,age,length,insurance,discount,time,
            temperature,bmi,weight,digital;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InternetCheck.checkInternetOnAppStart(this);
        emi = findViewById(R.id.emi);
        swp = findViewById(R.id.swp);
        gst = findViewById(R.id.gst);
        vat = findViewById(R.id.vat);
        breakevenpoint=findViewById(R.id.breakevenpoint);
        sale=findViewById(R.id.sale);
        lumpsum=findViewById(R.id.lumpsum);
        profit=findViewById(R.id.profit);
        margin=findViewById(R.id.margin);
        inflation=findViewById(R.id.inflation);
        simple=findViewById(R.id.simple);
        fix=findViewById(R.id.fix);
        reccuring=findViewById(R.id.reccuring);
        compound=findViewById(R.id.compound);
        ppf=findViewById(R.id.ppf);
        roi=findViewById(R.id.roi);
        advancesip=findViewById(R.id.advancesip);
        sip=findViewById(R.id.sip);
        interest=findViewById(R.id.interest);
        equity=findViewById(R.id.equity);
        intraday=findViewById(R.id.intraday);
        delivery=findViewById(R.id.delivery);
        futures=findViewById(R.id.futures);
        option=findViewById(R.id.option);
        compare=findViewById(R.id.compare);
        advnce=findViewById(R.id.advnce);
        interestrate=findViewById(R.id.interestrate);
        loan=findViewById(R.id.loan);
        loantenure=findViewById(R.id.loantenure);
        moratorium=findViewById(R.id.moratorium);
        cash=findViewById(R.id.cash);
        currancy=findViewById(R.id.currancy);
        length=findViewById(R.id.length);
        insurance=findViewById(R.id.insurance);
        discount=findViewById(R.id.discount);
        time=findViewById(R.id.time);
        temperature=findViewById(R.id.temperature);
        bmi=findViewById(R.id.bmi);
        weight=findViewById(R.id.weight);
        digital=findViewById(R.id.digital);
        age=findViewById(R.id.age);


        showInternetUsageDialog(this);












        emi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load Fragment1 into container_fragment1
                Intent intent = new Intent(MainActivity.this, Fregment_container.class);
                intent.putExtra("dailycheck", 101);

                startActivity(intent);
            }
        });
        gst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load Fragment1 into container_fragment1
                Intent intent = new Intent(MainActivity.this, Fregment_container.class);
                intent.putExtra("dailycheck", 102);

                startActivity(intent);
            }
        });
        vat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load Fragment1 into container_fragment1
                Intent intent = new Intent(MainActivity.this, Fregment_container.class);
                intent.putExtra("dailycheck", 103);

                startActivity(intent);
            }
        });
        swp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load Fragment1 into container_fragment1
                Intent intent = new Intent(MainActivity.this, Fregment_container.class);
                intent.putExtra("dailycheck", 104);

                startActivity(intent);
            }
        });


        breakevenpoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load Fragment1 into container_fragment1
                Intent intent = new Intent(MainActivity.this, Fregment_container.class);
                intent.putExtra("dailycheck", 107);

                startActivity(intent);
            }
        });
        sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load Fragment1 into container_fragment1
                Intent intent = new Intent(MainActivity.this, Fregment_container.class);
                intent.putExtra("dailycheck", 108);

                startActivity(intent);
            }
        });
        profit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load Fragment1 into container_fragment1
                Intent intent = new Intent(MainActivity.this, Fregment_container.class);
                intent.putExtra("dailycheck", 109);

                startActivity(intent);
            }
        });
        margin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load Fragment1 into container_fragment1
                Intent intent = new Intent(MainActivity.this, Fregment_container.class);
                intent.putExtra("dailycheck", 110);

                startActivity(intent);
            }
        });
        inflation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load Fragment1 into container_fragment1
                Intent intent = new Intent(MainActivity.this, Fregment_container.class);
                intent.putExtra("dailycheck", 111);

                startActivity(intent);
            }
        });
        simple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load Fragment1 into container_fragment1
                Intent intent = new Intent(MainActivity.this, Fregment_container.class);
                intent.putExtra("dailycheck", 112);

                startActivity(intent);
            }
        });
        fix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load Fragment1 into container_fragment1
                Intent intent = new Intent(MainActivity.this, Fregment_container.class);
                intent.putExtra("dailycheck", 113);

                startActivity(intent);
            }
        });
        reccuring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load Fragment1 into container_fragment1
                Intent intent = new Intent(MainActivity.this, Fregment_container.class);
                intent.putExtra("dailycheck", 114);

                startActivity(intent);
            }
        });
        compound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load Fragment1 into container_fragment1
                Intent intent = new Intent(MainActivity.this, Fregment_container.class);
                intent.putExtra("dailycheck", 115);

                startActivity(intent);
            }
        });
        ppf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load Fragment1 into container_fragment1
                Intent intent = new Intent(MainActivity.this, Fregment_container.class);
                intent.putExtra("dailycheck", 116);

                startActivity(intent);
            }
        });
        roi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load Fragment1 into container_fragment1
                Intent intent = new Intent(MainActivity.this, Fregment_container.class);
                intent.putExtra("dailycheck", 117);

                startActivity(intent);
            }
        });
        advancesip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load Fragment1 into container_fragment1
                Intent intent = new Intent(MainActivity.this, Fregment_container.class);
                intent.putExtra("dailycheck", 118);

                startActivity(intent);
            }
        });
        sip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load Fragment1 into container_fragment1
                Intent intent = new Intent(MainActivity.this, Fregment_container.class);
                intent.putExtra("dailycheck", 119);

                startActivity(intent);
            }
        });
        interest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load Fragment1 into container_fragment1
                Intent intent = new Intent(MainActivity.this, Fregment_container.class);
                intent.putExtra("dailycheck", 120);

                startActivity(intent);
            }
        });
        lumpsum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load Fragment1 into container_fragment1
                Intent intent = new Intent(MainActivity.this, Fregment_container.class);
                intent.putExtra("dailycheck", 121);

                startActivity(intent);
            }
        });
        equity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load Fragment1 into container_fragment1
                Intent intent = new Intent(MainActivity.this, Fregment_container.class);
                intent.putExtra("dailycheck", 122);

                startActivity(intent);
            }
        });
        intraday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load Fragment1 into container_fragment1
                Intent intent = new Intent(MainActivity.this, Fregment_container.class);
                intent.putExtra("dailycheck", 123);

                startActivity(intent);
            }
        });
        delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load Fragment1 into container_fragment1
                Intent intent = new Intent(MainActivity.this, Fregment_container.class);
                intent.putExtra("dailycheck", 124);

                startActivity(intent);
            }
        });
        futures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load Fragment1 into container_fragment1
                Intent intent = new Intent(MainActivity.this, Fregment_container.class);
                intent.putExtra("dailycheck", 125);

                startActivity(intent);
            }
        });
        option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load Fragment1 into container_fragment1
                Intent intent = new Intent(MainActivity.this, Fregment_container.class);
                intent.putExtra("dailycheck", 126);

                startActivity(intent);
            }
        });
        compare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load Fragment1 into container_fragment1
                Intent intent = new Intent(MainActivity.this, Fregment_container.class);
                intent.putExtra("dailycheck", 127);

                startActivity(intent);
            }
        });
        advnce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load Fragment1 into container_fragment1
                Intent intent = new Intent(MainActivity.this, Fregment_container.class);
                intent.putExtra("dailycheck", 128);

                startActivity(intent);
            }
        });
        interestrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load Fragment1 into container_fragment1
                Intent intent = new Intent(MainActivity.this, Fregment_container.class);
                intent.putExtra("dailycheck", 129);

                startActivity(intent);
            }
        });
        loan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load Fragment1 into container_fragment1
                Intent intent = new Intent(MainActivity.this, Fregment_container.class);
                intent.putExtra("dailycheck", 130);

                startActivity(intent);
            }
        });
        loantenure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load Fragment1 into container_fragment1
                Intent intent = new Intent(MainActivity.this, Fregment_container.class);
                intent.putExtra("dailycheck", 131);

                startActivity(intent);
            }
        });
        moratorium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load Fragment1 into container_fragment1
                Intent intent = new Intent(MainActivity.this, Fregment_container.class);
                intent.putExtra("dailycheck", 132);

                startActivity(intent);
            }
        });
        cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load Fragment1 into container_fragment1
                Intent intent = new Intent(MainActivity.this, Fregment_container.class);
                intent.putExtra("dailycheck", 133);

                startActivity(intent);
            }
        });
        currancy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load Fragment1 into container_fragment1
                Intent intent = new Intent(MainActivity.this, Fregment_container.class);
                intent.putExtra("dailycheck", 134);

                startActivity(intent);
            }
        });
        age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load Fragment1 into container_fragment1
                Intent intent = new Intent(MainActivity.this, Fregment_container.class);
                intent.putExtra("dailycheck", 135);

                startActivity(intent);
            }
        });
        length.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load Fragment1 into container_fragment1
                Intent intent = new Intent(MainActivity.this, Fregment_container.class);
                intent.putExtra("dailycheck", 136);

                startActivity(intent);
            }
        });
        insurance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load Fragment1 into container_fragment1
                Intent intent = new Intent(MainActivity.this, Fregment_container.class);
                intent.putExtra("dailycheck", 137);

                startActivity(intent);
            }
        });
        discount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load Fragment1 into container_fragment1
                Intent intent = new Intent(MainActivity.this, Fregment_container.class);
                intent.putExtra("dailycheck", 138);

                startActivity(intent);
            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load Fragment1 into container_fragment1
                Intent intent = new Intent(MainActivity.this, Fregment_container.class);
                intent.putExtra("dailycheck", 139);

                startActivity(intent);
            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load Fragment1 into container_fragment1
                Intent intent = new Intent(MainActivity.this, Fregment_container.class);
                intent.putExtra("dailycheck", 139);

                startActivity(intent);
            }
        });
        temperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load Fragment1 into container_fragment1
                Intent intent = new Intent(MainActivity.this, Fregment_container.class);
                intent.putExtra("dailycheck", 140);

                startActivity(intent);
            }
        });
        bmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load Fragment1 into container_fragment1
                Intent intent = new Intent(MainActivity.this, Fregment_container.class);
                intent.putExtra("dailycheck", 141);

                startActivity(intent);
            }
        });
        weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load Fragment1 into container_fragment1
                Intent intent = new Intent(MainActivity.this, Fregment_container.class);
                intent.putExtra("dailycheck", 142);

                startActivity(intent);
            }
        });
        digital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load Fragment1 into container_fragment1
                Intent intent = new Intent(MainActivity.this, Fregment_container.class);
                intent.putExtra("dailycheck", 143);

                startActivity(intent);
            }
        });

    }
    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {

        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.exitdialog, null);
        alert.setView(view);
        final AlertDialog dialog = alert.create();
        dialog.setCancelable(false);
        view.findViewById(R.id.btnyes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finishAffinity();
                // finishAndRemoveTask();
            }
        });
        view.findViewById(R.id.btnno).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
    public static void showInternetUsageDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Internet Usage")
                .setMessage("This feature uses internet connectivity.")
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }
}