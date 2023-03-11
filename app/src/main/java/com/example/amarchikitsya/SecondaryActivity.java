package com.example.amarchikitsya;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.amarchikitsya.databinding.ActivityDrawerNavigationBinding;
import com.example.amarchikitsya.fragment.BookAppointmentFragment;
import com.example.amarchikitsya.fragment.ContactFragment;
import com.example.amarchikitsya.fragment.FeedbackFragment;
import com.example.amarchikitsya.utils.InternetConnection;

public class SecondaryActivity extends AppCompatActivity {

    ContactFragment contactFragment = new ContactFragment();
    FeedbackFragment feedbackFragment = new FeedbackFragment();
    BookAppointmentFragment bookAppointmentFragment = new BookAppointmentFragment();
    ActivityDrawerNavigationBinding binding;
    Intent intent;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityDrawerNavigationBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        
        binding.Dtoolbar.backBtn.setVisibility(View.VISIBLE);
        binding.Dtoolbar.backBtn.setOnClickListener(v -> {
            super.onBackPressed();
        });
        checkInternet();
        
        intent = getIntent();
        if(intent.getStringExtra("activity").equals("SentContactIntent")){
            getSupportFragmentManager().beginTransaction().replace(R.id.drawer_content, contactFragment).commit();
            binding.Dtoolbar.pageTitle.setText("Contact");
        }else if(intent.getStringExtra("activity").equals("SentFeedbackIntent")){
            getSupportFragmentManager().beginTransaction().replace(R.id.drawer_content, feedbackFragment).commit();
            binding.Dtoolbar.pageTitle.setText("Feedback");
        }else if(intent.getStringExtra("activity").equals("bookAppointment")){
            getSupportFragmentManager().beginTransaction().replace(R.id.drawer_content, bookAppointmentFragment).commit();
            binding.Dtoolbar.pageTitle.setText("Book Appointment");
        }
    }
    
    private void checkInternet() {
        if (!InternetConnection.checkConnection(SecondaryActivity.this)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(SecondaryActivity.this);
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
                            startActivity(new Intent(SecondaryActivity.this, CurrentWeatherActivity.class));
                            finish();
                        }
                    }).create().show();
        }
    }
}