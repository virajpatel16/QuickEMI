package com.example.emi_master.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
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


public class Moratorium_calculate_Fragment extends Fragment {

    private TextInputEditText edtPrincipalAmount, edtInterest, edtLoanTenure, edtInstallmentsPaid, edtMoratoriumPeriod;
    private TextView txtTotalInterest, txtTotalPrincipal, txtOverallTenure, txtTotalPayment, txtMonthlyEmi;
    private ExtendedFloatingActionButton btnCalculate, btnReset;

    ImageView backmoratorium;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_moratorium_calculate_, container, false);
        edtPrincipalAmount = view.findViewById(R.id.edt_principal_amount_moratorium);
        edtInterest = view. findViewById(R.id.edt_interets_moratorium);
        edtLoanTenure =  view.findViewById(R.id.edt_loan_tenure);
        edtInstallmentsPaid =  view.findViewById(R.id.edt_installment_paid);
        edtMoratoriumPeriod = view. findViewById(R.id.edt_moratorium_period);

        txtTotalInterest = view. findViewById(R.id.txt_total_interest_moratorium);
        txtTotalPrincipal =  view.findViewById(R.id.txt_total_principal_moratorium);
        txtOverallTenure = view. findViewById(R.id.txt_overall_tenure);
        txtTotalPayment = view. findViewById(R.id.txt_total_prayment_moratorium);
        txtMonthlyEmi =  view.findViewById(R.id.txt_total_monthly_emi_moratorium);
        backmoratorium=view.findViewById(R.id.backmoratorium);


        btnCalculate = view. findViewById(R.id.btnCalculate);
        btnReset =  view.findViewById(R.id.btnReset);

        backmoratorium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().finish();
            }
        });
        // Set click listeners
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateMoratorium();
                keybord(v);
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFields();
            }
        });

        return view;
    }
    private void calculateMoratorium() {
        String principalStr = edtPrincipalAmount.getText().toString().trim();
        String interestStr = edtInterest.getText().toString().trim();
        String tenureStr = edtLoanTenure.getText().toString().trim();
        String installmentsStr = edtInstallmentsPaid.getText().toString().trim();
        String moratoriumStr = edtMoratoriumPeriod.getText().toString().trim();

        if (TextUtils.isEmpty(principalStr) || TextUtils.isEmpty(interestStr) || TextUtils.isEmpty(tenureStr)
                || TextUtils.isEmpty(installmentsStr) || TextUtils.isEmpty(moratoriumStr)) {
            Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double principalAmount = Double.parseDouble(principalStr);
        double interestRate = Double.parseDouble(interestStr);
        int loanTenure = Integer.parseInt(tenureStr);
        int installmentsPaid = Integer.parseInt(installmentsStr);
        int moratoriumPeriod = Integer.parseInt(moratoriumStr);

        double totalInterest = calculateTotalInterest(principalAmount, interestRate, loanTenure, installmentsPaid, moratoriumPeriod);
        double totalPrincipal = principalAmount; // Assuming no additional principal is added
        int overallTenure = loanTenure + moratoriumPeriod;
        double totalPayment = totalPrincipal + totalInterest;
        double monthlyEmi = totalPayment / overallTenure;

        txtTotalInterest.setText(String.format("%.2f", totalInterest));
        txtTotalPrincipal.setText(String.format("%.2f", totalPrincipal));
        txtOverallTenure.setText(String.format("%d months", overallTenure));
        txtTotalPayment.setText(String.format("%.2f", totalPayment));
        txtMonthlyEmi.setText(String.format("%.2f", monthlyEmi));
    }

    private double calculateTotalInterest(double principal, double interestRate, int tenure, int installmentsPaid, int moratoriumPeriod) {
        // Simplified calculation for demonstration purposes
        // Formula: Total Interest = P * R * T / 100
        double remainingPrincipal = principal - (principal / tenure) * installmentsPaid;
        double moratoriumInterest = remainingPrincipal * (interestRate / 100) * (moratoriumPeriod / 12.0);
        return moratoriumInterest;
    }

    private void resetFields() {
        edtPrincipalAmount.setText("");
        edtInterest.setText("");
        edtLoanTenure.setText("");
        edtInstallmentsPaid.setText("");
        edtMoratoriumPeriod.setText("");

        txtTotalInterest.setText("");
        txtTotalPrincipal.setText("");
        txtOverallTenure.setText("");
        txtTotalPayment.setText("");
        txtMonthlyEmi.setText("");
        Toast.makeText(getContext(), "Value is 0", Toast.LENGTH_SHORT).show();
    }
    private void keybord(View v) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

}