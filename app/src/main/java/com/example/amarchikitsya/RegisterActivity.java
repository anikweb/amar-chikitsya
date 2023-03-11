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

import com.example.amarchikitsya.databinding.ActivityRegisterBinding;
import com.example.amarchikitsya.utils.InternetConnection;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;
    FirebaseAuth firebaseAuth;
    DatabaseReference firebaseDatabase;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        if (!InternetConnection.checkConnection(RegisterActivity.this)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
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
                            startActivity(new Intent(RegisterActivity.this, RegisterActivity.class));
                            finish();
                        }
                    }).create().show();
        }

        progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setMessage("Please Wait......");
        firebaseDatabase = FirebaseDatabase.getInstance().getReference("User");

        binding.emailCard.setOnClickListener(v -> {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    binding.layout3.setVisibility(View.VISIBLE);
                    binding.layout2.setVisibility(View.GONE);
                }
            }, 500);
        });
        binding.backToPrevious.setOnClickListener(v -> {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    binding.layout3.setVisibility(View.GONE);
                    binding.layout2.setVisibility(View.VISIBLE);
                }
            }, 500);
        });
        binding.mobileNumberCard.setOnClickListener(v -> {
            Toast.makeText(this, "Mobile Number", Toast.LENGTH_SHORT).show();
        });
        firebaseAuth = FirebaseAuth.getInstance();
        binding.registerBtn.setOnClickListener(v -> {
            String name = binding.inputFullname.getText().toString();
            String email = binding.inputEmail.getText().toString();
            String pass = binding.inputPassword.getText().toString();
            String conPass = binding.inputConPassword.getText().toString();

            if (name.equals("")) {
                binding.fullnameError.setVisibility(View.VISIBLE);
                binding.passError.setVisibility(View.GONE);
                binding.emailError.setVisibility(View.GONE);
                binding.conPassError.setVisibility(View.GONE);
                binding.fullnameError.setText("Please Enter your name");
                binding.inputFullname.setError("Please Enter your name");

            } else if (email.equals("")) {
                binding.fullnameError.setVisibility(View.GONE);
                binding.passError.setVisibility(View.GONE);
                binding.emailError.setVisibility(View.VISIBLE);
                binding.conPassError.setVisibility(View.GONE);
                binding.inputEmail.setError("Please Enter your email");
                binding.emailError.setText("Please Enter your email");
            } else if (pass.equals("")) {
                binding.fullnameError.setVisibility(View.GONE);
                binding.passError.setVisibility(View.VISIBLE);
                binding.emailError.setVisibility(View.GONE);
                binding.conPassError.setVisibility(View.GONE);
                binding.inputPassword.setError("Please create password");
                binding.passError.setText("Please create password");
            } else if (conPass.equals("")) {
                binding.fullnameError.setVisibility(View.GONE);
                binding.passError.setVisibility(View.GONE);
                binding.emailError.setVisibility(View.GONE);
                binding.conPassError.setVisibility(View.VISIBLE);
                binding.inputConPassword.setError("Please confirm password");
                binding.conPassError.setText("Please confirm password");
            } else if (!conPass.equals(pass)) {
                binding.fullnameError.setVisibility(View.GONE);
                binding.passError.setVisibility(View.GONE);
                binding.emailError.setVisibility(View.GONE);
                binding.conPassError.setVisibility(View.VISIBLE);
                binding.inputConPassword.setError("Password did not match");
                binding.conPassError.setText("Password did not match");
            } else if (pass.length() < 6) {
                binding.fullnameError.setVisibility(View.GONE);
                binding.passError.setVisibility(View.VISIBLE);
                binding.emailError.setVisibility(View.GONE);
                binding.conPassError.setVisibility(View.GONE);
                binding.inputPassword.setError("Password must be more than 6 character");
                binding.passError.setText("Password must be more than 6 character");
            } else {
                progressDialog.show();
                firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            String userId = firebaseUser.getUid();
                            HashMap<String, Object> map = new HashMap<>();
                            map.put("name", name);
                            map.put("email", email);
                            map.put("pass", pass);
                            map.put("profileImage", "");
                            map.put("userId", userId);
                            map.put("birthdate", "");
                            map.put("profession", "");
                            map.put("address", "");
                            map.put("phone", "");
                            map.put("role", "");
                            map.put("bloodGroup", "");
                            map.put("gender", "");
                            map.put("userCreatedAt", System.currentTimeMillis());
                            map.put("userUpdatedAt", "");
                            firebaseDatabase.child(userId).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        progressDialog.dismiss();
                                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                        finish();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismiss();
                                    InputValidation(RegisterActivity.this, "" + e.getMessage());
                                }
                            });
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        InputValidation(RegisterActivity.this, "" + e.getMessage());
                    }
                });
            }
        });
        binding.loginNow.setOnClickListener(v -> {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                }
            }, 500);
        });
    }
}