package com.opiant.voymate;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;


public class Tools extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    CircleImageView Listen, ConvertCurrency;
    private OnFragmentInteractionListener mListener;

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
        // Inflate the layout for this fragment
         View view = inflater.inflate(R.layout.fragment_tools, container, false);
         ConvertCurrency = (CircleImageView)view.findViewById(R.id.currency);
         Listen = (CircleImageView)view.findViewById(R.id.listen);

         ConvertCurrency.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Fragment exploreScreen = new CurrencyConvert();
                 FragmentTransaction exploreScreenTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                 exploreScreenTransaction.replace(R.id.containerView1, exploreScreen);
                 exploreScreenTransaction.addToBackStack(null);
                 exploreScreenTransaction.commit();
             }
         });

        Listen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(getActivity(), Listen);
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        String language = String.valueOf(item.getTitle());

                        if (language.equals("Hindi")){

                            Toast.makeText(getContext(),"Language Updated To:"+ language,Toast.LENGTH_SHORT).show();

                        }
                        else if (language.equals("English")){
                            Toast.makeText(getContext(),"Language Updated To:"+ language,Toast.LENGTH_SHORT).show();

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
        });


        return view;
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

    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }
}
