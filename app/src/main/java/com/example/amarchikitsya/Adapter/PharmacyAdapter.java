package com.example.amarchikitsya.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amarchikitsya.DiagnosticDetailsActivity;
import com.example.amarchikitsya.DoctorDetailsActivity;
import com.example.amarchikitsya.LoginActivity;
import com.example.amarchikitsya.PharmacyDetailsActivity;
import com.example.amarchikitsya.R;
import com.example.amarchikitsya.ViewHolder.DoctorViewHolder;
import com.example.amarchikitsya.ViewHolder.PharmacyViewHolder;
import com.example.amarchikitsya.model.Doctors;
import com.example.amarchikitsya.model.Pharmacy;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class PharmacyAdapter extends RecyclerView.Adapter<PharmacyViewHolder> {
    Context context;
    List<Pharmacy> pharmacyList;
    FirebaseUser firebaseUser;

    public PharmacyAdapter(Context context, List<Pharmacy> pharmacyList) {
        this.context = context;
        this.pharmacyList = pharmacyList;
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    }
    @NonNull
    @Override
    public PharmacyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.pharmacy_list, parent, false);
        return new PharmacyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull PharmacyViewHolder holder, int position) {
        Pharmacy pharmacy = pharmacyList.get(position);
        holder.name.setText(pharmacy.getName());
        holder.address.setText(pharmacy.getAddress());
        holder.itemView.setOnClickListener(v -> {

            Intent intent;
            if (firebaseUser == null) {
                intent = new Intent(context, LoginActivity.class);
                intent.putExtra("drawer", "yes");
            } else {
                intent = new Intent(context, PharmacyDetailsActivity.class);
                intent.putExtra("name",pharmacy.getName());
                intent.putExtra("address",pharmacy.getAddress());
                intent.putExtra("city",pharmacy.getCity());
                intent.putExtra("mobileNumber",pharmacy.getNumber());
            }
            context.startActivity(intent);

        });
    }
    @Override
    public int getItemCount() {
        return pharmacyList.size();
    }

}
