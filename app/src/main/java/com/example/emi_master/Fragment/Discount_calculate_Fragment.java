package com.example.emi_master.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emi_master.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;


public class Discount_calculate_Fragment extends Fragment {
    private TextInputEditText edtInitialValue, edtDiscount;
    private TextView txtFinalValue, txtDiscountValue;
    private ExtendedFloatingActionButton btnCalculate, btnReset;

ImageView backdiscount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_discount_calculate_, container, false);
        edtInitialValue = view.findViewById(R.id.edt_intialvalue);
        edtDiscount = view.findViewById(R.id.edt_discount);
        txtFinalValue = view.findViewById(R.id.txt_finalvalue);
        txtDiscountValue = view.findViewById(R.id.txt_discount_value);
        btnCalculate = view.findViewById(R.id.btn_brake_calculate);
        btnReset = view.findViewById(R.id.btn_breake_reset);
        backdiscount=view.findViewById(R.id.backdiscount);

        backdiscount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().finish();
            }
        });

        // Set click listeners
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateDiscount();
                keybord(v);
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFields();
                Toast.makeText(getContext(), "Value is 0", Toast.LENGTH_SHORT).show();
            }
        });

        return view;

    }
    private void calculateDiscount() {
        String initialValueStr = edtInitialValue.getText().toString().trim();
        String discountStr = edtDiscount.getText().toString().trim();

        if (TextUtils.isEmpty(initialValueStr) || TextUtils.isEmpty(discountStr)) {
            Toast.makeText(getActivity(), "Please enter both values", Toast.LENGTH_SHORT).show();
            return;
        }

        double initialValue = Double.parseDouble(initialValueStr);
        double discount = Double.parseDouble(discountStr);

        double discountValue = initialValue * discount / 100;
        double finalValue = initialValue - discountValue;

        txtDiscountValue.setText(String.format("%.2f", discountValue));
        txtFinalValue.setText(String.format("%.2f", finalValue));
    }

    private void resetFields() {
        edtInitialValue.setText("");
        edtDiscount.setText("");
        txtDiscountValue.setText("");
        txtFinalValue.setText("");
    }
    private void keybord(View v) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
}