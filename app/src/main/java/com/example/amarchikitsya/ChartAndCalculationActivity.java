
package com.example.amarchikitsya;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.amarchikitsya.databinding.ActivityChartAndCalculationBinding;
import com.example.amarchikitsya.utils.InternetConnection;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChartAndCalculationActivity extends AppCompatActivity {

    ActivityChartAndCalculationBinding binding;
    FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityChartAndCalculationBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        if (!InternetConnection.checkConnection(ChartAndCalculationActivity.this)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ChartAndCalculationActivity.this);
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
                            startActivity(new Intent(ChartAndCalculationActivity.this, ChartAndCalculationActivity.class));
                            finish();
                        }
                    }).create().show();
        }
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        binding.cardBmiCalculator.setOnClickListener(v -> {

            if (firebaseUser == null) {
                Intent intent = new Intent(ChartAndCalculationActivity.this, LoginActivity.class);
                intent.putExtra("drawer", "yes");
                startActivity(intent);
            } else {
                startActivity(new Intent(ChartAndCalculationActivity.this,BmiCalculationActivity.class));
            }


        });
        binding.toolbar.pageTitle.setText("Chart and Calculation");
        binding.toolbar.backBtn.setOnClickListener(v -> {
            super.onBackPressed();
        });
        binding.cardWeightChart.setOnClickListener(v -> {
            startActivity(new Intent(ChartAndCalculationActivity.this,WeightChartActivity.class));
        });


    }
}