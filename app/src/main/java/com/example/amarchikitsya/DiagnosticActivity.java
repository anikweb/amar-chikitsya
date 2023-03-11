package com.example.amarchikitsya;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.amarchikitsya.Adapter.DiagnosticAdapter;
import com.example.amarchikitsya.Adapter.DoctorAdapter;
import com.example.amarchikitsya.databinding.ActivityDiagnosticBinding;
import com.example.amarchikitsya.model.Diagnostics;
import com.example.amarchikitsya.model.Doctors;
import com.example.amarchikitsya.utils.InternetConnection;

import java.util.ArrayList;
import java.util.List;

public class DiagnosticActivity extends AppCompatActivity {

    ActivityDiagnosticBinding binding;
    List<Diagnostics> diagnosticList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityDiagnosticBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        if (!InternetConnection.checkConnection(DiagnosticActivity.this)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(DiagnosticActivity.this);
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
                            startActivity(new Intent(DiagnosticActivity.this, DiagnosticActivity.class));
                            finish();
                        }
                    }).create().show();
        }
        diagnosticList = new ArrayList<>();

        diagnosticList.add(new Diagnostics(1, "Popular Diagnostic Centre Ltd.","House # 16 Road No. 2, Dhaka 1205","9666787801","7:00 AM","11:00 PM",null));
        diagnosticList.add(new Diagnostics(2, "Dhanmondi Diagnostic & Consultation Center.","1205 79 Satmasjid Road, Dhaka 1205","01774080049","24 Hour","24 Hour",null));
        diagnosticList.add(new Diagnostics(3, "Modern Diagnostic Center.","House # 17 Rd No. 8, Dhaka 1205","29663840","24 Hour","24 Hour",null));
        diagnosticList.add(new Diagnostics(4, "Ibn Sina Diagnostic and Imaging Center","House 48 Rd No 9A, Dhaka 1209","9610010615","7:00 AM","10:00 PM",null));
        diagnosticList.add(new Diagnostics(5, "City General Hospital And Diagnostic Center","Rd No 19, Dhaka 1205","01781278379","8:00 AM","11:00 PM","Friday"));
        diagnosticList.add(new Diagnostics(6, "icddr,b diagnostic laboratory Dhanmondi"," Rd 12/A, Dhaka 1205","01750557722","24 Hour","24 Hour",null));
        diagnosticList.add(new Diagnostics(7, "Medinova Medical Services Ltd.","Road 5/A, Dhanmondi R/A, Dhaka 1209","01750557722","24 Hour","24 Hour",null));
        DiagnosticAdapter diagnosticAdapter = new DiagnosticAdapter(DiagnosticActivity.this, diagnosticList);
        binding.diagnosticRecycler.setAdapter(diagnosticAdapter);

        binding.toolbar.pageTitle.setText("Diagnostic Centre");
        binding.toolbar.backBtn.setOnClickListener(v -> {
            super.onBackPressed();
        });
    }
}