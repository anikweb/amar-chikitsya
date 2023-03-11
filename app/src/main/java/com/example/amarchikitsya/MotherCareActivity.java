package com.example.amarchikitsya;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.amarchikitsya.Adapter.MotherCareAdapter;
import com.example.amarchikitsya.databinding.ActivityMotherCareBinding;
import com.example.amarchikitsya.model.MotherCare;
import com.example.amarchikitsya.utils.InternetConnection;

import java.util.ArrayList;
import java.util.List;

public class MotherCareActivity extends AppCompatActivity {

    ActivityMotherCareBinding binding;
    List<MotherCare> motherCareList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityMotherCareBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        if (!InternetConnection.checkConnection(MotherCareActivity.this)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MotherCareActivity.this);
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
                            startActivity(new Intent(MotherCareActivity.this,MotherCareActivity.class));
                            finish();
                        }
                    }).create().show();
        }

        motherCareList = new ArrayList<>();

        motherCareList.add(new MotherCare(1,"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRuArtX-D8DNTvHEK0j59JPtS_SKOajkmm0HA&usqp=CAU","Pre Conceptional Care","A Preconception care visit can help woman take steps for a safe and healthy pregnancy before they get they get pregnant \n\n Woman can help to promote a healthy pregnancy and birth of a healthy infant by taking the following steps before they become pregnant" +
                "\n01. Develop a plan for their reproductive life." +
                "\n02. increase their daily intake of folic acid (one of the B vitamin) to at least 4 micrograms" +
                "\n03. Make sure their immunizations are up to date" +
                "\n04. Control Diabetes and other medical conditions." +
                "\n05. Avoid smoking, drinking alcohol, and using drugs." +
                "\n06. Attain a healthy weight." +
                "\n07. Learn about their family health history and that of their partner." +
                "\n08. Seek help for depression or anxiety."));
        motherCareList.add(new MotherCare(2,"https://i0.wp.com/nursingexercise.com/wp-content/uploads/2016/05/High-risk-pregnancy.jpg?resize=650%2C450","High Risk Pregnancy","A High Risk Pregnancy is one that threatens the health or life of the mother or her fetus.\n\n For most women, early and regular prenatal care promotes a healthy pregnancy amd delivery without complications, but some women are at an increased risk for complications even before they get pregnant for a variety of reasons. \n\n Risk factors for a high-risk pregnant can include: " +
                "\n\n01. Existing health conditions, such as high blood pressure, diabetes or being HIV-Positive." +
                "\n02. Overweight and obesity. obesity increases the risk for high blood pressure, preeclampsia, gestational diabetes, stillbirth, neural tube defects and cesarean delivery. obesity can raise infants risk of heart problems at birth by 15%." +
                "\n03. Multiple births. The risk of complications is higher in women carrying more than one fetus (twins and higher-order multiples). Common complications include preeclampsia, premature labor, and preterm birth. More than half of all twins and as many an 93% of triplets are born at less than 37 weeks' gestation." +
                "\n04. Your or old maternal age. Pregnancy in teens and women aged 35 or over increases the risk for preeclampsia and gestational high blood pressure." +
                "\n\nWomen with high-risk pregnancies should receive care from a special team of health care providers to ensure that their pregnancies are healthy and that they can carry their infant or infants to term."));
        MotherCareAdapter motherCareAdapter = new MotherCareAdapter(MotherCareActivity.this,motherCareList);
        binding.motherCareRecycler.setAdapter(motherCareAdapter);
        binding.toolbar.pageTitle.setText("Mother Care");
        binding.toolbar.backBtn.setOnClickListener(v -> {
            super.onBackPressed();
        });




    }
}