package com.example.emi_master.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.emi_master.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;


public class SWPFragment extends Fragment {

    ImageView back_swp;

    LinearLayout swp;
    private TextInputEditText edtInitialInvestment, edtSwpAmount, edtRateOfReturn, edtYears, edtMonths;
    private EditText answerTotalWithdrawal, answerTotalProfit, answerEndBalance, answerNoOfWithdrawals;
    private ExtendedFloatingActionButton btnCalculate, btnReset;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_s_w_p, container, false);
        back_swp=view.findViewById(R.id.back_swp);
        edtInitialInvestment = view.findViewById(R.id.initialinvestmentcard);
        edtSwpAmount = view.findViewById(R.id.swpEditText);
        swp=view.findViewById(R.id.swp);
        edtRateOfReturn = view.findViewById(R.id.rateofreturnswpEdittext);
        edtYears = view.findViewById(R.id.edt_years);
        edtMonths = view.findViewById(R.id.edt_month);
        answerTotalWithdrawal = view.findViewById(R.id.answertotalwithdrawal);
        answerTotalProfit = view.findViewById(R.id.answertotalprofit);
        answerEndBalance = view.findViewById(R.id.answerendbalance);
        answerNoOfWithdrawals = view.findViewById(R.id.answernoofwithdrawal);
        btnCalculate = view.findViewById(R.id.btn_emi_calculate);
        btnReset = view.findViewById(R.id.btn_emi_reset);

        back_swp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().finish();
            }
        });
        btnCalculate.setOnClickListener(v -> calculateSWP());
        btnCalculate.setOnClickListener(v -> keybord(v));
        btnReset.setOnClickListener(v -> resetFields());

        swp.setVisibility(View.INVISIBLE);
        return view;

    }private void calculateSWP() {
        String initialInvestmentText = edtInitialInvestment.getText().toString().trim();
        String swpAmountText = edtSwpAmount.getText().toString().trim();
        String rateOfReturnText = edtRateOfReturn.getText().toString().trim();
        String yearsText = edtYears.getText().toString().trim();
        String monthsText = edtMonths.getText().toString().trim();

        // Validate input fields
        if (initialInvestmentText.isEmpty() || swpAmountText.isEmpty() ||
                rateOfReturnText.isEmpty() || (yearsText.isEmpty() && monthsText.isEmpty())) {
            // Handle empty fields
            if (initialInvestmentText.isEmpty()) edtInitialInvestment.setError("Enter initial investment");
            if (swpAmountText.isEmpty()) edtSwpAmount.setError("Enter SWP amount");
            if (rateOfReturnText.isEmpty()) edtRateOfReturn.setError("Enter rate of return");
            if (yearsText.isEmpty() && monthsText.isEmpty()) {
                edtYears.setError("Enter either years or months");
                edtMonths.setError("Enter either years or months");
            }
            return;
        }

        int years = yearsText.isEmpty() ? 0 : Integer.parseInt(yearsText);
        int months = monthsText.isEmpty() ? 0 : Integer.parseInt(monthsText);

        // Convert years to months
        int totalMonths = (years * 12) + months;

        // Parse other input values
        double initialInvestment = Double.parseDouble(initialInvestmentText);
        double swpAmount = Double.parseDouble(swpAmountText);
        double rateOfReturn = Double.parseDouble(rateOfReturnText) / 100;
        double monthlyRate = rateOfReturn / 12;

        // Initialize variables for calculations
        double totalWithdrawal = swpAmount * totalMonths;
        double endBalance = initialInvestment;
        double totalProfit = 0;
        double noOfWithdrawals = 0;

        // Perform SWP calculations
        for (int i = 0; i < totalMonths; i++) {
            if (endBalance < swpAmount) break;

            endBalance += (endBalance * monthlyRate) - swpAmount;
            noOfWithdrawals++;
        }

        swp.setVisibility(View.VISIBLE);
        // Calculate total profit
        totalProfit = (initialInvestment + (initialInvestment * monthlyRate * totalMonths)) - totalWithdrawal;

        // Display results
        answerTotalWithdrawal.setText(String.format("₹%.2f", totalWithdrawal));
        answerTotalProfit.setText(String.format("₹%.2f", totalProfit));
        answerEndBalance.setText(String.format("₹%.2f", endBalance));
        answerNoOfWithdrawals.setText(String.valueOf(noOfWithdrawals));
    }


    private void resetFields() {
        edtInitialInvestment.setText("");
        edtSwpAmount.setText("");
        edtRateOfReturn.setText("");
        edtYears.setText("");
        edtMonths.setText("");
        answerTotalWithdrawal.setText("");
        answerTotalProfit.setText("");
        answerEndBalance.setText("");
        answerNoOfWithdrawals.setText("");
        Toast.makeText(getContext(), "Value is 0", Toast.LENGTH_SHORT).show();
        swp.setVisibility(View.INVISIBLE);
    }
    private void keybord(View v) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
}