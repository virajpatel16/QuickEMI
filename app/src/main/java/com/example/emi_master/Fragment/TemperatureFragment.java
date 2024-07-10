package com.example.emi_master.Fragment;

import android.content.Context;
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
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.text.DecimalFormat;


public class TemperatureFragment extends Fragment {

    private EditText editTextTemperature;
    private Spinner spinnerFromUnit;
    private TableLayout resultTableLayout;
    ImageView backtemperature;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_temperature, container, false);
        backtemperature=view.findViewById(R.id.backtemperature);
        editTextTemperature = view.findViewById(R.id.editTextTemperature);
        spinnerFromUnit = view.findViewById(R.id.spinnerFromUnit);
        resultTableLayout = view.findViewById(R.id.MetricLayout);

        backtemperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().finish();
            }
        });


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.temperature_units_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFromUnit.setAdapter(adapter);

        ExtendedFloatingActionButton btnCalculate = view.findViewById(R.id.btn_business_calculate);
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateTemperature();
                keybord(v);
            }
        });

        ExtendedFloatingActionButton btnReset = view.findViewById(R.id.btn_business_reset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextTemperature.setText("");
                spinnerFromUnit.setSelection(0);
                resultTableLayout.removeAllViews();
                Toast.makeText(getContext(), "Value is 0", Toast.LENGTH_SHORT).show();
            }
        });
    return  view;
    }

    private void calculateTemperature() {
        hideKeyboard();
        String temperatureStr = editTextTemperature.getText().toString().trim();

        if (temperatureStr.isEmpty()) {
            Toast.makeText(requireContext(), "Please enter a temperature", Toast.LENGTH_SHORT).show();
            return;
        }

        double temperature = Double.parseDouble(temperatureStr);
        String fromUnit = spinnerFromUnit.getSelectedItem().toString();

        // Clear the result table before adding new results
        resultTableLayout.removeAllViews();

        // Iterate over all units and display the converted temperatures
        String[] units = getResources().getStringArray(R.array.temperature_units_array);
        for (String unit : units) {
            double convertedTemperature = convertTemperature(temperature, fromUnit, unit);
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            String result = decimalFormat.format(convertedTemperature);
            updateResultTable(result, unit);
        }
    }
    private void hideKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    private double convertTemperature(double temperature, String fromUnit, String toUnit) {
        double result = temperature;

        if (fromUnit.equals("Celsius")) {
            if (toUnit.equals("Fahrenheit")) {
                result = (temperature * 9 / 5) + 32;
            } else if (toUnit.equals("Kelvin")) {
                result = temperature + 273.15;
            } else if (toUnit.equals("Rankine")) {
                result = (temperature + 273.15) * 9 / 5;
            }
        } else if (fromUnit.equals("Fahrenheit")) {
            if (toUnit.equals("Celsius")) {
                result = (temperature - 32) * 5 / 9;
            } else if (toUnit.equals("Kelvin")) {
                result = (temperature + 459.67) * 5 / 9;
            } else if (toUnit.equals("Rankine")) {
                result = temperature + 459.67;
            }
        } else if (fromUnit.equals("Kelvin")) {
            if (toUnit.equals("Celsius")) {
                result = temperature - 273.15;
            } else if (toUnit.equals("Fahrenheit")) {
                result = (temperature * 9 / 5) - 459.67;
            } else if (toUnit.equals("Rankine")) {
                result = temperature * 9 / 5;
            }
        } else if (fromUnit.equals("Rankine")) {
            if (toUnit.equals("Celsius")) {
                result = (temperature - 491.67) * 5 / 9;
            } else if (toUnit.equals("Fahrenheit")) {
                result = temperature - 459.67;
            } else if (toUnit.equals("Kelvin")) {
                result = temperature * 5 / 9;
            }
        }

        return result;
    }

    private void updateResultTable(String result, String unit) {
        TableRow row = new TableRow(requireContext());
        TextView textViewResult = new TextView(requireContext());
        textViewResult.setText(result + " " + unit);
        textViewResult.setTextColor(getResources().getColor(R.color.white));
        row.addView(textViewResult);
        resultTableLayout.addView(row);
    }
    private void keybord(View v) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
}