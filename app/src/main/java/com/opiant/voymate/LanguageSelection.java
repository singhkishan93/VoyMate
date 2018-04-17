package com.opiant.voymate;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Locale;

public class LanguageSelection extends Fragment {

    Spinner spinnerctrl;
    Button btn;
    Locale myLocale;
    int Id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.activity_language_selection, container, false);

        Bundle bundle = this.getArguments();

        if (bundle != null) {

            Id = bundle.getInt("ID");
        }
        spinnerctrl = (Spinner) view.findViewById(R.id.spinner1);
        spinnerctrl.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

               if (pos == 1) {

                    Toast.makeText(parent.getContext(), "You have selected Hindi", Toast.LENGTH_SHORT).show();
                    setLocale("hi");
                } else if (pos == 2) {

                    Toast.makeText(parent.getContext(), "You have selected English", Toast.LENGTH_SHORT).show();
                    setLocale("en");
                }
                else if (pos == 3){
                    Toast.makeText(parent.getContext(), "You have selected French", Toast.LENGTH_SHORT).show();
                    setLocale("fr");
                }
                else if (pos == 4){
                    Toast.makeText(parent.getContext(), "You have selected Spanish", Toast.LENGTH_SHORT).show();
                    setLocale("ES");
                }

            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }

        });

        return view;
    }

    public void setLocale(String lang) {

        myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Bundle b = new Bundle();
        b.putString("FavLang",lang);
        b.putInt("ID",Id);
        Fragment exploreScreen = new ReaderListener();
        exploreScreen.setArguments(b);
        FragmentTransaction exploreScreenTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        exploreScreenTransaction.add(R.id.containerView1, exploreScreen);
        exploreScreenTransaction.addToBackStack(null);
        exploreScreenTransaction.commit();
    }
}
