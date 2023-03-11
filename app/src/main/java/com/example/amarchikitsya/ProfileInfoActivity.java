package com.example.amarchikitsya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.amarchikitsya.databinding.ActivityProfileInfoBinding;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProfileInfoActivity extends AppCompatActivity {

    ActivityProfileInfoBinding binding;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        binding = ActivityProfileInfoBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());


        if (!InternetConnection.checkConnection(ProfileInfoActivity.this)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ProfileInfoActivity.this);
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
                            startActivity(new Intent(ProfileInfoActivity.this, ProfileInfoActivity.class));
                            finish();
                        }
                    }).create().show();
        }


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser != null) {
            currentUserId = firebaseUser.getUid();

            databaseReference = FirebaseDatabase.getInstance().getReference("User");
            databaseReference.child(currentUserId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User user = snapshot.getValue(User.class);
                    if (user != null) {
                        if (user.getProfileImage().equals("")) {

                            binding.profileImage.setImageResource(R.drawable.ic_baseline_person_24);
                        } else {
                            if (!ProfileInfoActivity.this.isDestroyed()) {
                                Glide.with(ProfileInfoActivity.this).load(user.getProfileImage()).into(binding.profileImage);
                            }
                        }
                        binding.name.setText(user.getName());
                        binding.emailTxt.setText(user.getEmail());
                        if (!user.getPhone().equals("")) {
                            binding.mobileTxt.setText(user.getPhone());
                        }
                        if (!user.getGender().equals("")) {
                            binding.genderTxt.setText(user.getGender());
                        }
                        if (!user.getBloodGroup().equals("")) {
                            binding.bloodTxt.setText(user.getBloodGroup());
                        }
                        if (!user.getProfession().equals("")) {
                            binding.professionTxt.setText(user.getProfession());
                        }
                        if (!user.getAddress().equals("")) {
                            binding.addressTxt.setText(user.getAddress());
                        }
                        Date date = new Date(user.getUserCreatedAt());
                        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                        binding.jointedDate.setText("Jointed at: " + dateFormat.format(date));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        binding.toolbar.backBtn.setOnClickListener(v -> {
            super.onBackPressed();
        });
        binding.toolbar.editBtn.setOnClickListener(v -> {
            startActivity(new Intent(ProfileInfoActivity.this, EditProfileActivity.class));
        });
    }
}