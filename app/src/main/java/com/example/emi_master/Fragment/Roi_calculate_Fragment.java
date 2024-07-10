package com.example.emi_master.Fragment;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.emi_master.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DecimalFormat;


public class Roi_calculate_Fragment extends Fragment {
    ExtendedFloatingActionButton calculateButton, btn_reset;

    TextInputEditText FromdateEdittext, todateEdittext;
    private TextInputEditText startingInvestmentEditText;
    private TextInputEditText lastInvestmentEditText;

    ImageView backroi;
    private TextInputEditText periodYearEditText;
    private TextInputEditText periodMonthEditText;

    private TextView answerInvestmentPeriodTextView;
    private TextView answerGainOrLossTextView;
    private TextView answerROITextView;
    private TextView answerSimpleAnnualROITextView;
    private TextView answerCompoundAnnualROITextView;
    CardView resultinvestmentperiod;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_roi_calculate_, container, false);

        startingInvestmentEditText = view.findViewById(R.id.startinginvestmentedittext);
        backroi=view.findViewById(R.id.backroi);
        lastInvestmentEditText = view.findViewById(R.id.lastinvetsmentEditText);
        periodYearEditText = view.findViewById(R.id.periodyearEdittext);
        periodMonthEditText = view.findViewById(R.id.periodmonthEditext);
        calculateButton = view.findViewById(R.id.calculatebuttonreturnoninvestment);
        answerInvestmentPeriodTextView = view.findViewById(R.id.answerinvestmentperiod);
        answerGainOrLossTextView = view.findViewById(R.id.answergainorloass);
        answerROITextView = view.findViewById(R.id.answerreturnoninvestment);
        answerSimpleAnnualROITextView = view.findViewById(R.id.answersimpleannualroi);
        answerCompoundAnnualROITextView = view.findViewById(R.id.answercompoundannualroi);
        resultinvestmentperiod = view.findViewById(R.id.resultinvestmentperiod);
        btn_reset=view.findViewById(R.id.btn_reset);
        resultinvestmentperiod.setVisibility(View.INVISIBLE);

        backroi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().finish();
            }
        });
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear EditText fields
                startingInvestmentEditText.setText("");
                lastInvestmentEditText.setText("");
                periodYearEditText.setText("");
                periodMonthEditText.setText("");

                // Clear TextViews
                resultinvestmentperiod.setVisibility(View.INVISIBLE);
                answerInvestmentPeriodTextView.setText("");
                answerGainOrLossTextView.setText("");
                answerROITextView.setText("");
                answerSimpleAnnualROITextView.setText("");
                answerCompoundAnnualROITextView.setText("");
                Toast.makeText(getContext(), "Value is 0", Toast.LENGTH_SHORT).show();
            }
        });

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
keybord(v);
                resultinvestmentperiod.setVisibility(View.VISIBLE);
                calculateROI();


            }
        });
        return view;

    }

    private void calculateROI() {
        String startin = startingInvestmentEditText.getText().toString().trim();
        String last = lastInvestmentEditText.getText().toString().trim();
        String period = periodYearEditText.getText().toString().trim();
        String month = periodMonthEditText.getText().toString().trim();


        if (startin.isEmpty() || last.isEmpty() || period.isEmpty() || month.isEmpty()) {
            Toast.makeText(requireActivity(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            resultinvestmentperiod.setVisibility(View.INVISIBLE);
            return;
        }
        // Get input values
        double startingInvestment = parseDouble(startingInvestmentEditText.getText().toString());
        double lastInvestment = parseDouble(lastInvestmentEditText.getText().toString());
        int periodYear = parseInt(periodYearEditText.getText().toString());
        int periodMonth = parseInt(periodMonthEditText.getText().toString());

        // Calculate ROI and other values
        double gainOrLoss = lastInvestment - startingInvestment;
        double ROI = (gainOrLoss / startingInvestment) * 100;
        double totalMonths = periodYear * 12 + periodMonth;
        double simpleAnnualROI = (ROI / totalMonths) * 12;
        double compoundAnnualROI = Math.pow((lastInvestment / startingInvestment), (1.0 / totalMonths)) - 1;

        // Display results
        answerInvestmentPeriodTextView.setText(periodYear + " years " + periodMonth + " months");
        answerGainOrLossTextView.setText(String.format("%.2f", gainOrLoss));
        answerROITextView.setText(String.format("%.2f", ROI) + "%");
        answerSimpleAnnualROITextView.setText(String.format("%.2f", simpleAnnualROI) + "%");
        answerCompoundAnnualROITextView.setText(String.format("%.2f", compoundAnnualROI * 100) + "%");
    }
    private void keybord(View v) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

}