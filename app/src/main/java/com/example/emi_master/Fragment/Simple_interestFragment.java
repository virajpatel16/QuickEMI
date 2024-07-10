package com.example.emi_master.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emi_master.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;


public class Simple_interestFragment extends Fragment {

ImageView back_simple;
    private TextInputEditText amountEditText, interestEditText, yearsEditText, monthsEditText, daysEditText;
    private TextView principalAmountTextView, interestAmountTextView, totalAmountTextView;
    private ExtendedFloatingActionButton calculateButton, resetButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_simple_interest, container, false);
        amountEditText = view.findViewById(R.id.Amountsimpleinterest);
        resetButton=view.findViewById(R.id.btn_simple_reset);
        interestEditText = view.findViewById(R.id.Interest);
        yearsEditText = view.findViewById(R.id.interestyears);
        monthsEditText = view.findViewById(R.id.interestmonth);
        daysEditText = view.findViewById(R.id.interestday);

        principalAmountTextView = view.findViewById(R.id.answerprincipalamount);
        interestAmountTextView = view.findViewById(R.id.answerinterestamount);
        totalAmountTextView = view.findViewById(R.id.answertotalamount);

        calculateButton = view.findViewById(R.id.btn_simpleinterest_calculate);

        back_simple=view.findViewById(R.id.back_simple);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateSimpleInterest();
                keybord(v);
            }
        });
        back_simple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().finish();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFields();
            }
        });
        return view;
    }
    private void calculateSimpleInterest() {
        String amountStr = amountEditText.getText().toString();
        String interestStr = interestEditText.getText().toString();
        String yearsStr = yearsEditText.getText().toString();
        String monthsStr = monthsEditText.getText().toString();
        String daysStr = daysEditText.getText().toString();

        if (amountStr.isEmpty() || interestStr.isEmpty() || (yearsStr.isEmpty() && monthsStr.isEmpty() && daysStr.isEmpty())) {
            Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double principalAmount = Double.parseDouble(amountStr);
        double annualInterestRate = Double.parseDouble(interestStr);

        int years = yearsStr.isEmpty() ? 0 : Integer.parseInt(yearsStr);
        int months = monthsStr.isEmpty() ? 0 : Integer.parseInt(monthsStr);
        int days = daysStr.isEmpty() ? 0 : Integer.parseInt(daysStr);

        double totalTimeInYears = years + (months / 12.0) + (days / 365.0);
        double interestAmount = principalAmount * (annualInterestRate / 100) * totalTimeInYears;
        double totalAmount = principalAmount + interestAmount;

      //Format the results to 2 decimal places
        String formattedPrincipalAmount = String.format("%.2f", principalAmount);
        String formattedInterestAmount = String.format("%.2f", interestAmount);
        String formattedTotalAmount = String.format("%.2f", totalAmount);

        principalAmountTextView.setText(formattedPrincipalAmount);
        interestAmountTextView.setText(formattedInterestAmount);
        totalAmountTextView.setText(formattedTotalAmount);
    }

    private void resetFields() {
        amountEditText.setText("");
        interestEditText.setText("");
        yearsEditText.setText("");
        monthsEditText.setText("");
        daysEditText.setText("");

        principalAmountTextView.setText("");
        interestAmountTextView.setText("");
        totalAmountTextView.setText("");
        Toast.makeText(getContext(), "Value is 0", Toast.LENGTH_SHORT).show();
    }
    private void keybord(View v) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
}