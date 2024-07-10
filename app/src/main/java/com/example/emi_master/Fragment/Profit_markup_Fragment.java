package com.example.emi_master.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.emi_master.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class Profit_markup_Fragment extends Fragment {

    private EditText edtCost, edtRevenue;
    private TextView answerMarkup;
    private ExtendedFloatingActionButton btn_markup_calculate, btn_markup_reset;
    private ImageView back_markup;
    LinearLayout markup;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profit_markup_, container, false);

        // Initialize views
        back_markup = view.findViewById(R.id.back_markup);
        edtCost = view.findViewById(R.id.CostmarkupEditText);
        edtRevenue = view.findViewById(R.id.RevenuemarkupEdittext);
        answerMarkup = view.findViewById(R.id.answergrossprice);
        btn_markup_calculate = view.findViewById(R.id.btn_markup_calculate);
        btn_markup_reset = view.findViewById(R.id.btn_markup_reset);
        markup = view.findViewById(R.id.markup);

        // Set OnClickListener for back button
        back_markup.setOnClickListener(v -> requireActivity().onBackPressed());

        markup.setVisibility(View.INVISIBLE);
        // Set OnClickListener for Calculate button
        btn_markup_calculate.setOnClickListener(v -> calculateMarkup());
        btn_markup_calculate.setOnClickListener(v -> keybord(v));

        // Set OnClickListener for Reset button
        btn_markup_reset.setOnClickListener(v -> resetMarkup());

        return view;
    }

    private void calculateMarkup() {
        // Get the values from EditTexts
        String costStr = edtCost.getText().toString().trim();
        String revenueStr = edtRevenue.getText().toString().trim();
        markup.setVisibility(View.VISIBLE);
        if (costStr.isEmpty() || revenueStr.isEmpty()) {
            answerMarkup.setText("Please enter both Cost and Revenue");
            return;
        }

        try {
            double cost = Double.parseDouble(costStr);
            double revenue = Double.parseDouble(revenueStr);

            // Calculate markup percentage
            double markup = ((revenue - cost) / cost) * 100;

            // Display the markup percentage
            answerMarkup.setText(String.format("%.2f%%", markup));

        } catch (NumberFormatException e) {
            answerMarkup.setText("Invalid input. Please enter valid numbers.");
        }
    }

    private void resetMarkup() {
        // Clear EditTexts and TextView
        edtCost.setText("");
        edtRevenue.setText("");
        markup.setVisibility(View.INVISIBLE);
        answerMarkup.setText("");
        Toast.makeText(getContext(), "Value is 0", Toast.LENGTH_SHORT).show();
    }
    private void keybord(View v) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
}
