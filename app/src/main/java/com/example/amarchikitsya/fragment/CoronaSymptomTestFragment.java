package com.example.amarchikitsya.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.amarchikitsya.R;
import com.example.amarchikitsya.databinding.FragmentCoronaSymptomTestBinding;


public class CoronaSymptomTestFragment extends Fragment {
	
	FragmentCoronaSymptomTestBinding binding;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		binding = FragmentCoronaSymptomTestBinding.inflate(inflater);
		
		
		
		return binding.getRoot();
	}
}