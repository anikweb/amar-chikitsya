package com.example.amarchikitsya.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.amarchikitsya.R;
import com.example.amarchikitsya.ViewHolder.ChatWithDoctorViewHolder;
import com.example.amarchikitsya.model.ChatsWithDoctor;
import com.example.amarchikitsya.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ChatWithDoctorAdapter extends RecyclerView.Adapter<ChatWithDoctorViewHolder> {
    Context context;
    List<ChatsWithDoctor> chatsWithDoctorList;
    FirebaseUser firebaseUser;
    String currentUserId;
    DatabaseReference databaseReference;

    public ChatWithDoctorAdapter(Context context, List<ChatsWithDoctor> chatsWithDoctorList) {
        this.context = context;
        this.chatsWithDoctorList = chatsWithDoctorList;

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            currentUserId = firebaseUser.getUid();
        }
        databaseReference = FirebaseDatabase.getInstance().getReference("User");
    }
    final int RIGHT_CHAT = 1;
    final int LEFT_CHAT = 2;
    int chatSide;

    @NonNull
    @Override
    public ChatWithDoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        chatSide = viewType;
        if (viewType == RIGHT_CHAT) {
            view = LayoutInflater.from(context).inflate(R.layout.right_chat, parent, false);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.left_chat, parent, false);
        }
        return new ChatWithDoctorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatWithDoctorViewHolder holder, int position) {
        ChatsWithDoctor chatsWithDoctor = chatsWithDoctorList.get(position);
        if (chatsWithDoctor != null) {
            holder.chatText.setText(chatsWithDoctor.getMsg());
            Date date = new Date(chatsWithDoctor.getTimestamp());
            DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
            holder.date.setText(dateFormat.format(date));
            databaseReference.child(chatsWithDoctor.getSender_id()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User user = snapshot.getValue(User.class);
                    if (user != null) {
                        if (!user.getProfileImage().equals("")) {
                           Picasso.get().load(user.getProfileImage()).placeholder(R.drawable.loading).into(holder.profileImage);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return chatsWithDoctorList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (chatsWithDoctorList.get(position).getSender_id().equals(currentUserId)) {
            return RIGHT_CHAT;
        } else {
            return LEFT_CHAT;
        }

    }
}
