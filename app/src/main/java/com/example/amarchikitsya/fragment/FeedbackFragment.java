package com.example.amarchikitsya.fragment;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.amarchikitsya.databinding.FragmentFeedbackBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class FeedbackFragment extends Fragment {

    FragmentFeedbackBinding binding;
    FirebaseAuth firebaseAuth;
    DatabaseReference firebaseDatabase;
    String name, email, message,userId;
    ProgressDialog progressDialog;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFeedbackBinding.inflate(inflater);


        firebaseDatabase = FirebaseDatabase.getInstance().getReference("Feedback");
        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Feedback Sending...");


        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        userId = firebaseUser.getUid();
        name = firebaseUser.getDisplayName();
        email = firebaseUser.getEmail();
        String feedbackId = firebaseDatabase.push().getKey();


        binding.feedbackBtn.setOnClickListener(v -> {
            message = binding.feedbackMessageText.getText().toString();
            if(message.equals("")){
                binding.feedbackMessageText.setError("You can not send empty message!");
                
            }else {
                progressDialog.show();
                binding.errorText.setVisibility(View.GONE);
                HashMap<String, Object> map = new HashMap<>();
                map.put("userId",userId);
                map.put("name",name);
                map.put("email",email);
                map.put("message",message);
                firebaseDatabase.child(feedbackId).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "Your feedback sent successful", Toast.LENGTH_SHORT).show();
                        getActivity().finishAndRemoveTask();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }

                });
            }

        });

        return binding.getRoot();
    }
}