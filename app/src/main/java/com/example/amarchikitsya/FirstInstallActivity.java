package com.example.amarchikitsya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.amarchikitsya.databinding.ActivityFirstInstallBinding;
import com.example.amarchikitsya.model.Greetings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class FirstInstallActivity extends AppCompatActivity {

    ActivityFirstInstallBinding binding;
    int index = 0;
    List<Greetings> greetings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityFirstInstallBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        greetings = new ArrayList<>();
        addDataToGreetings();
        initialContent();
        binding.nextBtn.setOnClickListener(v -> {
            index++;
            if (greetings.size() > index) {
                setContent(index);
            }
        });
        binding.backBtn.setOnClickListener(v -> {
            index--;
            if (greetings.size() > index) {
                setContent(index);
            }
        });
        binding.starterBtn.setOnClickListener(v -> {
            SharedPreferences preferences = getSharedPreferences("PREFERENCE",MODE_PRIVATE);
            String FirstTimeInstall = preferences.getString("FirstTimeInstall","");
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("FirstTimeInstall","Yes");
                editor.apply();
                startActivity(new Intent(FirstInstallActivity.this, LoginActivity.class));
                finish();
        });
    }
    private void initialContent() {
        binding.pageTitle.setText("Amar Chikitsya");
        binding.pageSubtitle.setText("Your Health Assistant.");
        binding.image.setImageResource(R.mipmap.ic_launcher_foreground);
        binding.backBtn.setVisibility(View.GONE);
    }
    private void addDataToGreetings() {
        greetings.add(new Greetings("Amar Chikitsya", "Your Health Is Our Priority."));
        greetings.add(new Greetings("Consult with Doctors", "Get list of best doctor nearby you. according to symptoms consult with a specialized doctor via video call."));
        greetings.add(new Greetings("Book Appointment", "Book Appointment with a specialized doctor"));
        greetings.add(new Greetings("Diagnostics Centre", "Get list of best diagnostic centre nearby you and test at home very easily."));
        greetings.add(new Greetings("Emergency Ambulance", "Call emergency ambulance nearby you!"));
        greetings.add(new Greetings("Buy Medicines", "Get list of best pharmacy nearby you and buy authentic medicines at the affordable price by from trusted stores."));
        greetings.add(new Greetings("Nursing Care", "Get nursing care at home from a registered trusted nursing home."));
        greetings.add(new Greetings("Your Health Assistant.", "Get daily health services through this app. Let's enter the new world of healthcare."));
    }
    private void setContent(int index) {
        binding.pageTitle.setText(greetings.get(index).getTitle());
        binding.pageSubtitle.setText(greetings.get(index).getSubtitle());
        if (index == 0) {
            binding.image.setImageResource(R.mipmap.ic_launcher_foreground);
            binding.backBtn.setVisibility(View.GONE);
        } else if (index == 1) {
            binding.backBtn.setVisibility(View.VISIBLE);
            binding.image.setImageResource(R.drawable.ic_doctor_large);
        } else if (index == 2) {
            binding.image.setImageResource(R.drawable.ic_book_appointment);
        } else if (index == 3) {
            binding.image.setImageResource(R.drawable.ic_diagnostic_centre);
        } else if (index == 4) {
            binding.image.setImageResource(R.drawable.img_ambulance);
        } else if (index == 5) {
            binding.image.setImageResource(R.drawable.pharmacy);
        } else if (index == 6) {
            binding.image.setImageResource(R.drawable.ic_nurse_care);
        } else if (index == 7) {
            binding.image.setImageResource(R.drawable.ic_health_assistant);
            binding.nextBtn.setVisibility(View.GONE);
            binding.backBtn.setVisibility(View.GONE);
            binding.starterBtn.setVisibility(View.VISIBLE);
        }
    }
}