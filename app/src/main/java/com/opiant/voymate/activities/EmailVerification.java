package com.opiant.voymate.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.opiant.voymate.R;

public class EmailVerification extends AppCompatActivity {

    String OTP = "K143P!";
    EditText Otp;
    Button Submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verification);
        Otp = findViewById(R.id.et_otp);
        Submit = findViewById(R.id.submit);

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String etOtp = Otp.getText().toString();

                if (etOtp.equals(OTP)){

                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
                }
                else {

                    Otp.setError("Required");
                }
            }
        });
    }
}
