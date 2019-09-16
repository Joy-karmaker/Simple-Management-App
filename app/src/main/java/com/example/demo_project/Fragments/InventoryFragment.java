package com.example.demo_project.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.demo_project.InvoiceActivity;
import com.example.demo_project.LoginActivity;
import com.example.demo_project.MainActivity;
import com.example.demo_project.R;
import com.example.demo_project.StockgroupActivity;
import com.example.demo_project.StockitemActivity;
import com.example.demo_project.Tokens;

import java.util.ArrayList;
import java.util.List;

public class InventoryFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    Button master, stock_group, stock_item;
    String Blood_group[] = {"Select","Master", "Blaster"};
    String bloodgroup, username;

    ArrayAdapter<CharSequence> adapterBG;

    List<String> listsource = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inventory, container, false);

        //master =(Button)view.findViewById(R.id.master);
        stock_group =(Button)view.findViewById(R.id.stock_group);
        stock_item =(Button)view.findViewById(R.id.stock_item);

        /*Bundle bundle = this.getArguments();
        if (bundle != null) {
            username = bundle.getString("username");
        }*/
        username = LoginActivity.token;

        Toast.makeText(getActivity(),username, Toast.LENGTH_LONG).show();

        stock_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), StockgroupActivity.class);
                startActivity(intent);
            }
        });

        stock_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), StockitemActivity.class);
                startActivity(intent);
            }
        });

        generateData();

        Spinner spin = (Spinner) view.findViewById(R.id.spinner);
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, Blood_group);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);
        spin.setOnItemSelectedListener(this);

        return view;
    }

    private void generateData() {
        for (int i = 0; i < 3; i++) {
            listsource.add(Blood_group[i]);
        }
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int position, long Id) {


        Spinner spinner = (Spinner) parent;

        if (spinner.getId() == R.id.spinner) {
            bloodgroup = parent.getItemAtPosition(position).toString();
            if(bloodgroup == "Master") {
                stock_group.setVisibility(View.VISIBLE);
                stock_item.setVisibility(View.VISIBLE);
            }
            else {
                stock_group.setVisibility(View.GONE);
                stock_item.setVisibility(View.GONE);
            }
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {

    }

}
