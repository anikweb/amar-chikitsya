package com.example.amarchikitsya;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.amarchikitsya.databinding.ActivityWeightChartBinding;
import com.example.amarchikitsya.utils.InternetConnection;

public class WeightChartActivity extends AppCompatActivity {

    ActivityWeightChartBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityWeightChartBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        if (!InternetConnection.checkConnection(WeightChartActivity.this)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(WeightChartActivity.this);
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
                            startActivity(new Intent(WeightChartActivity.this, WeightChartActivity.class));
                            finish();
                        }
                    }).create().show();
        }
        binding.toolbar.pageTitle.setText("Weight Chart");
        binding.toolbar.backBtn.setOnClickListener(v -> {
            super.onBackPressed();
        });
    }
}