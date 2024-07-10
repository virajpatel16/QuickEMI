package com.example.emi_master.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emi_master.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;


public class GSTFragment extends Fragment {

    private EditText edtGstAmount;
    private RadioGroup radioGroupGST;
    private RadioButton radioTwo, radioFive, radioTwelve, radioEighteen, radioTwentyEight, radioOther;
    private EditText edtCustomGST;
    private TextView txtNetAmount, txtGstAmount, txtTotalAmount, txtCgstSgst;
    private ExtendedFloatingActionButton btnCalculateGST, btnResetGST;
    private CardView resultduration;
    private double gstPercentage = 0.0;
    ImageView back_gst;
    private boolean isAddGST = true;
    private RadioButton addGSTRadio, removeGSTRadio;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=  inflater.inflate(R.layout.fragment_g_s_t, container, false);
        // Initialize views
        edtGstAmount = view.findViewById(R.id.edt_gst_amount);
        radioGroupGST = view.findViewById(R.id.radio_group_gst);

        edtGstAmount = view.findViewById(R.id.edt_gst_amount);
        radioGroupGST = view.findViewById(R.id.radio_group_gst);
        radioTwo = view.findViewById(R.id.radio_two);
        radioFive = view.findViewById(R.id.radio_five);
        radioTwelve = view.findViewById(R.id.radio_twelve);
        radioEighteen = view.findViewById(R.id.radio_eighteen);
        radioTwentyEight = view.findViewById(R.id.radio_twentyeight);
        radioOther = view.findViewById(R.id.other);
        edtCustomGST = view.findViewById(R.id.gst);
        resultduration = view.findViewById(R.id.resultduration);
        txtNetAmount = view.findViewById(R.id.net_amount_edit_text);
        txtGstAmount = view.findViewById(R.id.gst_amount_edit_text);
        txtTotalAmount = view.findViewById(R.id.total_gst_involves);
        txtCgstSgst = view.findViewById(R.id.txt_cgst_sgst);
        btnCalculateGST = view.findViewById(R.id.btn_gst_calculate);
        btnResetGST = view.findViewById(R.id.btn_gst_reset);
        addGSTRadio = view.findViewById(R.id.add_gst);
        removeGSTRadio = view.findViewById(R.id.remove_gst);
        back_gst=view.findViewById(R.id.back_gst);

        // Hide the result duration initially
        resultduration.setVisibility(View.INVISIBLE);

        btnCalculateGST.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateGST();
                keybord(v);
            }
        });

        btnResetGST.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFields();
            }
        });

        back_gst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().finish();
            }
        });
        // Set click listeners

        resultduration.setVisibility(View.INVISIBLE);

        // RadioGroup listener for GST options
        radioGroupGST.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.other) {
                    edtCustomGST.setVisibility(View.VISIBLE);  // Show custom GST EditText
                } else {
                    edtCustomGST.setVisibility(View.GONE);     // Hide custom GST EditText
                }
            }
        });
        addGSTRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAddGST = true;
            }
        });

        removeGSTRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAddGST = false;
            }
        });

        return view;
    }
    @SuppressLint("NonConstantResourceId")
    private void calculateGST() {
        String amountStr = edtGstAmount.getText().toString();
        String customGSTStr = edtCustomGST.getText().toString();

        // Show the result duration card
        resultduration.setVisibility(View.VISIBLE);

        if (amountStr.isEmpty()) {
            // Handle empty amount
            return;
        }

        double amount = Double.parseDouble(amountStr);

        // Determine GST percentage based on selected radio button or custom input
        if (radioTwo.isChecked()) {
            gstPercentage = 2.0;
        } else if (radioFive.isChecked()) {
            gstPercentage = 5.0;
        } else if (radioTwelve.isChecked()) {
            gstPercentage = 12.0;
        } else if (radioEighteen.isChecked()) {
            gstPercentage = 18.0;
        } else if (radioTwentyEight.isChecked()) {
            gstPercentage = 28.0;
        } else if (!customGSTStr.isEmpty()) {
            gstPercentage = Double.parseDouble(customGSTStr);
        }

        // Calculate GST amount and total amount based on whether to add or remove GST
        double gstAmount;
        if (isAddGST) {
            gstAmount = (amount * gstPercentage) / 100;
        } else {
            gstAmount = (amount * 100) / (100 + gstPercentage);
            gstAmount = amount - gstAmount;
        }

        double totalAmount = isAddGST ? amount + gstAmount : amount - gstAmount;

        // Display results
        txtNetAmount.setText(String.format("%.2f", amount));
        txtGstAmount.setText(String.format("%.2f", gstAmount));
        txtTotalAmount.setText(String.format("%.2f", totalAmount));
        txtCgstSgst.setText(String.format("(CGST : %.2f%% = %.2f)\n(SGST : %.2f%% = %.2f)",
                gstPercentage / 2, gstAmount / 2,
                gstPercentage / 2, gstAmount / 2));
    }

    private void resetFields() {
        edtGstAmount.setText("");
        edtCustomGST.setText("");
        txtNetAmount.setText("");
        txtGstAmount.setText("");
        txtTotalAmount.setText("");
        txtCgstSgst.setText("");
        radioGroupGST.clearCheck();
        radioTwo.setChecked(false);
        radioFive.setChecked(false);
        radioTwelve.setChecked(false);
        radioEighteen.setChecked(false);
        resultduration.setVisibility(View.INVISIBLE);
        radioTwentyEight.setChecked(false);
        radioOther.setChecked(false);
        Toast.makeText(getContext(), "Value is 0", Toast.LENGTH_SHORT).show();
    }private void keybord(View v) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

}