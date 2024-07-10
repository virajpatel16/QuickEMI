package com.example.emi_master.Fragment;

import static android.text.TextUtils.*;

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


public class Loan_amountFragment extends Fragment {

    private TextInputEditText edtMonthlyEmi, edtInterest, edtLoanTenure;
    private TextView txtTotalInterest, txtTotalPrincipal, txtTotalPayment, txtTotalMonthlyEmi;
    private ExtendedFloatingActionButton btnCalculate, btnReset;

    ImageView backamount;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_loan_amount, container, false);


        edtMonthlyEmi =view. findViewById(R.id.edt_monthly_emi);
        edtInterest = view. findViewById(R.id.edt_interest);
        edtLoanTenure =view.  findViewById(R.id.edt_loan_tenure);
        txtTotalInterest = view. findViewById(R.id.txt_total_interest);
        txtTotalPrincipal = view. findViewById(R.id.txt_total_principal);
        txtTotalPayment =view.  findViewById(R.id.txt_total_prayment);
        txtTotalMonthlyEmi = view. findViewById(R.id.txt_total_monthly_emi);
        btnCalculate = view. findViewById(R.id.btn_loan_calculate);
        btnReset = view. findViewById(R.id.btn_loan_reset);
        backamount=view.findViewById(R.id.backamount);
        backamount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().finish();
            }
        });
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateLoanDetails();
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
    private void calculateLoanDetails() {
        String strEmi = edtMonthlyEmi.getText().toString().trim();
        String strInterest = edtInterest.getText().toString().trim();
        String strTenure = edtLoanTenure.getText().toString().trim();

        if (isEmpty(strEmi) || isEmpty(strInterest) || isEmpty(strTenure)) {
            Toast.makeText(requireActivity(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double emi = Double.parseDouble(strEmi);
        double annualInterestRate = Double.parseDouble(strInterest);
        double tenure = Double.parseDouble(strTenure);

        double monthlyInterestRate = annualInterestRate / (12 * 100);
        double numberOfMonths = tenure * 12;

        double loanAmount = (emi / monthlyInterestRate) * (1 - Math.pow(1 + monthlyInterestRate, -numberOfMonths));
        double totalPayment = emi * numberOfMonths;
        double totalInterest = totalPayment - loanAmount;

        txtTotalPrincipal.setText(String.format("%.2f", loanAmount));
        txtTotalInterest.setText(String.format("%.2f", totalInterest));
        txtTotalPayment.setText(String.format("%.2f", totalPayment));
        txtTotalMonthlyEmi.setText(String.format("%.2f", emi));
    }

    private void resetFields() {
        edtMonthlyEmi.setText("");
        edtInterest.setText("");
        edtLoanTenure.setText("");
        txtTotalPrincipal.setText("");
        txtTotalInterest.setText("");
        txtTotalPayment.setText("");
        txtTotalMonthlyEmi.setText("");
        Toast.makeText(getContext(), "Value is 0", Toast.LENGTH_SHORT).show();
    }
    private void keybord(View v) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
}