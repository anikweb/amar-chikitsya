package com.example.amarchikitsya;


import static java.lang.Math.round;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.example.amarchikitsya.ApiInterface.CurrentWeatherAPI;
import com.example.amarchikitsya.MyRetrofit.WeatherRetrofit;

import com.example.amarchikitsya.databinding.ActivityMainBinding;
import com.example.amarchikitsya.model.Current.CurrentWeather;
import com.example.amarchikitsya.model.User;
import com.example.amarchikitsya.utils.InternetConnection;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    ActionBarDrawerToggle toggle;
    ActivityMainBinding binding;
    Toolbar toolbar;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    private Toast toast;
    private long lastBackPressTime = 0;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    String currentUserUid;
    CircleImageView circleImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Please Wait");
        binding.toolbar.pageTitle.setText("Home");

        if (!InternetConnection.checkConnection(MainActivity.this)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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
                            startActivity(new Intent(MainActivity.this, MainActivity.class));
                            finish();

                        }
                    }).create().show();
        }
        getCurrentTemp();

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        firebaseAuth = FirebaseAuth.getInstance();

        toggle = new ActionBarDrawerToggle(MainActivity.this, binding.drawer, binding.toolbar.getRoot(), R.string.open, R.string.close);
        binding.drawer.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser != null) {
            currentUserUid = firebaseUser.getUid();
            databaseReference = FirebaseDatabase.getInstance().getReference("User");
            databaseReference.child(currentUserUid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User user = snapshot.getValue(User.class);
                    if (user != null) {
                        if (!user.getName().equals("")) {

                            if (user.getProfileImage().equals("")) {
                                binding.toolbar.homeToolbarProfile.setImageResource(R.drawable.ic_baseline_person_24);
                            } else {
                                Picasso.get().load(user.getProfileImage()).placeholder(R.drawable.loading).into(binding.toolbar.homeToolbarProfile);
                            }

                            if (user.getName().length() > 16) {
                                binding.userName.setText("Hello, " + user.getName().substring(0, 15) + "...");
                            } else {
                                binding.userName.setText("Hello, " + user.getName());

                            }
                            TextView username = findViewById(R.id.user_name_drawer);
                            if (!user.getName().equals("")){
                                username.setText(user.getName().toString());
                            }else{
                                username.setText("Unknown user");
                            }
                            TextView email = findViewById(R.id.user_email_drawer);
                            email.setText(user.getEmail());
                            if (!user.getProfileImage().equals("")) {
                                circleImageView = findViewById(R.id.profile_image_drawer);
                                if (!MainActivity.this.isDestroyed()) {
                                    Glide.with(MainActivity.this).load(user.getProfileImage()).placeholder(R.drawable.loading).into(circleImageView);
                                }
                            }

                        } else {
                            binding.userName.setText("Unknown User");
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        } else {
            binding.userName.setText("Visiting Mode");
        }

        binding.navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.account:
                        startActivity(new Intent(MainActivity.this, UserProfileActivity.class));
                        break;
                    case R.id.contact:
                        Intent intent3 = new Intent(MainActivity.this, SecondaryActivity.class);
                        intent3.putExtra("activity", "SentContactIntent");
                        startActivity(intent3);
                        break;
                    case R.id.registration:
                        startActivity(new Intent(MainActivity.this, RegisterActivity.class));
                        break;
                    case R.id.login:
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        intent.putExtra("drawer", "yes");
                        startActivity(intent);
                        break;
                    case R.id.logout_drawer:
                        progressDialog.show();
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Warning")
                                .setMessage("Are You Sure to log out?")
                                .setIcon(R.drawable.ic_baseline_info_24)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                progressDialog.dismiss();
                                                firebaseAuth.signOut();
                                                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                                                finish();
                                            }
                                        }, 1000);
                                    }
                                }).setNegativeButton("No", null)
                                .create().show();
                        break;
                    case R.id.share_drawer:
                        Intent intent1 = new Intent(Intent.ACTION_SEND);
                        intent1.setType("text/pain");
                        intent1.putExtra(Intent.EXTRA_SUBJECT, "Share This App");
                        intent1.putExtra(Intent.EXTRA_TEXT, "Package Name");
                        startActivity(Intent.createChooser(intent1, "Share Via"));
                        break;
                    case R.id.feedback:
                        Intent intent2 = new Intent(MainActivity.this, SecondaryActivity.class);
                        intent2.putExtra("activity", "SentFeedbackIntent");
                        startActivity(intent2);
                        break;
                }
                return false;
            }
        });
        binding.cardDoctor.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, DoctorActivity.class));
        });
        binding.appointmentBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SecondaryActivity.class);
            intent.putExtra("activity", "bookAppointment");
            startActivity(intent);
        });
        binding.cardCoronaSymptomChecker.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SecondaryActivity.class);
            intent.putExtra("activity", "coronaSymptomChecker");
            startActivity(intent);
        });
        binding.cardDiagnostics.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, DiagnosticActivity.class));
        });
        binding.cardAmbulance.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AmbulanceActivity.class));
        });
        binding.cardPharmacy.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, PharmacyActivity.class));
        });
        binding.cardNursingCare.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, NurseCareActivity.class));
        });
        binding.cardChartCalculation.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ChartAndCalculationActivity.class));
        });
        binding.cardMotherCare.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, MotherCareActivity.class));
        });
        binding.cardCurrentWeather.setOnClickListener(v -> {
            Intent intent;
            if (firebaseUser == null) {
                intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.putExtra("drawer", "yes");
            } else {
                intent = new Intent(MainActivity.this, CurrentWeatherActivity.class);
            }
            startActivity(intent);
        });

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            Menu menu = binding.navigation.getMenu();
            menu.findItem(R.id.registration).setVisible(false);
            menu.findItem(R.id.login).setVisible(false);
            menu.findItem(R.id.logout_drawer).setVisible(true);
        } else {
            Menu menu = binding.navigation.getMenu();
            menu.findItem(R.id.registration).setVisible(true);
            menu.findItem(R.id.login).setVisible(true);
            menu.findItem(R.id.logout_drawer).setVisible(false);
        }

        binding.bottomMenu.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bottom_menu_inbox:
                    if (firebaseUser == null) {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        intent.putExtra("drawer", "yes");
                        startActivity(intent);
                    } else {
                        startActivity(new Intent(MainActivity.this, InboxActivity.class));
                    }

                    break;
            }
            return true;
        });
       
    }

    private void getCurrentTemp() {
        CurrentWeatherAPI currentWeatherAPI = WeatherRetrofit.getRetrofit().create(CurrentWeatherAPI.class);
        Call<CurrentWeather> currentWeatherCall = currentWeatherAPI.getCurrentWeather();
        currentWeatherCall.enqueue(new Callback<CurrentWeather>() {
            @Override
            public void onResponse(Call<CurrentWeather> call, Response<CurrentWeather> response) {

                binding.weatherTxt.setText("" + round(response.body().getMain().getTemp()) + " \u2103");

            }

            @Override
            public void onFailure(Call<CurrentWeather> call, Throwable t) {
                Toast.makeText(MainActivity.this, "" + t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            binding.toolbar.homeToolbarProfile.setVisibility(View.VISIBLE);
            binding.toolbar.homeToolbarProfile.setOnClickListener(v -> {
                startActivity(new Intent(MainActivity.this, UserProfileActivity.class));
            });
        } else {
            menuInflater.inflate(R.menu.top_menu, menu);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            item.setVisible(false);
        } else {
            switch (item.getItemId()) {
                case R.id.top_menu_profile:
                    if (firebaseUser == null) {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        intent.putExtra("drawer", "yes");
                        startActivity(intent);
                    } else {
                        startActivity(new Intent(MainActivity.this, UserProfileActivity.class));
                    }
                    break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (this.lastBackPressTime < System.currentTimeMillis() - 3000) {
            toast = Toast.makeText(this, "Press back again to close this app", 3000);
            toast.show();
            this.lastBackPressTime = System.currentTimeMillis();
        } else {
            if (toast != null) {
                toast.cancel();
            }
            super.onBackPressed();
        }
    }
}
