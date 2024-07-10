package com.example.emi_master.Fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emi_master.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class Recurring_deposite_Fragment extends Fragment {
    private EditText FirstEmiReccurringEditext;
    private TextInputEditText investmentAmountEditText;
    private TextInputEditText expectedRateOfInterestEditText;
    private TextInputEditText tenureEditText;
    private TextView answerInvestmentAmountFixed;
    private TextView answerTotalInterestValue;
    private TextView answerMaturityDateFixed;
    private TextView answerMaturityValueFixed;

    private ExtendedFloatingActionButton btnCalculate;
    private ExtendedFloatingActionButton btnReset;
    ImageView back_recurring;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_recurring_deposite_, container, false);

        back_recurring=view.findViewById(R.id.back_recurring);

        FirstEmiReccurringEditext = view.findViewById(R.id.FirstEmiReccurringEditext);
        investmentAmountEditText = view.findViewById(R.id.InvestmentAmountedittext);
        expectedRateOfInterestEditText = view.findViewById(R.id.ExpectrateofinterestRecurringEditText);
        tenureEditText = view.findViewById(R.id.TenureRecurringEdittext);
        answerInvestmentAmountFixed = view.findViewById(R.id.answerinvestmentamountfixed);
        answerTotalInterestValue = view.findViewById(R.id.answertotalinterestvalue);
        answerMaturityDateFixed = view.findViewById(R.id.answermatururitydatefixed);
        answerMaturityValueFixed = view.findViewById(R.id.answermatururityvaluefixed);

        btnCalculate = view.findViewById(R.id.btn_fix_calculate);
        btnReset = view.findViewById(R.id.btn_fix_reset);

        back_recurring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().finish();
            }
        });
        FirstEmiReccurringEditext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateRecurringDeposit();
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
    public void showDatePickerDialog(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // Do something with the selected date
                String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                FirstEmiReccurringEditext.setText(selectedDate);
            }
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    private void calculateRecurringDeposit() {
        String amountStr = investmentAmountEditText.getText().toString();
        String interestStr = expectedRateOfInterestEditText.getText().toString();
        String tenureStr = tenureEditText.getText().toString();
        String firstEmiStr = FirstEmiReccurringEditext.getText().toString().trim();

        if (amountStr.isEmpty() || interestStr.isEmpty() || tenureStr.isEmpty()) {
            Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }


        double investmentAmount = Double.parseDouble(amountStr);
        double rateOfInterest = Double.parseDouble(interestStr);
        int tenure = Integer.parseInt(tenureStr);

        double interestRate = rateOfInterest / 100.0;
        double totalInterest = investmentAmount * interestRate * tenure;
        double maturityValue = investmentAmount + totalInterest;

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try {
            calendar.setTime(sdf.parse(firstEmiStr));
            calendar.add(Calendar.MONTH, tenure);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String maturityDate = sdf.format(calendar.getTime());

        answerInvestmentAmountFixed.setText(String.format("%.2f", investmentAmount));
        answerTotalInterestValue.setText(String.format("%.2f", totalInterest));
        answerMaturityDateFixed.setText(maturityDate);
        answerMaturityValueFixed.setText(String.format("%.2f", maturityValue));
    }

    private void resetFields() {
        investmentAmountEditText.setText("");
        expectedRateOfInterestEditText.setText("");
        tenureEditText.setText("");
        answerInvestmentAmountFixed.setText("");
        answerTotalInterestValue.setText("");
        answerMaturityDateFixed.setText("");
        answerMaturityValueFixed.setText("");
        FirstEmiReccurringEditext.setText("");
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
