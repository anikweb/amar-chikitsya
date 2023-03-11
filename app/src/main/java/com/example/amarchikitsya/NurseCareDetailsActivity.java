package com.example.amarchikitsya;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.amarchikitsya.databinding.ActivityNurseCareDetailsBinding;
import com.example.amarchikitsya.utils.InternetConnection;

public class NurseCareDetailsActivity extends AppCompatActivity {

    ActivityNurseCareDetailsBinding binding;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityNurseCareDetailsBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        if (!InternetConnection.checkConnection(NurseCareDetailsActivity.this)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(NurseCareDetailsActivity.this);
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
                            startActivity(new Intent(NurseCareDetailsActivity.this, NurseCareDetailsActivity.class));
                            finish();
                        }
                    }).create().show();
        }

        intent = getIntent();

        binding.nurseName.setText(intent.getStringExtra("name"));
        binding.nurseAddress.setText(intent.getStringExtra("address"));
        binding.hospitalName.setText(intent.getStringExtra("hospital"));
        binding.nurseMobileNumber.setText(intent.getStringExtra("mobileNumber"));
        binding.nurseExperience.setText(intent.getStringExtra("experience"));
        binding.gender.setText(intent.getStringExtra("gender"));

        binding.call.setOnClickListener(v -> {
            String callNumber = intent.getStringExtra("mobileNumber");
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:"+callNumber));
            startActivity(intent);
        });
        binding.toolbar.pageTitle.setText("Nurse Details");
        binding.toolbar.backBtn.setOnClickListener(v -> {
            super.onBackPressed();
        });
    }
}