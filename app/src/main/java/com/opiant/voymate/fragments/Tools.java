package com.opiant.voymate.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.Toast;

import com.opiant.voymate.activities.MainActivity;
import com.opiant.voymate.currencyapi.CurrencyConvert;
import com.opiant.voymate.R;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;


public class Tools extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    View view;
    CircleImageView Listen, ConvertCurrency;
    private OnFragmentInteractionListener mListener;
    String Language;

    public Tools() {
        // Required empty public constructor
    }

    public static Tools newInstance(String param1, String param2) {
        Tools fragment = new Tools();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_tools, container, false);
         initViews();

        return view;
    }

    public void initViews(){

        ConvertCurrency = view.findViewById(R.id.currency);
        Listen = view.findViewById(R.id.listen);

        ConvertCurrency.setOnClickListener(this);
        Listen.setOnClickListener(this);
    }

    public void retoCurrency(){

        Fragment exploreScreen = new CurrencyConvert();
        FragmentTransaction exploreScreenTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        exploreScreenTransaction.replace(R.id.containerView1, exploreScreen);
        exploreScreenTransaction.addToBackStack(null);
        exploreScreenTransaction.commit();
    }

    public void openPopUp(){

        //Creating the instance of PopupMenu
        PopupMenu popup = new PopupMenu(getActivity(), Listen);
        popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {

                String language = String.valueOf(item.getTitle());

                if (language.equals("Hindi")){

                    Toast.makeText(getContext(),"Language Updated To:"+ language,Toast.LENGTH_SHORT).show();
                    SharedPreferences passwordPref = getActivity().getSharedPreferences("VoyMate", MODE_PRIVATE);
                    //initializing editor
                    SharedPreferences.Editor editor = passwordPref.edit();
                    editor.putString("language", "hi");
                    editor.apply();
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    startActivity(intent);

                }
                else if (language.equals("English")){
                    Toast.makeText(getContext(),"Language Updated To:"+ language,Toast.LENGTH_SHORT).show();
                    SharedPreferences passwordPref = getActivity().getSharedPreferences("VoyMate", MODE_PRIVATE);
                    //initializing editor
                    SharedPreferences.Editor editor = passwordPref.edit();
                    editor.putString("language", "en");
                    editor.apply();
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    startActivity(intent);

                }
                else if (language.equals("Français")){
                    Toast.makeText(getContext(),"Language Updated To:"+ language,Toast.LENGTH_SHORT).show();

                }
                else if (language.equals("Español")){
                    Toast.makeText(getContext(),"Language Updated To:"+ language,Toast.LENGTH_SHORT).show();

                }

                return true;
            }
        });

        popup.show();//showing popup menu

    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /*@Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){

            case R.id.currency:
                retoCurrency();
                break;

            case R.id.listen:
                openPopUp();
                break;
        }
    }

    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }
}
