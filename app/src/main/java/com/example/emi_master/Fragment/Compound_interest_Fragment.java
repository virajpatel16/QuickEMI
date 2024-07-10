package com.example.emi_master.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emi_master.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DecimalFormat;

public class Compound_interest_Fragment extends Fragment {

    private TextInputEditText amountInput, interestInput, yearsInput, monthInput, dayInput;
    private ExtendedFloatingActionButton calculateButton, resetButton;
    private TextView principalAmountText, interestAmountText, totalAmountText;
    LinearLayout compund;
ImageView backcompound;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_compound_interest_, container, false);


        amountInput = view.findViewById(R.id.Amountsimpleinterest);
        interestInput = view.findViewById(R.id.Interest);
        yearsInput = view.findViewById(R.id.interestyears);
        monthInput = view.findViewById(R.id.interestmonth);
        dayInput = view.findViewById(R.id.interestday);
        compund=view.findViewById(R.id.compund);
        backcompound=view.findViewById(R.id.backcompound);
        calculateButton = view.findViewById(R.id.btncompoundinterest_calculate);
        resetButton = view.findViewById(R.id.btn_compound_reset);

        principalAmountText = view.findViewById(R.id.answerprincipalamount);
        interestAmountText = view.findViewById(R.id.answerinterestamount);
        totalAmountText = view.findViewById(R.id.answertotalamount);
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateCompoundInterest();
                compund.setVisibility(View.VISIBLE);
                keybord(v);
            }
        });
        backcompound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().finish();
            }
        });
        compund.setVisibility(View.INVISIBLE);

        // Reset button click listener
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetInputs();
                compund.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(), "Value is 0", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
    private void calculateCompoundInterest() {
        try {
            // Retrieve input values
            double principal = Double.parseDouble(amountInput.getText().toString());
            double rate = Double.parseDouble(interestInput.getText().toString()) / 100;

            // Handle empty inputs for years, months, and days
            double years = yearsInput.getText().toString().isEmpty() ? 0 : Double.parseDouble(yearsInput.getText().toString());
            double months = monthInput.getText().toString().isEmpty() ? 0 : Double.parseDouble(monthInput.getText().toString());
            double days = dayInput.getText().toString().isEmpty() ? 0 : Double.parseDouble(dayInput.getText().toString());

            // Calculate total time in years
            double totalTime = years + (months / 12) + (days / 365);

            // Calculate compound interest
            double amount = principal * Math.pow((1 + rate / 12), 12 * totalTime);

            // Calculate interest amount
            double interest = amount - principal;

            // Format results to two decimal places
            DecimalFormat df = new DecimalFormat("#.##");

            // Display results
            principalAmountText.setText("₹ " + df.format(principal));
            interestAmountText.setText("₹ " + df.format(interest));
            totalAmountText.setText("₹ " + df.format(amount));

        } catch (NumberFormatException e) {
            // Handle input format errors
            e.printStackTrace();
        }
    }

    // Method to reset all input fields
    private void resetInputs() {
        amountInput.setText("");
        interestInput.setText("");
        yearsInput.setText("");
        monthInput.setText("");
        dayInput.setText("");

        principalAmountText.setText("");
        interestAmountText.setText("");
        totalAmountText.setText("");
    }
    private void keybord(View v) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
}