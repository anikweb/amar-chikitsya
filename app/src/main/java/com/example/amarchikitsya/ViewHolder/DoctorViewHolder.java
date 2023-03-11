package com.example.amarchikitsya.ViewHolder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amarchikitsya.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorViewHolder extends RecyclerView.ViewHolder {
    public CircleImageView image;
    public AppCompatTextView name,specialization;
    public ConstraintLayout layout;

    public DoctorViewHolder(@NonNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.doctor_image);
        name = itemView.findViewById(R.id.doctor_name);
        specialization = itemView.findViewById(R.id.doctor_specialization);
        layout = itemView.findViewById(R.id.doctor_con);
    }
}
