package com.example.emi_master.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.emi_master.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import org.eazegraph.lib.models.PieModel;

public class EmiFragment extends Fragment {

    private TextInputEditText edtPrincipalAmount, edtInterestRate, edtYears, edtFirstEmi;
    private ExtendedFloatingActionButton btnCalculate, btnReset;
    private TextView txtEmiMonthlyPayment, txtTotalPayment, txtInterestRate, txtTotalPrincipal;
    private org.eazegraph.lib.charts.PieChart chartTotalInterest, chartTotalPrincipal;
    ImageView back_emi;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_emi, container, false);

        // Initialize views
        edtPrincipalAmount = view.findViewById(R.id.edt_principal_amount);
        edtInterestRate = view.findViewById(R.id.interest_rate);
        edtYears = view.findViewById(R.id.edt_years);
        edtFirstEmi = view.findViewById(R.id.firstemi);
        btnCalculate = view.findViewById(R.id.btn_emi_calculate);
        btnReset = view.findViewById(R.id.btn_emi_reset);
        txtEmiMonthlyPayment = view.findViewById(R.id.txt_emi_monthly_payment);
        txtTotalPayment = view.findViewById(R.id.txt_total_payment);
        txtInterestRate = view.findViewById(R.id.txt_interest_rate);
        txtTotalPrincipal = view.findViewById(R.id.txt_total_principal);
        chartTotalInterest = view.findViewById(R.id.chart_total_interest);
        chartTotalPrincipal = view.findViewById(R.id.chart_total_principal);
        back_emi=view.findViewById(R.id.back_emi);

        // Set click listeners
        back_emi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().finish();
            }
        });
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateEmi();
                keybord(v);
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFields();
                Toast.makeText(getContext(), "Value is 0", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void calculateEmi() {
        String principalStr = edtPrincipalAmount.getText().toString();
        String interestRateStr = edtInterestRate.getText().toString();
        String yearsStr = edtYears.getText().toString();

        if (TextUtils.isEmpty(principalStr) || TextUtils.isEmpty(interestRateStr) || TextUtils.isEmpty(yearsStr)) {
            Toast.makeText(getContext(), "Please enter all the values", Toast.LENGTH_SHORT).show();
            return;
        }

        double principal = Double.parseDouble(principalStr);
        double annualInterestRate = Double.parseDouble(interestRateStr);
        double years = Double.parseDouble(yearsStr);

        double monthlyInterestRate = (annualInterestRate / 100) / 12;
        double numberOfMonths = years * 12;

        double emi = (principal * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, numberOfMonths))
                / (Math.pow(1 + monthlyInterestRate, numberOfMonths) - 1);

        double totalPayment = emi * numberOfMonths;
        double totalInterest = totalPayment - principal;

        txtEmiMonthlyPayment.setText(String.format("₹ %.2f", emi));
        txtTotalPayment.setText(String.format("₹ %.2f", totalPayment));
        txtInterestRate.setText(String.format("₹ %.2f", totalInterest));
        txtTotalPrincipal.setText(String.format("₹ %.2f", principal));

        // Update pie charts
        chartTotalInterest.clearChart();
        chartTotalInterest.addPieSlice(new PieModel("Interest", (float) totalInterest, getResources().getColor(R.color.app_color)));
        chartTotalInterest.addPieSlice(new PieModel("Principal", (float) principal, getResources().getColor(R.color.white)));
        chartTotalInterest.startAnimation();

        chartTotalPrincipal.clearChart();
        chartTotalPrincipal.addPieSlice(new PieModel("Principal", (float) principal, getResources().getColor(R.color.app_color)));
        chartTotalPrincipal.addPieSlice(new PieModel("Interest", (float) totalInterest, getResources().getColor(R.color.white)));
        chartTotalPrincipal.startAnimation();
    }

    private void resetFields() {
        edtPrincipalAmount.setText("");
        edtInterestRate.setText("");
        edtYears.setText("");
        edtFirstEmi.setText("");
        txtEmiMonthlyPayment.setText("");
        txtTotalPayment.setText("");
        txtInterestRate.setText("");
        txtTotalPrincipal.setText("");
        chartTotalInterest.clearChart();
        chartTotalPrincipal.clearChart();
    }
    private  void keybord(View v){
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
}
