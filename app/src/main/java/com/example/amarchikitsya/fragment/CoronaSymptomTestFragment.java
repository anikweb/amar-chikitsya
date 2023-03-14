package com.example.amarchikitsya.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.amarchikitsya.CoronaTestActivity;
import com.example.amarchikitsya.R;
import com.example.amarchikitsya.databinding.FragmentCoronaSymptomTestBinding;
import com.example.amarchikitsya.model.CoronaSymptoms;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class CoronaSymptomTestFragment extends Fragment {
	
	FragmentCoronaSymptomTestBinding binding;
	List<CoronaSymptoms> coronaSymptoms;
	int index = 0;
	float marks = 0,
		Que = 1;
	String takenAns;
	float resultFinal;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		binding = FragmentCoronaSymptomTestBinding.inflate(inflater);
		
		binding.coronaQueAnsYes.setOnClickListener(view -> {
			binding.coronaQueAnsYes.setBackgroundResource(R.drawable.que_strock_selected);
			binding.coronaQueAnsNo.setBackgroundResource(R.drawable.que_strock);
		});
		binding.coronaQueAnsNo.setOnClickListener(view -> {
			binding.coronaQueAnsNo.setBackgroundResource(R.drawable.que_strock_selected);
			binding.coronaQueAnsYes.setBackgroundResource(R.drawable.que_strock);
		});
		coronaSymptoms = new ArrayList<>();
		coronaSymptoms.add(new CoronaSymptoms("Fever or chills"));
		coronaSymptoms.add(new CoronaSymptoms("Cough"));
		coronaSymptoms.add(new CoronaSymptoms("Shortness of breath or difficulty breathing"));
		coronaSymptoms.add(new CoronaSymptoms("Fatigue"));
		coronaSymptoms.add(new CoronaSymptoms("Muscle or body aches"));
		coronaSymptoms.add(new CoronaSymptoms("Headache"));
		coronaSymptoms.add(new CoronaSymptoms("Loss of taste or smell"));
		coronaSymptoms.add(new CoronaSymptoms("Sore throat"));
		coronaSymptoms.add(new CoronaSymptoms("A cold."));
		coronaSymptoms.add(new CoronaSymptoms("Nausea or vomiting"));
		coronaSymptoms.add(new CoronaSymptoms("Diarrhea"));
		setUpQuestion(index,resultFinal);
		
		binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup radioGroup, int i) {
				RadioButton givenAns = getActivity().findViewById(i);
				takenAns = givenAns.getText().toString();
			}
		});
		binding.nextBtn.setOnClickListener(view -> {
			if(coronaSymptoms.size() > index && index < Que){
				if(takenAns.equals("Yes")){
					marks +=1;
				}
				index++;
				Que++;
				float result =  (marks/coronaSymptoms.size()) * 100f;
				DecimalFormat decimalFormat = new DecimalFormat("#.##");
				resultFinal = Float.valueOf(decimalFormat.format(result));
				
				setUpQuestion(index,resultFinal);
			}else{
				Intent intent = new Intent(getActivity(), CoronaTestActivity.class);
				intent.putExtra("activity","coronaTestReport");
				intent.putExtra("testResult",resultFinal);
				startActivity(intent);
			}
			
		});
		
		
		
		return binding.getRoot();
	}
	
	private void setUpQuestion(int index,float resultFinal) {
		if(coronaSymptoms.size()>index){
			binding.coronaQueNo.setText(index+1 + " of "+ coronaSymptoms.size());
			binding.coronaQue.setText(coronaSymptoms.get(index).getQue());
		}else{
			Intent intent = new Intent(getActivity(), CoronaTestActivity.class);
			intent.putExtra("activity","coronaTestReport");
			intent.putExtra("testResult",resultFinal);
			startActivity(intent);
		}
		
	}
	
}