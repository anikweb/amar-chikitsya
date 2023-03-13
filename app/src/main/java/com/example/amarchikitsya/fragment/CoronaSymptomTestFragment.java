package com.example.amarchikitsya.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
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
		
		binding.coronaQueAnsYes.setOnClickListener(view -> {
			binding.coronaQueAnsYesCheck.setChecked(true);
			binding.coronaQueAnsNoCheck.setChecked(false);
			binding.coronaQueAnsYesConst.setBackgroundResource(R.drawable.que_strock_selected);
			binding.coronaQueAnsNoConst.setBackgroundResource(R.drawable.que_strock);
			binding.coronaQueAnsYesText.setTextColor(ContextCompat.getColor(getActivity(),R.color.white));
			binding.coronaQueNoText.setTextColor(ContextCompat.getColor(getActivity(),R.color.black));
		});
		binding.coronaQueAnsNo.setOnClickListener(view -> {
			binding.coronaQueAnsNoCheck.setChecked(true);
			binding.coronaQueAnsYesCheck.setChecked(false);
			binding.coronaQueAnsNoConst.setBackgroundResource(R.drawable.que_strock_selected);
			binding.coronaQueAnsYesConst.setBackgroundResource(R.drawable.que_strock);
			binding.coronaQueNoText.setTextColor(ContextCompat.getColor(getActivity(),R.color.white));
			binding.coronaQueAnsYesText.setTextColor(ContextCompat.getColor(getActivity(),R.color.black));
		});
		
		
		return binding.getRoot();
	}
}