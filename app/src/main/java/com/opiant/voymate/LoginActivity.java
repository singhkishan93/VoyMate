package com.opiant.voymate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail,etPassword;
    Button Login;
    FloatingActionButton fab;
    LoginButton loginButton;
    private static final String EMAIL = "email";
    CallbackManager callbackManager;
    String id,personGivenName,personFamilyName,personEmail,personId,birthday,android_id;


    private final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    String Email = "voymateapp@android.com", Password = "voymate",userName="VoyMate";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Intent in = getIntent();
        //Getting bundle
        Bundle b = in.getExtras();

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

        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.fblogin);
        loginButton.setReadPermissions("public_profile");
        boolean loggedIn = AccessToken.getCurrentAccessToken() == null;

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                String accessToken = loginResult.getAccessToken().getToken();
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.i("LoginActivity", response.toString());
                        Bundle bFacebookData = getFacebookData(object);
                        String Email = bFacebookData.getString("email");
                        String Fname = bFacebookData.getString("first_name");
                        String Lname = bFacebookData.getString("last_name");
                        String Image = bFacebookData.getString("profile_pic");
                        String FullName = Fname+" "+Lname;

                        if (object.has("work")){
                            try {
                                String Work = object.getString("work");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        if (object.has("photos")){
                            try {
                                String Photos = object.getString("photos");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        if (object.has("birthday")) {
                            try {
                                birthday = object.getString("birthday");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        if (object.has("location")){

                            try {
                                String location = object.getString("location");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        if (object.has("education")){

                            try {

                                JSONArray educationDetails = object.getJSONArray("education");

                                for (int i = 0; i < educationDetails.length(); i++) {

                                    JSONObject Details = (JSONObject) educationDetails.get(i);
                                    JSONObject school = Details.getJSONObject("school");
                                    String SchoolName = school.getString("name");
                                    String Type = Details.getString("type");
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        if (object.has("photos")){

                            try {

                                JSONArray photosDetails = object.getJSONArray("photos");
                                //String Photos = object.getString("photos");
                                for (int i = 0; i < photosDetails.length(); i++) {

                                    JSONObject Details = (JSONObject) photosDetails.get(i);
                                    JSONObject ID = Details.getJSONObject("data");
                                    String Id = ID.getString("id");
                                }

                                //String photoUrl = "https://graph.facebook.com/478216262309083/picture?width=50&height=50";
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        if (object.has("id")){

                            try {
                                id = object.getString("id");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        Bundle b = new Bundle();
                        b.putString("myname", FullName);
                        b.putString("email", Email);
                        b.putString("uri", Image);
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        intent.putExtras(b);
                        startActivity(intent);



                    }
                });
                // Parámetros que pedimos a facebook
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, relationship_status, name, first_name, last_name, email,gender, link,birthday, location,education,work,likes,photos");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
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

    private Bundle getFacebookData(JSONObject object) {

        try {
            Bundle bundle = new Bundle();
            String id = object.getString("id");

            try {
                URL profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?width=200&height=150");
                Log.i("profile_pic", profile_pic + "");
                bundle.putString("profile_pic", profile_pic.toString());

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }

            bundle.putString("idFacebook", id);
            if (object.has("first_name"))
                bundle.putString("first_name", object.getString("first_name"));
            if (object.has("last_name"))
                bundle.putString("last_name", object.getString("last_name"));
            if (object.has("email"))
                bundle.putString("email", object.getString("email"));
            if (object.has("gender"))
                bundle.putString("gender", object.getString("gender"));
            if (object.has("birthday"))
                bundle.putString("birthday", object.getString("birthday"));
            if (object.has("location"))
                bundle.putString("location", object.getJSONObject("location").getString("name"));

            return bundle;
        }
        catch(JSONException e) {
            Log.d("Tag","Error parsing JSON");
        }
        return null;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

    }


    public void clickResetPassword(View view) {

        Intent forgot = new Intent(getApplicationContext(),ForgotPassword.class);
        startActivity(forgot);
    }
}
