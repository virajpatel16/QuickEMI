package com.example.emi_master.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emi_master.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DecimalFormat;


public class Lumpsum_calculate_Fragment extends Fragment {

    private ImageView imgBack2;
    LinearLayout lumpsum;
    private TextInputEditText edt_investment_amount1;
    private TextInputEditText edt_rate_of_interest1;
    private TextInputEditText edt_period1;
    private TextView txt_simple_interest;
    private TextView txt_principal_value;
    private TextView txt_total_amount1;
    private MaterialAutoCompleteTextView periodSpinner1;

    private boolean isPeriodInYears = true;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_lumpsum_calculate_, container, false);
        initView(view);
        return view;
    }
    @SuppressLint("WrongViewCast")
    private void initView(View view) {

        edt_investment_amount1 = view.findViewById(R.id.edt_investment_amount1);
        edt_rate_of_interest1 = view.findViewById(R.id.edt_rate_of_interest1);
        edt_period1 = view.findViewById(R.id.edt_period1);
        txt_simple_interest = view.findViewById(R.id.txt_lumpsum_interest);
        lumpsum=view.findViewById(R.id.lumpsum);
        txt_principal_value = view.findViewById(R.id.txt_principal_value);
        txt_total_amount1 = view.findViewById(R.id.txt_total_amount1);
        periodSpinner1 = view.findViewById(R.id.periodSpinner1);

        imgBack2 = view.findViewById(R.id.back_lumpsum);
        imgBack2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().onBackPressed();
            }
        });
        lumpsum.setVisibility(View.INVISIBLE);
        // Populate spinner options
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.period_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        periodSpinner1.setAdapter(adapter);

        periodSpinner1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Set period type based on selection
                isPeriodInYears = position == 0; // Assuming years are at position 0
            }
        });

        ExtendedFloatingActionButton btn_emi_reset1 = view.findViewById(R.id.btn_lumpsum_reset);
        ExtendedFloatingActionButton btn_emi_calculate1 = view.findViewById(R.id.btn_lumpsum_calculate);

        btn_emi_calculate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate();
                keybord(v);
                lumpsum.setVisibility(View.VISIBLE);
            }
        });

        btn_emi_reset1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetData();
                lumpsum.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void resetData() {
        edt_investment_amount1.setText("");
        edt_rate_of_interest1.setText("");
        edt_period1.setText("");
        txt_simple_interest.setText("");
        txt_principal_value.setText("");
        txt_total_amount1.setText("");
        Toast.makeText(getContext(), "Value is 0", Toast.LENGTH_SHORT).show();
    }

    private void calculate() {
        // Get input values
        double principal, rateOfInterest, period;
        try {
            String principalStr = edt_investment_amount1.getText().toString().trim();
            String rateOfInterestStr = edt_rate_of_interest1.getText().toString().trim();
            String periodStr = edt_period1.getText().toString().trim();

            // Check if any input field is empty
            if (principalStr.isEmpty() || rateOfInterestStr.isEmpty() || periodStr.isEmpty()) {
                Toast.makeText(getContext(), "Please enter all inputs", Toast.LENGTH_SHORT).show();
                return;
            }

            // Parse input values to double
            principal = Double.parseDouble(principalStr);
            rateOfInterest = Double.parseDouble(rateOfInterestStr);
            period = Double.parseDouble(periodStr);

            // Check if any of the input values is zero
            if (principal == 0 || rateOfInterest == 0 || period == 0) {
                Toast.makeText(getContext(), "Input values must be greater than 0", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (NumberFormatException e) {
            // Handle NumberFormatException (e.g., if input is not a valid number)
            Toast.makeText(getContext(), "Please enter valid numbers", Toast.LENGTH_SHORT).show();
            return;
        }

        // Determine if the period is provided in years or months
        boolean isYearly = isPeriodInYears;

        // Calculate interest based on the period
        double interest;
        if (isYearly) {
            // Calculate yearly interest
            interest = (principal * rateOfInterest * period) / 100;
        } else {
            // Calculate monthly interest
            double monthlyRate = rateOfInterest / 12 / 100; // Convert annual rate to monthly rate
            interest = principal * monthlyRate * period;
        }

        // Calculate total amount
        double totalAmount = principal + interest;

        // Format the values for display
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String formattedInterest = "₹" + decimalFormat.format(interest);
        String formattedTotalAmount = "₹" + decimalFormat.format(totalAmount);
        String formattedPrincipal = "₹" + decimalFormat.format(principal);

        // Display the results
        txt_simple_interest.setText(formattedInterest);
        txt_principal_value.setText(formattedPrincipal);
        txt_total_amount1.setText(formattedTotalAmount);
    }
    private void keybord(View v) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
}