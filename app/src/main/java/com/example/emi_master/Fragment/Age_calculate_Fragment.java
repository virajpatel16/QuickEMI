package com.example.emi_master.Fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.DecimalFormat;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emi_master.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;


public class Age_calculate_Fragment extends Fragment {

    TextView txt_years, txt_months, txt_days, txt_months_second, txt_days_second, txt_years_third, txt_months_third, edt_today_date,txt_days_third, txt_weeks_third, txt_hours_third, txt_minutes_third, txt_seconds_third;
    EditText edt_date_of_birth;
    ExtendedFloatingActionButton btn_business_reset, btn_business_calculate;
    TableLayout tableLayout;
    ImageView brack;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_age_calculate_, container, false);
        edt_today_date = view.findViewById(R.id.edt_today_date);
        edt_date_of_birth = view.findViewById(R.id.edt_date_of_birth);
        btn_business_reset = view.findViewById(R.id.btn_business_reset);
        btn_business_calculate = view.findViewById(R.id.btn_business_calculate);
        txt_years = view.findViewById(R.id.txt_Years);
        txt_months = view.findViewById(R.id.txt_Months);
        txt_days = view.findViewById(R.id.txt_Days);
        txt_months_second = view.findViewById(R.id.txt_Months_second);
        txt_days_second = view.findViewById(R.id.txt_Days_second);
        txt_years_third = view.findViewById(R.id.txt_Years_third);
        txt_months_third = view.findViewById(R.id.txt_Months_third);
        txt_days_third = view.findViewById(R.id.txt_Days_third);
        txt_weeks_third = view.findViewById(R.id.txt_Weeks_third);
        txt_hours_third = view.findViewById(R.id.txt_hours_third);
        txt_minutes_third = view.findViewById(R.id.txt_Minutes_third);
        txt_seconds_third = view.findViewById(R.id.txt_Seconds_third);
        tableLayout = view.findViewById(R.id.tableLayout);
        brack=view.findViewById(R.id.back_age);
        setCurrentDateOnEditText();



        brack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        edt_date_of_birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDatePicker(edt_date_of_birth);
            }
        });
        // Set OnClickListener to calculate age
        btn_business_calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call method to calculate age
                calculateAge();
                keybord(v);
            }
        });

        // Set OnClickListener to reset
        btn_business_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear birthdate EditText and set today's date to today's date EditText
                edt_date_of_birth.setText("dd/mm/yyyy");
                setCurrentDateOnEditText();
                txt_years.setText("0");
                txt_months.setText("0");
                txt_days.setText("0");
                txt_months_second.setText("0");
                txt_days_second.setText("0");
                txt_years_third.setText("0");
                txt_months_third.setText("0");
                txt_days_third.setText("0");
                txt_weeks_third.setText("0");
                txt_hours_third.setText("0");
                txt_minutes_third.setText("0");
                txt_seconds_third.setText("0");

                tableLayout.removeAllViews();
                Toast.makeText(getContext(), "Value is 0", Toast.LENGTH_SHORT).show();

            }
        });

        return view;
    }
    @SuppressLint("NewApi")
    private void calculateAge() {
        hideKeyboard();
        String strTodayDate = edt_today_date.getText().toString();
        String strBirthDate = edt_date_of_birth.getText().toString();

        if (strBirthDate.isEmpty() || strBirthDate.equals("dd/mm/yyyy")) {
            // EditText is empty, show toast
            Toast.makeText(getContext(), "Date of birth is empty", Toast.LENGTH_SHORT).show();
        }

        // Parse the input dates
        LocalDate todayDate = parseDate(strTodayDate);
        LocalDate birthDate = parseDate(strBirthDate);

        if (todayDate != null && birthDate != null) {
            // Calculate age
            Period age = Period.between(birthDate, todayDate);
            int years = age.getYears();
            int months = age.getMonths();
            int days = age.getDays();

            // Set age in TextViews
            txt_years.setText(String.valueOf(years));
            txt_months.setText(String.valueOf(months));
            txt_days.setText(String.valueOf(days));

            // Calculate next birthday
            LocalDate nextBirthday = birthDate.plusYears(years);
            if (nextBirthday.isBefore(todayDate) || nextBirthday.isEqual(todayDate)) {
                nextBirthday = nextBirthday.plusYears(1);
            }
            Period timeUntilNextBirthday = Period.between(todayDate, nextBirthday);
            int monthsUntilNextBirthday = timeUntilNextBirthday.getMonths();
            int daysUntilNextBirthday = timeUntilNextBirthday.getDays();

            // Set next birthday in TextViews
            txt_months_second.setText(String.valueOf(monthsUntilNextBirthday));
            txt_days_second.setText(String.valueOf(daysUntilNextBirthday));

            // Additional information (Using ChronoUnit for compatibility)
            long yearsDiff = ChronoUnit.YEARS.between(birthDate, todayDate);
            long monthsDiff = ChronoUnit.MONTHS.between(birthDate, todayDate);
            long daysDiff = ChronoUnit.DAYS.between(birthDate, todayDate);
            long weeksDiff = daysDiff / 7; // Weeks are not directly supported by Period
            long hoursDiff = ChronoUnit.HOURS.between(birthDate.atStartOfDay(), todayDate.atStartOfDay());
            long minutesDiff = ChronoUnit.MINUTES.between(birthDate.atStartOfDay(), todayDate.atStartOfDay());
            long secondsDiff = ChronoUnit.SECONDS.between(birthDate.atStartOfDay(), todayDate.atStartOfDay());

            // Format the differences using a DecimalFormat
            DecimalFormat formatter = new DecimalFormat("#,##,###");
            String formattedYearsDiff = formatter.format(yearsDiff);
            String formattedMonthsDiff = formatter.format(monthsDiff);
            String formattedDaysDiff = formatter.format(daysDiff);
            String formattedWeeksDiff = formatter.format(weeksDiff);
            String formattedHoursDiff = formatter.format(hoursDiff);
            String formattedMinutesDiff = formatter.format(minutesDiff);
            String formattedSecondsDiff = formatter.format(secondsDiff);

            // Set additional information in TextViews
            txt_years_third.setText(formattedYearsDiff);
            txt_months_third.setText(formattedMonthsDiff);
            txt_days_third.setText(formattedDaysDiff);
            txt_weeks_third.setText(formattedWeeksDiff);
            txt_hours_third.setText(formattedHoursDiff);
            txt_minutes_third.setText(formattedMinutesDiff);
            txt_seconds_third.setText(formattedSecondsDiff);

            printNextBirthdays(strBirthDate);

        }
    }
    private void hideKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    @SuppressLint("NewApi")
    private void printNextBirthdays(String strBirthDate) {
        tableLayout.removeAllViews();

        printTableHeader();
        LocalDate birthdate_main = parseDate(strBirthDate);
        int birthDate = birthdate_main.getDayOfMonth();
        int birthMonth = birthdate_main.getMonthValue();
        LocalDate currentDate = LocalDate.now();

        for (int i = 1; i <= 10; i++) {
            LocalDate nextBirthdate = LocalDate.of(currentDate.getYear() + i, birthMonth, birthDate);
            String formattedDate = String.format("%02d/%02d/%d", birthDate,birthMonth, nextBirthdate.getYear());
            String dayofweeks = nextBirthdate.getDayOfWeek().toString();
            addTableRow(formattedDate, dayofweeks);
        }

    }

    private void addTableRow(String date, String dayOfWeek) {
        TableRow tableRow = new TableRow(requireContext());
        tableRow.setPadding(3, 3, 3, 3);
        tableRow.setBackgroundColor(requireContext().getColor(R.color.black)); // Set background color

        TextView txtDate = createTextView(date, Gravity.CENTER);
        txtDate.setTextColor(requireContext().getColor(R.color.white));
        txtDate.setBackgroundColor(requireContext().getColor(R.color.app_color)); // Set grid lines color
        tableRow.addView(txtDate);

        TextView txtDay = createTextView(dayOfWeek, Gravity.CENTER);
        txtDay.setTextColor(requireContext().getColor(R.color.white));
        txtDay.setBackgroundColor(requireContext().getColor(R.color.app_color)); // Set grid lines color
        tableRow.addView(txtDay);

        tableLayout.addView(tableRow);
    }

    private void printTableHeader() {
        TableRow tableRow = new TableRow(requireContext());
        tableRow.setPadding(3, 3, 3, 3);
        tableRow.setBackgroundColor(requireContext().getColor(R.color.black)); // Set background color

        TextView txtNo = createTextView("Date", Gravity.CENTER);
        txtNo.setTextColor(requireContext().getColor(R.color.white));
        txtNo.setBackgroundColor(requireContext().getColor(R.color.app_color)); // Set grid lines color
        tableRow.addView(txtNo);

        TextView txtPrincipal = createTextView("Day", Gravity.CENTER);
        txtPrincipal.setTextColor(requireContext().getColor(R.color.white));
        txtPrincipal.setBackgroundColor(requireContext().getColor(R.color.app_color)); // Set grid lines color
        tableRow.addView(txtPrincipal);

        tableLayout.addView(tableRow);
    }

    private TextView createTextView(String text, int gravity) {
        TextView textView = new TextView(requireContext());
        textView.setPadding(2, 15, 2, 15);
        textView.setTextColor(requireContext().getColor(R.color.black)); // Set text color
        textView.setGravity(gravity); // Set text alignment
        textView.setText(text);
        textView.setBackgroundColor(Color.BLACK); // Set grid lines color
        return textView;
    }


    @SuppressLint("NewApi")
    private LocalDate parseDate(String dateString) {
        try {
            String[] parts = dateString.split("/");
            int day = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int year = Integer.parseInt(parts[2]);
            return LocalDate.of(year, month, day);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private void setCurrentDateOnEditText() {
        // Get current date
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        int dayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        // Set current date to EditText
        String currentDate = String.format("%02d/%02d/%d", dayOfMonth, month + 1, year);
        edt_today_date.setText(currentDate);
    }
    @SuppressLint("NewApi")
    private void showCustomTodayDatePicker(TextView textView) {
        // Parse the existing birthdate
        String existingBirthDate = textView.getText().toString();
        LocalDate birthDate = parseDate(existingBirthDate);
        int year, month, dayOfMonth;

        // If birthdate exists, set the initial date to the birthdate
        if (birthDate != null) {
            year = birthDate.getYear();
            month = birthDate.getMonthValue() - 1; // Month is zero-based in DatePickerDialog
            dayOfMonth = birthDate.getDayOfMonth();
        } else {
            // If birthdate doesn't exist or is invalid, set the initial date to today's date
            year = Calendar.getInstance().get(Calendar.YEAR);
            month = Calendar.getInstance().get(Calendar.MONTH);
            dayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        }
        // Create a DatePickerDialog with the initial date set
        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                (view, selectedYear, selectedMonth, selectedDayOfMonth) -> {
                    // Set selected date to the EditText
                    String selectedDate = String.format("%02d/%02d/%d", selectedDayOfMonth, selectedMonth + 1, selectedYear);
                    textView.setText(selectedDate);
                },
                year, month, dayOfMonth);

        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // Set date picker dialog color
        datePickerDialog.show();
        // Show the dialog
    }
    @SuppressLint("NewApi")
    private void showCustomDatePicker(EditText editText) {
        // Parse the existing birthdate
        String existingBirthDate = editText.getText().toString();
        LocalDate birthDate = parseDate(existingBirthDate);
        int year, month, dayOfMonth;

        // If birthdate exists, set the initial date to the birthdate
        if (birthDate != null) {
            year = birthDate.getYear();
            month = birthDate.getMonthValue() - 1; // Month is zero-based in DatePickerDialog
            dayOfMonth = birthDate.getDayOfMonth();
        } else {
            // If birthdate doesn't exist or is invalid, set the initial date to today's date
            year = Calendar.getInstance().get(Calendar.YEAR);
            month = Calendar.getInstance().get(Calendar.MONTH);
            dayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        }
        // Create a DatePickerDialog with the initial date set
        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                (view, selectedYear, selectedMonth, selectedDayOfMonth) -> {
                    // Set selected date to the EditText
                    String selectedDate = String.format("%02d/%02d/%d", selectedDayOfMonth, selectedMonth + 1, selectedYear);
                    editText.setText(selectedDate);
                },
                year, month, dayOfMonth);

        // Set date picker dialog color
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // Show the dialog
        datePickerDialog.show();
    }
    private  void keybord(View v){
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
}