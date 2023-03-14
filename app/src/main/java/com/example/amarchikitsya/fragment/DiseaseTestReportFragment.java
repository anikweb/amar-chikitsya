package com.example.amarchikitsya.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.amarchikitsya.R;
import com.example.amarchikitsya.databinding.FragmentDiseaseTestReportBinding;


public class DiseaseTestReportFragment extends Fragment {
	
	FragmentDiseaseTestReportBinding binding;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		binding = FragmentDiseaseTestReportBinding.inflate(inflater);
		
		binding.resultResult.setText("You have a 35 percent chance of contracting Covid-19");
		
		binding.backToHomeBtn.setOnClickListener(view -> {
			getActivity().finish();
		});
		
		return binding.getRoot();
	}
}