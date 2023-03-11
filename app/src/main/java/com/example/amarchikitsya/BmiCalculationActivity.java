package com.example.amarchikitsya;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.amarchikitsya.databinding.ActivityBmiCalculationBinding;
import com.example.amarchikitsya.utils.InternetConnection;

import java.text.DecimalFormat;

public class BmiCalculationActivity extends AppCompatActivity {

    ActivityBmiCalculationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityBmiCalculationBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        if (!InternetConnection.checkConnection(BmiCalculationActivity.this)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(BmiCalculationActivity.this);
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
                            startActivity(new Intent(BmiCalculationActivity.this, BmiCalculationActivity.class));
                            finish();
                        }
                    }).create().show();
        }

        binding.calculateBtn.setOnClickListener(v -> {
            if (binding.inputFeet.getText().toString().equals("")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(BmiCalculationActivity.this);
                builder.setTitle("Warning")
                        .setMessage("Please enter your height")
                        .setPositiveButton("ok", null)
                        .create()
                        .show();
            } else if (binding.inputWeight.getText().toString().equals("")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(BmiCalculationActivity.this);
                builder.setTitle("Warning")
                        .setMessage("Please enter your weight")
                        .setPositiveButton("ok", null)
                        .create()
                        .show();
            } else {
                double totalInch;
                if (binding.inputInch.getText().toString().equals("")) {
                    totalInch = Long.parseLong(binding.inputFeet.getText().toString()) * 12;
                }else {
                    double feetToInch = Double.parseDouble(binding.inputFeet.getText().toString()) * 12;
                    double Inch = Double.parseDouble(binding.inputInch.getText().toString());
                    totalInch = feetToInch+Inch;
                }
                double meter = totalInch * 0.0254;
                double weight = Double.parseDouble(binding.inputWeight.getText().toString());
                double getBMI1 = weight/meter;
                double getBMI = getBMI1/meter;
                getBMI = Double.parseDouble(new DecimalFormat("##.##").format(getBMI));
                binding.bmiTxt.setText("Your BMI: "+getBMI);
                checkBMI(getBMI);
                View view = this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(BmiCalculationActivity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

            }
        });
        binding.toolbar.backBtn.setOnClickListener(v -> {
            super.onBackPressed();
        });
        binding.toolbar.pageTitle.setText("Body Mass Index (BMI) Calculator");

    }

    private void checkBMI(double getBMI) {
        if(getBMI < 18.5){
            binding.bmiUnderweight.setTextSize(26);
            binding.bmiNormal.setTextSize(18);
            binding.bmiOverweight.setTextSize(18);
            binding.bmiObese.setTextSize(18);
            binding.calculatorImg.setVisibility(View.VISIBLE);
            binding.weightCatResult.setVisibility(View.VISIBLE);
            binding.weightCatResult.setText("Weight Status: Underweight");
            binding.healthRiskResult.setVisibility(View.VISIBLE);
            binding.healthRiskResult.setText("Health Risk: Increased");
            binding.healthImg.setVisibility(View.VISIBLE);
            binding.nbTitle.setVisibility(View.VISIBLE);
            binding.nbTxt.setVisibility(View.VISIBLE);
            binding.nbTxt.setText("If you are underweight(BMI less than 18.5), you may be malnourished and likely to suffer from the following health problems.");
            binding.nbHealthProblem.setVisibility(View.VISIBLE);

            binding.nbHealthProblem
                    .setText("01. Compromise immune function." +
                            "\n02. Respiratory disease" +
                            "\n03. Digestive disease" +
                            "\n04. Cancer" +
                            "\n05. Osteoporosis");

        }else if(getBMI > 18.4 && getBMI < 25){
            binding.bmiNormal.setTextSize(24);
            binding.bmiUnderweight.setTextSize(18);
            binding.bmiOverweight.setTextSize(18);
            binding.bmiObese.setTextSize(18);
            binding.calculatorImg.setVisibility(View.VISIBLE);
            binding.weightCatResult.setVisibility(View.VISIBLE);
            binding.weightCatResult.setText("Weight Status: Normal");
            binding.healthRiskResult.setVisibility(View.VISIBLE);
            binding.healthRiskResult.setText("Health Risk: Least");
            binding.healthImg.setVisibility(View.VISIBLE);
            binding.nbTitle.setVisibility(View.GONE);
            binding.nbTxt.setVisibility(View.GONE);
            binding.nbHealthProblem.setVisibility(View.GONE);
        }else if(getBMI > 24.9 && getBMI < 30.1){
            binding.bmiUnderweight.setTextSize(18);
            binding.bmiNormal.setTextSize(18);
            binding.bmiOverweight.setTextSize(24);
            binding.bmiObese.setTextSize(18);

            binding.calculatorImg.setVisibility(View.VISIBLE);
            binding.weightCatResult.setVisibility(View.VISIBLE);
            binding.weightCatResult.setText("Weight Status: Overweight");
            binding.healthRiskResult.setVisibility(View.VISIBLE);
            binding.healthRiskResult.setText("Health Risk: Increased");
            binding.healthImg.setVisibility(View.VISIBLE);
            binding.nbTitle.setVisibility(View.VISIBLE);
            binding.nbTxt.setVisibility(View.VISIBLE);
            binding.nbTxt.setText("If you are overweight or obese and physically inactive, you may develop the following conditions.");
            binding.nbHealthProblem.setVisibility(View.VISIBLE);

            binding.nbHealthProblem
                    .setText("01. High blood pressure." +
                            "\n02. High level of cholesterol" +
                            "\n03. Diabetes" +
                            "\n04. Heart Disease" +
                            "\n05. Stroke" +
                            "\n06. Arthritis" +
                            "\n07. Gallbladder disease" +
                            "\n08. Breathing problem" +
                            "\n09. Cancers" +
                            "\n10. Mental illness");

        }else if(getBMI > 30.1 && getBMI < 40.1){
            binding.bmiUnderweight.setTextSize(18);
            binding.bmiNormal.setTextSize(18);
            binding.bmiOverweight.setTextSize(18);
            binding.bmiObese.setTextSize(24);

            binding.calculatorImg.setVisibility(View.VISIBLE);
            binding.weightCatResult.setVisibility(View.VISIBLE);
            binding.weightCatResult.setText("Weight Status: Obese");
            binding.healthRiskResult.setVisibility(View.VISIBLE);
            binding.healthRiskResult.setText("Health Risk: High");
            binding.healthImg.setVisibility(View.VISIBLE);
            binding.nbTitle.setVisibility(View.VISIBLE);
            binding.nbTxt.setVisibility(View.VISIBLE);
            binding.nbTxt.setText("If you are overweight or obese and physically inactive, you may develop the following conditions");
            binding.nbHealthProblem.setVisibility(View.VISIBLE);

            binding.nbHealthProblem
                    .setText("01. High blood pressure." +
                            "\n02. High level of cholesterol" +
                            "\n03. Diabetes" +
                            "\n04. Heart Disease" +
                            "\n05. Stroke" +
                            "\n06. Arthritis" +
                            "\n07. Gallbladder disease" +
                            "\n08. Breathing problem" +
                            "\n09. Cancers" +
                            "\n10. Mental illness");

        }else if(getBMI > 40){
            binding.bmiUnderweight.setTextSize(18);
            binding.bmiNormal.setTextSize(18);
            binding.bmiOverweight.setTextSize(18);
            binding.bmiObese.setTextSize(24);

            binding.calculatorImg.setVisibility(View.VISIBLE);
            binding.weightCatResult.setVisibility(View.VISIBLE);
            binding.weightCatResult.setText("Weight Status: Obese");
            binding.healthRiskResult.setVisibility(View.VISIBLE);
            binding.healthRiskResult.setText("Health Risk: Extremely High");
            binding.healthImg.setVisibility(View.VISIBLE);
            binding.nbTitle.setVisibility(View.VISIBLE);
            binding.nbTxt.setVisibility(View.VISIBLE);
            binding.nbTxt.setText("If you are overweight or obese and physically inactive, you may develop the following conditions");
            binding.nbHealthProblem.setVisibility(View.VISIBLE);
            binding.nbHealthProblem
                    .setText("01. High blood pressure." +
                            "\n02. High level of cholesterol" +
                            "\n03. Diabetes" +
                            "\n04. Heart Disease" +
                            "\n05. Stroke" +
                            "\n06. Arthritis" +
                            "\n07. Gallbladder disease" +
                            "\n08. Breathing problem" +
                            "\n09. Cancers" +
                            "\n10. Mental illness");

        }
    }
}