package com.example.emi_master.Fragment;

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
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.text.DecimalFormat;


public class Time_calculate_Fragment extends Fragment {
    private EditText editTextTime;
    private Spinner spinnerFromUnit;
    private TableLayout resultTableLayout;
    ExtendedFloatingActionButton btn_business_reset;
    ImageView backtime;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_time_calculate_, container, false);
        backtime=view.findViewById(R.id.backtime);

        editTextTime = view.findViewById(R.id.editTexttime);
        spinnerFromUnit = view.findViewById(R.id.spinnerFromUnit);
        resultTableLayout = view.findViewById(R.id.MetricLayout);
        btn_business_reset = view.findViewById(R.id.btn_business_reset);

        backtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().finish();
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.time_units_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFromUnit.setAdapter(adapter);



        ExtendedFloatingActionButton btnCalculate = view.findViewById(R.id.btn_business_calculate);
        btn_business_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextTime.setText("");

                // Set the spinner to the first item
                spinnerFromUnit.setSelection(0);

                // Clear the result table
                resultTableLayout.removeAllViews();
                Toast.makeText(getContext(), "Value is 0", Toast.LENGTH_SHORT).show();
            }
        });
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keybord(v);
                // Calculate and display the result
                calculateTime();
            }
        });
    return view;
    }
    private void calculateTime() {
        hideKeyboard();
        String timeStr = editTextTime.getText().toString().trim();
        if (timeStr.isEmpty()) {
            editTextTime.setError("Please enter a valid time");
            return;
        }

        double time = Double.parseDouble(timeStr);
        String selectedUnit = spinnerFromUnit.getSelectedItem().toString();

        // Perform time conversion based on selected unit
        double convertedTime;
        switch (selectedUnit) {
            case "Second":
                convertedTime = time;
                break;
            case "Minute":
                convertedTime = time * 60;
                break;
            case "Hour":
                convertedTime = time * 3600;
                break;
            case "Day":
                convertedTime = time * 86400;
                break;
            case "Week":
                convertedTime = time * 604800;
                break;
            case "Month":
                convertedTime = time * (30.4368 * 24 * 3600); // Average number of seconds in a month
                break;
            case "Year":
                convertedTime = time * (365.2425 * 24 * 3600); // Average number of seconds in a year (including leap years)
                break;
            case "Decade":
                convertedTime = time * (10 * 365.2425 * 24 * 3600); // Approximate value for 10 years
                break;
            case "Century":
                convertedTime = time * (100 * 365.2425 * 24 * 3600); // Approximate value for 100 years
                break;
            case "Millennium":
                convertedTime = time * (1000 * 365.2425 * 24 * 3600); // Approximate value for 1000 years
                break;
            default:
                convertedTime = time;
        }
        updateResultTable(convertedTime);
    }
    private void hideKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    private void addRow(String unit, String value, int textColor) {
        TableRow row = new TableRow(requireContext());
        TextView textViewUnit = new TextView(requireContext());
        textViewUnit.setText(unit);
        textViewUnit.setTextColor(Color.WHITE);
        TextView textViewValue = new TextView(requireContext());
        textViewValue.setText(value);
        textViewValue.setTextColor(Color.WHITE);
        textViewValue.getGravity();
        textViewUnit.getGravity();
        row.addView(textViewUnit);
        row.addView(textViewValue);
        resultTableLayout.addView(row);
    }

    private void updateResultTable(double convertedTime) {
        resultTableLayout.removeAllViews();

        DecimalFormat decimalFormat = new DecimalFormat("#.###################");

        String[] units = getResources().getStringArray(R.array.time_units_array);
        for (String unit : units) {
            double value;
            switch (unit) {
                case "Second":
                    value = convertedTime;
                    break;
                case "Minute":
                    value = convertedTime / 60;
                    break;
                case "Hour":
                    value = convertedTime / 3600;
                    break;
                case "Day":
                    value = convertedTime / 86400;
                    break;
                case "Week":
                    value = convertedTime / 604800;
                    break;
                case "Month":
                    value = convertedTime / (30.4368 * 24 * 3600); // Average number of seconds in a month
                    break;
                case "Year":
                    value = convertedTime / (365.2425 * 24 * 3600); // Average number of seconds in a year (including leap years)
                    break;
                case "Decade":
                    value = convertedTime / (10 * 365.2425 * 24 * 3600); // Approximate value for 10 years
                    break;
                case "Century":
                    value = convertedTime / (100 * 365.2425 * 24 * 3600); // Approximate value for 100 years
                    break;
                case "Millennium":
                    value = convertedTime / (1000 * 365.2425 * 24 * 3600); // Approximate value for 1000 years
                    break;
                default:
                    value = 0;
            }
            addRow(unit, decimalFormat.format(value), Color.BLACK); // Example usage with white color
        }
    }
    private void keybord(View v) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
}