package com.opiant.voymate.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.opiant.voymate.R;

public class SplashScreen extends AppCompatActivity {

    private boolean mLocationPermissionGranted;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        SharedPreferences IdShared = getSharedPreferences("VoyMate", MODE_PRIVATE);
        final String userEmail = IdShared.getString("Email", "");

        //getLocationPermission();
        Thread timerThread = new Thread() {
            public void run() {
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {

                    if (userEmail!=null){

                        Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Intent intent = new Intent(SplashScreen.this, AppIntroSlider.class);
                        startActivity(intent);
                    }
                }
            }
        };
        timerThread.start();
    }

    private void getLocationPermission() {

        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;

        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

        }
    }
}
