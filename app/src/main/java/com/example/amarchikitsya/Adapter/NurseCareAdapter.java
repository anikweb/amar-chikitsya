package com.example.amarchikitsya.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amarchikitsya.LoginActivity;
import com.example.amarchikitsya.NurseCareDetailsActivity;
import com.example.amarchikitsya.PharmacyDetailsActivity;
import com.example.amarchikitsya.R;
import com.example.amarchikitsya.ViewHolder.NurseCareViewHolder;
import com.example.amarchikitsya.model.NurseCare;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class NurseCareAdapter extends RecyclerView.Adapter<NurseCareViewHolder> {
    Context context;
    List<NurseCare> nurseCareList;
    FirebaseUser firebaseUser;
    public NurseCareAdapter(Context context, List<NurseCare> nurseCareList) {
        this.context = context;
        this.nurseCareList = nurseCareList;
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    }
    @NonNull
    @Override
    public NurseCareViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.nurse_care_list, parent, false);
        return new NurseCareViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull NurseCareViewHolder holder, int position) {
        NurseCare nurseCare = nurseCareList.get(position);
        holder.name.setText(nurseCare.getName());
        holder.address.setText(nurseCare.getAddress());

        holder.itemView.setOnClickListener(v -> {

            Intent intent;
            if (firebaseUser == null) {
                intent = new Intent(context, LoginActivity.class);
                intent.putExtra("drawer", "yes");
            } else {
                intent = new Intent(context, NurseCareDetailsActivity.class);
                intent.putExtra("name",nurseCare.getName());
                intent.putExtra("address",nurseCare.getAddress());
                intent.putExtra("hospital",nurseCare.getNursingHome());
                intent.putExtra("mobileNumber",nurseCare.getMobileNumber());
                intent.putExtra("experience",nurseCare.getExperience());
                intent.putExtra("gender",nurseCare.getGender());
            }
            context.startActivity(intent);
        });
    }
    @Override
    public int getItemCount() {
        return nurseCareList.size();
    }

}
