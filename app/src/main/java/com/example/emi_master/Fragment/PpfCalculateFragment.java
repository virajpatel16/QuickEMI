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

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class PpfCalculateFragment extends Fragment {

    private EditText investmentAmountEditText, interestRateEditText, tenureEditText, firstEmiEditText;
    private ExtendedFloatingActionButton calculateButton, resetButton;
    private TextView investmentAmountTextView, totalInterestValueTextView, maturityDateTextView, maturityValueTextView;

    private Calendar firstEmiCalendar;
    ImageView back_ppf;

    LinearLayout ppf;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ppf__calculate_, container, false);

        // Initialize views
        investmentAmountEditText = view.findViewById(R.id.InvestmentAmountedPublicittext);
        interestRateEditText = view.findViewById(R.id.ExpectrateofinterestPublicEditText);
        tenureEditText = view.findViewById(R.id.TenurePublicEdittext);
        firstEmiEditText = view.findViewById(R.id.FirstEmiPublicEditext);

        calculateButton = view.findViewById(R.id.btn_ppf_calculate);
        resetButton = view.findViewById(R.id.btn_ppf_reset);
        back_ppf=view.findViewById(R.id.back_ppf);

        investmentAmountTextView = view.findViewById(R.id.answerinvestmentamountpublic);
        totalInterestValueTextView = view.findViewById(R.id.answertotalinterestvaluepublic);
        maturityDateTextView = view.findViewById(R.id.answermatururitydatepublic);
        maturityValueTextView = view.findViewById(R.id.answermatururityvaluepublic);
        ppf=view.findViewById(R.id.ppf);

        // Set up DatePicker dialog for FirstEmiPublicEditext field
        firstEmiEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        back_ppf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().finish();
            }
        });

        // Calculate button click listener
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculatePpfValues();
                ppf.setVisibility(View.VISIBLE);
                keybord(v);
            }
        });
        ppf.setVisibility(View.INVISIBLE);

        // Reset button click listener
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetInputs();
                ppf.setVisibility(View.INVISIBLE);
            }
        });

        return view;
    }

    private void showDatePickerDialog() {
        // Initialize Calendar instance for date picker dialog
        firstEmiCalendar = Calendar.getInstance();

        // Create DatePickerDialog instance
        new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // Set selected date to Calendar instance
                firstEmiCalendar.set(Calendar.YEAR, year);
                firstEmiCalendar.set(Calendar.MONTH, month);
                firstEmiCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                // Update EditText with selected date
                updateFirstEmiEditText();
            }
        }, firstEmiCalendar.get(Calendar.YEAR), firstEmiCalendar.get(Calendar.MONTH), firstEmiCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void updateFirstEmiEditText() {
        // Format selected date and set to EditText
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        firstEmiEditText.setText(sdf.format(firstEmiCalendar.getTime()));
    }

    private void calculatePpfValues() {
        try {
            // Retrieve input values
            double investmentAmount = Double.parseDouble(investmentAmountEditText.getText().toString());
            double interestRate = Double.parseDouble(interestRateEditText.getText().toString()) / 100;
            double tenure = Double.parseDouble(tenureEditText.getText().toString());

            // Calculate maturity date based on tenure and first EMI date
            Calendar maturityCalendar = Calendar.getInstance();
            maturityCalendar.setTime(firstEmiCalendar.getTime()); // Ensure firstEmiCalendar is initialized correctly
            maturityCalendar.add(Calendar.YEAR, (int) tenure);

            // Format results to two decimal places
            DecimalFormat df = new DecimalFormat("#.##");

            // Calculate total interest value
            double totalInterestValue = investmentAmount * Math.pow(1 + interestRate, tenure) - investmentAmount;

            // Calculate maturity value
            double maturityValue = investmentAmount + totalInterestValue;

            // Format the maturity date for display
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String formattedMaturityDate = sdf.format(maturityCalendar.getTime());

            // Display results
            investmentAmountTextView.setText("₹ " + df.format(investmentAmount));
            totalInterestValueTextView.setText("₹ " + df.format(totalInterestValue));
            maturityDateTextView.setText(formattedMaturityDate); // Set formatted date
            maturityValueTextView.setText("₹ " + df.format(maturityValue));

        } catch (NumberFormatException e) {
            // Handle input format errors
            e.printStackTrace();
        }
    }


    private void resetInputs() {
        // Clear all input fields and TextViews
        investmentAmountEditText.setText("");
        interestRateEditText.setText("");
        tenureEditText.setText("");
        firstEmiEditText.setText("");

        investmentAmountTextView.setText("");
        totalInterestValueTextView.setText("");
        maturityDateTextView.setText("");
        Toast.makeText(getContext(), "Value is 0", Toast.LENGTH_SHORT).show();
        maturityValueTextView.setText("");
    }
    private void keybord(View v) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
}
