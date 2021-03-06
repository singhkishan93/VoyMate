package com.opiant.voymate.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import static android.content.Context.MODE_PRIVATE;


public class PlaceInfo extends Fragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    TextView Sight, txtListen,PlaceName,txtHangout,AboutPlace,KnowMore,Hotel,Shop,txtRestaurant,txtHint;
    ImageView PlaceImage;
    int Id;
    String moreTxt;
    Locale myLocale;
    double latitude = 0.0, longitude =0.0;
    LinearLayout Activities,Sights,Hangout,Restaurant,Shopping,Listen;
    private OnFragmentInteractionListener mListener;
    View view;
    String Language;
    SharedPreferences IdShared;

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
        view = inflater.inflate(R.layout.fragment_place_info, container, false);

        initViews();
        return view;
    }

    public void initViews(){

        PlaceImage = view.findViewById(R.id.image);
        PlaceName = view.findViewById(R.id.head);
        AboutPlace = view.findViewById(R.id.expandable_text);
        KnowMore = view.findViewById(R.id.more);
        Activities = view.findViewById(R.id.activities);
        Hangout = view.findViewById(R.id.hangout);
        Sights = view.findViewById(R.id.sight);
        Restaurant = view.findViewById(R.id.restau);
        Shopping = view.findViewById(R.id.shoppp);
        Listen = view.findViewById(R.id.listen);
        txtHint = view.findViewById(R.id.expandable_text);


        Sight = view.findViewById(R.id.mntext);
        txtHangout = view.findViewById(R.id.cttext);
        txtListen = view.findViewById(R.id.fftext);
        txtRestaurant = view.findViewById(R.id.rstext);
        Hotel = view.findViewById(R.id.hgtext);
        Shop = view.findViewById(R.id.shtext);

        IdShared = getActivity().getSharedPreferences("VoyMate", MODE_PRIVATE);


        if (IdShared!=null) {
            Language = IdShared.getString("language", "");

            if (Language.equals("hi")) {

                Hotel.setText(R.string.hhotel);
                txtRestaurant.setText(R.string.hrestaurant);
                Sight.setText(R.string.hsights);
                txtListen.setText(R.string.hlisten);
                Shop.setText(R.string.hshop);
                txtHangout.setText(R.string.hhangout);

            }
            else if (Language.equals("fr")){

                Hotel.setText(R.string.fhotel);
                txtRestaurant.setText(R.string.frestaurant);
                Sight.setText(R.string.fsights);
                txtListen.setText(R.string.flisten);
                Shop.setText(R.string.fshop);
                txtHangout.setText(R.string.fhangout);


            }
            else if (Language.equals("ES")){

                Hotel.setText(R.string.shotel);
                txtRestaurant.setText(R.string.sresta);
                Sight.setText(R.string.fsights);
                txtListen.setText(R.string.slisten);
                Shop.setText(R.string.sshoping);
                txtHangout.setText(R.string.shangout);


            }
        }

        Activities.setOnClickListener(this);
        Hangout.setOnClickListener(this);
        Sights.setOnClickListener(this);
        Shopping.setOnClickListener(this);
        Restaurant.setOnClickListener(this);
        Listen.setOnClickListener(this);

        KnowMore.setClickable(true);
        KnowMore.setMovementMethod(LinkMovementMethod.getInstance());
        Bundle bundle = getArguments();
        Id = bundle.getInt("ID");
        latitude = bundle.getDouble("lat");
        longitude = bundle.getDouble("lng");



        if (Id==1){

            String mainURl = "https://en.wikipedia.org/wiki/";
            String placeName ="red_fort";
            String a = "<a href='";
            String b = "'> Know More </a>";
            String finalURl = mainURl+placeName;
            String text = a+finalURl+b;
            KnowMore.setText(Html.fromHtml(text));
            PlaceImage.setImageResource(R.drawable.redfort);

            //AboutPlace.setText(R.string.red_hint);
            if (Language.equals("hi")) {
                PlaceName.setText(R.string.lalkila);
                AboutPlace.setText(R.string.hred_hint);
            }
            else if (Language.equals("fr")){
                PlaceName.setText(R.string.flalkila);
                AboutPlace.setText(R.string.fred_hint);
            }
            else {
                PlaceName.setText("Red Fort");
                AboutPlace.setText(R.string.red_hint);
            }

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
    }

    public void showPopUp(){
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

            case R.id.listen:
                //showPopUp();
                if (Language.equals("hi")){
                    setLocale("hi");
                }
                else if (Language.equals("en")){
                        setLocale("en");
                }
                else if (Language.equals("fr")){
                    setLocale("fr");
                }
                else if (Language.equals("ES")){
                    setLocale("ES");
                }

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

        intent.putExtra("lat", latitude);
        intent.putExtra("lng", longitude);
        intent.putExtra("Key", type);
        startActivity(intent);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
