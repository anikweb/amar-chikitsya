package com.example.amarchikitsya;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.amarchikitsya.databinding.ActivityPharmacyDetailsBinding;
import com.example.amarchikitsya.utils.InternetConnection;

public class PharmacyDetailsActivity extends AppCompatActivity {

    ActivityPharmacyDetailsBinding binding;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityPharmacyDetailsBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        if (!InternetConnection.checkConnection(PharmacyDetailsActivity.this)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(PharmacyDetailsActivity.this);
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
                            startActivity(new Intent(PharmacyDetailsActivity.this,PharmacyDetailsActivity.class));
                            finish();
                        }
                    }).create().show();
        }

        intent = getIntent();
        binding.pharmacyName.setText(intent.getStringExtra("name"));
        binding.pharmacyAddress.setText(intent.getStringExtra("address"));
        binding.pharmacyCity.setText(intent.getStringExtra("city"));
        binding.pharmacyMobileNumber.setText(intent.getStringExtra("mobileNumber"));

        binding.call.setOnClickListener(v -> {
            String callNumber = intent.getStringExtra("mobileNumber");
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:"+callNumber));
            startActivity(intent);
        });
        binding.toolbar.pageTitle.setText("Pharmacy Details");
        binding.toolbar.backBtn.setOnClickListener(v -> {
            super.onBackPressed();
        });
    }
}