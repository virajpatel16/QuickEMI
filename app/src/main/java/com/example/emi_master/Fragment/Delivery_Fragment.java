package com.example.emi_master.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emi_master.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Locale;
import java.util.Objects;


public class Delivery_Fragment extends Fragment {
    private TextInputEditText edtBuyPrice, edtSellPrice, edtQuantity;
        private Spinner spinnerState;
        ImageView back_delivery;
    private TextView txtNetProfit, txtInvestAmount, txtTurnover, txtBrokerage, txtStt, txtExchange, txtGst, txtSebi, txtStamp, txtTotalChg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_delivery_, container, false);
        edtBuyPrice =view. findViewById(R.id.edt_buy);
        back_delivery=view.findViewById(R.id.back_delivery);
        edtSellPrice = view.findViewById(R.id.edt_sell);
        edtQuantity = view.findViewById(R.id.edt_quantity);
        spinnerState = view.findViewById(R.id.spinner_state_delivery);
        txtNetProfit = view.findViewById(R.id.txt_net_profit);
        txtInvestAmount = view.findViewById(R.id.InvestAmount);
        txtTurnover = view.findViewById(R.id.txt_turnover1);
        txtBrokerage = view.findViewById(R.id.txt_brokerage1);
        txtStt = view.findViewById(R.id.txt_stt1);
        txtExchange = view.findViewById(R.id.txt_exchange1);
        txtGst = view.findViewById(R.id.txt_gst1);
        txtSebi = view.findViewById(R.id.txt_sebi1);
        txtStamp = view.findViewById(R.id.txt_stamp1);
        txtTotalChg = view.findViewById(R.id.txt_totalchg1);
back_delivery=view.findViewById(R.id.back_delivery);
        ExtendedFloatingActionButton btnCalculate =view. findViewById(R.id.btn_delivery_calculate);
        ExtendedFloatingActionButton btnReset = view.findViewById(R.id.btn_delivery_reset);
        // Define states array directly in Java code
        String[] states = {"Andhra Pradesh", "Delhi", "Gujarat", "Karnataka", "Madhya Pradesh", "Maharashtra", "Rajasthan", "Tamil Nadu", "Uttar Pradesh", "West Bengal"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, states);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerState.setAdapter(adapter);


        back_delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().finish();
            }
        });
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateDelivery();
                keybord(v);
            }
        });

        // Reset button click listener
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFields();
                Toast.makeText(getContext(), "Value is 0", Toast.LENGTH_SHORT).show();
            }
        });
        spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String state = parent.getItemAtPosition(position).toString();
                double investmentAmount = Double.parseDouble(txtInvestAmount.getText().toString());
                double taxAmount = getStateSpecificTax(state, investmentAmount);
                txtNetProfit.setText("Tax Amount: " + taxAmount);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        return view;
    }
    private void calculateDelivery() {
        String buyPriceStr = edtBuyPrice.getText().toString();
        String sellPriceStr = edtSellPrice.getText().toString();
        String quantityStr = edtQuantity.getText().toString();

        if (buyPriceStr.isEmpty() || sellPriceStr.isEmpty() || quantityStr.isEmpty()) {
            Toast.makeText(requireActivity(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double buyPrice = Double.parseDouble(buyPriceStr);
        double sellPrice = Double.parseDouble(sellPriceStr);
        int quantity = Integer.parseInt(quantityStr);

        double investmentAmount = buyPrice * quantity;
        double turnover = sellPrice * quantity;
        double brokerage = calculateBrokerage(turnover);
        double stt = calculateStt(turnover);
        double exchangeTxnCharge = calculateExchangeTxnCharge(turnover);
        double gst = calculateGst(brokerage);
        double sebiFees = calculateSebiFees(turnover);
        double stampDuty = calculateStampDuty(turnover);
        double totalCharges = brokerage + stt + exchangeTxnCharge + gst + sebiFees + stampDuty;

        String selectedState = spinnerState.getSelectedItem().toString();
        double stateSpecificTax = getStateSpecificTax(selectedState, investmentAmount);

        // Update UI with calculated values

        double totalTaxAndChargesAmount = brokerage + stt + exchangeTxnCharge + gst + sebiFees + stampDuty + stateSpecificTax;
        double netProfit = (sellPrice * quantity) - investmentAmount - totalTaxAndChargesAmount;
        txtInvestAmount.setText(String.format(Locale.US, "%.2f", investmentAmount));
        txtTurnover.setText(String.format(Locale.US, "%.2f", turnover));
        txtBrokerage.setText(String.format(Locale.US, "%.2f", brokerage));
        txtStt.setText(String.format(Locale.US, "%.2f", stt));
        txtExchange.setText(String.format(Locale.US, "%.2f", exchangeTxnCharge));
        txtGst.setText(String.format(Locale.US, "%.2f", gst));
        txtSebi.setText(String.format(Locale.US, "%.2f", sebiFees));
        txtStamp.setText(String.format(Locale.US, "%.2f", stampDuty));
        txtTotalChg.setText(String.format(Locale.US, "%.2f", totalCharges));
        txtTotalChg.setText(String.format(Locale.US, "%.2f", totalCharges));
        txtNetProfit.setText(String.format(Locale.US, "%.2f", netProfit));
    }

    private double calculateBrokerage(double turnover) {
        // Add your brokerage calculation logic here
        return 0.01 * turnover; // Example: 1% brokerage
    }

    private double calculateStt(double turnover) {
        // Add your STT calculation logic here
        return 0.001 * turnover; // Example: 0.1% STT
    }

    private double calculateExchangeTxnCharge(double turnover) {
        // Add your exchange transaction charge calculation logic here
        return 0.0001 * turnover; // Example: 0.01% exchange transaction charge
    }

    private double calculateGst(double brokerage) {
        // Add your GST calculation logic here
        return 0.18 * brokerage; // Example: 18% GST
    }

    private double calculateSebiFees(double turnover) {
        // Add your SEBI fees calculation logic here
        return 0.00005 * turnover; // Example: 0.005% SEBI turnover fees
    }

    private double calculateStampDuty(double turnover) {
        // Add your stamp duty calculation logic here
        return 0.00015 * turnover; // Example: 0.015% stamp duty
    }

    private void resetFields() {
        edtBuyPrice.setText("");
        edtSellPrice.setText("");
        edtQuantity.setText("");
        txtNetProfit.setText("0");
        txtInvestAmount.setText("0");
        txtTurnover.setText("0");
        txtBrokerage.setText("0");
        txtStt.setText("0");
        txtExchange.setText("0");
        txtGst.setText("0");
        txtSebi.setText("0");
        txtStamp.setText("0");
        txtTotalChg.setText("0");
    }
    private double getStateSpecificTax(String state, double investmentAmount) {
        double taxRate = 0.0;

        switch (state) {
            case "Andhra Pradesh":
                taxRate = 0.05; // 5%
                break;
            case "Delhi":
                taxRate = 0.04; // 4%
                break;
            case "Gujarat":
                taxRate = 0.03; // 3%
                break;
            case "Karnataka":
                taxRate = 0.02; // 2%
                break;
            case "Madhya Pradesh":
                taxRate = 0.01; // 1%
                break;
            case "Maharashtra":
                taxRate = 0.06; // 6%
                break;
            case "Rajasthan":
                taxRate = 0.07; // 7%
                break;
            case "Tamil Nadu":
                taxRate = 0.08; // 8%
                break;
            case "Uttar Pradesh":
                taxRate = 0.09; // 9%
                break;
            case "West Bengal":
                taxRate = 0.10; // 10%
                break;
            default:
                break;
        }

        // Calculate tax amount as a percentage of the investment amount
        return (taxRate / 100) * investmentAmount;
    }

    private void keybord(View v) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
    }