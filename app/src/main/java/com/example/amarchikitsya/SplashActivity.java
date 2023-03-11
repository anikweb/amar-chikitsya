package com.example.amarchikitsya;

import androidx.appcompat.app.AppCompatActivity;

import com.example.amarchikitsya.databinding.ActivitySplashBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    
    ActivitySplashBinding binding;
    FirebaseUser firebaseUser;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        String versionName = BuildConfig.VERSION_NAME;
        binding.versionName.setText("Version: " + versionName);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences preferences = getSharedPreferences("PREFERENCE", MODE_PRIVATE);
                String FirstTimeInstall = preferences.getString("FirstTimeInstall", "");
                firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

                if (FirstTimeInstall.equals("Yes")) {
                    if (firebaseUser == null) {
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    } else {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    }
                } else {
                    startActivity(new Intent(SplashActivity.this, FirstInstallActivity.class));
                }
                finish();
            }
        }, 1500);

    }
}