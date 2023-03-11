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
import com.example.amarchikitsya.DoctorDetailsActivity;
import com.example.amarchikitsya.LoginActivity;
import com.example.amarchikitsya.R;
import com.example.amarchikitsya.ViewHolder.DiagnosticViewHolder;
import com.example.amarchikitsya.ViewHolder.DoctorViewHolder;
import com.example.amarchikitsya.model.Diagnostics;
import com.example.amarchikitsya.model.Doctors;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class DiagnosticAdapter extends RecyclerView.Adapter<DiagnosticViewHolder> {
    Context context;
    List<Diagnostics> diagnosticsList;
    FirebaseUser firebaseUser;
    public DiagnosticAdapter(Context context,  List<Diagnostics> diagnosticsList) {
        this.context = context;
        this.diagnosticsList = diagnosticsList;
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    }
    @NonNull
    @Override
    public DiagnosticViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.diagnostic_list, parent, false);
        return new DiagnosticViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull DiagnosticViewHolder holder, int position) {
        Diagnostics diagnostics = diagnosticsList.get(position);
        holder.name.setText(diagnostics.getName());
        holder.address.setText(diagnostics.getAddress());

        holder.itemView.setOnClickListener(v -> {
            Intent intent;
            if (firebaseUser == null) {
                intent = new Intent(context, LoginActivity.class);
                intent.putExtra("drawer", "yes");
            } else {
                intent = new Intent(context, DiagnosticDetailsActivity.class);
                intent.putExtra("name",diagnostics.getName());
                intent.putExtra("address",diagnostics.getAddress());
                intent.putExtra("mobile_number",diagnostics.getMobile());
                intent.putExtra("opening_hour",diagnostics.getOpening_hour());
                intent.putExtra("closing_hour",diagnostics.getClosing_hour());
                intent.putExtra("day_off",diagnostics.getDay_off());
            }
            context.startActivity(intent);
        });
    }
    @Override
    public int getItemCount() {
        return diagnosticsList.size();
    }

}
