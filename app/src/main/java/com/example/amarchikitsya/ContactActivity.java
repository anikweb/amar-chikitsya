package com.example.amarchikitsya;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.amarchikitsya.databinding.ActivityContactBinding;

public class ContactActivity extends AppCompatActivity {

    ActivityContactBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityContactBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();
    }
}