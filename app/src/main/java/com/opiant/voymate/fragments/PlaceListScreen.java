package com.opiant.voymate.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.opiant.voymate.R;


public class PlaceListScreen extends Fragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    TextView placeName,placeType;
    LinearLayout linearLayout;
    double lat,lng;
    View view;
    int Id;


    public PlaceListScreen() {
        // Required empty public constructor
    }


    public static PlaceListScreen newInstance(String param1, String param2) {
        PlaceListScreen fragment = new PlaceListScreen();
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
       view = inflater.inflate(R.layout.fragment_place_list_screen, container, false);
       initViews();
       /* Bundle bundle = getArguments();
        Id = bundle.getInt("ID");*/
        return view ;
    }

    public void initViews(){
        placeName = view.findViewById(R.id.textViewName);
        placeType = view.findViewById(R.id.textViewSubtitle);
        linearLayout = view.findViewById(R.id.ll);



        Bundle bundle = getArguments();
        Id = bundle.getInt("ID");

        if (Id==1){

            placeName.setText("Red Fort");
            placeType.setText("Monuments");
          /*  lat=28.6562;
            lng=77.2410;
            linearLayout.setOnClickListener(this);*/
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   Bundle b = new Bundle();
                    b.putInt("ID",Id);
                    b.putDouble("lat", 28.6562);
                    b.putDouble("lng",77.2410);
                    Fragment placedetailsScreen = new PlaceInfo();
                    placedetailsScreen.setArguments(b);
                    FragmentTransaction placedetailsScreenTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    placedetailsScreenTransaction.replace(R.id.containerView1, placedetailsScreen);
                    placedetailsScreenTransaction.addToBackStack(null);
                    placedetailsScreenTransaction.commit();
                }
            });
        }
        else if (Id==2){

            placeName.setText("Akshardham");
            placeType.setText("Religious Place");
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle b = new Bundle();
                    b.putInt("ID",Id);
                    b.putDouble("lat", 28.6127);
                    b.putDouble("lng",77.2773);
                    Fragment placedetailsScreen = new PlaceInfo();
                    placedetailsScreen.setArguments(b);
                    FragmentTransaction placedetailsScreenTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    placedetailsScreenTransaction.replace(R.id.containerView1, placedetailsScreen);
                    placedetailsScreenTransaction.addToBackStack(null);
                    placedetailsScreenTransaction.commit();
                }
            });
        }

        else if (Id==3){
            placeName.setText("Taj Mahal");
            placeType.setText("Tourist Place");
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle b = new Bundle();
                    b.putInt("ID",Id);
                    b.putDouble("lat", 27.1750);
                    b.putDouble("lng",78.0422);
                    Fragment placedetailsScreen = new PlaceInfo();
                    placedetailsScreen.setArguments(b);
                    FragmentTransaction placedetailsScreenTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    placedetailsScreenTransaction.replace(R.id.containerView1, placedetailsScreen);
                    placedetailsScreenTransaction.addToBackStack(null);
                    placedetailsScreenTransaction.commit();
                }
            });

        }

        else if (Id==4){
            placeName.setText("Dilli Haat");
            placeType.setText("Market & Shopping");
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle b = new Bundle();
                    b.putInt("ID",Id);
                    b.putDouble("lat", 28.5732);
                    b.putDouble("lng",77.2085);
                    Fragment placedetailsScreen = new PlaceInfo();
                    placedetailsScreen.setArguments(b);
                    FragmentTransaction placedetailsScreenTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    placedetailsScreenTransaction.replace(R.id.containerView1, placedetailsScreen);
                    placedetailsScreenTransaction.addToBackStack(null);
                    placedetailsScreenTransaction.commit();
                }
            });

        }


    }

    public void retoPlaceInfo(int id,double lat,double lng){
        Bundle b = new Bundle();
        b.putInt("ID",id);
        b.putDouble("lat", lat);
        b.putDouble("lng",lng);
        Fragment placedetailsScreen = new PlaceInfo();
        placedetailsScreen.setArguments(b);
        FragmentTransaction placedetailsScreenTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        placedetailsScreenTransaction.replace(R.id.containerView1, placedetailsScreen);
        placedetailsScreenTransaction.addToBackStack(null);
        placedetailsScreenTransaction.commit();

    }

    // TODO: Rename method, update argument and hook method into UI event
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

        switch (view.getId()){

            case R.id.ll:
                    retoPlaceInfo(Id,lat,lng);

                break;
        }

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
