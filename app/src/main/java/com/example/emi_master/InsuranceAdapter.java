package com.example.emi_master;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class InsuranceAdapter extends RecyclerView.Adapter<InsuranceAdapter.InsuranceViewHolder> {
    private Context context;
    private ArrayList<InsuranceItem> insuranceList;
    public InsuranceAdapter(Context context, ArrayList<InsuranceItem> insuranceList) {
        this.context = context;
        this.insuranceList = insuranceList;
    }





    @NonNull
    @Override
    public InsuranceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_insurance, parent, false);
        return new InsuranceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InsuranceViewHolder holder, int position) {
        InsuranceItem insuranceItem = insuranceList.get(position);
        holder.imageView.setImageResource(insuranceItem.getImageResId());
        holder.textView.setText(insuranceItem.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDataToOtherActivities(insuranceItem.getDetail(),insuranceItem.getTitle());
            }
        });

    }

    @Override
    public int getItemCount() {
        return insuranceList.size();
    }

    public static class InsuranceViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public InsuranceViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.insurance_image);
            textView = itemView.findViewById(R.id.insurance_title);
        }
    }

    private void sendDataToOtherActivities(String data,String title) {
        // Create an Intent to start other activities
        Intent intent1 = new Intent(context, Insurance.class);
        intent1.putExtra("data", data);
        intent1.putExtra("title", title);
        context.startActivity(intent1);


    }
}