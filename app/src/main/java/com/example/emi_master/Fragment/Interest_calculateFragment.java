package com.example.emi_master.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.emi_master.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DecimalFormat;

public class Interest_calculateFragment extends Fragment {

    private RadioGroup radioGroup;
    private TextView edt_simple, edt_compound, totalAmount;
    private TextInputEditText etPrincipal, etRate, etTime;
    private CardView resultCard;
    private ExtendedFloatingActionButton btnCalculate, btnReset;
    ImageView back_interest;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_interest_calculate, container, false);

        // Initialize views
        radioGroup = view.findViewById(R.id.radio_group_interest);
        edt_simple = view.findViewById(R.id.edt_simple);
        edt_compound = view.findViewById(R.id.edt_compound);
        etPrincipal = view.findViewById(R.id.etPrincipal);
        etRate = view.findViewById(R.id.etRate);
        etTime = view.findViewById(R.id.etTime);
        back_interest=view.findViewById(R.id.back_interest);
        totalAmount = view.findViewById(R.id.total_amount);
        resultCard = view.findViewById(R.id.resultCard);
        btnCalculate = view.findViewById(R.id.btnCalculate);
        btnReset = view.findViewById(R.id.btnReset);
        resultCard.setVisibility(View.INVISIBLE);
        // Set OnClickListener for Calculate button
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateInterest();
                keybord(v);
            }
        });

        // Set OnClickListener for Reset button if needed
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFields();
            }
        });

        back_interest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().finish();
            }
        });
        return view;
    }

    private void calculateInterest() {
        try {
            // Parse input values
            double principal = Double.parseDouble(etPrincipal.getText().toString());
            double rate = Double.parseDouble(etRate.getText().toString());
            double time = Double.parseDouble(etTime.getText().toString());

            // Check which radio button is selected
            int selectedId = radioGroup.getCheckedRadioButtonId();
            if (selectedId == R.id.radio_simple) {
                // Simple Interest Calculation
                double simpleInterest = (principal * rate * time) / 100;
                double totalAmountValue = principal + simpleInterest;

                // Display results for simple interest
                DecimalFormat df = new DecimalFormat("#.##");
                edt_simple.setText(df.format(simpleInterest));
                totalAmount.setText(df.format(totalAmountValue));

                // Show simple interest result and hide compound interest result
                resultCard.setVisibility(View.VISIBLE);
                edt_simple.setVisibility(View.VISIBLE);
                edt_compound.setVisibility(View.GONE);

            } else if (selectedId == R.id.radio_compound) {
                // Compound Interest Calculation
                int times = 1; // Set a default value for times (change this logic as per your requirements)
                double compoundInterest = principal * Math.pow((1 + (rate / (times * 100))), (times * time)) - principal;
                double totalAmountValue = principal + compoundInterest;

                // Display results for compound interest
                DecimalFormat df = new DecimalFormat("#.##");
                edt_compound.setText(df.format(compoundInterest));
                totalAmount.setText(df.format(totalAmountValue));

                // Show compound interest result and hide simple interest result
                resultCard.setVisibility(View.VISIBLE);
                edt_simple.setVisibility(View.GONE);
                edt_compound.setVisibility(View.VISIBLE);
            }
        } catch (NumberFormatException e) {
            // Handle the case where parsing fails (e.g., non-numeric input)
            // You can show an error message or handle it gracefully
            e.printStackTrace();
        }
    }

    private void clearFields() {
        // Clear all input fields and results
        etPrincipal.setText("");
        etRate.setText("");
        etTime.setText("");
        edt_simple.setText("");
        edt_compound.setText("");
        totalAmount.setText("");

        // Show both simple and compound interest views
        resultCard.setVisibility(View.GONE);
        edt_simple.setVisibility(View.VISIBLE);
        edt_compound.setVisibility(View.VISIBLE);
        Toast.makeText(getContext(), "Value is 0", Toast.LENGTH_SHORT).show();
    }
    private void keybord(View v) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
}
