package com.example.amarchikitsya.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.amarchikitsya.Adapter.InboxUserDoctorsAdapter;
import com.example.amarchikitsya.R;
import com.example.amarchikitsya.databinding.FragmentUsersDoctorsInboxBinding;
import com.example.amarchikitsya.model.Doctors;
import com.example.amarchikitsya.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class InboxDoctorUserFragment extends Fragment {

    FragmentUsersDoctorsInboxBinding binding;
    List<User> userList;
    InboxUserDoctorsAdapter inboxUserDoctorsAdapter;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    String currenUserId;

    public InboxDoctorUserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUsersDoctorsInboxBinding.inflate(inflater);


//        doctorList.add(new Doctors(1, "Brig. Gen. (Retd) Dr. ABM Sayed Hossain", "Neurologist/Neurophysician", "Mohakhali Dhaka", "MBBS DCN Trained in Neurology", " ", "Universal Medical College and Hospital.","https://familydoctor.org/wp-content/uploads/2018/02/41808433_l.jpg"));
//        doctorList.add(new Doctors(2, "Dr. ADM Al Masum Khan", "Medicine and Neurologist", "Kalyanpur, Dhaka", "MBBS, BCS(Health), MD (Neuro Medicine)", " ", "Ibn Sina Medical College Hospital.","https://static.vecteezy.com/system/resources/previews/002/896/807/original/female-doctor-using-her-digital-tablet-free-vector.jpg"));
//        doctorList.add(new Doctors(3, "Prof. Dr. ABM Fazlur Rahman", "Orthopedic Specialist", "Farmgate, Dhaka", "MBBS(DMC), MS (Orthopedic Surgery), FICS", " ", "Al- Raji Hospital (Pvt.) Ltd.","https://familydoctor.org/wp-content/uploads/2018/02/41808433_l.jpg"));
//        doctorList.add(new Doctors(4, "Dr. Jakir Uddin", "Orthopedic Specialist", "English Road, Dhaka", "MBBS, D-Ortho, MS (Ortho)", " ", "Popular Diagnostic Centre Ltd.","https://familydoctor.org/wp-content/uploads/2018/02/41808433_l.jpg"));
//        doctorList.add(new Doctors(5, "Prof. Dr. A A Mohiuddin", "Eye Specialist (Opthalmologist)", "Malibag, Dhaka", "MBBS, FCPS", " ", "Padma Diagnostic Centre Ltd.","https://familydoctor.org/wp-content/uploads/2018/02/41808433_l.jpg"));
//        doctorList.add(new Doctors(6, "Dr. A H M Sazzad Hossain Khan", "Eye Specialist (Opthalmologist)", "Mehedibag, Chattogram", "MBBS,DCO", "", "Metro Imaging and Diagnostic Centre.","https://familydoctor.org/wp-content/uploads/2018/02/41808433_l.jpg"));
//        doctorList.add(new Doctors(7, "Dr. AEMN Jahangir Selim", "Cardiologist", "Mehedibag, Dhaka", "MBBS,BCS(Health), MD (Cardiology)", "", "Sigma Lab Limited.","https://familydoctor.org/wp-content/uploads/2018/02/41808433_l.jpg"));
//        doctorList.add(new Doctors(8, "Assoc. Prof. Dr. AF Kabir Uddin Ahmed", "Cardiologist", "Uttara, Dhaka", "MBBS,MD (Cardiology)", "", "Ibn Sina Diagnostic and Consultation Centre.","https://familydoctor.org/wp-content/uploads/2018/02/41808433_l.jpg"));
//        doctorList.add(new Doctors(9, "Prof. Dr. AKM Asifuzzaman", "Dental Surgeon", "Dhap,Rangpur", "MBBS,MD (Cardiology)", "", "Ibn Sina Diagnostic and Consultation Centre.","https://familydoctor.org/wp-content/uploads/2018/02/41808433_l.jpg"));
//        doctorList.add(new Doctors(10, "Asst. Prof. Dr. AKM. Salahuddin", "Dentist", "Nizam Road, Chattogram", "BDS, MS", "", "Core Dental","https://familydoctor.org/wp-content/uploads/2018/02/41808433_l.jpg"));

//        inboxUserDoctorsAdapter = new InboxUserDoctorsAdapter(requireActivity(),doctorList);
//        binding.inboxUserDoctorsRecycler.setAdapter(inboxUserDoctorsAdapter);


//        Get all doctors from Firebase

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null){
            currenUserId = firebaseUser.getUid();
        }

        userList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("User");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    User user = dataSnapshot.getValue(User.class);
                    if (user != null){
                        if (user.getProfession().equals("Doctor") && !user.getUserId().equals(currenUserId)){
                            userList.add(user);
                        }
                    }
                }
                inboxUserDoctorsAdapter = new InboxUserDoctorsAdapter(requireContext(), userList);
                binding.inboxUserDoctorsRecycler.setAdapter(inboxUserDoctorsAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        return binding.getRoot();
    }
}