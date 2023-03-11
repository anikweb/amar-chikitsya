package com.example.amarchikitsya;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.amarchikitsya.Adapter.NurseCareAdapter;
import com.example.amarchikitsya.databinding.ActivityNurseCareBinding;
import com.example.amarchikitsya.model.NurseCare;
import com.example.amarchikitsya.utils.InternetConnection;

import java.util.ArrayList;
import java.util.List;

public class NurseCareActivity extends AppCompatActivity {

    ActivityNurseCareBinding binding;
    List<NurseCare>  nurseCareList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityNurseCareBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        if (!InternetConnection.checkConnection(NurseCareActivity.this)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(NurseCareActivity.this);
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
                            startActivity(new Intent(NurseCareActivity.this, NurseCareActivity.class));
                            finish();
                        }
                    }).create().show();
        }

        nurseCareList = new ArrayList<>();
        nurseCareList.add(new NurseCare(1,"Anik Kumar Nandi","Medilab Nursing Home","Dhanmondi, Dhaka","01783674545","2 Years","Male"));
        nurseCareList.add(new NurseCare(2,"Angelina Jolie","Central Nursing Home","Kolabagan, Dhaka","0154511458744","5 Years","Female"));

        NurseCareAdapter nurseCareAdapter = new NurseCareAdapter(NurseCareActivity.this,nurseCareList);
        binding.nurseRecycler.setAdapter(nurseCareAdapter);


        binding.toolbar.pageTitle.setText("Nurse Cares");
        binding.toolbar.backBtn.setOnClickListener(v -> {
            super.onBackPressed();
        });

    }
}