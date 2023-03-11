package com.example.amarchikitsya.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.amarchikitsya.Adapter.ChatWithDoctorAdapter;
import com.example.amarchikitsya.Adapter.InboxUserAdapter;
import com.example.amarchikitsya.Adapter.InboxUserDoctorsAdapter;
import com.example.amarchikitsya.ChatWithDoctorConversationActivity;
import com.example.amarchikitsya.R;

import com.example.amarchikitsya.databinding.FragmentInboxUserBinding;
import com.example.amarchikitsya.model.ChatsWithDoctor;
import com.example.amarchikitsya.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class InboxUserFragment extends Fragment {

    FragmentInboxUserBinding binding;
    List<User> userList;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    String currentUserId;
    InboxUserAdapter inboxUserAdapter;

    public InboxUserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInboxUserBinding.inflate(inflater);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser != null) {
            currentUserId = firebaseUser.getUid();
        }

        userList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("User");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    User user = dataSnapshot.getValue(User.class);
                    if (user != null) {
                        if (!user.getProfession().equals("Doctor") && !user.getUserId().equals(currentUserId)) {
                            userList.add(user);
                        }
                    }
                }

                inboxUserAdapter = new InboxUserAdapter(requireContext(), userList);
                binding.inboxUserRecycler.setAdapter(inboxUserAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return binding.getRoot();
    }
}