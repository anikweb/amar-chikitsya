package com.example.amarchikitsya;

import static com.example.amarchikitsya.utils.CustomAlert.InputValidation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.amarchikitsya.Adapter.DoctorAdapter;
import com.example.amarchikitsya.ApiInterface.MyApi;
import com.example.amarchikitsya.MyRetrofit.MyRetrofit;
import com.example.amarchikitsya.databinding.ActivityDoctorBinding;
import com.example.amarchikitsya.model.Doctors;
import com.example.amarchikitsya.utils.InternetConnection;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorActivity extends AppCompatActivity {

    ActivityDoctorBinding binding;
    List<Doctors> doctorRecyclerList;
    DoctorAdapter doctorAdapter;
    DatabaseReference firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityDoctorBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        if (!InternetConnection.checkConnection(DoctorActivity.this)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(DoctorActivity.this);
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
                            startActivity(new Intent(DoctorActivity.this, DoctorActivity.class));
                            finish();
                        }
                    }).create().show();
        }

        doctorRecyclerList = new ArrayList<>();
        doctorRecyclerList.add(new Doctors(1, "Brig. Gen. (Retd) Dr. ABM Sayed Hossain", "Neurologist/Neurophysician", "Mohakhali Dhaka", "MBBS DCN Trained in Neurology", "0123456789", "Universal Medical College and Hospital.","https://familydoctor.org/wp-content/uploads/2018/02/41808433_l.jpg"));
        doctorRecyclerList.add(new Doctors(2, "Dr. ADM Al Masum Khan", "Medicine and Neurologist", "Kalyanpur, Dhaka", "MBBS, BCS(Health), MD (Neuro Medicine)", "0123456789 ", "Ibn Sina Medical College Hospital.","https://img.freepik.com/free-vector/doctor-character-background_1270-84.jpg?w=2000"));
        doctorRecyclerList.add(new Doctors(3, "Prof. Dr. ABM Fazlur Rahman", "Orthopedic Specialist", "Farmgate, Dhaka", "MBBS(DMC), MS (Orthopedic Surgery), FICS", "0123456789 ", "Al- Raji Hospital (Pvt.) Ltd.","https://img.freepik.com/free-photo/pleased-young-female-doctor-wearing-medical-robe-stethoscope-around-neck-standing-with-closed-posture_409827-254.jpg?w=2000"));
        doctorRecyclerList.add(new Doctors(4, "Asst. Prof. Dr. ABM Jakir Uddin.", "Orthopedic Specialist", "English Road, Dhaka", "MBBS, D-Ortho, MS (Ortho)", " 0123456789", "Popular Diagnostic Centre Ltd.","https://thumbs.dreamstime.com/b/male-doctor-portrait-isolated-white-background-56744085.jpg"));
        doctorRecyclerList.add(new Doctors(5, "Prof. Dr. A A Mohiuddin", "Eye Specialist (Opthalmologist)", "Malibag, Dhaka", "MBBS, FCPS", " ", "Padma Diagnostic Centre Ltd.","https://cdn.pixabay.com/photo/2017/03/14/03/20/woman-2141808__340.jpg"));
        doctorRecyclerList.add(new Doctors(6, "Dr. A H M Sazzad Hossain Khan", "Eye Specialist (Opthalmologist)", "Mehedibag, Chattogram", "MBBS,DCO", "0123456789", "Metro Imaging and Diagnostic Centre.","https://indianriveracupuncture.com/wp-content/uploads/2018/07/doctor-img-01-free-img.jpg"));
        doctorRecyclerList.add(new Doctors(7, "Dr. AEMN Jahangir Selim", "Cardiologist", "Mehedibag, Dhaka", "MBBS,BCS(Health), MD (Cardiology)", "0123456789", "Sigma Lab Limited.","https://st.depositphotos.com/1518767/2572/i/450/depositphotos_25727021-stock-photo-smiling-doctor.jpg"));
        doctorRecyclerList.add(new Doctors(8, "Assoc. Prof. Dr. AF Kabir Uddin Ahmed", "Cardiologist", "Uttara, Dhaka", "MBBS,MD (Cardiology)", "0123456789", "Ibn Sina Diagnostic and Consultation Centre.","https://www.woodlandshospital.in/images/doctor-img/pallab-gangopadhyay.jpg"));
        doctorRecyclerList.add(new Doctors(9, "Prof. Dr. AKM Asifuzzaman", "Dental Surgeon", "Dhap,Rangpur", "MBBS,MD (Cardiology)", "0123456789", "Ibn Sina Diagnostic and Consultation Centre.","https://media.istockphoto.com/id/1366374033/photo/shot-of-a-young-doctor-using-a-digital-tablet-in-a-modern-hospital.jpg?b=1&s=170667a&w=0&k=20&c=I3nSyS-hAorfMhDCXrv16JyQ7VYgaFr7rrZDW2bC-qs="));
        doctorRecyclerList.add(new Doctors(10, "Asst. Prof. Dr. AKM. Salahuddin", "Dentist", "Nizam Road, Chattogram", "BDS, MS", "0123456789", "Core Dental","https://familydoctor.org/wp-content/uploads/2018/02/41808433_l.jpg"));

        DoctorAdapter doctorAdapter = new DoctorAdapter(DoctorActivity.this, doctorRecyclerList);
        binding.doctorRecycler.setAdapter(doctorAdapter);

        
        binding.toolbar.pageTitle.setText("Doctors");
        binding.toolbar.backBtn.setOnClickListener(v -> {
            super.onBackPressed();
        });

    }
}