package com.example.emi_master.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emi_master.R;

import java.text.DecimalFormat;


public class Bmi_calculator_Fragment extends Fragment {

    private EditText heightEditText;
    private EditText weightEditText;
    private TextView bmiTextView;

    private Button btn_emi_calculate;
    private Button btn_emi_reset;

ImageView backbmi;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bmi_calculator_, container, false);
        backbmi=view.findViewById(R.id.backbmi);
        heightEditText = view.findViewById(R.id.height);
        weightEditText = view.findViewById(R.id.Weight);
        bmiTextView = view.findViewById(R.id.body_mass);
        btn_emi_calculate = view.findViewById(R.id.btn_emi_calculate);
        btn_emi_reset = view.findViewById(R.id.btn_emi_reset);
        backbmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().finish();
            }
        });
        btn_emi_calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateBMI();
                keybord(v);
            }
        });

        btn_emi_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFields();
            }
        });

    return view;
    }
    private void calculateBMI() {
        hideKeyboard();
        String heightStr = heightEditText.getText().toString().trim();
        String weightStr = weightEditText.getText().toString().trim();

        if (!heightStr.isEmpty() && !weightStr.isEmpty()) {
            try {
                double height = Double.parseDouble(heightStr) / 100; // Convert height to meters if entered in cm
                double weight = Double.parseDouble(weightStr);

                double bmi = calculateBMIValue(height, weight);

                DecimalFormat decimalFormat = new DecimalFormat("#.##");
                String formattedBMI = decimalFormat.format(bmi);
                bmiTextView.setText(formattedBMI);
            } catch (NumberFormatException e) {
                // Handle invalid input gracefully
                bmiTextView.setText(getString(R.string.error_invalid_input));
            }
        }
        else {
            Toast.makeText(requireActivity(), "please enter input ", Toast.LENGTH_SHORT).show();
        }
    }
    private void hideKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    private double calculateBMIValue(double height, double weight) {
        return weight / (height * height);
    }

    private void resetFields() {
        heightEditText.setText("");
        weightEditText.setText("");
        bmiTextView.setText("");
        Toast.makeText(getContext(), "Value is 0", Toast.LENGTH_SHORT).show();
    }
    private  void keybord(View v){
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
}