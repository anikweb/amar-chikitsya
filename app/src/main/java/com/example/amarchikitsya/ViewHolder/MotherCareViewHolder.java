package com.example.amarchikitsya.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amarchikitsya.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class MotherCareViewHolder extends RecyclerView.ViewHolder {
    public ImageView image;
    public TextView title, txt;

    public MotherCareViewHolder(@NonNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.mother_care_image);
        title = itemView.findViewById(R.id.mother_care_title);
        txt = itemView.findViewById(R.id.mother_care_txt);
    }
}
