package com.example.amarchikitsya;

import static com.example.amarchikitsya.utils.CustomAlert.InputValidation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.amarchikitsya.databinding.ActivityDoctorProfessionalInfoBinding;
import com.example.amarchikitsya.databinding.ActivityEditDoctorProfessionalInfoBinding;
import com.example.amarchikitsya.model.DoctorProfessionalInfo;
import com.example.amarchikitsya.utils.InternetConnection;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DoctorProfessionalInfoActivity extends AppCompatActivity {

    ActivityDoctorProfessionalInfoBinding binding;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityDoctorProfessionalInfoBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        if (!InternetConnection.checkConnection(DoctorProfessionalInfoActivity.this)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(DoctorProfessionalInfoActivity.this);
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
                            startActivity(new Intent(DoctorProfessionalInfoActivity.this, DoctorProfessionalInfoActivity.class));
                            finish();
                        }
                    }).create().show();
        }

        binding.toolbar.editBtn.setOnClickListener(v -> {
            startActivity(new Intent(DoctorProfessionalInfoActivity.this, EditDoctorProfessionalInfoActivity.class));
        });

        databaseReference = FirebaseDatabase.getInstance().getReference("DoctorProfessionalInfo");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            currentUser = firebaseUser.getUid();
            databaseReference.child(currentUser).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    DoctorProfessionalInfo doctorProfessionalInfo = snapshot.getValue(DoctorProfessionalInfo.class);
                    if (doctorProfessionalInfo != null) {
                        if (!doctorProfessionalInfo.getSpecialization().equals("")) {
                            binding.specializationTxt.setText(doctorProfessionalInfo.getSpecialization().toString());
                        }
                        if (!doctorProfessionalInfo.getDesignation().equals("")) {
                            binding.designationTxt.setText(doctorProfessionalInfo.getDesignation().toString());
                        }
                        if (!doctorProfessionalInfo.getQualification().equals("")) {
                            binding.qualificationTxt.setText(doctorProfessionalInfo.getQualification().toString());
                        }
                        if (!doctorProfessionalInfo.getChamber().equals("")) {
                            binding.chamberTxt.setText(doctorProfessionalInfo.getChamber().toString());
                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } else {
            InputValidation(DoctorProfessionalInfoActivity.this, "Please Log in First");
        }

        binding.toolbar.backBtn.setOnClickListener(v -> {
            super.onBackPressed();
        });
        binding.toolbar.pageTitle.setText("Doctors Professional Info");
    }
}