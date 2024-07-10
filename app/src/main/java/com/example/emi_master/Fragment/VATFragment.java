package com.example.emi_master.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emi_master.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import java.util.ArrayList;

public class VATFragment extends Fragment {

    private Spinner vatSpinner;
    CardView resultduration;
    private final ArrayList<String> name = new ArrayList<>();
    private ImageView backVat;
    private TextInputEditText edtVatAmount;
    ImageView back_vat;
    private RadioGroup radioGroupVat;
    private EditText edtOtherVat;
    private TextView vatOriginalCost, vatPriceEditText, vatNetPrice;
    private ExtendedFloatingActionButton btnVatCalculate, btnVatReset;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_v_a_t, container, false);

        // Initialize UI elements
        backVat = view.findViewById(R.id.back_vat);
        vatSpinner = view.findViewById(R.id.mode_vat_spinner);
        edtVatAmount = view.findViewById(R.id.edt_vat_amount);
        radioGroupVat = view.findViewById(R.id.radio_group_gst);
        edtOtherVat = view.findViewById(R.id.gst);
        vatOriginalCost = view.findViewById(R.id.vat_original_cost);
        vatPriceEditText = view.findViewById(R.id.vat_price_edit_text);
        vatNetPrice = view.findViewById(R.id.vat_net_price);
        btnVatCalculate = view.findViewById(R.id.btn_vat_calculate);
        btnVatReset = view.findViewById(R.id.btn_vat_reset);
        resultduration=view.findViewById(R.id.resultduration);
        back_vat=view.findViewById(R.id.back_vat);
        back_vat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().finish();
            }
        });

        // Populate spinner
        name.add("Add VAT");
        name.add("Remove VAT");
        vatSpinner.setAdapter(new ArrayAdapter<>(requireContext(), R.layout.list, R.id.textlist, name));

        // Set click listener for back button
        backVat.setOnClickListener(v -> requireActivity().finish());

        // Set visibility for custom VAT EditText
        edtOtherVat.setVisibility(View.GONE);
        radioGroupVat.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.other) {
                edtOtherVat.setVisibility(View.VISIBLE);
            } else {
                edtOtherVat.setVisibility(View.GONE);
            }
        });
        resultduration.setVisibility(View.INVISIBLE);

        // Set click listeners for buttons
        btnVatCalculate.setOnClickListener(v -> calculateVAT());
        btnVatCalculate.setOnClickListener(v -> keybord(v));
        btnVatReset.setOnClickListener(v -> resetFields());

        return view;
    }

    private void calculateVAT() {
        String amountText = edtVatAmount.getText().toString().trim();
        if (amountText.isEmpty()) {
            edtVatAmount.setError("Enter net price");
            return;
        }


        resultduration.setVisibility(View.VISIBLE);
        double amount = Double.parseDouble(amountText);
        double vatPercentage = getSelectedVATPercentage();
        if (vatPercentage == 0) return;

        String selectedMode = vatSpinner.getSelectedItem().toString();
        double vatAmount;
        double netPrice;

        if (selectedMode.equals("Add VAT")) {
            vatAmount = (amount * vatPercentage) / 100;
            netPrice = amount + vatAmount;
        } else {
            vatAmount = (amount * vatPercentage) / (100 + vatPercentage);
            netPrice = amount - vatAmount;
        }

        vatOriginalCost.setText(String.format("₹%.2f", amount));
        vatPriceEditText.setText(String.format("₹%.2f", vatAmount));
        vatNetPrice.setText(String.format("₹%.2f", netPrice));
    }

    private double getSelectedVATPercentage() {
        int selectedId = radioGroupVat.getCheckedRadioButtonId();
        if (selectedId == -1) {
            return 0;
        }

        if (selectedId == R.id.other) {
            String otherVatText = edtOtherVat.getText().toString().trim();
            if (otherVatText.isEmpty()) {
                edtOtherVat.setError("Enter VAT percentage");
                return 0;
            }
            return Double.parseDouble(otherVatText);
        }

        RadioButton selectedRadioButton = radioGroupVat.findViewById(selectedId);
        String vatText = selectedRadioButton.getText().toString().replace("%", "");
        return Double.parseDouble(vatText);
    }

    private void resetFields() {
        edtVatAmount.setText("");
        radioGroupVat.clearCheck();
        edtOtherVat.setText("");
        vatOriginalCost.setText("");
        vatPriceEditText.setText("");
        vatNetPrice.setText("");
        Toast.makeText(getContext(), "Value is 0", Toast.LENGTH_SHORT).show();
        resultduration.setVisibility(View.INVISIBLE);
    }
    private void keybord(View v) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
}
