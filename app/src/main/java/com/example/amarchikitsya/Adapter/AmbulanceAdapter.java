package com.example.amarchikitsya.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amarchikitsya.AmbulanceDetailsActivity;
import com.example.amarchikitsya.DiagnosticDetailsActivity;
import com.example.amarchikitsya.LoginActivity;
import com.example.amarchikitsya.MotherCareDetailsActivity;
import com.example.amarchikitsya.R;
import com.example.amarchikitsya.ViewHolder.AmbulanceViewHolder;
import com.example.amarchikitsya.ViewHolder.DiagnosticViewHolder;
import com.example.amarchikitsya.model.Ambulance;
import com.example.amarchikitsya.model.Diagnostics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class AmbulanceAdapter extends RecyclerView.Adapter<AmbulanceViewHolder> {
    Context context;
    List<Ambulance> ambulanceList;
    FirebaseUser firebaseUser;
    public AmbulanceAdapter(Context context, List<Ambulance> ambulanceList) {
        this.context = context;
        this.ambulanceList = ambulanceList;
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    }
    @NonNull
    @Override
    public AmbulanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ambulance_list, parent, false);
        return new AmbulanceViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull AmbulanceViewHolder holder, int position) {
        Ambulance ambulance = ambulanceList.get(position);
        holder.name.setText(ambulance.getName());
        holder.address.setText(ambulance.getAddress());

        holder.itemView.setOnClickListener(v -> {
            Intent intent;
            if (firebaseUser == null) {
                intent = new Intent(context, LoginActivity.class);
                intent.putExtra("drawer", "yes");
            } else {
                intent = new Intent(context, AmbulanceDetailsActivity.class);
                intent.putExtra("name",ambulance.getName());
                intent.putExtra("address",ambulance.getAddress());
                intent.putExtra("mobile_number",ambulance.getMobileNumber());
            }
            context.startActivity(intent);
        });
    }
    @Override
    public int getItemCount() {
        return ambulanceList.size();
    }

}
