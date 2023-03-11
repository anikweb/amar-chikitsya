package com.example.amarchikitsya;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.amarchikitsya.Adapter.PharmacyAdapter;
import com.example.amarchikitsya.databinding.ActivityPharmacyBinding;
import com.example.amarchikitsya.model.Pharmacy;
import com.example.amarchikitsya.utils.InternetConnection;

import java.util.ArrayList;
import java.util.List;

public class PharmacyActivity extends AppCompatActivity {

    ActivityPharmacyBinding binding;
    List<Pharmacy> pharmacyList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding  = ActivityPharmacyBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        if (!InternetConnection.checkConnection(PharmacyActivity.this)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(PharmacyActivity.this);
            builder.setTitle("Network Error!")
                    .setMessage("No Internet Connection, Please Check your Network Connection")
                    .setIcon(R.drawable.ic_baseline_info_24)
                    .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finishAffinity();
                            System.exit(0);

                        }
                    }).setNegativeButton("Retry", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(PharmacyActivity.this, PharmacyActivity.class));
                            finish();
                        }
                    }).create().show();
        }

        pharmacyList  = new ArrayList<>();

        pharmacyList.add(new Pharmacy(1, "24 Hours Pharmacy","House 14E, Road 6, Dhanmondi, Dhaka","Dhaka","01783674575"));

        PharmacyAdapter pharmacyAdapter = new PharmacyAdapter(PharmacyActivity.this,pharmacyList);
        binding.pharmacyRecycler.setAdapter(pharmacyAdapter);



        binding.toolbar.pageTitle.setText("Pharmaies");
        binding.toolbar.backBtn.setOnClickListener(v -> {
            super.onBackPressed();
        });





    }
}