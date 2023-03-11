package com.example.amarchikitsya;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.amarchikitsya.databinding.ActivityDoctorDetails2Binding;
import com.example.amarchikitsya.utils.InternetConnection;
import com.squareup.picasso.Picasso;

public class DoctorDetails2Activity extends AppCompatActivity {

    ActivityDoctorDetails2Binding binding;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityDoctorDetails2Binding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        if (!InternetConnection.checkConnection(DoctorDetails2Activity.this)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(DoctorDetails2Activity.this);
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
                            startActivity(new Intent(DoctorDetails2Activity.this, DoctorDetails2Activity.class));
                            finish();
                        }
                    }).create().show();
        }

        intent = getIntent();

        binding.doctorName.setText(intent.getStringExtra("name"));
        binding.specialization.setText("( "+intent.getStringExtra("specialized")+" )");
        binding.doctorAddress.setText(intent.getStringExtra("address"));
        binding.qualification.setText(intent.getStringExtra("qualification"));
        binding.doctorMobileNumber.setText(intent.getStringExtra("mobile"));
        binding.chamber.setText(intent.getStringExtra("chamber"));
        Picasso.get().load(intent.getStringExtra("img")).placeholder(R.drawable.loading).into(binding.img);


        binding.call.setOnClickListener(v -> {
            String callNumber = intent.getStringExtra("mobile");
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:"+callNumber));
            startActivity(intent);
        });

        binding.toolbar.backBtn.setOnClickListener(v -> {
            super.onBackPressed();
        });
        binding.toolbar.pageTitle.setText("Doctor Details");

    }
}