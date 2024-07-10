package com.example.emi_master.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.emi_master.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.NumberFormat;
import java.util.Locale;


public class Equity_saving_calculate_Fragment extends Fragment {

ImageView backequity;
    private TextInputEditText edtInvestmentAmount, edtTenureYears, edtYear;
    private EditText txtExpectedAmount, edtEquityInterest, edtWealthGain;
    private ExtendedFloatingActionButton btnCalculate, btnReset;

    LinearLayout equity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_equity_saving_calculate_, container, false);
        edtInvestmentAmount = view.findViewById(R.id.edt_investment_amount);
        edtTenureYears = view.findViewById(R.id.edt_tenureyears);
        edtYear = view.findViewById(R.id.edt_year);
        txtExpectedAmount = view.findViewById(R.id.txt_expected_amount);
        edtEquityInterest = view.findViewById(R.id.edt_equity_interest);
        edtWealthGain = view.findViewById(R.id.edt_wealth_gain);
        btnCalculate = view.findViewById(R.id.btn_equity_calculate);
        equity=view.findViewById(R.id.equity);
        btnReset = view.findViewById(R.id.btn_equity_reset);
        equity.setVisibility(View.INVISIBLE);
        backequity=view.findViewById(R.id.backequity);

        backequity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().finish();
            }
        });

        // Set up listeners
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateEquitySavings();
                keybord(v);
                equity.setVisibility(View.VISIBLE);
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFields();
                equity.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(), "Value is 0", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
    private void calculateEquitySavings() {
        String investmentAmountStr = edtInvestmentAmount.getText().toString().trim();
        String tenureYearsStr = edtTenureYears.getText().toString().trim();
        String yearStr = edtYear.getText().toString().trim();

        if (investmentAmountStr.isEmpty() || tenureYearsStr.isEmpty() || yearStr.isEmpty()) {
            Toast.makeText(getActivity(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double investmentAmount = Double.parseDouble(investmentAmountStr);
            double tenureYears = Double.parseDouble(tenureYearsStr);
            double yearlyInterestRate = Double.parseDouble(yearStr);

            double expectedAmount = calculateFutureValue(investmentAmount, tenureYears, yearlyInterestRate);
            double totalInvestment = investmentAmount * tenureYears * 12;
            double wealthGain = expectedAmount - totalInvestment;

            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
            txtExpectedAmount.setText(currencyFormat.format(expectedAmount));
            edtEquityInterest.setText(currencyFormat.format(totalInvestment));
            edtWealthGain.setText(currencyFormat.format(wealthGain));

        } catch (NumberFormatException e) {
            Toast.makeText(getActivity(), "Invalid input", Toast.LENGTH_SHORT).show();
        }
    }

    private double calculateFutureValue(double monthlyInvestment, double tenureYears, double annualRate) {
        double monthlyRate = annualRate / 12 / 100;
        int months = (int) (tenureYears * 12);
        return monthlyInvestment * (Math.pow(1 + monthlyRate, months) - 1) / monthlyRate;
    }


    private void resetFields() {
        edtInvestmentAmount.setText("");
        edtTenureYears.setText("");
        edtYear.setText("");
        txtExpectedAmount.setText("");
        edtEquityInterest.setText("");
        edtWealthGain.setText("");
    }
    private void keybord(View v) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
}