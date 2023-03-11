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

import com.example.amarchikitsya.databinding.ActivityLoginBinding;
import com.example.amarchikitsya.utils.InternetConnection;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    String email, pass;
    ActivityLoginBinding binding;
//    ActivityLoginBinding binding;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    Intent intent;
    private long lastBackPressedTime = 0;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        if (!InternetConnection.checkConnection(LoginActivity.this)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
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
                            startActivity(new Intent(LoginActivity.this, LoginActivity.class));
                            finish();
                        }
                    }).create().show();
        }

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Please Wait");
        firebaseAuth = FirebaseAuth.getInstance();
        intent = getIntent();
        if (intent.getStringExtra("drawer") != null) {
            binding.guestCard.setVisibility(View.GONE);
            binding.loginAlertForGuest.setVisibility(View.VISIBLE);
        }
//        Onclick Buttons
        binding.emailCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.layout2.setVisibility(View.GONE);
                        binding.layout3.setVisibility(View.VISIBLE);
                    }
                }, 500);
            }
        });
        binding.backToPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.layout2.setVisibility(View.VISIBLE);
                        binding.layout3.setVisibility(View.GONE);
                    }
                }, 500);
            }
        });
        binding.registerNow.setOnClickListener(v -> {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                }
            }, 500);
        });
        binding.loginBtn.setOnClickListener(v -> {
            email = binding.inputEmail.getText().toString();
            pass = binding.inputPassword.getText().toString();

            if (email.equals("")) {
                binding.passError.setVisibility(View.GONE);
                binding.emailError.setVisibility(View.VISIBLE);
                binding.inputEmail.setError("Please Enter your email");
                binding.emailError.setText("Please Enter your email");
            } else if (pass.equals("")) {
                binding.passError.setVisibility(View.VISIBLE);
                binding.emailError.setVisibility(View.GONE);
                binding.inputPassword.setError("Please enter password");
                binding.passError.setText("Please enter password");
            } else {
                progressDialog.show();
                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        InputValidation(LoginActivity.this, "" + e.getMessage());
                    }
                });
            }
        });
        binding.guestCard.setOnClickListener(v -> {
            progressDialog.show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismiss();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }
            }, 1000);

        });
    }

    @Override
    public void onBackPressed() {
        if (intent.getStringExtra("drawer") == null) {
            if (this.lastBackPressedTime < System.currentTimeMillis() - 3000) {
                toast = Toast.makeText(this, "Press back again to close this app", 3000);
                toast.show();
                this.lastBackPressedTime = System.currentTimeMillis();
            } else {
                if (toast != null) {
                    toast.cancel();
                }
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }

    }
}