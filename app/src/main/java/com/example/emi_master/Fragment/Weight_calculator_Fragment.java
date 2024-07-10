package com.example.emi_master.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emi_master.R;
import com.google.android.material.appbar.MaterialToolbar;

import java.text.DecimalFormat;


public class Weight_calculator_Fragment extends Fragment {
    private EditText weightEditText;
    private TableLayout resultLayout;
    private Spinner spinnerFromUnit;

    private static final double[] WEIGHT_CONVERSION_FACTORS = {
            // Conversion factors to grams
            0.001,      // Milligram
            0.01,       // Centigram
            1,          // Gram
            1000,       // Kilogram
            1000000,    // Metric Ton
            28.3495,    // Ounce
            453.592,    // Pound
            6350.29,    // Stone
            907184.74,  // Short Ton
            1016046.91  // Long Ton
    };
    ImageView backweight;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_weight_calculator_, container, false);
      


            weightEditText = view.findViewById(R.id.editTexttime);
            resultLayout = view.findViewById(R.id.resultLayout);
            spinnerFromUnit = view.findViewById(R.id.spinnerFromUnit);
        backweight=view.findViewById(R.id.backweight);
        backweight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().finish();
            }
        });
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                    R.array.Weight_units_array, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerFromUnit.setAdapter(adapter);

            view.findViewById(R.id.btn_business_calculate).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    calculateWeight();
                    keybord(v);
                }
            });
            view.findViewById(R.id.btn_business_reset).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    resetFields();
                }
            });

   return view;
    }
    private void calculateWeight() {
        hideKeyboard();
        String weightStr = weightEditText.getText().toString().trim();
        if (weightStr.isEmpty()) {
            // Handle case when weight is empty
            Toast.makeText(requireActivity(), "weight is empty", Toast.LENGTH_SHORT).show();
            return;
        }
        double weight = Double.parseDouble(weightStr);

        String selectedUnit = spinnerFromUnit.getSelectedItem().toString();

        // Convert the entered weight to grams
        double weightInGrams = convertToGrams(weight, selectedUnit);

        // Display the conversion results for all units
        displayConversionResults(weightInGrams);
    }
    private void hideKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    private double convertToGrams(double weight, String fromUnit) {
        int selectedIndex = spinnerFromUnit.getSelectedItemPosition();
        double conversionFactor = WEIGHT_CONVERSION_FACTORS[selectedIndex];
        return weight * conversionFactor;
    }

    private void addResultRow(String unitLabel, double convertedWeight, int textColor) {
        TableRow row = new TableRow(requireContext());

        TextView unitTextView = new TextView(requireContext());
        unitTextView.setText(unitLabel);
        unitTextView.setTextColor(textColor); // Set text color for unit TextView
        row.addView(unitTextView);

        TextView weightTextView = new TextView(requireContext());
        DecimalFormat decimalFormat = new DecimalFormat("#.###################");
        weightTextView.setText(decimalFormat.format(convertedWeight));
        weightTextView.setTextColor(textColor); // Set text color for weight TextView
        row.addView(weightTextView);

        resultLayout.addView(row);
    }

    private void displayConversionResults(double weightInGrams) {
        resultLayout.removeAllViews();

        String[] unitLabels = getResources().getStringArray(R.array.Weight_units_array);
        for (int i = 0; i < unitLabels.length; i++) {
            double convertedWeight = weightInGrams / WEIGHT_CONVERSION_FACTORS[i];
            addResultRow(unitLabels[i], convertedWeight, Color.WHITE); // Example usage with white color
        }
    }

    private void resetFields() {
        weightEditText.setText("");
        resultLayout.removeAllViews();
        Toast.makeText(getContext(), "Value is 0", Toast.LENGTH_SHORT).show();
    }
    private void keybord(View v) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
}