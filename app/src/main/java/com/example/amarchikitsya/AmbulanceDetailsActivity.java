package com.example.amarchikitsya;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.amarchikitsya.databinding.ActivityAmbulanceDetailsBinding;
import com.example.amarchikitsya.utils.InternetConnection;

public class AmbulanceDetailsActivity extends AppCompatActivity {

    ActivityAmbulanceDetailsBinding binding;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityAmbulanceDetailsBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        if (!InternetConnection.checkConnection(AmbulanceDetailsActivity.this)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(AmbulanceDetailsActivity.this);
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
                            startActivity(new Intent(AmbulanceDetailsActivity.this, AmbulanceDetailsActivity.class));
                            finish();
                        }
                    }).create().show();
        }


        intent = getIntent();

        binding.ambulanceName.setText( intent.getStringExtra("name"));
        binding.ambulanceAddress.setText(intent.getStringExtra("address"));
        binding.ambulanceMobileNumber.setText(intent.getStringExtra("mobile_number"));

        binding.call.setOnClickListener(v -> {
            String callNumber = intent.getStringExtra("mobile_number");
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:"+callNumber));
            startActivity(intent);
        });
        binding.toolbar.pageTitle.setText("Ambulance Details");
        binding.toolbar.backBtn.setOnClickListener(v -> {
            super.onBackPressed();
        });
    }
}