package com.example.amarchikitsya.Adapter;

import static com.example.amarchikitsya.Const.WebsiteImageBaseUrl;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.amarchikitsya.DoctorDetails2Activity;
import com.example.amarchikitsya.DoctorDetailsActivity;
import com.example.amarchikitsya.LoginActivity;
import com.example.amarchikitsya.PharmacyDetailsActivity;
import com.example.amarchikitsya.R;
import com.example.amarchikitsya.ViewHolder.DoctorViewHolder;
import com.example.amarchikitsya.model.Doctors;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorViewHolder> {
    Context context;
    List<Doctors> doctorsList;
    FirebaseUser firebaseUser;
    public DoctorAdapter(Context context, List<Doctors> doctorsList) {
        this.context = context;
        this.doctorsList = doctorsList;
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    }
    @NonNull
    @Override
    public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.doctor_list, parent, false);
        return new DoctorViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull DoctorViewHolder holder, int position) {
        Doctors doctor = doctorsList.get(position);
        holder.name.setText(doctor.getName());
        holder.specialization.setText(doctor.getSpecialized());
        if(!doctor.getPhoto().equals("")){
            Picasso.get().load(doctor.getPhoto()).placeholder(R.drawable.loading).into(holder.image);
        }
        holder.itemView.setOnClickListener(v -> {
            Intent intent;
            if (firebaseUser == null) {
                intent = new Intent(context, LoginActivity.class);
                intent.putExtra("drawer", "yes");
            } else {
                intent = new Intent(context, DoctorDetails2Activity.class);
                intent.putExtra("name",doctor.getName());
                intent.putExtra("specialized",doctor.getSpecialized());
                intent.putExtra("address",doctor.getAddress());
                intent.putExtra("qualification",doctor.getQualification());
                intent.putExtra("mobile",doctor.getMobile());
                intent.putExtra("chamber",doctor.getChamber());
                intent.putExtra("img",doctor.getPhoto());
            }
            context.startActivity(intent);
        });
    }
    @Override
    public int getItemCount() {
        return doctorsList.size();
    }

}
