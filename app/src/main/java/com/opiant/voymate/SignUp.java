package com.opiant.voymate;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

    FloatingActionButton fab;
    private final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    CardView cvAdd;
    EditText etEmail,etPassword,etName,etCpassword,etLanguage,etMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        etEmail = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        etCpassword = findViewById(R.id.et_repeatpassword);
        etName = findViewById(R.id.et_name);
        etLanguage = findViewById(R.id.et_language);
        etMobile = findViewById(R.id.et_mnumber);
        fab = findViewById(R.id.fab);
        cvAdd = findViewById(R.id.cv_add);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ShowEnterAnimation();
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                animateRevealClose();
            }
        });
    }

    @SuppressLint("NewApi")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void ShowEnterAnimation() {
        Transition transition = TransitionInflater.from(getApplicationContext()).inflateTransition(R.transition.fabtransition);
        getWindow().setSharedElementEnterTransition(transition);

        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                cvAdd.setVisibility(View.GONE);
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                animateRevealShow();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }


        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void animateRevealShow() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd, cvAdd.getWidth()/2,0, fab.getWidth() / 2, cvAdd.getHeight());
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                cvAdd.setVisibility(View.VISIBLE);
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void animateRevealClose() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd,cvAdd.getWidth()/2,0, cvAdd.getHeight(), fab.getWidth() / 2);
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                cvAdd.setVisibility(View.INVISIBLE);
                super.onAnimationEnd(animation);
                fab.setImageResource(R.drawable.ic_signup);
                SignUp.super.onBackPressed();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    public void clickRegister(View view) {

        String username = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String cpassword = etCpassword.getText().toString();
        String name = etName.getText().toString();
        String mobile = etMobile.getText().toString();
        String language = etLanguage.getText().toString();

         if (!isValidName(name)) {
            etName.setError("Invalid Name");
        }
        else if (!isValidEmail(username)) {

            etEmail.setError("Invalid Email");
        }
        else if (!isValidPassword(password)) {
            etPassword.setError("Invalid Password");

        }
         else if (!checkPassWordAndConfirmPassword(password, cpassword)) {
             etCpassword.setError("Password and Confirmpassword is Not Matching.");
         }
        else if (!isValidMobile(mobile)) {
            etMobile.setError("Invalid Number");
        }

        else {
            Intent verify = new Intent(getApplicationContext(), EmailVerification.class);
            startActivity(verify);
        }
    }

    private boolean isValidName(String username) {
        String NAME_PATTERN = "^[\\p{L} '-]+$";
        Pattern pattern = Pattern.compile(NAME_PATTERN);
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }

    // validate last name
    private boolean validateLastName( String lastName )
    {
        String NAME_PATTERN = "^[\\p{L} '-]+$";
        Pattern pattern = Pattern.compile(NAME_PATTERN);
        Matcher matcher = pattern.matcher(lastName);
        return matcher.matches();
    } // end method validateLastName

    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private boolean isValidMobile(String phone2)
    {
        return android.util.Patterns.PHONE.matcher(phone2).matches();
    }

    private boolean isValidPassword(String cpass) {
        if (cpass != null && cpass.length() > 0) {
            return true;
        }
        return false;
    }
    // validating password with retype password
    private boolean checkPassWordAndConfirmPassword(String pass, String cpass ) {

        boolean pstatus = false;
        if (pass != null && cpass != null) {
            if (pass.equals(cpass))
            {
                pstatus = true;
            }
        }
        return pstatus;
    }

}
