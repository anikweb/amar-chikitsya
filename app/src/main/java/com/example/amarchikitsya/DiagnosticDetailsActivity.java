package com.example.amarchikitsya;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.amarchikitsya.databinding.ActivityDiagnosticDetailsBinding;
import com.example.amarchikitsya.utils.InternetConnection;

public class DiagnosticDetailsActivity extends AppCompatActivity {

    ActivityDiagnosticDetailsBinding binding;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityDiagnosticDetailsBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        intent = getIntent();

        if (!InternetConnection.checkConnection(DiagnosticDetailsActivity.this)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(DiagnosticDetailsActivity.this);
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
                            startActivity(new Intent(DiagnosticDetailsActivity.this, DiagnosticDetailsActivity.class));
                            finish();
                        }
                    }).create().show();
        }

        binding.diagnosticName.setText(intent.getStringExtra("name"));
        binding.diagnosticAddress.setText(intent.getStringExtra("address"));
        binding.mobileNumber.setText(intent.getStringExtra("mobile_number"));

        if (intent.getStringExtra("opening_hour").equals("24 Hour")) {
            binding.openingHour.setText("24 Hour Service");
            binding.closingHour.setVisibility(View.GONE);
        } else {
            binding.openingHour.setText(intent.getStringExtra("opening_hour"));
            binding.closingHour.setText(intent.getStringExtra("closing_hour"));
        }
        if (intent.getStringExtra("day_off") == null) {
            binding.dayOff.setVisibility(View.GONE);
        }
        {
            binding.dayOff.setText(intent.getStringExtra("day_off"));
        }
        binding.call.setOnClickListener(v -> {
            String callNumber = intent.getStringExtra("mobile_number");
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + callNumber));
            startActivity(intent);
        });

        binding.toolbar.backBtn.setOnClickListener(v -> {
            super.onBackPressed();
        });

    }
}