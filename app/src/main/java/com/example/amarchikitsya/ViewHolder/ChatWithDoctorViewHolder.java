package com.example.amarchikitsya.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amarchikitsya.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatWithDoctorViewHolder extends RecyclerView.ViewHolder {
    public TextView chatText,date;
    public CircleImageView profileImage;
    public ChatWithDoctorViewHolder(@NonNull View itemView) {
        super(itemView);
        profileImage = itemView.findViewById(R.id.receiver_img);
        chatText = itemView.findViewById(R.id.chat_msg);
        date = itemView.findViewById(R.id.chat_date);
    }
}
