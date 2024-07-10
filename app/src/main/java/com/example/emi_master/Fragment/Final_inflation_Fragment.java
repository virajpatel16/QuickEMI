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
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.L;
import com.example.emi_master.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class Final_inflation_Fragment extends Fragment {

    private EditText inflationEditText, startYearEditText, finalYearEditText, initialValueEditText;
    private TextView finalValueTextView;

    ImageView back_inflation;
    LinearLayout inflation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_final_inflation_, container, false);

        inflationEditText = view.findViewById(R.id.InflationEditText);
        startYearEditText = view.findViewById(R.id.StartyearEditText);
        finalYearEditText = view.findViewById(R.id.finalyeareditext);
        initialValueEditText = view.findViewById(R.id.InitialvalueInflationEdittext);
        finalValueTextView = view.findViewById(R.id.answerfinalvalueinflation);
        inflation=view.findViewById(R.id.inflation);
        back_inflation=view.findViewById(R.id.back_inflation);


        ExtendedFloatingActionButton calculateButton = view.findViewById(R.id.btn_inflation_calculate);
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keybord(v);
                calculateFinalValue();
                inflation.setVisibility(View.VISIBLE);
            }
        });
        back_inflation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().finish();
            }
        });
        inflation.setVisibility(View.INVISIBLE);
        // Reset button click listener
        ExtendedFloatingActionButton resetButton = view.findViewById(R.id.btn_inflation_reset);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFields();
                inflation.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(), "Value is 0", Toast.LENGTH_SHORT).show();
            }
        });
    return view;

    }
    private void calculateFinalValue() {
        try {
            // Get values from EditText fields
            String inflationStr = inflationEditText.getText().toString().trim();
            String startYearStr = startYearEditText.getText().toString().trim();
            String finalYearStr = finalYearEditText.getText().toString().trim();
            String initialValueStr = initialValueEditText.getText().toString().trim();

            // Convert input to double
            double inflationRate = Double.parseDouble(inflationStr) / 100;
            int startYear = Integer.parseInt(startYearStr);
            int finalYear = Integer.parseInt(finalYearStr);
            double initialValue = Double.parseDouble(initialValueStr);

            // Calculate the number of years
            int years = finalYear - startYear;

            // Calculate the final value
            double finalValue = initialValue * Math.pow(1 + inflationRate, years);
            finalValueTextView.setText(String.format("%.2f", finalValue));
        } catch (NumberFormatException e) {
            finalValueTextView.setText("Invalid input");
        }
    }

    private void resetFields() {
        // Clear EditText fields
        inflationEditText.setText("");
        startYearEditText.setText("");
        finalYearEditText.setText("");
        initialValueEditText.setText("");

        // Clear result TextView
        finalValueTextView.setText("");
    }
    private void keybord(View v) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
}