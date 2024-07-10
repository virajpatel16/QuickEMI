package com.example.emi_master.Fragment;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emi_master.R;
import com.google.android.material.appbar.MaterialToolbar;

public class Cash_counter_Fragment extends Fragment {
    private EditText[] editTexts;
    private TextView[] textViews;
    private TextView ettotal,etnote;
    ImageView cashback;

    private EditText etTotal;
    private Button copyResultButton,shareresult;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_cash_counter_, container, false);
        etTotal = view.findViewById(R.id.ettotal);
        copyResultButton = view.findViewById(R.id.copyresult);
        shareresult=view.findViewById(R.id.shareresult);
        cashback=view.findViewById(R.id.cashback);

        cashback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().finish();
            }
        });

        shareresult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textToShare = etTotal.getText().toString();
                if (!textToShare.isEmpty()) {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, textToShare);
                    startActivity(Intent.createChooser(shareIntent, "Share via"));
                } else {
                    Toast.makeText(requireContext(), "Nothing to share", Toast.LENGTH_SHORT).show();
                }
            }
        });
        copyResultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textToCopy = etTotal.getText().toString();
                if (!textToCopy.isEmpty()) {
                    ClipboardManager clipboard = (ClipboardManager) requireContext().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("Copied Text", textToCopy);
                    if (clipboard != null) {
                        clipboard.setPrimaryClip(clip);
                        Toast.makeText(requireContext(), "Text copied to clipboard", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(requireContext(), "Nothing to copy", Toast.LENGTH_SHORT).show();
                }
            }
        });



        editTexts = new EditText[]{
                view.findViewById(R.id.et2000),
                view.findViewById(R.id.et500),
                view.findViewById(R.id.et200),
                view.findViewById(R.id.et100),
                view.findViewById(R.id.et50),
                view.findViewById(R.id.et20),
                view.findViewById(R.id.et10),
                view.findViewById(R.id.et5),
                view.findViewById(R.id.et2),
                view.findViewById(R.id.et1)
        };

        textViews = new TextView[]{
                view.findViewById(R.id.tv2000),
                view.findViewById(R.id.tv500),
                view.findViewById(R.id.tv200),
                view.findViewById(R.id.tv100),
                view.findViewById(R.id.tv50),
                view.findViewById(R.id.tv20),
                view.findViewById(R.id.tv10),
                view.findViewById(R.id.tv5),
                view.findViewById(R.id.tv2),
                view.findViewById(R.id.tv1)
        };

        ettotal = view.findViewById(R.id.ettotal);
        etnote = view.findViewById(R.id.etnote);
        view.findViewById(R.id.btn_business_reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear text in all EditText fields
                for (EditText editText : editTexts) {
                    editText.getText().clear();
                }

                // Set text to "0" in all TextView fields
                for (TextView textView : textViews) {
                    textView.setText("0");
                }

                // Clear text in the total TextView field
                ettotal.setText("0");
                etnote.setText("0");
                Toast.makeText(getContext(), "Value is 0", Toast.LENGTH_SHORT).show();
            }
        });
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No action needed before text changed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // No action needed during text changed
            }

            @Override
            public void afterTextChanged(Editable s) {
                // After text changed, calculate the cash count and update relevant TextViews
                calculateCashCount();
            }
        };

        // Add the same TextWatcher instance to all EditText fields
        for (EditText editText : editTexts) {
            editText.addTextChangedListener(textWatcher);
        }
        return view;
    }
    private void calculateCashCount() {
        hideKeyboard();
        int totalCashCount = 0;

        // Iterate through each EditText and calculate the cash count
        for (int i = 0; i < editTexts.length; i++) {
            EditText editText = editTexts[i];
            TextView textView = textViews[i];

            try {
                int denomination = Integer.parseInt(editText.getText().toString());
                int total = denomination * (i == 0 ? 2000 : i == 1 ? 500 : i == 2 ? 200 : i == 3 ? 100 : i == 4 ? 50 : i == 5 ? 20 : i == 6 ? 10 : i == 7 ? 5 : i == 8 ? 2 : 1);
                textView.setText(String.valueOf(total));
                totalCashCount += total;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        // Display the total cash count across all denominations
        ettotal.setText(String.valueOf(totalCashCount));

        // Display the total count of notes in etnote EditText
        int totalSum = 0;

        // Iterate through each EditText and calculate the sum of values
        for (EditText editText : editTexts) {
            String denominationStr = editText.getText().toString().trim();
            if (!denominationStr.isEmpty()) {
                int denomination = Integer.parseInt(denominationStr);
                totalSum += denomination;
            }
        }

        // Display the total sum of values along with the text "Total Notes:" in etnote EditText
        etnote.setText("Total :" + totalSum);
    }
    private void hideKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    private void keybord(View v) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
}