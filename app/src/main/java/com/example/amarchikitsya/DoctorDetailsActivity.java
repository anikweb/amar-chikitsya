package com.example.amarchikitsya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.amarchikitsya.ApiInterface.MyApi;
import com.example.amarchikitsya.MyRetrofit.MyRetrofit;
import com.example.amarchikitsya.databinding.ActivityDoctorDetailsBinding;
import com.example.amarchikitsya.model.DoctorProfessionalInfo;
import com.example.amarchikitsya.model.User;
import com.example.amarchikitsya.utils.InternetConnection;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorDetailsActivity extends AppCompatActivity {

    ActivityDoctorDetailsBinding binding;
    DatabaseReference databaseReference,databaseReference2;
    Intent intent;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityDoctorDetailsBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        if (!InternetConnection.checkConnection(DoctorDetailsActivity.this)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(DoctorDetailsActivity.this);
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
                            startActivity(new Intent(DoctorDetailsActivity.this, DoctorDetailsActivity.class));
                            finish();
                        }
                    }).create().show();
        }

        intent = getIntent();
        userId = intent.getStringExtra("userId");
        binding.professionalInfoConstraint.setVisibility(View.GONE);
        databaseReference = FirebaseDatabase.getInstance().getReference("User");
        databaseReference.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (user != null){
                    if(!user.getName().equals("")){
                        binding.name.setText(user.getName());
                    }else {
                        binding.name.setText("");
                    }
                    if(!user.getProfileImage().equals("")){
                        Picasso.get().load(user.getProfileImage()).placeholder(R.drawable.loading).into(binding.profileImage);
                    }else {
                        binding.profileImage.setImageResource(R.drawable.ic_user_doctor);
                    }
                    if(!user.getEmail().equals("")){
                        binding.emailTxt.setText(user.getEmail());
                    }else {
                        binding.emailTxt.setText("");
                    }
                    if (!user.getPhone().equals("")){
                        binding.mobileTxt.setText(user.getPhone());
                    }else {
                        binding.mobileTxt.setText("");
                    }
                    if (!user.getGender().equals("")){
                        binding.genderTxt.setText(user.getGender());
                    }else {
                        binding.genderTxt.setText("");
                    }
                    if (!user.getBloodGroup().equals("")) {
                        binding.bloodTxt.setText(user.getBloodGroup());
                    }else {
                        binding.bloodTxt.setText("");
                    }
                    if (!user.getProfession().equals("")){
                        binding.professionTxt.setText(user.getProfession());
                        if(user.getProfession().equals("Doctor")){
                            getDoctorProfessionalInfo();
                        }
                    }else {
                        binding.professionTxt.setText("");
                    }
                    if (!user.getAddress().equals("")){
                        binding.addressTxt.setText(user.getAddress());
                    }else {
                        binding.addressTxt.setText("");
                    }
                    Date date = new Date(user.getUserCreatedAt());
                    DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
                    binding.jointedDate.setText("Joined at: "+dateFormat.format(date));
                }
            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        binding.toolbar.backBtn.setOnClickListener(v -> {
            super.onBackPressed();
        });
    }
    private void getDoctorProfessionalInfo() {
        binding.professionalInfoConstraint.setVisibility(View.VISIBLE);
        databaseReference2 = FirebaseDatabase.getInstance().getReference("DoctorProfessionalInfo");
        databaseReference2.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DoctorProfessionalInfo doctorProfessionalInfo = snapshot.getValue(DoctorProfessionalInfo.class);
                if (doctorProfessionalInfo != null){
                    binding.specializationTxt.setText(doctorProfessionalInfo.getSpecialization());
                    binding.designationTxt.setText(doctorProfessionalInfo.getDesignation());
                    binding.qualificationTxt.setText(doctorProfessionalInfo.getQualification());
                    binding.chamberTxt.setText(doctorProfessionalInfo.getChamber());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}