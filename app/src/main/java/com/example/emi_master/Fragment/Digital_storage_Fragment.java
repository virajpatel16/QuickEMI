package com.example.emi_master.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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

public class Digital_storage_Fragment extends Fragment {


    private TextInputEditText edtValueByte;
    private TextView txtBit, txtByte, txtKilobyte, txtMegabyte, txtGigabyte, txtTerabyte, txtPetabyte;
    private ExtendedFloatingActionButton btnCalculate, btnReset;

    ImageView backdigital;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_digital_storage_, container, false);
        edtValueByte = view.findViewById(R.id.edt_value_byte);
        txtBit = view.findViewById(R.id.txt_bit);
        txtByte = view.findViewById(R.id.txt_byte);
        txtKilobyte = view.findViewById(R.id.txt_killobyte);
        backdigital=view.findViewById(R.id.backdigital);
        txtMegabyte = view.findViewById(R.id.txt_megabyte);
        txtGigabyte = view.findViewById(R.id.txt_gigabyte);
        txtTerabyte = view.findViewById(R.id.txt_terabyte);
        txtPetabyte = view.findViewById(R.id.txt_perabyte);
        btnCalculate = view.findViewById(R.id.btn_digital_calculate);
        btnReset = view.findViewById(R.id.btn_digital_reset);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateValues();
                keybord(v);
            }
        });

        backdigital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().finish();
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetValues();
            }
        });

        return  view;
    }
    private void calculateValues() {
        String valueByteStr = edtValueByte.getText().toString().trim();

        if (!valueByteStr.isEmpty()) {
            double valueByte = Double.parseDouble(valueByteStr);

            double bit = valueByte * 8;
            double kilobyte = valueByte / 1024;
            double megabyte = kilobyte / 1024;
            double gigabyte = megabyte / 1024;
            double terabyte = gigabyte / 1024;
            double petabyte = terabyte / 1024;

            txtBit.setText(String.format("%.2f", bit));
            txtByte.setText(String.format("%.2f", valueByte));
            txtKilobyte.setText(String.format("%.2f", kilobyte));
            txtMegabyte.setText(String.format("%.2f", megabyte));
            txtGigabyte.setText(String.format("%.2f", gigabyte));
            txtTerabyte.setText(String.format("%.2f", terabyte));
            txtPetabyte.setText(String.format("%.2f", petabyte));
        }
    }
    private void resetValues() {
        edtValueByte.setText("");
        txtBit.setText("");
        txtByte.setText("");
        txtKilobyte.setText("");
        txtMegabyte.setText("");
        txtGigabyte.setText("");
        txtTerabyte.setText("");
        txtPetabyte.setText("");
        Toast.makeText(getContext(), "Value is 0", Toast.LENGTH_SHORT).show();
    }
    private void keybord(View v) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
}