package com.example.amarchikitsya.ViewHolder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amarchikitsya.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class NurseCareViewHolder extends RecyclerView.ViewHolder {

    public AppCompatTextView name,address;
    public ConstraintLayout layout;

    public NurseCareViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.nurse_name);
        address = itemView.findViewById(R.id.nurse_address);

    }
}
