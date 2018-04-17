package com.opiant.voymate;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;


public class PlaceDetailsScreen extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TextView Sight, Ratings,PlaceName,More,AboutPlace;
    ImageView PlaceImage;
    Button Submit;
    RatingBar Ratingbar;
    private String mParam1;
    private String mParam2;
    CircleImageView Listen;
    private OnFragmentInteractionListener mListener;
    int Id;
    Locale myLocale;

    public PlaceDetailsScreen() {
        // Required empty public constructor
    }

    public static PlaceDetailsScreen newInstance(String param1, String param2) {
        PlaceDetailsScreen fragment = new PlaceDetailsScreen();
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
        View view = inflater.inflate(R.layout.fragment_place_details_screen, container, false);
        //Sight = (TextView)view.findViewById(R.id.textView5);
        PlaceImage = (ImageView) view.findViewById(R.id.imageView18);
        PlaceName = (TextView) view.findViewById(R.id.textView5);
        More = (TextView)view.findViewById(R.id.textView10);
        AboutPlace = (TextView)view.findViewById(R.id.aboutplace);
        /*Ratings = (TextView)view.findViewById(R.id.textView10);*/
        /*Submit = (Button) view.findViewById(R.id.submit);
        Ratingbar=(RatingBar)view.findViewById(R.id.ratingBar);
        Ratings.setVisibility(View.INVISIBLE);*/
        Bundle bundle = getArguments();
        Id = bundle.getInt("ID");

        if (Id==1){

            PlaceImage.setImageResource(R.drawable.redfort);
            PlaceName.setText("Red Fort");
            AboutPlace.setText(R.string.english);
        }
        else if (Id==2){

            PlaceImage.setImageResource(R.drawable.aksharsham);
            PlaceName.setText("Akshardham");
            AboutPlace.setText(R.string.akenglish);
        }
        else if (Id==3){

            PlaceImage.setImageResource(R.drawable.taj);
            PlaceName.setText("Taj Mahal");
            AboutPlace.setText(R.string.tajenglish);
        }
        else if (Id==4){

            PlaceImage.setImageResource(R.drawable.sarojni);
            PlaceName.setText("Dilli Haat");
            AboutPlace.setText(R.string.dillihaatenglish);
        }


        /*Sight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Sight.setTextColor(Color.MAGENTA);
            }
        });
        float cratings = (float) 5.0;*/
        Listen = (CircleImageView)view.findViewById(R.id.listen);

        /*Ratingbar.setRating(cratings);

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String ratings = String.valueOf(Ratingbar.getRating());
                String ratings = String.valueOf(Ratingbar.getRating());
                Ratings.setVisibility(View.VISIBLE);
                Ratings.setText(ratings);
            }
        });*/

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

                            setLocale("hi");
                        }
                        else if (language.equals("English")){

                            setLocale("en");
                        }
                        else if (language.equals("Français")){

                            setLocale("fr");
                        }
                        else if (language.equals("Español")){

                            setLocale("ES");
                        }

                        return true;
                    }
                });

                popup.show();//showing popup menu

               /* Bundle b = new Bundle();
                b.putInt("ID",Id);
                Fragment exploreScreen = new LanguageSelection();
                exploreScreen.setArguments(b);
                FragmentTransaction exploreScreenTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                exploreScreenTransaction.replace(R.id.containerView1, exploreScreen);
                exploreScreenTransaction.addToBackStack(null);
                exploreScreenTransaction.commit();*/
            }
        });

        More.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putInt("ID",Id);
                Fragment exploreScreen = new LanguageSelection();
                exploreScreen.setArguments(b);
                FragmentTransaction exploreScreenTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                exploreScreenTransaction.replace(R.id.containerView1, exploreScreen);
                exploreScreenTransaction.addToBackStack(null);
                exploreScreenTransaction.commit();
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