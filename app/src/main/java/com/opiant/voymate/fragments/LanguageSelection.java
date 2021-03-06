package com.opiant.voymate.fragments;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.opiant.voymate.R;
import com.opiant.voymate.fragments.ReaderListener;

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
        spinnerctrl = view.findViewById(R.id.spinner1);
        spinnerctrl.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

               if (pos == 1) {
                    String text = spinnerctrl.getSelectedItem().toString();
                   Toast.makeText(parent.getContext(), "You selected "+text, Toast.LENGTH_SHORT).show();

                   setLocale("hi");
                } else if (pos == 2) {

                    Toast.makeText(parent.getContext(), "You selected English", Toast.LENGTH_SHORT).show();
                    setLocale("en");
                }
                else if (pos == 3){
                    Toast.makeText(parent.getContext(), "You selected French", Toast.LENGTH_SHORT).show();
                    setLocale("fr");
                }
                else if (pos == 4){
                    Toast.makeText(parent.getContext(), "You selected Spanish", Toast.LENGTH_SHORT).show();
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
