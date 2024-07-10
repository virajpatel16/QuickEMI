package com.example.emi_master.Fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emi_master.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class Advance_sip_Fragment extends Fragment {

    private TextInputEditText investmentAmountEditText, interestRateEditText, tenureEditText;
    private RadioGroup tenureRadioGroup;
    private RadioButton yearRadioButton, monthRadioButton;
    private Button dateButton;
    CardView fullinfo;
    ImageView back_advance;
    private Calendar selectedDate;
    private ExtendedFloatingActionButton calculateButton, resetButton;
    private TextView totalInvestmentTextView, totalInterestTextView, maturityDateTextView, maturityValueTextView;

    @SuppressLint("WrongViewCast")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_advance_sip_, container, false);
        investmentAmountEditText = view.findViewById(R.id.InvestmentAmoount);
        interestRateEditText = view.findViewById(R.id.interestAmount);
        tenureEditText = view.findViewById(R.id.tenure);
        tenureRadioGroup = view.findViewById(R.id.togle);
        yearRadioButton = view.findViewById(R.id.year);
        monthRadioButton = view.findViewById(R.id.month);
        dateButton = view.findViewById(R.id.date);
        back_advance=view.findViewById(R.id.back_advance);
        calculateButton = view.findViewById(R.id.btn_calculate);
        resetButton = view.findViewById(R.id.btn_reset);
        totalInvestmentTextView = view.findViewById(R.id.totalInvestment);
        totalInterestTextView = view.findViewById(R.id.totalInterest);
        maturityDateTextView = view.findViewById(R.id.Matuirtydate);
        fullinfo=view.findViewById(R.id.fullinfo);
        maturityValueTextView = view.findViewById(R.id.MaturityValue);
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateAdvanceSip();
                keybord(v);
                fullinfo.setVisibility(View.VISIBLE);
            }
        });
        back_advance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().finish();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetInputs();

                fullinfo.setVisibility(View.INVISIBLE);
            }
        });
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        fullinfo.setVisibility(View.INVISIBLE);

    return  view;
    }

    private void showDatePickerDialog() {
        // Get the current date
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and show it
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {


                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Update the button text with the selected date
                        selectedDate = Calendar.getInstance();
                        selectedDate.set(year, month, dayOfMonth);
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        dateButton.setText(sdf.format(selectedDate.getTime()));
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    private void calculateAdvanceSip() {
        try {
            double investmentAmount = Double.parseDouble(investmentAmountEditText.getText().toString());
            double interestRate = Double.parseDouble(interestRateEditText.getText().toString()) / 100;
            double tenure = Double.parseDouble(tenureEditText.getText().toString());

            boolean isYear = yearRadioButton.isChecked();
            double totalInvestment, totalInterest, maturityValue;
            String maturityDate;

            if (isYear) {
                // Calculate for year
                totalInvestment = investmentAmount * tenure;
                totalInterest = totalInvestment * interestRate;

                // Calculate the maturity date
                Calendar maturityCalendar = (Calendar) selectedDate.clone();
                maturityCalendar.add(Calendar.YEAR, (int) tenure);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                maturityDate = sdf.format(maturityCalendar.getTime());

            } else {
                // Calculate for month
                totalInvestment = investmentAmount * tenure / 12;
                totalInterest = totalInvestment * interestRate;

                // Calculate the maturity date
                Calendar maturityCalendar = (Calendar) selectedDate.clone();
                maturityCalendar.add(Calendar.MONTH, (int) tenure);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                maturityDate = sdf.format(maturityCalendar.getTime());
            }

            maturityValue = totalInvestment + totalInterest;

            totalInvestmentTextView.setText("₹ " + String.format("%.2f", totalInvestment));
            totalInterestTextView.setText("₹ " + String.format("%.2f", totalInterest));
            maturityDateTextView.setText(maturityDate);
            maturityValueTextView.setText("₹ " + String.format("%.2f", maturityValue));

        } catch (NumberFormatException e) {
            // Handle input format errors
            e.printStackTrace();
        }
    }
    private void resetInputs() {
        investmentAmountEditText.setText("");
        interestRateEditText.setText("");
        tenureEditText.setText("");
        totalInvestmentTextView.setText("0 ₹");
        totalInterestTextView.setText("0 ₹");
        maturityDateTextView.setText("0");
        maturityValueTextView.setText("0 ₹");
        selectedDate = null;
        dateButton.setText("Date of SIP:");
        maturityDateTextView.setText("");
        Toast.makeText(getContext(), "Value is 0", Toast.LENGTH_SHORT).show();
    }
    private  void keybord(View v){
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
}