package com.opiant.voymate.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.opiant.voymate.R;

public class SelectLanguage extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {

    String[] language = { "English", "हिंदी", "Français", "Español"};
    String langCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_language);

        Spinner spin = findViewById(R.id.language);
        spin.setOnItemSelectedListener(this);

        ArrayAdapter langAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,language);
        langAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(langAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        String prefLang = language[i];
        Toast.makeText(getApplicationContext(),prefLang ,Toast.LENGTH_LONG).show();

        if (prefLang.equals("हिंदी")) {
            langCode="hi";
            SharedPreferences passwordPref = getSharedPreferences("VoyMate", MODE_PRIVATE);
            //initializing editor
            SharedPreferences.Editor editor = passwordPref.edit();
            editor.putString("language", langCode);
            editor.apply();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
