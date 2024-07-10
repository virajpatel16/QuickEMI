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

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.text.DecimalFormat;


public class Loan_Tenure_Fragment extends Fragment {
    private TextInputEditText etPrincipal, etEMI, etInterest;
    private TextView tvInterestAmt, tvPrincipalAmt, tvTotalPayAmt, tvLoanTenure;
    private ExtendedFloatingActionButton btnCalculate, btnReset;
    private PieChart chartLoanTenure;
ImageView loantenuretback;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_loan__tenure_, container, false);
        etPrincipal = view.findViewById(R.id.et_principal);
        etEMI = view.findViewById(R.id.et_emi);
        etInterest = view.findViewById(R.id.et_interest);
        tvInterestAmt = view.findViewById(R.id.tv_interest_amt);
        tvPrincipalAmt = view.findViewById(R.id.tv_principal_amt);
        tvTotalPayAmt = view.findViewById(R.id.tv_total_pay_amt);
        tvLoanTenure = view.findViewById(R.id.Loan_Tenture);
        btnCalculate = view.findViewById(R.id.btn_calculate);
        btnReset = view.findViewById(R.id.btn_reset);
        chartLoanTenure = view.findViewById(R.id.chart_loan_tenure);
        loantenuretback=view.findViewById(R.id.loantenuretback);
        loantenuretback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateLoanTenure();
                keybord(v);
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFields();
            }
        });

        return  view;
    }
    private void calculateLoanTenure() {
        String principalStr = etPrincipal.getText().toString();
        String emiStr = etEMI.getText().toString();
        String interestStr = etInterest.getText().toString();

        if (principalStr.isEmpty() || emiStr.isEmpty() || interestStr.isEmpty()) {
            Toast.makeText(getActivity(), "Please enter all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double principal = Double.parseDouble(principalStr);
        double emi = Double.parseDouble(emiStr);
        double rate = Double.parseDouble(interestStr) / 100.0 / 12.0;

        double numerator = Math.log(emi / (emi - (principal * rate)));
        double denominator = Math.log(1 + rate);
        double tenure = numerator / denominator;

        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        double totalInterest = emi * tenure - principal;
        double totalPayment = emi * tenure;

        tvInterestAmt.setText("" + decimalFormat.format(totalInterest));
        tvPrincipalAmt.setText("" + decimalFormat.format(principal));
        tvTotalPayAmt.setText("" + decimalFormat.format(totalPayment));
        tvLoanTenure.setText("" + decimalFormat.format(tenure) + "");

        chartLoanTenure.clearChart();
        chartLoanTenure.addPieSlice(new PieModel("Interest", (float) totalInterest, getResources().getColor(R.color.black)));
        chartLoanTenure.addPieSlice(new PieModel("Principal", (float) principal, getResources().getColor(R.color.white)));
        chartLoanTenure.startAnimation();
    }

    private void resetFields() {
        etPrincipal.setText("");
        etEMI.setText("");
        etInterest.setText("");
        tvInterestAmt.setText("");
        tvPrincipalAmt.setText("");
        tvTotalPayAmt.setText("");
        tvLoanTenure.setText("");
        chartLoanTenure.clearChart();
        Toast.makeText(getContext(), "Value is 0", Toast.LENGTH_SHORT).show();
    }
    private void keybord(View v) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
}