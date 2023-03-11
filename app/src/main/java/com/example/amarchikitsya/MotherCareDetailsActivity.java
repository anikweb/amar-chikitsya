package com.example.amarchikitsya;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.amarchikitsya.databinding.ActivityMotherCareDetailsBinding;
import com.example.amarchikitsya.utils.InternetConnection;

public class MotherCareDetailsActivity extends AppCompatActivity {

    ActivityMotherCareDetailsBinding binding;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityMotherCareDetailsBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        if (!InternetConnection.checkConnection(MotherCareDetailsActivity.this)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MotherCareDetailsActivity.this);
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
                            startActivity(new Intent(MotherCareDetailsActivity.this,MotherCareDetailsActivity.class));
                            finish();
                        }
                    }).create().show();
        }

        intent = getIntent();

        Glide.with(MotherCareDetailsActivity.this).load(intent.getStringExtra("image")).into(binding.motherCareImg);
        binding.title.setText(intent.getStringExtra("title"));
        binding.txt.setText(intent.getStringExtra("txt"));
        binding.toolbar.pageTitle.setText(intent.getStringExtra("title"));

        binding.toolbar.backBtn.setOnClickListener(v -> {
            super.onBackPressed();
        });
    }
}