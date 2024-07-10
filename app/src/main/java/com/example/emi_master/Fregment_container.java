package com.example.emi_master;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.emi_master.Fragment.AdvanceEmiFragment;
import com.example.emi_master.Fragment.Advance_sip_Fragment;
import com.example.emi_master.Fragment.Age_calculate_Fragment;
import com.example.emi_master.Fragment.Bmi_calculator_Fragment;
import com.example.emi_master.Fragment.Break_even_pointFragment;
import com.example.emi_master.Fragment.Cash_counter_Fragment;
import com.example.emi_master.Fragment.Compare_loan_Fragment;
import com.example.emi_master.Fragment.Compound_interest_Fragment;
import com.example.emi_master.Fragment.Currency_converter_Fragment;
import com.example.emi_master.Fragment.Delivery_Fragment;
import com.example.emi_master.Fragment.Digital_storage_Fragment;
import com.example.emi_master.Fragment.Discount_calculate_Fragment;
import com.example.emi_master.Fragment.EmiFragment;
import com.example.emi_master.Fragment.Equity_saving_calculate_Fragment;
import com.example.emi_master.Fragment.Final_inflation_Fragment;
import com.example.emi_master.Fragment.Fix_Deposite_Fragment;
import com.example.emi_master.Fragment.Futures_Fragment;
import com.example.emi_master.Fragment.GSTFragment;
import com.example.emi_master.Fragment.Insurance_Fragment;
import com.example.emi_master.Fragment.Interest_calculateFragment;
import com.example.emi_master.Fragment.Interest_rate_Fragment;
import com.example.emi_master.Fragment.Intraday_Fragment;
import com.example.emi_master.Fragment.Length_Calculate_Fragment;
import com.example.emi_master.Fragment.Loan_Tenure_Fragment;
import com.example.emi_master.Fragment.Loan_amountFragment;
import com.example.emi_master.Fragment.Lumpsum_calculate_Fragment;
import com.example.emi_master.Fragment.Moratorium_calculate_Fragment;
import com.example.emi_master.Fragment.Option_Fragment;

import com.example.emi_master.Fragment.PpfCalculateFragment;
import com.example.emi_master.Fragment.Profit_markup_Fragment;
import com.example.emi_master.Fragment.Recurring_deposite_Fragment;
import com.example.emi_master.Fragment.Roi_calculate_Fragment;
import com.example.emi_master.Fragment.SWPFragment;
import com.example.emi_master.Fragment.Sale_taxFragment;
import com.example.emi_master.Fragment.Simple_interestFragment;
import com.example.emi_master.Fragment.Sip_calculate_Fragment;
import com.example.emi_master.Fragment.TemperatureFragment;
import com.example.emi_master.Fragment.Time_calculate_Fragment;
import com.example.emi_master.Fragment.Total_margin_Fragment;
import com.example.emi_master.Fragment.VATFragment;
import com.example.emi_master.Fragment.Weight_calculator_Fragment;

public class Fregment_container extends AppCompatActivity {
    int checkin = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fregment_container);
        if (getIntent().getExtras() != null) {
            checkin = getIntent().getIntExtra("dailycheck", 1);

            switch (checkin) {
                case 101:


                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, new EmiFragment()).commit();
                    break;
                case 102:


                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, new GSTFragment()).commit();
                    break;
                case 103:


                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, new VATFragment()).commit();
                    break;
                case 104:


                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, new SWPFragment()).commit();
                    break;
              
                case 107:


                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, new Break_even_pointFragment()).commit();
                    break;
                case 108:


                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, new Sale_taxFragment()).commit();
                    break;
                case 109:


                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, new Profit_markup_Fragment()).commit();
                    break;
                case 110:


                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, new Total_margin_Fragment()).commit();
                    break;
                case 111:


                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, new Final_inflation_Fragment()).commit();
                    break;
                case 112:


                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, new Simple_interestFragment()).commit();
                    break;
                case 113:


                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, new Fix_Deposite_Fragment()).commit();
                    break;
                case 114:


                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, new Recurring_deposite_Fragment()).commit();
                    break;
                case 115:


                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, new Compound_interest_Fragment()).commit();
                    break;
                case 116:


                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, new PpfCalculateFragment()).commit();
                    break;
                case 117:


                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, new Roi_calculate_Fragment()).commit();
                    break;
                case 118:


                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, new Advance_sip_Fragment()).commit();
                    break;
                case 119:


                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, new Sip_calculate_Fragment()).commit();
                    break;
                case 120:


                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, new Interest_calculateFragment()).commit();
                    break;
                case 121:


                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, new Lumpsum_calculate_Fragment()).commit();
                    break;
                case 122:


                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, new Equity_saving_calculate_Fragment()).commit();
                    break;
                case 123:


                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, new Intraday_Fragment()).commit();
                    break;
                case 124:


                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, new Delivery_Fragment()).commit();
                    break;
                case 125:


                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, new Futures_Fragment()).commit();
                    break;
                case 126:


                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, new Option_Fragment()).commit();
                    break;
                case 127:


                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, new Compare_loan_Fragment()).commit();
                    break;
                case 128:


                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, new AdvanceEmiFragment()).commit();
                    break;
                case 129:


                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, new Interest_rate_Fragment()).commit();
                    break;
                case 130:


                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, new Loan_amountFragment()).commit();
                    break;
                case 131:


                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, new Loan_Tenure_Fragment()).commit();
                    break;
                case 132:


                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, new Moratorium_calculate_Fragment()).commit();
                    break;
                case 133:


                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, new Cash_counter_Fragment()).commit();
                    break;
                case 134:


                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, new Currency_converter_Fragment()).commit();
                    break;
                    case 135:


                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, new Age_calculate_Fragment()).commit();
                    break;
                    case 136:


                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, new Length_Calculate_Fragment()).commit();
                    break;
                    case 137:


                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, new Insurance_Fragment()).commit();
                    break;
                    case 138:


                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, new Discount_calculate_Fragment()).commit();
                    break;
                    case 139:


                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, new Time_calculate_Fragment()).commit();
                    break;
                    case 140:


                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, new TemperatureFragment()).commit();
                    break;
                    case 141:


                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, new Bmi_calculator_Fragment()).commit();
                    break;
                    case 142:


                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, new Weight_calculator_Fragment()).commit();
                    break;
                    case 143:


                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, new Digital_storage_Fragment()).commit();
                    break;


            }
        }
    }
}