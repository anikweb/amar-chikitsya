package com.example.amarchikitsya;

import static com.example.amarchikitsya.utils.CustomAlert.InputValidation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.amarchikitsya.databinding.ActivityEditProfileBinding;
import com.example.amarchikitsya.model.User;
import com.example.amarchikitsya.utils.InternetConnection;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.List;

public class EditProfileActivity extends AppCompatActivity {

    ActivityEditProfileBinding binding;
    String[] genderList = {"Male", "Female", "Other"};
    String[] bloodGroupList = {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
    String[] professionList = {"Author", "Businessman", "Doctor", "Engineer", "Farmer", "Journalist", "Judge", "Lawyer", "Nurse", "Photographer", "Pilot", "Scientist", "Student", "Other"};
    FirebaseUser firebaseUser;
    String currentUserId, name, email,phone,gender,blood_group,profession,address;
    DatabaseReference databaseReference;
    Uri imageUri;
    StorageReference storageReference;
    String profileImageUrl;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        if (!InternetConnection.checkConnection(EditProfileActivity.this)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileActivity.this);
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
                            startActivity(new Intent(EditProfileActivity.this, EditProfileActivity.class));
                            finish();
                        }
                    }).create().show();
        }

        progressDialog = new ProgressDialog(EditProfileActivity.this);
        progressDialog.setMessage("Please Wait....");
        storageReference = FirebaseStorage.getInstance().getReference("ProfileImage");

        ArrayAdapter<String> genderAdapter = new ArrayAdapter<String>(
                EditProfileActivity.this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                genderList
        );
        binding.inputGender.setAdapter(genderAdapter);
        ArrayAdapter<String> bloodAdapter = new ArrayAdapter<String>(
                EditProfileActivity.this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                bloodGroupList
        );
        binding.inputBlood.setAdapter(bloodAdapter);
        ArrayAdapter<String> professionAdapter = new ArrayAdapter<String>(
                EditProfileActivity.this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                professionList
        );
        binding.inputProfession.setAdapter(professionAdapter);

        binding.profileImage.setOnClickListener(v -> {
            ImagePicker.with(EditProfileActivity.this)
                    .crop()                    //Crop image(Optional), Check Customization for more option
                    .compress(1024)            //Final image size will be less than 1 MB(Optional)
                    .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                    .start(222);
        });
        binding.toolbar.backBtn.setOnClickListener(v -> {
            super.onBackPressed();
        });

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser != null) {
            currentUserId = firebaseUser.getUid();

            databaseReference = FirebaseDatabase.getInstance().getReference("User");
            databaseReference.child(currentUserId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User user = snapshot.getValue(User.class);
                    if (user != null) {
                        if (!user.getProfileImage().equals("")) {

                            if(!EditProfileActivity.this.isDestroyed()){
                                Glide.with(EditProfileActivity.this).load(user.getProfileImage()).into(binding.profileImage);
                            }
                        } else {
                            binding.profileImage.setImageResource(R.drawable.ic_baseline_person_24);
                        }
                        binding.inputName.setText(user.getName());
                        binding.inputEmail.setText(user.getEmail());
                        if (!user.getPhone().equals("")) {
                            binding.inputPhone.setText(user.getPhone());
                        }
                        if (!user.getGender().equals("")) {
                            binding.inputGender.setText(user.getGender());
                        }
                        if (!user.getBloodGroup().equals("")) {
                            binding.inputBlood.setText(user.getBloodGroup());
                        }
                        if (!user.getProfession().equals("")) {
                            binding.inputProfession.setText(user.getProfession());
                        }
                        if (!user.getAddress().equals("")) {
                            binding.inputAddress.setText(user.getAddress());
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        binding.updateBtn.setOnClickListener(v -> {
            progressDialog.show();
            if (imageUri != null) {
                StorageReference storageImgReference = storageReference.child(currentUserId).child("profile_image");
                storageImgReference.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if(task.isSuccessful()){
                            storageImgReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    profileImageUrl = String.valueOf(uri);
                                    updateProfile();
                                }
                            });
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        InputValidation(EditProfileActivity.this,e.getMessage().toString());
                    }
                });
            }else{
                updateProfile();
            }


        });
    }

    private void updateProfile() {

        name = binding.inputName.getText().toString();
        email = binding.inputEmail.getText().toString();
        phone = binding.inputPhone.getText().toString();
        gender = binding.inputGender.getText().toString();
        blood_group = binding.inputBlood.getText().toString();
        profession = binding.inputProfession.getText().toString();
        address = binding.inputAddress.getText().toString();

        HashMap<String, Object> updateMap = new HashMap<>();
        updateMap.put("name",name);
        updateMap.put("email",email);
        updateMap.put("phone",phone);
        if(profileImageUrl != null){
            updateMap.put("profileImage",profileImageUrl);
        }
        updateMap.put("address",address);
        updateMap.put("bloodGroup",blood_group);
        updateMap.put("gender",gender);
        updateMap.put("profession",profession);
        databaseReference = FirebaseDatabase.getInstance().getReference("User");
        databaseReference.child(currentUserId).updateChildren(updateMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    EditProfileActivity.super.onBackPressed();
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                InputValidation(EditProfileActivity.this,e.getMessage().toString());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 222 && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            binding.profileImage.setImageURI(imageUri);
        }

    }
}