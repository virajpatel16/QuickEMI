package com.example.emi_master.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.emi_master.R;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DecimalFormat;


public class Sip_calculate_Fragment extends Fragment {

LinearLayout sip;
ImageView back_sip;

    private TextInputEditText edtPrincipalAmt, edtRate, edtYear;
    private Button btnSipCalculate, btnSipReset;
    private EditText edtExpectedAmount, edtTInterest, edtTPayment;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sip_calculate_, container, false);
        sip=view.findViewById(R.id.sip);
        edtPrincipalAmt = view.findViewById(R.id.edt_principal_amt);
        edtRate = view.findViewById(R.id.edt_rate);
        edtYear = view.findViewById(R.id.edt_year);
        btnSipCalculate = view.findViewById(R.id.btn_sip_calculate);
        btnSipReset = view.findViewById(R.id.btn_sip_reset);
        edtExpectedAmount = view.findViewById(R.id.edt_expected_amount);
        edtTInterest = view.findViewById(R.id.edt_t_interest);
        edtTPayment = view.findViewById(R.id.edt_t_payment);
        sip.setVisibility(View.INVISIBLE);
        back_sip=view.findViewById(R.id.back_sip);


        btnSipCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateSIP();
                sip.setVisibility(View.VISIBLE);
                keybord(v);
            }
        });

        btnSipReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetSip();
            }
        });

        return view;
    }

    private void calculateSIP() {
        // Check if input fields are empty
        if (edtPrincipalAmt.getText().toString().isEmpty() ||
                edtRate.getText().toString().isEmpty() ||
                edtYear.getText().toString().isEmpty()) {
            Toast.makeText(requireActivity(), "Please enter all inputs", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // Retrieve values entered by the user
            double monthlyAmount = Double.parseDouble(edtPrincipalAmt.getText().toString());
            double annualReturn = Double.parseDouble(edtRate.getText().toString()) / 100;
            double period = Double.parseDouble(edtYear.getText().toString());
            int n = 12; // Number of times interest is compounded per year (assuming monthly compounding)
            int months = (int) (period * 12); // Total number of months

            // Calculate future value using compound interest formula
            double monthlyInterestRate = annualReturn / n; // Monthly interest rate
            double futureValue = monthlyAmount * (((Math.pow(1 + monthlyInterestRate, months) - 1) / monthlyInterestRate) * (1 + monthlyInterestRate));

            // Calculate total investment
            double totalInvestment = monthlyAmount * months;

            // Calculate wealth gain
            double wealthGain = futureValue - totalInvestment;

            // Display the calculated values
            edtExpectedAmount.setText(String.format("%.2f", futureValue));
            edtTInterest.setText(String.format("%.2f", totalInvestment));
            edtTPayment.setText(String.format("%.2f", wealthGain));
        } catch (NumberFormatException e) {
            // Handle NumberFormatException (e.g., if input is not a valid number)
            Toast.makeText(requireActivity(), "Please enter valid numbers", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            // Handle other exceptions
            Toast.makeText(requireActivity(), "An error occurred", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    private void resetSip() {
        edtPrincipalAmt.setText("");
        edtRate.setText("");
        edtYear.setText("");
        edtExpectedAmount.setText("");
        edtTInterest.setText("");
        edtTPayment.setText("");
        Toast.makeText(getContext(), "Value is 0", Toast.LENGTH_SHORT).show();
    }
    private void keybord(View v) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
}