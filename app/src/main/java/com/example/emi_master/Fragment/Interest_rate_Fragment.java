package com.example.emi_master.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emi_master.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DecimalFormat;


public class Interest_rate_Fragment extends Fragment {

    private TextInputEditText amountEditText, durationEditText, interestEditText;
    private RadioGroup radioGroup;
    private TextView resultTextView, maturityTextView, totalAmountTextView, interestEarnedTextView;
    private ExtendedFloatingActionButton calculateButton, resetButton;
ImageView interestback;
    private static final double MONTHS_IN_YEAR = 12.0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_interest_rate_, container, false);

        amountEditText = view.findViewById(R.id.amount);
        durationEditText = view.findViewById(R.id.durationEditText);
        interestEditText = view.findViewById(R.id.edtInterest);
        radioGroup = view.findViewById(R.id.radiogroup);
        resultTextView = view.findViewById(R.id.resultTextView);
        maturityTextView = view.findViewById(R.id.maturity);
        totalAmountTextView = view.findViewById(R.id.total_amount);
        interestEarnedTextView = view.findViewById(R.id.Earned);
        calculateButton = view.findViewById(R.id.btnCalculate);
        resetButton = view.findViewById(R.id.btnReset);
        interestback=view.findViewById(R.id.interestback);

        interestback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateInterest();
                keybord(v);
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
    private void calculateInterest() {
        // Get user inputs
        String amountStr = amountEditText.getText().toString();
        String durationStr = durationEditText.getText().toString();
        String interestStr = interestEditText.getText().toString();

        if (amountStr.isEmpty() || durationStr.isEmpty() || interestStr.isEmpty()) {
            Toast.makeText(getActivity(), "Please enter all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double principal = Double.parseDouble(amountStr);
        double rate = Double.parseDouble(interestStr) / 100.0;

        // Calculate interest based on selected duration (yearly or monthly)
        int checkedId = radioGroup.getCheckedRadioButtonId();
        double duration = Double.parseDouble(durationStr);
        double maturityAmount = 0.0;
        double totalAmount = 0.0;
        double interestEarned = 0.0;

        if (checkedId == R.id.year) {
            // Calculate for years
            maturityAmount = principal * Math.pow(1 + (rate), duration);
            totalAmount = principal * duration;
            interestEarned = maturityAmount - totalAmount;
        } else if (checkedId == R.id.month) {
            // Calculate for months
            maturityAmount = principal * Math.pow(1 + (rate / MONTHS_IN_YEAR), duration);
            totalAmount = principal * duration / MONTHS_IN_YEAR;
            interestEarned = maturityAmount - totalAmount;
        }

        // Format results to two decimal places
        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        maturityTextView.setText("" + decimalFormat.format(maturityAmount));
        totalAmountTextView.setText("" + decimalFormat.format(totalAmount));
        interestEarnedTextView.setText("" + decimalFormat.format(interestEarned));

        // Show the result text view
        resultTextView.setVisibility(View.VISIBLE);
    }

    private void resetFields() {
        amountEditText.setText("");
        durationEditText.setText("");
        interestEditText.setText("");
        radioGroup.check(R.id.year); // Default to yearly calculation
        resultTextView.setVisibility(View.INVISIBLE);
        maturityTextView.setText("");
        totalAmountTextView.setText("");
        interestEarnedTextView.setText("");
        Toast.makeText(getContext(), "Value is 0", Toast.LENGTH_SHORT).show();
    }
    private void keybord(View v) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
}
