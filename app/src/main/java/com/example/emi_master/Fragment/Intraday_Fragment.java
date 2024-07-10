package com.example.emi_master.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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

import java.text.NumberFormat;
import java.util.Locale;

public class Intraday_Fragment extends Fragment {
    private TextInputEditText edtBuyPrice, edtSellPrice, edtQuantity;
    private TextView edtNetProfit, edtInvestmentAmount, edtTurnover, edtBrokerage,
            edtSttTotal, edtExchangeTxnCharge, edtGst, edtSebiTurnoverFees, edtStampDuty, edtTotalTaxCharges;
    private Spinner spinnerState;

    ImageView backintraday;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_intraday_, container, false);

        edtBuyPrice = view.findViewById(R.id.edt_price);
        edtSellPrice = view.findViewById(R.id.edt_sell_price);
        edtQuantity = view.findViewById(R.id.edt_quantity);
        edtNetProfit = view.findViewById(R.id.edt_net_profit);
        edtInvestmentAmount = view.findViewById(R.id.edt_investment_amount);
        edtTurnover = view.findViewById(R.id.edt_turnover);
        edtBrokerage = view.findViewById(R.id.edt_brokerage);
        edtSttTotal = view.findViewById(R.id.edt_stt_total);
        edtExchangeTxnCharge = view.findViewById(R.id.edt_exchange_txn_charge);
        edtGst = view.findViewById(R.id.edt_gst);
        edtSebiTurnoverFees = view.findViewById(R.id.edt_sebi_turnover_fees);
        edtStampDuty = view.findViewById(R.id.edt_stamp_duty);
        edtTotalTaxCharges = view.findViewById(R.id.edt_total_tax_charges);
        spinnerState = view.findViewById(R.id.spinner_state);
        backintraday=view.findViewById(R.id.backintraday);
        ExtendedFloatingActionButton btnCalculate = view.findViewById(R.id.btn_intraday_calculate);
        ExtendedFloatingActionButton btnReset = view.findViewById(R.id.btn_intraday_reset);

        backintraday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().finish();
            }
        });
        // Set up spinner with state names
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.States, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerState.setAdapter(adapter);

        btnCalculate.setOnClickListener(v -> calculateIntraday());
        btnCalculate.setOnClickListener(v -> keybord(v));

        btnReset.setOnClickListener(v -> resetFields());


    return view;
    }
    private void calculateIntraday() {
        String buyPriceStr = edtBuyPrice.getText().toString().trim();
        String sellPriceStr = edtSellPrice.getText().toString().trim();
        String quantityStr = edtQuantity.getText().toString().trim();
        String selectedState = spinnerState.getSelectedItem().toString();

        if (buyPriceStr.isEmpty() || sellPriceStr.isEmpty() || quantityStr.isEmpty() || selectedState.isEmpty()) {
            Toast.makeText(getActivity(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double buyPrice = Double.parseDouble(buyPriceStr);
            double sellPrice = Double.parseDouble(sellPriceStr);
            double quantity = Double.parseDouble(quantityStr);

            // Calculate the turnover
            double turnover = (buyPrice + sellPrice) * quantity;

            // Brokerage calculation (assuming a flat rate of 0.03%)
            double brokerage = turnover * 0.0003;

            // STT (Securities Transaction Tax) calculation
            double sttTotal = sellPrice * quantity * 0.00025;

            // Exchange transaction charge calculation
            double exchangeTxnCharge = turnover * 0.0000325;

            // GST (Goods and Services Tax) calculation
            double gst = (brokerage + exchangeTxnCharge) * 0.18;

            // SEBI (Securities and Exchange Board of India) turnover fees
            double sebiTurnoverFees = turnover * 0.000001;

            // Stamp Duty calculation (assuming a flat rate of 0.015%)
            double stampDuty = buyPrice * quantity * 0.00015;

            // Adjust calculations based on the selected state
            // Adjust calculations based on the selected state
            switch (selectedState) {
                case "Andhra Pradesh":
                    // Modify rates as needed for Andhra Pradesh
                    gst = (brokerage + exchangeTxnCharge) * 0.05; // Example: different GST rate for Andhra Pradesh
                    break;
                case "Delhi":
                    // Modify rates as needed for Delhi
                    // Example: different STT rate for Delhi
                    sttTotal = sellPrice * quantity * 0.0003;
                    break;
                case "Gujarat":
                    // Modify rates as needed for Gujarat
                    // Example: different Stamp Duty rate for Gujarat
                    stampDuty = buyPrice * quantity * 0.0002;
                    break;
                case "Karnataka":
                    // Modify rates as needed for Karnataka
                    // Example: different brokerage rate for Karnataka
                    brokerage = turnover * 0.0004;
                    break;
                case "Madhya Pradesh":
                    // Modify rates as needed for Madhya Pradesh
                    // Example: different SEBI fees for Madhya Pradesh
                    sebiTurnoverFees = turnover * 0.000002;
                    break;
                case "Maharashtra":
                    // Modify rates as needed for Maharashtra
                    // Example: adjusting turnover calculation for Maharashtra
                    turnover = (buyPrice + sellPrice) * quantity * 1.1;
                    break;
                case "Rajasthan":
                    // Modify rates as needed for Rajasthan
                    // Example: adjusting GST rate for Rajasthan
                    gst = (brokerage + exchangeTxnCharge) * 0.15;
                    break;
                case "Tamil Nadu":
                    // Modify rates as needed for Tamil Nadu
                    // Example: Adjusting GST rate
                    gst = (brokerage + exchangeTxnCharge) * 0.12; // Assuming a different GST rate for Tamil Nadu
                    break;
                case "Uttar Pradesh":
                    // Modify rates as needed for Uttar Pradesh
                    // Example: adjusting brokerage calculation for Uttar Pradesh
                    brokerage = turnover * 0.0002;
                    break;
                case "West Bengal":
                    // Modify rates as needed for West Bengal
                    // Example: adjusting STT calculation for West Bengal
                    sttTotal = sellPrice * quantity * 0.0002;
                    break;
                default:
                    Toast.makeText(getActivity(), "State not recognized", Toast.LENGTH_SHORT).show();
                    return;
            }

            // Total tax and charges calculation
            double totalTaxCharges = brokerage + sttTotal + exchangeTxnCharge + gst + sebiTurnoverFees + stampDuty;

            // Net profit calculation
            double netProfit = (sellPrice - buyPrice) * quantity - totalTaxCharges;

            // Update the UI with the calculated values
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
            edtInvestmentAmount.setText(currencyFormat.format(buyPrice * quantity));
            edtTurnover.setText(currencyFormat.format(turnover));
            edtBrokerage.setText(currencyFormat.format(brokerage));
            edtSttTotal.setText(currencyFormat.format(sttTotal));
            edtExchangeTxnCharge.setText(currencyFormat.format(exchangeTxnCharge));
            edtGst.setText(currencyFormat.format(gst));
            edtSebiTurnoverFees.setText(currencyFormat.format(sebiTurnoverFees));
            edtStampDuty.setText(currencyFormat.format(stampDuty));
            edtTotalTaxCharges.setText(currencyFormat.format(totalTaxCharges));
            edtNetProfit.setText(currencyFormat.format(netProfit));

        } catch (NumberFormatException e) {
            Toast.makeText(getActivity(), "Invalid input", Toast.LENGTH_SHORT).show();
        }
    }

    private void resetFields() {
        edtBuyPrice.setText("");
        edtSellPrice.setText("");
        edtQuantity.setText("");
        edtNetProfit.setText("");
        edtInvestmentAmount.setText("");
        edtTurnover.setText("");
        edtBrokerage.setText("");
        edtSttTotal.setText("");
        edtExchangeTxnCharge.setText("");
        edtGst.setText("");
        edtSebiTurnoverFees.setText("");
        edtStampDuty.setText("");
        edtTotalTaxCharges.setText("");
        Toast.makeText(getContext(), "Value is 0", Toast.LENGTH_SHORT).show();
    }
    private void keybord(View v) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
}