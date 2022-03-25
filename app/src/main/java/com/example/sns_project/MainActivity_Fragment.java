package com.example.sns_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.sns_project.tab.Tab1_Map;
import com.example.sns_project.tab.Tab2_Training;
import com.example.sns_project.tab.Tab3_Sns;
import com.example.sns_project.tab.Tab4_Setting;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity_Fragment extends AppCompatActivity {

    Tab1_Map tab1_map;
    Tab2_Training tab2_training;
    Tab3_Sns tab3_sns;
    Tab4_Setting tab4_setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fragment);

        tab1_map = new Tab1_Map();
        tab2_training = new Tab2_Training();
        tab3_sns = new Tab3_Sns();
        tab4_setting = new Tab4_Setting();

        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, tab1_map);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnavi);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.tab1map:
                        getSupportFragmentManager().beginTransaction().
                                replace(R.id.framelayout, tab1_map).commit();

                        return true;

                    case R.id.tab2training:

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.framelayout, tab2_training).commit();
                        return true;

                    case R.id.tab3sns:

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.framelayout, tab3_sns).commit();
                        return true;

                    case R.id.tab4setting:

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.framelayout, tab4_setting).commit();
                        return true;

                }
                return false;
            }
        });
    }
}