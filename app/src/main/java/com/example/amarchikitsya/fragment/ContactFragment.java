package com.example.amarchikitsya.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.amarchikitsya.databinding.FragmentContactBinding;
import com.example.amarchikitsya.utils.EmailValidation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.regex.Pattern;

public class ContactFragment extends Fragment {

    FragmentContactBinding binding;
    DatabaseReference firebaseDatabase;
    FirebaseAuth firebaseAuth;
    String name, email, message,userId,contactId;
    ProgressDialog progressDialog;
    EmailValidation emailValidation;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentContactBinding.inflate(inflater);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Sending Message");
        firebaseDatabase = FirebaseDatabase.getInstance().getReference("Contact");
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        binding.contactBtn.setOnClickListener(v -> {
            emailValidation = new EmailValidation();
            name = binding.contactNameText.getText().toString();
            email = binding.contactEmailText.getText().toString();
            message = binding.contactMessage.getText().toString();
            userId = firebaseUser.getUid();
            contactId = firebaseDatabase.push().getKey();
            if(name.equals("")){
                binding.contactName.setError("Please enter your name!");
            }else if(email.equals("")){
                binding.contactEmail.setError("Please enter your email");
                
            }else if (!emailValidation.isValid(email)){
                binding.contactEmail.setError("Email address is not valid");
                
            }else if(message.equals("")){
                binding.contactMessage.setError("Please write a message to send!");
            }else{
                progressDialog.show();
                HashMap<String, Object> map = new HashMap<>();
                map.put("name",name);
                map.put("email",email);
                map.put("message",message);
                map.put("userId",userId);
                firebaseDatabase.child(contactId).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        getActivity().finish();
                        Toast.makeText(getContext(), "Your message has been sent. thanks for your message", Toast.LENGTH_SHORT).show();
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