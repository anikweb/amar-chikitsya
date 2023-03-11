package com.example.amarchikitsya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.amarchikitsya.Adapter.ChatWithDoctorAdapter;
import com.example.amarchikitsya.databinding.ActivityChatWithDoctorConversationBinding;
import com.example.amarchikitsya.model.ChatsWithDoctor;
import com.example.amarchikitsya.model.DoctorProfessionalInfo;
import com.example.amarchikitsya.model.User;
import com.example.amarchikitsya.utils.InternetConnection;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatWithDoctorConversationActivity extends AppCompatActivity {

    ActivityChatWithDoctorConversationBinding binding;
    Intent intent;
    String userId, myid, msgTxt, chatId;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference1, databaseReference2, chatDatabaseReference;
    List<ChatsWithDoctor> chatsWithDoctorList;
    ChatWithDoctorAdapter chatWithDoctorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityChatWithDoctorConversationBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        intent = getIntent();
        chatsWithDoctorList = new ArrayList<>();

        if (!InternetConnection.checkConnection(ChatWithDoctorConversationActivity.this)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ChatWithDoctorConversationActivity.this);
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
                            startActivity(new Intent(ChatWithDoctorConversationActivity.this, ChatWithDoctorConversationActivity.class));
                            finish();
                        }
                    }).create().show();
        }

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            myid = firebaseUser.getUid();
        }
        userId = intent.getStringExtra("userId");
        databaseReference1 = FirebaseDatabase.getInstance().getReference("User");
        getReciverInfo(userId);




        binding.msgText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.toString().trim().length() == 0) {
                    binding.sendTextMsg.setVisibility(View.GONE);
                    binding.sendEmoje.setVisibility(View.VISIBLE);
                } else {
                    binding.sendTextMsg.setVisibility(View.VISIBLE);
                    binding.sendEmoje.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.sendTextMsg.setVisibility(View.VISIBLE);
                binding.sendEmoje.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() == 0) {
                    binding.sendTextMsg.setVisibility(View.GONE);
                    binding.sendEmoje.setVisibility(View.VISIBLE);
                } else {
                    binding.sendTextMsg.setVisibility(View.VISIBLE);
                    binding.sendEmoje.setVisibility(View.GONE);
                }

            }
        });


        chatDatabaseReference = FirebaseDatabase.getInstance().getReference("ChatsWithDoctor");
        binding.sendTextMsg.setOnClickListener(v -> {
            msgTxt = binding.msgText.getText().toString();
            if (!msgTxt.equals("")) {
                sendMessage(msgTxt,false);
            }
        });
        binding.sendEmoje.setOnClickListener(v -> {
            String msg = "\uD83D\uDC4D";
            sendMessage(msg,true);
        });


        LinearLayoutManager layoutManager = new LinearLayoutManager(ChatWithDoctorConversationActivity.this);
        layoutManager.setStackFromEnd(true);
        binding.chatRecycler.setLayoutManager(layoutManager);
        chatDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatsWithDoctorList.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    ChatsWithDoctor chatsWithDoctor = dataSnapshot.getValue(ChatsWithDoctor.class);
                    if (chatsWithDoctor != null){
                        if (chatsWithDoctor.getSender_id().equals(myid) && chatsWithDoctor.getReceiver_id().equals(userId) || chatsWithDoctor.getSender_id().equals(userId) && chatsWithDoctor.getReceiver_id().equals(myid)){
                            chatsWithDoctorList.add(chatsWithDoctor);
                        }
                    }
                }

                chatWithDoctorAdapter = new ChatWithDoctorAdapter(ChatWithDoctorConversationActivity.this, chatsWithDoctorList);
                binding.chatRecycler.setAdapter(chatWithDoctorAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.backBtn.setOnClickListener(v -> {
            super.onBackPressed();
        });
    }
    private void getReciverInfo(String userId) {
        databaseReference1.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (user != null) {
                    if (!user.getName().equals("")) {
                        if (user.getName().length() < 16) {
                            binding.inboxUserDoctorsName.setText(user.getName());
                        } else {
                            binding.inboxUserDoctorsName.setText(user.getName().substring(0, 15) + "...");
                        }
                        if(!user.getProfession().equals("")){
                            binding.inboxUserDoctorsSpecialization.setText(user.getProfession());
                            if(user.getProfession().equals("Doctor")){
                                databaseReference2 = FirebaseDatabase.getInstance().getReference("DoctorProfessionalInfo");
                                getReciverProfessionalInfo(userId);
                            }
                        }else{
                            binding.inboxUserDoctorsSpecialization.setText("undefined");
                        }
                    }
                    if (!user.getProfileImage().equals("")) {
                        if(!ChatWithDoctorConversationActivity.this.isDestroyed()){
                            Glide.with(ChatWithDoctorConversationActivity.this).load(user.getProfileImage()).placeholder(R.drawable.loading).into(binding.inboxUserDoctorsImg);
                        }

                    }else {
                        binding.inboxUserDoctorsImg.setImageResource(R.drawable.ic_baseline_person_24);
                    }
                    binding.inboxUserDoctorsImg.setOnClickListener(v -> {
                        Intent intent = new Intent(ChatWithDoctorConversationActivity.this,DoctorDetailsActivity.class);
                        intent.putExtra("userId",user.getUserId());
                        startActivity(intent);
                    });
                    binding.inboxUserDoctorsName.setOnClickListener(v -> {
                        Intent intent = new Intent(ChatWithDoctorConversationActivity.this,DoctorDetailsActivity.class);
                        intent.putExtra("userId",user.getUserId());
                        startActivity(intent);
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getReciverProfessionalInfo(String userId) {
        databaseReference2.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DoctorProfessionalInfo doctorProfessionalInfo = snapshot.getValue(DoctorProfessionalInfo.class);
                if (doctorProfessionalInfo != null) {
                    if (!doctorProfessionalInfo.getSpecialization().equals("")) {
                        if (doctorProfessionalInfo.getSpecialization().length() < 22) {
                            binding.inboxUserDoctorsSpecialization.setText(doctorProfessionalInfo.getSpecialization().toString());
                        } else {
                            binding.inboxUserDoctorsSpecialization.setText(doctorProfessionalInfo.getSpecialization().substring(0, 21) + "...");
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void sendMessage(String msg, boolean isEmoji) {
        chatId = chatDatabaseReference.push().getKey();
        HashMap<String, Object> chatMap = new HashMap<>();
        chatMap.put("chat_id", chatId);
        chatMap.put("sender_id", myid);
        chatMap.put("receiver_id", userId);
        chatMap.put("msg", msg);
        chatMap.put("timestamp", System.currentTimeMillis());
        chatDatabaseReference.child(chatId).setValue(chatMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(isEmoji == false){
                    if (task.isSuccessful()) {
                        binding.msgText.setText("");
                    }
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ChatWithDoctorConversationActivity.this, "" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}