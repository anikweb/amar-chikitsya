package com.example.amarchikitsya;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amarchikitsya.databinding.ActivityCoronaTestBinding;
import com.example.amarchikitsya.fragment.CoronaSymptomTestFragment;
import com.example.amarchikitsya.fragment.CoronaSymptomsCheckerFragment;
import com.example.amarchikitsya.fragment.DiseaseTestReportFragment;
import com.example.amarchikitsya.utils.InternetConnection;

public class CoronaTestActivity extends AppCompatActivity {
	
	ActivityCoronaTestBinding binding;
	CoronaSymptomsCheckerFragment coronaSymptomsCheckerFragment = new CoronaSymptomsCheckerFragment();
	CoronaSymptomTestFragment coronaSymptomTestFragment = new CoronaSymptomTestFragment();
	DiseaseTestReportFragment diseaseTestReportFragment = new DiseaseTestReportFragment();
	Intent intent;
	
	@SuppressLint({"SetTextI18n", "ResourceAsColor"})
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ActivityCoronaTestBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());
		checkInternet();
		intent = getIntent();
		binding.coronaToolbar.coronaTestExitBtn.setVisibility(View.GONE);
		binding.coronaToolbar.backBtn.setOnClickListener(view -> {
			super.onBackPressed();
		});
		binding.coronaToolbar.coronaQueNumber.setText("Test Covid-19 Risk");
		getSupportFragmentManager().beginTransaction().replace(R.id.corona_content, coronaSymptomsCheckerFragment).commit();
		
		if(intent.getStringExtra("activity").equals("coronaTest")){
			getSupportFragmentManager().beginTransaction().replace(R.id.corona_content, coronaSymptomTestFragment).commit();
			
		}
		
		
	}
	private void checkInternet() {
		if (!InternetConnection.checkConnection(CoronaTestActivity.this)) {
			AlertDialog.Builder builder = new AlertDialog.Builder(CoronaTestActivity.this);
			builder.setTitle("Network Error!")
					.setMessage("No Internet Connection, Please Check your Network Connection")
					.setIcon(R.drawable.ic_baseline_info_24)
					.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							finishAffinity();
							System.exit(0);
							
						}
					}).setNegativeButton("Retry", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							startActivity(new Intent(CoronaTestActivity.this, CurrentWeatherActivity.class));
							finish();
						}
					}).create().show();
		}
	}
}