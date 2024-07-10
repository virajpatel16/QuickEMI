package com.example.emi_master.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.emi_master.InsuranceAdapter;
import com.example.emi_master.InsuranceItem;
import com.example.emi_master.R;

import java.util.ArrayList;
import java.util.List;

public class Insurance_Fragment extends Fragment {

    private RecyclerView rvInsurancegeneral,rvInsurancelife;

    ImageView backinsurance;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_insurance_, container, false);
        rvInsurancegeneral = view.findViewById(R.id.rv_insurance_general);
        rvInsurancelife = view.findViewById(R.id.rv_insurance_life);
        backinsurance=view.findViewById(R.id.backinsurance);
        rvInsurancegeneral.setLayoutManager(new GridLayoutManager(getContext(),3));
        ArrayList<InsuranceItem> insuranceList = new ArrayList<>();
        insuranceList.add(new InsuranceItem(R.drawable.health, "Health  Insurance",getContext().getString(R.string.health)));
        insuranceList.add(new InsuranceItem(R.drawable.car, "Motor  Insurance ",getContext().getString(R.string.motor)));
        insuranceList.add(new InsuranceItem(R.drawable.home, "Home Insurance",getContext().getString(R.string.home)));
        insuranceList.add(new InsuranceItem(R.drawable.fire, "Fire   Insurance",getContext().getString(R.string.fire)));
        insuranceList.add(new InsuranceItem(R.drawable.travel, "Travel  Insurance",getContext().getString(R.string.travel)));
        insuranceList.add(new InsuranceItem(R.drawable.property, "Property Insurance",getContext().getString(R.string.property)));
        InsuranceAdapter adapter = new InsuranceAdapter(getContext(), insuranceList);
        rvInsurancegeneral.setAdapter(adapter);


        rvInsurancelife.setLayoutManager(new GridLayoutManager(getContext(),3));
        ArrayList<InsuranceItem> insuranceList1 = new ArrayList<>();
        insuranceList1.add(new InsuranceItem(R.drawable.life, "Term Life Insurance",getContext().getString(R.string.Term)));
        insuranceList1.add(new InsuranceItem(R.drawable.whole, "Whole  Insurance",getContext().getString(R.string.whole)));
        insuranceList1.add(new InsuranceItem(R.drawable.end, "Endowment  Insurance",getContext().getString(R.string.end)));
        insuranceList1.add(new InsuranceItem(R.drawable.united, "United-LinkedInsurance",getContext().getString(R.string.united)));
        insuranceList1.add(new InsuranceItem(R.drawable.child, "Child  Insurance",getContext().getString(R.string.child)));
        insuranceList1.add(new InsuranceItem(R.drawable.pension, "Pension  Insurance",getContext().getString(R.string.pension)));
        InsuranceAdapter adapter1 = new InsuranceAdapter(getContext(), insuranceList1);
        rvInsurancelife.setAdapter(adapter1);
        backinsurance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().finish();
            }
        });
    return view;
    }
}