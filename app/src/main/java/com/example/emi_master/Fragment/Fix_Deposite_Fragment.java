package com.example.emi_master.Fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.emi_master.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Fix_Deposite_Fragment extends Fragment {
    private EditText FirstEmieditext;
    LinearLayout fix;
    private TextInputEditText investmentAmountEditText;
    private TextInputEditText expectedRateOfInterestEditText;
    private TextInputEditText tenureEditText;
    private TextView answerInvestmentAmountFixed;
    ImageView back_fix;
    private TextView answerTotalInterestValue;
    private TextView answerMaturityDateFixed;
    private TextView answerMaturityValueFixed;

    private ExtendedFloatingActionButton btnCalculate;
    private ExtendedFloatingActionButton btnReset;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fix__deposite_, container, false);

        FirstEmieditext = view.findViewById(R.id.FirstEmieditext);
        investmentAmountEditText = view.findViewById(R.id.InvestmentAmountittext);
        expectedRateOfInterestEditText = view.findViewById(R.id.ExpectrateofinterestEditText);
        tenureEditText = view.findViewById(R.id.TenurefixedEdittext);
        answerInvestmentAmountFixed = view.findViewById(R.id.answerinvestmentamountfixed);
        answerTotalInterestValue = view.findViewById(R.id.answertotalinterestvalue);
        answerMaturityDateFixed = view.findViewById(R.id.answermatururitydatefixed);
        answerMaturityValueFixed = view.findViewById(R.id.answermatururityvaluefixed);
        fix=view.findViewById(R.id.fix);

        back_fix=view.findViewById(R.id.back_fix);
        btnCalculate = view.findViewById(R.id.btn_fix_calculate);
        btnReset = view.findViewById(R.id.btn_fix_reset);

        FirstEmieditext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateFixedDeposit();
                fix.setVisibility(View.VISIBLE);
                keybord(v);
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFields();
                fix.setVisibility(View.INVISIBLE);
            }
        });
        back_fix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().finish();
            }
        });
        fix.setVisibility(View.INVISIBLE);
        return view;
    }

    public void showDatePickerDialog(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // Do something with the selected date
                String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                FirstEmieditext.setText(selectedDate);
            }
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    private void calculateFixedDeposit() {
        String amountStr = investmentAmountEditText.getText().toString();
        String interestStr = expectedRateOfInterestEditText.getText().toString();
        String tenureStr = tenureEditText.getText().toString();

        if (amountStr.isEmpty() || interestStr.isEmpty() || tenureStr.isEmpty()) {
            Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double principalAmount = Double.parseDouble(amountStr);
        double annualInterestRate = Double.parseDouble(interestStr);
        double tenureInMonths = Double.parseDouble(tenureStr);

        double interestAmount = principalAmount * (annualInterestRate / 100) * (tenureInMonths / 12);
        double totalAmount = principalAmount + interestAmount;

        String formattedPrincipalAmount = String.format(Locale.getDefault(), "%.2f", principalAmount);
        String formattedInterestAmount = String.format(Locale.getDefault(), "%.2f", interestAmount);
        String formattedTotalAmount = String.format(Locale.getDefault(), "%.2f", totalAmount);

        answerInvestmentAmountFixed.setText(formattedPrincipalAmount);
        answerTotalInterestValue.setText(formattedInterestAmount);
        // Assuming tenure is in months, calculate the maturity date
        answerMaturityDateFixed.setText(calculateMaturityDate(tenureInMonths));
        answerMaturityValueFixed.setText(formattedTotalAmount);
    }

    private void resetFields() {
        investmentAmountEditText.setText("");
        expectedRateOfInterestEditText.setText("");
        tenureEditText.setText("");
        answerInvestmentAmountFixed.setText("");
        answerTotalInterestValue.setText("");
        answerMaturityDateFixed.setText("");
        answerMaturityValueFixed.setText("");
        FirstEmieditext.setText("");
        Toast.makeText(getContext(), "Value is 0", Toast.LENGTH_SHORT).show();
    }

    private String calculateMaturityDate(double tenureInMonths) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, (int) tenureInMonths);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return dateFormat.format(calendar.getTime());
    }
    private void keybord(View v) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
}
