package com.example.emi_master.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emi_master.R;

public class Total_margin_Fragment extends Fragment {

ImageView back_margin;
    private EditText costEditText, revenueEditText;
    private TextView marginTextView, profitTextView;
    LinearLayout margin;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_total_margin_, container, false);
        back_margin=view.findViewById(R.id.back_margin);
        costEditText = view.findViewById(R.id.CostmarginEditText);
        revenueEditText = view.findViewById(R.id.RevenuemarginEdittext);
        marginTextView = view.findViewById(R.id.answermargin);
        profitTextView = view.findViewById(R.id.answerprofit);
        margin=view.findViewById(R.id.margin);
        back_margin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().finish();
            }
        });
        // Calculate button click listener
        view.findViewById(R.id.btn_margin_calculate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateMarginAndProfit();
                margin.setVisibility(View.VISIBLE);
                keybord(v);
            }
        });
        // Reset button click listener
        view.findViewById(R.id.btn_margin_reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFields();
                margin.setVisibility(View.INVISIBLE);
            }
        });
        margin.setVisibility(View.INVISIBLE);
    return  view;
    }
    private void calculateMarginAndProfit() {
        // Get cost and revenue values from EditText fields
        String costStr = costEditText.getText().toString().trim();
        String revenueStr = revenueEditText.getText().toString().trim();
        if (TextUtils.isEmpty(costStr) || TextUtils.isEmpty(revenueStr) ) {
            // Handle empty input
            Toast.makeText(requireContext(), "Please enter all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        // Convert input to double
        double cost = Double.parseDouble(costStr);
        double revenue = Double.parseDouble(revenueStr);

        // Calculate margin percentage
        double margin = ((revenue - cost) / revenue) * 100;
        marginTextView.setText(String.format("%.2f", margin) + "%");

        // Calculate profit
        double profit = revenue - cost;
        profitTextView.setText(String.format("%.2f", profit));
    }

    private void resetFields() {
        // Clear EditText fields
        costEditText.setText("");
        revenueEditText.setText("");
        Toast.makeText(getContext(), "Value is 0", Toast.LENGTH_SHORT).show();
        // Clear result TextViews
        marginTextView.setText("");
        profitTextView.setText("");
    }
    private void keybord(View v) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
}