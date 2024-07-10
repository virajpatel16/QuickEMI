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


public class Length_Calculate_Fragment extends Fragment {

    private EditText editTextLength;
    private Spinner spinnerFromUnit;
    private TableLayout metricLayout;
    private TableLayout imperialLayout;

    ImageView backlength;
    private static final double METER_TO_FEET = 3.28084;
    private static final double METER_TO_YARD = 1.09361;
    private static final double METER_TO_MILE = 0.000621371;
    private static final double METER_TO_NAUTICAL_MILE = 0.000539957;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_length__calculate_, container, false);

        editTextLength = view.findViewById(R.id.editTextLength);
        spinnerFromUnit = view.findViewById(R.id.spinnerFromUnit);
        metricLayout = view.findViewById(R.id.MetricLayout);
        imperialLayout = view.findViewById(R.id.ImperialLayout);
        backlength=view.findViewById(R.id.backlength);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.length_units_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFromUnit.setAdapter(adapter);

        backlength.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().finish();
            }
        });

        view.findViewById(R.id.btn_business_calculate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateLength();
                keybord(v);
            }
        });
        view.findViewById(R.id.btn_business_reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextLength.setText("");
                spinnerFromUnit.setSelection(0);
                Toast.makeText(getContext(), "Value is 0", Toast.LENGTH_SHORT).show();
                imperialLayout.removeAllViews();
                metricLayout.removeAllViews();
                Toast.makeText(getContext(), "Value is 0", Toast.LENGTH_SHORT).show();
            }
        });
    return view;
    }

    private void calculateLength() {
        hideKeyboard();
        String lengthStr = editTextLength.getText().toString().trim();
        if (lengthStr.isEmpty()) {
            editTextLength.setError("Please enter a valid length");
            return;
        }

        double length = Double.parseDouble(lengthStr);
        String selectedUnit = spinnerFromUnit.getSelectedItem().toString();

        double lengthInMeter;

        switch (selectedUnit) {
            case "Meter":
                lengthInMeter = length;
                break;
            case "Centimeter":
                lengthInMeter = length / 100;
                break;
            case "Decimeter":
                lengthInMeter = length / 10;
                break;
            case "Foot":
                lengthInMeter = length / METER_TO_FEET;
                break;
            case "Inch":
                lengthInMeter = length / METER_TO_FEET / 12;
                break;
            case "Kilometer":
                lengthInMeter = length * 1000;
                break;
            case "Yard":
                lengthInMeter = length / METER_TO_YARD;
                break;
            case "Mile":
                lengthInMeter = length / METER_TO_MILE;
                break;
            case "Nautical Mile":
                lengthInMeter = length / METER_TO_NAUTICAL_MILE;
                break;
            default:
                lengthInMeter = length;
        }


        updateTables(lengthInMeter);
    }
    private void hideKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    private void addRow(TableLayout tableLayout, String unit, String value, int textColor) {
        TableRow row = new TableRow(requireContext());
        TextView textViewUnit = new TextView(requireContext());
        textViewUnit.setText(unit);
        textViewUnit.setTextColor(textColor); // Set text color for unit TextView
        TextView textViewValue = new TextView(requireContext());
        textViewValue.setText(value);
        textViewValue.setTextColor(textColor); // Set text color for value TextView
        row.addView(textViewUnit);
        row.addView(textViewValue);
        tableLayout.addView(row);
    }

    private void updateTables(double lengthInMeter) {
        clearTables();

        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        // Update Metric TableLayout
        addRow(metricLayout, "Meter", decimalFormat.format(lengthInMeter), Color.WHITE);
        addRow(metricLayout, "Centimeter", decimalFormat.format(lengthInMeter * 100), Color.WHITE);
        addRow(metricLayout, "Decimeter", decimalFormat.format(lengthInMeter * 10), Color.WHITE);
        addRow(metricLayout, "Kilometer", decimalFormat.format(lengthInMeter / 1000), Color.WHITE);

        // Update Imperial TableLayout
        addRow(imperialLayout, "Foot", decimalFormat.format(lengthInMeter * METER_TO_FEET), Color.WHITE);
        addRow(imperialLayout, "Inch", decimalFormat.format(lengthInMeter * METER_TO_FEET * 12), Color.WHITE);
        addRow(imperialLayout, "Yard", decimalFormat.format(lengthInMeter * METER_TO_YARD), Color.WHITE);
        addRow(imperialLayout, "Mile", decimalFormat.format(lengthInMeter * METER_TO_MILE), Color.WHITE);
        addRow(imperialLayout, "Nautical Mile", decimalFormat.format(lengthInMeter * METER_TO_NAUTICAL_MILE), Color.WHITE);
    }


    private void clearTables() {
        metricLayout.removeAllViews();
        imperialLayout.removeAllViews();
    }
    private void keybord(View v) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
}