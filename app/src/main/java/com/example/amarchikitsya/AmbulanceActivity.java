package com.example.amarchikitsya;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.amarchikitsya.Adapter.AmbulanceAdapter;
import com.example.amarchikitsya.Adapter.DiagnosticAdapter;
import com.example.amarchikitsya.databinding.ActivityAmbulanceBinding;
import com.example.amarchikitsya.model.Ambulance;
import com.example.amarchikitsya.model.Diagnostics;
import com.example.amarchikitsya.utils.InternetConnection;

import java.util.ArrayList;
import java.util.List;

public class AmbulanceActivity extends AppCompatActivity {

    ActivityAmbulanceBinding binding;
    List<Ambulance> ambulanceList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityAmbulanceBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        if (!InternetConnection.checkConnection(AmbulanceActivity.this)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(AmbulanceActivity.this);
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
                            startActivity(new Intent(AmbulanceActivity.this, AmbulanceActivity.class));
                            finish();
                        }
                    }).create().show();
        }

        ambulanceList = new ArrayList<>();

        ambulanceList.add(new Ambulance(1, "Anik Kumar Nandi","Dhaka","01783674575"));
        ambulanceList.add(new Ambulance(2, "Rahul Driver","Dhaka","015454"));
        ambulanceList.add(new Ambulance(3, "Niloy Driver","Dhaka","015742416542"));

        AmbulanceAdapter ambulanceAdapter = new AmbulanceAdapter(AmbulanceActivity.this, ambulanceList);
        binding.ambulanceRecycler.setAdapter(ambulanceAdapter);

        binding.toolbar.pageTitle.setText("Ambulances");
        binding.toolbar.backBtn.setOnClickListener(v -> {
            super.onBackPressed();
        });

    }
}