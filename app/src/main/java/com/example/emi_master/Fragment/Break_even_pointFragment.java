package com.example.emi_master.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.emi_master.R;
import com.google.android.material.textfield.TextInputEditText;


public class Break_even_pointFragment extends Fragment {

    ImageView back_break;
    LinearLayout finalvalue;
    private TextInputEditText edtFixedCost, edtVariableCost, edtSellingPrice;
    private EditText answerBreakEven;
    Button calculate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_break_even_point, container, false);
        back_break = view.findViewById(R.id.back_break);
        edtFixedCost = view.findViewById(R.id.FixedcostEditText);
        edtVariableCost = view.findViewById(R.id.VeriableCostEdittext);
        edtSellingPrice = view.findViewById(R.id.SellingpriceEdittext);
        answerBreakEven = view.findViewById(R.id.answerbreak);
        finalvalue = view.findViewById(R.id.finalvalue);
        back_break.setOnClickListener(v -> requireActivity().finish());

        // Set click listener for calculation button
        calculate = view.findViewById(R.id.btn_brake_calculate);
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keybord(v);
                calculateBreakEven();
            }
        });

        view.findViewById(R.id.btn_breake_reset).setOnClickListener(v -> resetBreakEven());
        finalvalue.setVisibility(View.INVISIBLE);
        return view;
    }

    private void calculateBreakEven() {
        String fixedCostText = edtFixedCost.getText().toString().trim();
        String variableCostText = edtVariableCost.getText().toString().trim();
        String sellingPriceText = edtSellingPrice.getText().toString().trim();
        finalvalue.setVisibility(View.VISIBLE);

        // Validate input fields
        if (fixedCostText.isEmpty() || variableCostText.isEmpty() || sellingPriceText.isEmpty()) {
            // Handle empty fields
            if (fixedCostText.isEmpty()) edtFixedCost.setError("Enter fixed cost");
            if (variableCostText.isEmpty()) edtVariableCost.setError("Enter variable cost");
            if (sellingPriceText.isEmpty()) edtSellingPrice.setError("Enter selling price");
            return;
        }

        // Parse input values
        double fixedCost = Double.parseDouble(fixedCostText);
        double variableCost = Double.parseDouble(variableCostText);
        double sellingPrice = Double.parseDouble(sellingPriceText);

        // Calculate break-even point
        double breakEvenPoint;

        if (variableCost == 0) {
            // Total variable cost case
            breakEvenPoint = fixedCost / sellingPrice;
        } else {
            // Variable cost per unit case
            breakEvenPoint = fixedCost / (sellingPrice - variableCost);
        }

        // Display result
        answerBreakEven.setText(String.format("%.2f", breakEvenPoint));
    }

    private void resetBreakEven() {
        edtFixedCost.setText("");
        edtVariableCost.setText("");
        edtSellingPrice.setText("");
        answerBreakEven.setText("");
        finalvalue.setVisibility(View.INVISIBLE);
        Toast.makeText(getContext(), "Value is 0", Toast.LENGTH_SHORT).show();
    }

    private void keybord(View v) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
}