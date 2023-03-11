package com.example.amarchikitsya.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.amarchikitsya.ChatWithDoctorConversationActivity;
import com.example.amarchikitsya.DoctorDetailsActivity;
import com.example.amarchikitsya.R;
import com.example.amarchikitsya.ViewHolder.InboxDoctorUserViewHolder;
import com.example.amarchikitsya.model.DoctorProfessionalInfo;
import com.example.amarchikitsya.model.Doctors;
import com.example.amarchikitsya.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class InboxUserDoctorsAdapter extends RecyclerView.Adapter<InboxDoctorUserViewHolder> {
    Context context;
    List<User> userList;
    DatabaseReference databaseReference;


    public InboxUserDoctorsAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public InboxDoctorUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InboxDoctorUserViewHolder(LayoutInflater.from(context).inflate(R.layout.item_inbox_user_doctors, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull InboxDoctorUserViewHolder holder, int position) {
        User user = userList.get(position);

        holder.name.setText(user.getName());

        databaseReference = FirebaseDatabase.getInstance().getReference("DoctorProfessionalInfo");
        databaseReference.child(user.getUserId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DoctorProfessionalInfo doctorProfessionalInfo = snapshot.getValue(DoctorProfessionalInfo.class);
                if (doctorProfessionalInfo != null){
                    holder.specialization.setText(doctorProfessionalInfo.getSpecialization());
                    holder.itemView.setOnClickListener(v -> {
                        gotoConversation(user);
                    });
                    holder.more.setOnClickListener(v -> {
                        showBottomSheet(context, user,doctorProfessionalInfo);
                    });
                    holder.itemView.setOnLongClickListener(v -> {
                        showBottomSheet(context, user,doctorProfessionalInfo);
                        return true;
                    });
                    holder.name.setText(user.getName());

                    if (!user.getProfileImage().equals("")) {

                        Glide.with(context).load(user.getProfileImage()).placeholder(R.drawable.loading).into(holder.img);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }




    @Override
    public int getItemCount() {
        return userList.size();
    }
    private void showBottomSheet(Context context, User user,DoctorProfessionalInfo doctorProfessionalInfo) {
        TextView name, specialization, msg,profile;
        CircleImageView image;
        Dialog dialog = new Dialog(context);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.inbox_doctor_user_bottom_sheet);

        image = dialog.findViewById(R.id.inbox_user_doctors_img_bottom_sheet);
        name = dialog.findViewById(R.id.inbox_user_doctors_name);
        specialization = dialog.findViewById(R.id.inbox_user_doctors_specialization);
        msg = dialog.findViewById(R.id.inbox_doctor_user_bottom_sheet_msg);
        profile = dialog.findViewById(R.id.inbox_doctor_user_bottom_sheet_profile);

        name.setText(user.getName());
        specialization.setText(doctorProfessionalInfo.getSpecialization());
        if (!user.getProfileImage().equals("")) {
            Glide.with(context).load(user.getProfileImage()).into(image);
        }

        dialog.create();
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.getWindow().setGravity(Gravity.BOTTOM);

        msg.setOnClickListener(v -> {
            gotoConversation(user);
            dialog.cancel();
        });
        profile.setOnClickListener(v -> {
            Intent intent = new Intent(context, DoctorDetailsActivity.class);
            intent.putExtra("userId", user.getUserId());
            context.startActivity(intent);
            dialog.cancel();
        });
    }
    private void gotoConversation(User user) {
        Intent intent = new Intent(context, ChatWithDoctorConversationActivity.class);
        intent.putExtra("userId",user.getUserId());
        context.startActivity(intent);
    }

}
