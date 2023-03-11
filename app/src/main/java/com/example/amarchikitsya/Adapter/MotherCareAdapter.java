package com.example.amarchikitsya.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.amarchikitsya.BmiCalculationActivity;
import com.example.amarchikitsya.ChartAndCalculationActivity;
import com.example.amarchikitsya.LoginActivity;
import com.example.amarchikitsya.MotherCareDetailsActivity;
import com.example.amarchikitsya.PharmacyDetailsActivity;
import com.example.amarchikitsya.R;
import com.example.amarchikitsya.ViewHolder.MotherCareViewHolder;
import com.example.amarchikitsya.ViewHolder.PharmacyViewHolder;
import com.example.amarchikitsya.model.MotherCare;
import com.example.amarchikitsya.model.Pharmacy;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MotherCareAdapter extends RecyclerView.Adapter<MotherCareViewHolder> {
    Context context;
    List<MotherCare> motherCareList;
    FirebaseUser firebaseUser;
    public MotherCareAdapter(Context context, List<MotherCare> motherCareList) {
        this.context = context;
        this.motherCareList = motherCareList;
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    }
    @NonNull
    @Override
    public MotherCareViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.mother_care_list, parent, false);
        return new MotherCareViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MotherCareViewHolder holder, int position) {
        MotherCare motherCare = motherCareList.get(position);
        Glide.with(context).load(motherCare.getImage()).into(holder.image);
        holder.title.setText(motherCare.getTitle());
        holder.txt.setText(motherCare.getTxt());

        holder.itemView.setOnClickListener(v -> {
            Intent intent;
            if (firebaseUser == null) {
                intent = new Intent(context, LoginActivity.class);
                intent.putExtra("drawer", "yes");
            } else {
                intent = new Intent(context, MotherCareDetailsActivity.class);
                intent.putExtra("image",motherCare.getImage());
                intent.putExtra("title",motherCare.getTitle());
                intent.putExtra("txt",motherCare.getTxt());
            }
            context.startActivity(intent);

        });
    }
    @Override
    public int getItemCount() {
        return motherCareList.size();
    }

}
