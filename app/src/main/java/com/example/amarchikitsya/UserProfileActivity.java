package com.example.amarchikitsya;

import static com.example.amarchikitsya.utils.CustomAlert.InputValidation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.amarchikitsya.databinding.ActivityUserProfileBinding;
import com.example.amarchikitsya.model.User;
import com.example.amarchikitsya.utils.InternetConnection;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfileActivity extends AppCompatActivity {

    ActivityUserProfileBinding binding;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityUserProfileBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        if (!InternetConnection.checkConnection(UserProfileActivity.this)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(UserProfileActivity.this);
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
                            startActivity(new Intent(UserProfileActivity.this, UserProfileActivity.class));
                            finish();
                        }
                    }).create().show();
        }
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(UserProfileActivity.this);
        progressDialog.setMessage("Are you sure?");

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        ;

        if (firebaseUser != null) {
            currentUserId = firebaseUser.getUid();
            databaseReference = FirebaseDatabase.getInstance().getReference("User");
            databaseReference.child(currentUserId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User user = snapshot.getValue(User.class);
                    if (user != null) {
                        if (!user.getProfileImage().equals("")) {
                            if (!UserProfileActivity.this.isDestroyed()) {
                                Glide.with(UserProfileActivity.this).load(user.getProfileImage()).into(binding.profileImage);
                            }
                        } else {
                            binding.profileImage.setImageResource(R.drawable.ic_baseline_person_24);
                        }
                        binding.name.setText(user.getName());
                        if (!user.getProfession().equals("")) {
                            binding.profession.setText(user.getProfession());
                        } else {
                            binding.profession.setText("");
                        }

                        if (user.getProfession().equals("Doctor")) {
                            binding.medicalHistoryLayout.setVisibility(View.GONE);
                            binding.professionalInfoLayout.setVisibility(View.VISIBLE);
                        } else {
                            binding.medicalHistoryLayout.setVisibility(View.VISIBLE);
                            binding.professionalInfoLayout.setVisibility(View.GONE);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        binding.personalInfoLayout.setOnClickListener(v -> {
            startActivity(new Intent(UserProfileActivity.this, ProfileInfoActivity.class));
        });
        binding.professionalInfoLayout.setOnClickListener(v -> {
            startActivity(new Intent(this, DoctorProfessionalInfoActivity.class));
        });

        binding.logoutBtn.setOnClickListener(v -> {
            progressDialog.show();
            AlertDialog.Builder builder = new AlertDialog.Builder(UserProfileActivity.this);
            builder.setTitle("Warning")
                    .setMessage("Are you sure?")
                    .setIcon(R.drawable.ic_baseline_info_24)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.dismiss();
                                    firebaseAuth.signOut();
                                    startActivity(new Intent(UserProfileActivity.this, LoginActivity.class));
                                    finishAffinity();
                                }
                            }, 500);

                        }
                    }).setNegativeButton("NO", null)
                    .create().show();
        });
        binding.toolbar.backBtn.setOnClickListener(v -> {
            super.onBackPressed();
        });
    }
}