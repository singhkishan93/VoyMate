package com.opiant.voymate.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.opiant.voymate.R;
import com.opiant.voymate.activities.PlacesOnMap;

import java.util.Locale;


public class PlaceInfo extends Fragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    TextView Sight, Ratings,PlaceName,More,AboutPlace,KnowMore;
    ImageView PlaceImage;
    int Id;
    String moreTxt;
    Locale myLocale;
    double latitude = 0.0, longitude =0.0;
    LinearLayout Activities,Sights,Hangout,Restaurant,Shopping,Listen;
    private OnFragmentInteractionListener mListener;

    public PlaceInfo() {
        // Required empty public constructor
    }

    public static PlaceInfo newInstance(String param1, String param2) {
        PlaceInfo fragment = new PlaceInfo();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_place_info, container, false);
        PlaceImage = (ImageView) view.findViewById(R.id.image);
        PlaceName = (TextView) view.findViewById(R.id.head);
        AboutPlace = (TextView)view.findViewById(R.id.expandable_text);
        KnowMore = (TextView)view.findViewById(R.id.more);
        Activities = (LinearLayout)view.findViewById(R.id.activities);
        Hangout = (LinearLayout)view.findViewById(R.id.hangout);
        Sights = (LinearLayout)view.findViewById(R.id.sight);
        Restaurant = (LinearLayout)view.findViewById(R.id.restau);
        Shopping = (LinearLayout)view.findViewById(R.id.shoppp);
        Listen = (LinearLayout)view.findViewById(R.id.listen);

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

                popup.show();


            }
        });

        KnowMore.setClickable(true);
        KnowMore.setMovementMethod(LinkMovementMethod.getInstance());
        Bundle bundle = getArguments();
        Id = bundle.getInt("ID");
        latitude = bundle.getDouble("lat");
        longitude = bundle.getDouble("lng");

        String mainURl = "https://en.wikipedia.org/wiki/";
        String placeName ="red_fort";
        String a = "<a href='";
        String b = "'> Know More </a>";
        String finalURl = mainURl+placeName;

        if (Id==1){
            String text = a+finalURl+b;
            KnowMore.setText(Html.fromHtml(text));
            PlaceImage.setImageResource(R.drawable.redfort);
            PlaceName.setText("Red Fort");
            AboutPlace.setText(R.string.red_hint);

        }
        else if (Id==2){
            String text = "<a href='https://en.wikipedia.org/wiki/Akshardham_(Delhi)'> Know More </a>";
            KnowMore.setText(Html.fromHtml(text));
            PlaceImage.setImageResource(R.drawable.aksharsham);
            PlaceName.setText("Akshardham");
            AboutPlace.setText(R.string.akshardham_hint);
        }
        else if (Id==3){
            String text = "<a href='https://en.wikipedia.org/wiki/taj_mahal'> Know More </a>";
            KnowMore.setText(Html.fromHtml(text));
            PlaceImage.setImageResource(R.drawable.taj);
            PlaceName.setText("Taj Mahal");
            AboutPlace.setText(R.string.taj_hint);
        }
        else if (Id==4){
            String text = "<a href='https://en.wikipedia.org/wiki/Dilli_Haat'> Know More </a>";
            KnowMore.setText(Html.fromHtml(text));
            PlaceImage.setImageResource(R.drawable.sarojni);
            PlaceName.setText("Dilli Haat");
            AboutPlace.setText(R.string.dillihaat_hint);
        }

        setClickListeners();
        return view;
    }

    private void setClickListeners() {
        Activities.setOnClickListener(this);
        Hangout.setOnClickListener(this);
        Sights.setOnClickListener(this);
        Shopping.setOnClickListener(this);
        Restaurant.setOnClickListener(this);

    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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


   /* @Override
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

        switch (view.getId()) {

            case R.id.activities:
                fireIntent(new Intent(getContext(), PlacesOnMap.class), "hangout");
                break;

            case R.id.hangout:
                fireIntent(new Intent(getContext(), PlacesOnMap.class), "Hotel");
                break;

            case R.id.sight:
                fireIntent(new Intent(getContext(), PlacesOnMap.class), "museum");
                break;

            case R.id.shoppp:
                //fireIntent(new Intent(getContext(), PlacesOnMap.class), "shopping");
                fireIntent(new Intent(getContext(), PlacesOnMap.class), "shopping_mall");
                break;

            case R.id.restau:
                fireIntent(new Intent(getContext(), PlacesOnMap.class), "restaurant");
                break;
        }

    }

    private void fireIntent(Intent intent, String type) {
        //intent.putExtra("id_", id);
        intent.putExtra("lat", latitude);
        intent.putExtra("lng", longitude);
        //intent.putExtra("name_", mTitle);
        intent.putExtra("Key", type);
        startActivity(intent);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
