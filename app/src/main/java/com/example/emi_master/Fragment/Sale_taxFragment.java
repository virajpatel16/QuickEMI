package com.example.emi_master.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emi_master.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DecimalFormat;


public class Sale_taxFragment extends Fragment {

  ImageView back_sales;

  LinearLayout sale;

    TextInputEditText initialInvestmentcard, swpEditText;
    TextView answergrossprice, answertaxamount;
    ExtendedFloatingActionButton btn_emi_calculate, btn_emi_reset;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sale_tax, container, false);
        back_sales=view.findViewById(R.id.back_sales);

        initialInvestmentcard = view.findViewById(R.id.initialinvestmentcard);
        swpEditText = view.findViewById(R.id.swpEditText);
        answergrossprice = view.findViewById(R.id.answergrossprice);
        answertaxamount = view.findViewById(R.id.answertaxamount);
        btn_emi_calculate = view.findViewById(R.id.btn_emi_calculate);
        btn_emi_reset = view.findViewById(R.id.btn_emi_reset);
        sale=view.findViewById(R.id.sale);
        back_sales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().finish();
            }
        });
        sale.setVisibility(View.INVISIBLE);

        btn_emi_calculate.setOnClickListener(v -> calculateSalesTax());
        btn_emi_calculate.setOnClickListener(v -> keybord(v));

        // Set click listener for reset button
        btn_emi_reset.setOnClickListener(v -> resetFields());
    return  view;
    }
    private void calculateSalesTax() {
        String initialInvestmentText = initialInvestmentcard.getText().toString().trim();
        String swpText = swpEditText.getText().toString().trim();
        sale.setVisibility(View.VISIBLE);
        if (initialInvestmentText.isEmpty() || swpText.isEmpty()) {
            // Handle empty fields
            if (initialInvestmentText.isEmpty()) initialInvestmentcard.setError("Enter Net Amount");
            if (swpText.isEmpty()) swpEditText.setError("Enter Sale Tax (%)");
            return;
        }

        double netAmount = Double.parseDouble(initialInvestmentText);
        double saleTaxPercent = Double.parseDouble(swpText);

        // Calculate gross price
        double grossPrice = netAmount * (1 + saleTaxPercent / 100);

        // Calculate tax amount
        double taxAmount = grossPrice - netAmount;

        // Format results to two decimal places
        DecimalFormat df = new DecimalFormat("#.##");
        String formattedGrossPrice = df.format(grossPrice);
        String formattedTaxAmount = df.format(taxAmount);

        // Display results
        answergrossprice.setText("₹" + formattedGrossPrice);
        answertaxamount.setText("₹" + formattedTaxAmount);
    }

    private void resetFields() {
        initialInvestmentcard.setText("");
        swpEditText.setText("");
        answergrossprice.setText("");
        answertaxamount.setText("");
        sale.setVisibility(View.INVISIBLE);
        Toast.makeText(getContext(), "Value is 0", Toast.LENGTH_SHORT).show();
    }
    private void keybord(View v) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
}