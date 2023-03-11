package com.example.amarchikitsya.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amarchikitsya.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class InboxDoctorUserViewHolder extends RecyclerView.ViewHolder {
    public ImageView more;
    public CircleImageView img;
    public TextView name,specialization;
    public InboxDoctorUserViewHolder(@NonNull View itemView) {
        super(itemView);
        more = itemView.findViewById(R.id.inbox_user_doctors_more);
        img = itemView.findViewById(R.id.inbox_user_doctors_img);
        name = itemView.findViewById(R.id.inbox_user_doctors_name);
        specialization = itemView.findViewById(R.id.inbox_user_doctors_specialization);
    }
}
