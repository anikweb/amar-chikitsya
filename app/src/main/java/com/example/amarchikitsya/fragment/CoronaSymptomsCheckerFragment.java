package com.example.amarchikitsya.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.amarchikitsya.CoronaTestActivity;
import com.example.amarchikitsya.R;
import com.example.amarchikitsya.databinding.FragmentCoronaSymptomsCheckerBinding;

public class CoronaSymptomsCheckerFragment extends Fragment {
	FragmentCoronaSymptomsCheckerBinding binding;
	ProgressDialog progressDialog;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		binding = FragmentCoronaSymptomsCheckerBinding.inflate(inflater);
		progressDialog = new ProgressDialog(getContext());
		progressDialog.setMessage("Your test being ready...");
		
		binding.startBtn.setOnClickListener(view -> {
			progressDialog.show();
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					progressDialog.dismiss();
					Intent intent = new Intent(getContext(), CoronaTestActivity.class);
					intent.putExtra("activity","coronaTest");
					startActivity(intent);
				}
			}, 1200);
		
		});
		
		return binding.getRoot();
	}
}