package com.example.emi_master.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emi_master.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class Futures_Fragment extends Fragment {
    private Map<String, Double> stateBrokerageRates;
    private Map<String, Double> stateSttRates;
    private Map<String, Double> stateExchangeTxnChargeRates;
    ImageView back_future;
    private Map<String, Double> stateStampDutyRates;
    private TextInputEditText edtBuy, edtSell, edtQuantity;
    private Spinner spinnerStateoption;
    private TextView txtNetProfit, txtInvestAmount, txtTurnover1, txtBrokerage1, txtStt1, txtExchange1, txtGst1, txtSebi1, txtStamp1, txtTotalChg1;
    private ExtendedFloatingActionButton btnFutureCalculate, btnFutureReset;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_futures_, container, false);

        // Initialize views
        edtBuy = view.findViewById(R.id.edt_buy);
        edtSell = view.findViewById(R.id.edt_sell);
        edtQuantity = view.findViewById(R.id.edt_quantity);
        spinnerStateoption = view.findViewById(R.id.spinner_state_future);
        txtNetProfit = view.findViewById(R.id.txt_net_profit);
        back_future=view.findViewById(R.id.back_future);
        txtInvestAmount = view.findViewById(R.id.InvestAmount);
        txtTurnover1 = view.findViewById(R.id.txt_turnover1);
        txtBrokerage1 = view.findViewById(R.id.txt_brokerage1);
        txtStt1 = view.findViewById(R.id.txt_stt1);
        txtExchange1 = view.findViewById(R.id.txt_exchange1);
        txtGst1 = view.findViewById(R.id.txt_gst1);
        txtSebi1 = view.findViewById(R.id.txt_sebi1);
        txtStamp1 = view.findViewById(R.id.txt_stamp1);
        txtTotalChg1 = view.findViewById(R.id.txt_totalchg1);
        btnFutureCalculate = view.findViewById(R.id.btn_future_calculate);
        btnFutureReset = view.findViewById(R.id.btn_future_reset);

        // Initialize spinner adapter
        String[] states = {"Andhra Pradesh", "Delhi", "Gujarat", "Karnataka", "Madhya Pradesh", "Maharashtra", "Rajasthan", "Tamil Nadu", "Uttar Pradesh", "West Bengal"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, states);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStateoption.setAdapter(adapter);

        back_future.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().finish();
            }
        });
        // Initialize state rates
        initializeStateRates();

        // Set button click listeners
        setListeners();

        return view;
    }

    private void initializeStateRates() {
        stateBrokerageRates = new HashMap<>();
        stateSttRates = new HashMap<>();
        stateExchangeTxnChargeRates = new HashMap<>();
        stateStampDutyRates = new HashMap<>();

        // Example rates, adjust according to actual values
        stateBrokerageRates.put("Andhra Pradesh", 0.0003);
        stateSttRates.put("Andhra Pradesh", 0.00025);
        stateExchangeTxnChargeRates.put("Andhra Pradesh", 0.0000345);
        stateStampDutyRates.put("Andhra Pradesh", 0.00015);

        stateBrokerageRates.put("Delhi", 0.00035);
        stateSttRates.put("Delhi", 0.0003);
        stateExchangeTxnChargeRates.put("Delhi", 0.00004);
        stateStampDutyRates.put("Delhi", 0.0002);

        // Add other states similarly
    }

    private void setListeners() {
        btnFutureCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateFuture();
                keybord(v);
            }
        });

        btnFutureReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFields();
            }
        });
    }

    private void calculateFuture() {
        // Get user inputs
        String buyPriceStr = edtBuy.getText().toString().trim();
        String sellPriceStr = edtSell.getText().toString().trim();
        String quantityStr = edtQuantity.getText().toString().trim();
        String selectedState = spinnerStateoption.getSelectedItem().toString();

        if (TextUtils.isEmpty(buyPriceStr) || TextUtils.isEmpty(sellPriceStr) || TextUtils.isEmpty(quantityStr)) {
            // Handle empty inputs, show error message or return
            return;
        }

        // Parse input values
        double buyPrice = Double.parseDouble(buyPriceStr);
        double sellPrice = Double.parseDouble(sellPriceStr);
        double quantity = Double.parseDouble(quantityStr);

        // Calculate investment amount
        double investmentAmount = buyPrice * quantity;

        // Calculate turnover
        double turnover = (buyPrice + sellPrice) * quantity;

        // Get state-specific rates
        double brokerageRate = stateBrokerageRates.getOrDefault(selectedState, 0.0003); // Default rate if state not found
        double sttRate = stateSttRates.getOrDefault(selectedState, 0.00025);
        double exchangeTxnChargeRate = stateExchangeTxnChargeRates.getOrDefault(selectedState, 0.0000345);
        double stampDutyRate = stateStampDutyRates.getOrDefault(selectedState, 0.00015);

        // Calculate brokerage based on turnover
        double brokerage = brokerageRate * turnover;

        // Calculate STT (Securities Transaction Tax) based on turnover
        double stt = sttRate * turnover;

        // Calculate exchange transaction charge based on turnover
        double exchangeTxnCharge = exchangeTxnChargeRate * turnover;

        // Calculate GST based on total charges
        double totalCharges = brokerage + stt + exchangeTxnCharge;
        double gst = calculateGST(totalCharges);

        // Calculate SEBI turnover fees based on turnover
        double sebiFees = calculateSebiFees(turnover);

        // Calculate stamp duty based on investment amount
        double stampDuty = stampDutyRate * investmentAmount;

        // Calculate total charges including all taxes and fees
        double totalTaxAndCharges = brokerage + stt + exchangeTxnCharge + gst + sebiFees + stampDuty;

        // Calculate net profit
        double netProfit = (sellPrice - buyPrice) * quantity - totalTaxAndCharges;

        // Display calculated values
        txtInvestAmount.setText(String.format(Locale.US, "%.2f", investmentAmount));
        txtTurnover1.setText(String.format(Locale.US, "%.2f", turnover));
        txtBrokerage1.setText(String.format(Locale.US, "%.2f", brokerage));
        txtStt1.setText(String.format(Locale.US, "%.2f", stt));
        txtExchange1.setText(String.format(Locale.US, "%.2f", exchangeTxnCharge));
        txtGst1.setText(String.format(Locale.US, "%.2f", gst));
        txtSebi1.setText(String.format(Locale.US, "%.2f", sebiFees));
        txtStamp1.setText(String.format(Locale.US, "%.2f", stampDuty));
        txtTotalChg1.setText(String.format(Locale.US, "%.2f", totalTaxAndCharges));
        txtNetProfit.setText(String.format(Locale.US, "%.2f", netProfit));
    }

    private void resetFields() {
        edtBuy.setText("");
        edtSell.setText("");
        edtQuantity.setText("");
        txtNetProfit.setText("Net Profit: 0");
        txtInvestAmount.setText("0");
        txtTurnover1.setText("0");
        txtBrokerage1.setText("0");
        txtStt1.setText("0");
        txtExchange1.setText("0");
        txtGst1.setText("0");
        txtSebi1.setText("0");
        txtStamp1.setText("0");
        txtTotalChg1.setText("0");
        Toast.makeText(getContext(), "Value is 0", Toast.LENGTH_SHORT).show();
    }

    private double calculateBrokerage(double turnover) {
        // Calculate brokerage based on your brokerage rates
        return 0.0003 * turnover; // Example rate
    }

    private double calculateSTT(double turnover) {
        // Calculate STT (Securities Transaction Tax)
        return 0.00025 * turnover; // Example rate
    }

    private double calculateExchangeTxnCharge(double turnover) {
        // Calculate exchange transaction charges
        return 0.0000345 * turnover; // Example rate
    }

    private double calculateGST(double charges) {
        // Calculate GST on the total charges
        return 0.18 * charges; // Example rate
    }

    private double calculateSebiFees(double turnover) {
        // Calculate SEBI turnover fees
        return 0.000001 * turnover; // Example rate
    }

    private double calculateStampDuty(double investmentAmount) {
        // Calculate stamp duty
        return 0.00015 * investmentAmount; // Example rate
    }
    private void keybord(View v) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
}
