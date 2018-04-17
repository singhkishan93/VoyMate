package com.opiant.voymate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail,etPassword;
    Button Login;
    FloatingActionButton fab;
    private final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    String Email = "voymateapp@android.com", Password = "voymate",userName="VoyMate";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        Login = findViewById(R.id.bt_go);
        fab = findViewById(R.id.fab);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                if (!isValidEmail(username)) {

                    etEmail.setError("Invalid Email");
                }
                else if (!isValidPassword(password)) {
                    etPassword.setError("Invalid Password");

                }
                else if (!(isValidEmail(username)) && !(isValidPassword(password))) {
                    Toast.makeText(getApplicationContext(), "Please Enter Valid Email Address and Password", Toast.LENGTH_SHORT).show();
                }
                else {

                    if (username.equals(Email)&& password.equals(Password)) {
                        Bundle b = new Bundle();
                        b.putString("Email", username);
                        SharedPreferences passwordPref = getSharedPreferences("VoyMate", MODE_PRIVATE);
                        //initializing editor
                        SharedPreferences.Editor editor = passwordPref.edit();
                        editor.putString("Email", username);
                        editor.apply();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtras(b);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(),SignUp.class);
                startActivity(intent);

            }
        });

    }

    private boolean isValidEmail(String email1) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email1);
        return matcher.matches();
    }

    // validating password with retype password
    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() > 0) {
            return true;
        }
        return false;
    }


    public void clickResetPassword(View view) {

        Intent forgot = new Intent(getApplicationContext(),ForgotPassword.class);
        startActivity(forgot);
    }
}
