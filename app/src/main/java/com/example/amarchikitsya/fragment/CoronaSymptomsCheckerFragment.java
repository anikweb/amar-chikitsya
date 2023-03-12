package com.example.amarchikitsya.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.amarchikitsya.R;
import com.example.amarchikitsya.databinding.FragmentCoronaSymptomsCheckerBinding;

public class CoronaSymptomsCheckerFragment extends Fragment {
	FragmentCoronaSymptomsCheckerBinding binding;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		binding = FragmentCoronaSymptomsCheckerBinding.inflate(inflater);
		
		binding.startBtn.setOnClickListener(view -> {
			Toast.makeText(getContext(), "Hello", Toast.LENGTH_SHORT).show();
		});
		
		return binding.getRoot();
	}
}