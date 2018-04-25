package com.opiant.voymate;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



public class PlaceListScreen extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    TextView placeName,placeType;
    View view;


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
       placeName = (TextView)view.findViewById(R.id.textViewName);
       placeType = (TextView)view.findViewById(R.id.textViewSubtitle);

        Bundle bundle = getArguments();
        final int Id = bundle.getInt("ID");

        if (Id==1){

            placeName.setText("Red Fort");
            placeType.setText("Monuments");

            placeName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle b = new Bundle();
                    b.putInt("ID",Id);
                    Fragment placedetailsScreen = new PlaceDetailsScreen();
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
            placeName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle b = new Bundle();
                    b.putInt("ID",Id);
                    Fragment placedetailsScreen = new PlaceDetailsScreen();
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
            placeName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle b = new Bundle();
                    b.putInt("ID",Id);
                    Fragment placedetailsScreen = new PlaceDetailsScreen();
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
            placeName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle b = new Bundle();
                    b.putInt("ID",Id);
                    Fragment placedetailsScreen = new PlaceDetailsScreen();
                    placedetailsScreen.setArguments(b);
                    FragmentTransaction placedetailsScreenTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    placedetailsScreenTransaction.replace(R.id.containerView1, placedetailsScreen);
                    placedetailsScreenTransaction.addToBackStack(null);
                    placedetailsScreenTransaction.commit();
                }
            });

        }

        return view ;
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
