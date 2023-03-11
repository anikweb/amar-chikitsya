package com.example.amarchikitsya;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.amarchikitsya.Adapter.InboxFragmentAdapter;
import com.example.amarchikitsya.databinding.ActivityInboxBinding;
import com.example.amarchikitsya.utils.InternetConnection;

public class InboxActivity extends AppCompatActivity {

    ActivityInboxBinding binding;
    FragmentManager fragmentManager;
    InboxFragmentAdapter inboxFragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityInboxBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        if (!InternetConnection.checkConnection(InboxActivity.this)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(InboxActivity.this);
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
                            startActivity(new Intent(InboxActivity.this, InboxActivity.class));
                            finish();
                        }
                    }).create().show();
        }

        fragmentManager = getSupportFragmentManager();

        inboxFragmentAdapter = new InboxFragmentAdapter(fragmentManager, 101);

        binding.viewPager.setAdapter(inboxFragmentAdapter);
        binding.tabLayout.setupWithViewPager(binding.viewPager);

        binding.toolbar.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.toolbar.pageTitle.setText("Chat with Doctors");
    }
}