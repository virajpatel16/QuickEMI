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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emi_master.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Locale;

public class Option_Fragment extends Fragment {

    private Spinner spinnerState;
    private TextView investAmount, txtTurnover, txtBrokerage, txtSTT, txtExchange, txtGST, txtSEBI, txtStamp, txtTotalCharges, txtNetProfit;
    private TextInputEditText edtBuy, edtSell, edtQuantity;
ImageView back_option;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_option_, container, false);

        spinnerState = view.findViewById(R.id.spinner_state_option);
        investAmount = view.findViewById(R.id.InvestAmount);
        back_option=view.findViewById(R.id.back_option);
        txtTurnover = view.findViewById(R.id.txt_turnover1);
        txtBrokerage = view.findViewById(R.id.txt_brokerage1);
        txtSTT = view.findViewById(R.id.txt_stt1);
        txtExchange = view.findViewById(R.id.txt_exchange1);
        txtGST = view.findViewById(R.id.txt_gst1);
        txtSEBI = view.findViewById(R.id.txt_sebi1);
        txtStamp = view.findViewById(R.id.txt_stamp1);
        txtTotalCharges = view.findViewById(R.id.txt_totalchg1);
        txtNetProfit = view.findViewById(R.id.txt_net_profit);
        edtBuy = view.findViewById(R.id.edt_buy);
        edtSell = view.findViewById(R.id.edt_sell);
        edtQuantity = view.findViewById(R.id.edt_quantity);

        String[] states = {"Andhra Pradesh", "Delhi", "Gujarat", "Karnataka", "Madhya Pradesh", "Maharashtra", "Rajasthan", "Tamil Nadu", "Uttar Pradesh", "West Bengal"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, states);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerState.setAdapter(adapter);

        back_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().finish();
            }
        });

        spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String state = parent.getItemAtPosition(position).toString();

                // Ensure fields are not empty before parsing
                if (isValidInput()) {
                    double investmentAmount = Double.parseDouble(investAmount.getText().toString());
                    double buyPrice = Double.parseDouble(edtBuy.getText().toString());
                    double sellPrice = Double.parseDouble(edtSell.getText().toString());
                    int quantity = Integer.parseInt(edtQuantity.getText().toString());

                    double turnover = calculateTurnover(buyPrice, sellPrice, quantity);
                    double brokerage = calculateBrokerage(turnover, state);
                    double stt = calculateSTT(turnover);
                    double exchange = calculateExchange(turnover);
                    double gst = calculateGST(brokerage);
                    double sebi = calculateSEBI(turnover);
                    double stamp = calculateStamp(turnover);
                    double totalCharges = calculateTotalCharges(brokerage, stt, exchange, gst, sebi, stamp);
                    double netProfit = calculateNetProfit(sellPrice, buyPrice, quantity, totalCharges);

                    txtTurnover.setText("Turnover: " + turnover);
                    txtBrokerage.setText("Brokerage: " + brokerage);
                    txtSTT.setText("STT: " + stt);
                    txtExchange.setText("Exchange: " + exchange);
                    txtGST.setText("GST: " + gst);
                    txtSEBI.setText("SEBI: " + sebi);
                    txtStamp.setText("Stamp: " + stamp);
                    txtTotalCharges.setText("Total Charges: " + totalCharges);
                    txtNetProfit.setText("Net Profit: " + netProfit);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Set up button listeners
        view.findViewById(R.id.btn_option_calculate).setOnClickListener(this::calculate);
        view.findViewById(R.id.btn_option_calculate).setOnClickListener(this::keybord);
        view.findViewById(R.id.btn_option_reset).setOnClickListener(this::resetFields);

        return view;
    }

    private boolean isValidInput() {
        if (edtBuy.getText().toString().trim().isEmpty() ||
                edtSell.getText().toString().trim().isEmpty() ||
                edtQuantity.getText().toString().trim().isEmpty()) {
            // Handle empty inputs, show error message or return false
            return false;
        }
        return true;
    }

    private void calculate(View view) {
        // Ensure fields are not empty before parsing
        if (isValidInput()) {
            double buyPrice = Double.parseDouble(edtBuy.getText().toString());
            double sellPrice = Double.parseDouble(edtSell.getText().toString());
            int quantity = Integer.parseInt(edtQuantity.getText().toString());

            double turnover = (buyPrice + sellPrice) * quantity;
            double brokerage = turnover * 0.0003;
            double stt = sellPrice * quantity * 0.00025;
            double exchangeTxnCharge = turnover * 0.0000345;
            double gst = (brokerage + exchangeTxnCharge) * 0.18;
            double sebiFees = turnover * 0.000002;
            double stampDuty = buyPrice * quantity * 0.00015;
            double totalTaxAndCharges = brokerage + stt + exchangeTxnCharge + gst + sebiFees + stampDuty;
            double investmentAmount = buyPrice * quantity;
            double netProfit = (sellPrice * quantity) - investmentAmount - totalTaxAndCharges;

            investAmount.setText(String.format(Locale.US, "%.2f",investmentAmount));
            txtTurnover.setText(String.format(Locale.US, "%.2f",turnover));
            txtBrokerage.setText(String.format(Locale.US, "%.2f",brokerage));
            txtSTT.setText(String.format(Locale.US, "%.2f",stt));
            txtExchange.setText(String.format(Locale.US, "%.2f",exchangeTxnCharge));
            txtGST.setText(String.format(Locale.US, "%.2f",gst));
            txtSEBI.setText(String.format(Locale.US, "%.2f",sebiFees));
            txtStamp.setText(String.format(Locale.US, "%.2f",stampDuty));
            txtTotalCharges.setText(String.format(Locale.US, "%.2f",totalTaxAndCharges));
            txtNetProfit.setText(String.format(Locale.US, "%.2f",netProfit));
        }
    }

    private void resetFields(View view) {
        edtBuy.setText("");
        edtSell.setText("");
        edtQuantity.setText("");
        investAmount.setText("0");
        txtTurnover.setText("0");
        txtBrokerage.setText("0");
        txtSTT.setText("0");
        txtExchange.setText("0");
        txtGST.setText("0");
        txtSEBI.setText("0");
        txtStamp.setText("0");
        txtTotalCharges.setText("0");
        txtNetProfit.setText("0");
        Toast.makeText(getContext(), "Value is 0", Toast.LENGTH_SHORT).show();
    }

    private double getBrokerageRate(String state) {
        switch (state) {
            case "Andhra Pradesh":
                return 0.05;
            case "Delhi":
                return 0.04;
            case "Gujarat":
                return 0.03;
            case "Karnataka":
                return 0.02;
            case "Madhya Pradesh":
                return 0.01;
            case "Maharashtra":
                return 0.06;
            case "Rajasthan":
                return 0.07;
            case "Tamil Nadu":
                return 0.08;
            case "Uttar Pradesh":
                return 0.09;
            case "West Bengal":
                return 0.10;
            default:
                return 0.05;
        }
    }

    private double calculateTurnover(double buyPrice, double sellPrice, int quantity) {
        return (buyPrice + sellPrice) * quantity;
    }

    private double calculateBrokerage(double turnover, String state) {
        double brokerageRate = getBrokerageRate(state);
        return turnover * brokerageRate;
    }

    private double calculateSTT(double turnover) {
        return turnover * 0.001;
    }

    private double calculateExchange(double turnover) {
        return turnover * 0.002;
    }

    private double calculateGST(double brokerage) {
        return brokerage * 0.18;
    }

    private double calculateSEBI(double turnover) {
        return turnover * 0.0005;
    }

    private double calculateStamp(double turnover) {
        return turnover * 0.0001;
    }

    private double calculateTotalCharges(double brokerage, double stt, double exchange, double gst, double sebi, double stamp) {
        return brokerage + stt + exchange + gst + sebi + stamp;
    }

    private double calculateNetProfit(double sellPrice, double buyPrice, int quantity, double totalCharges) {
        return (sellPrice - buyPrice) * quantity - totalCharges;
    }
    private void keybord(View v) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
}
