package com.example.amarchikitsya.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.amarchikitsya.R;
import com.example.amarchikitsya.databinding.FragmentBookAppointmentBinding;


public class BookAppointmentFragment extends Fragment {
	FragmentBookAppointmentBinding binding;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		binding = FragmentBookAppointmentBinding.inflate(inflater);
		
		
		
		return binding.getRoot();
	}
}