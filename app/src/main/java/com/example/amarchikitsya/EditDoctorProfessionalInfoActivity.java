package com.example.amarchikitsya;

import static com.example.amarchikitsya.utils.CustomAlert.InputValidation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.amarchikitsya.databinding.ActivityEditDoctorProfessionalInfoBinding;
import com.example.amarchikitsya.model.DoctorProfessionalInfo;
import com.example.amarchikitsya.utils.InternetConnection;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class EditDoctorProfessionalInfoActivity extends AppCompatActivity {

    ActivityEditDoctorProfessionalInfoBinding binding;
    String[] specializationList = {"Neurologist/Neurophysician", "Medicine and Neurologist", "Orthopedic Specialist", "Eye Specialist (Opthalmologist)", "Cardiologist", "Dental Surgeon", "Dentist"};
    String specialization,designation,qualification,chamber,currentUser;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityEditDoctorProfessionalInfoBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        if (!InternetConnection.checkConnection(EditDoctorProfessionalInfoActivity.this)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(EditDoctorProfessionalInfoActivity.this);
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
                            startActivity(new Intent(EditDoctorProfessionalInfoActivity.this, EditDoctorProfessionalInfoActivity.class));
                            finish();
                        }
                    }).create().show();
        }

        ArrayAdapter<String> specializationAdapter = new ArrayAdapter<String>(
                EditDoctorProfessionalInfoActivity.this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                specializationList
        );
        binding.inputDoctorSpecialization.setAdapter(specializationAdapter);

        binding.toolbar.backBtn.setOnClickListener(v -> {
            super.onBackPressed();
        });
        binding.toolbar.pageTitle.setText("Update Professional Info");

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        binding.updateBtn.setOnClickListener(v -> {
            specialization = binding.inputDoctorSpecialization.getText().toString();
            designation = binding.inputDesignation.getText().toString();
            qualification = binding.inputQualification.getText().toString();
            chamber = binding.inputChamber.getText().toString();

            if(firebaseUser != null){
                currentUser = firebaseUser.getUid();
                HashMap<String, Object> updateMap = new HashMap<>();

                updateMap.put("specialization",specialization);
                updateMap.put("designation",designation);
                updateMap.put("qualification",qualification);
                updateMap.put("chamber",chamber);

                databaseReference = FirebaseDatabase.getInstance().getReference("DoctorProfessionalInfo");
                databaseReference.child(currentUser).updateChildren(updateMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        InputValidation(EditDoctorProfessionalInfoActivity.this,e.getMessage().toString());
                    }
                });
            }else {
                InputValidation(EditDoctorProfessionalInfoActivity.this,"Please Log in First");
            }

        });

        databaseReference = FirebaseDatabase.getInstance().getReference("DoctorProfessionalInfo");

        if(firebaseUser != null) {
            currentUser = firebaseUser.getUid();
            databaseReference.child(currentUser).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    DoctorProfessionalInfo doctorProfessionalInfo = snapshot.getValue(DoctorProfessionalInfo.class);
                    if(doctorProfessionalInfo != null){
                        if(!doctorProfessionalInfo.getSpecialization().equals("")){
                            binding.inputDoctorSpecialization.setText(doctorProfessionalInfo.getSpecialization().toString());
                        }
                        if(!doctorProfessionalInfo.getDesignation().equals("")){
                            binding.inputDesignation.setText(doctorProfessionalInfo.getDesignation().toString());
                        }
                        if(!doctorProfessionalInfo.getQualification().equals("")){
                            binding.inputQualification.setText(doctorProfessionalInfo.getQualification().toString());
                        }
                        if(!doctorProfessionalInfo.getChamber().equals("")){
                            binding.inputChamber.setText(doctorProfessionalInfo.getChamber().toString());
                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }else{
            InputValidation(this,"Please Log in First");
        }



    }
}