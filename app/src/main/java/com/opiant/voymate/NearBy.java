package com.opiant.voymate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.Manifest.permission.SEND_SMS;
import static android.content.Context.LOCATION_SERVICE;
import static android.content.Context.MODE_PRIVATE;
import static android.support.v4.content.ContextCompat.checkSelfPermission;



public class NearBy extends Fragment{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    View view;
    private String mParam1;
    private String mParam2;
    private GoogleApiClient mGoogleApiClient;
    private OnFragmentInteractionListener mListener;
    private boolean mLocationPermissionGranted;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private int PLACE_PICKER_REQUEST = 1;
    double mlatitude = 0.0, mlongitude = 0.0, sLatitude=0.0,sLongitude=0.0;
    private GoogleMap mMap;
    SupportMapFragment mapFragment;
    Location location;
    private int PROXIMITY_RADIUS = 1000;
    private final static int MY_PERMISSION_FINELOCATION = 101;
    String cityname, address, address1, city, country, postalCode, state,HelpKey;

    public NearBy() {
        // Required empty public constructor
    }

    public static NearBy newInstance(String param1, String param2) {
        NearBy fragment = new NearBy();
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


           view = inflater.inflate(R.layout.fragment_near_by, container, false);

        CircleImageView Atm = (CircleImageView)view.findViewById(R.id.atm);
        CircleImageView Bank = (CircleImageView)view.findViewById(R.id.banks);
        CircleImageView Hospital = (CircleImageView)view.findViewById(R.id.hospitals);
        CircleImageView PoliceStation = (CircleImageView)view.findViewById(R.id.police);

        Atm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putString("Key", "atm");
                Intent intent = new Intent(getContext(),NearByPlace.class);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        Bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putString("Key", "bank");
                Intent intent = new Intent(getContext(),NearByPlace.class);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        Hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putString("Key", "hospital");
                Intent intent = new Intent(getContext(),NearByPlace.class);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        PoliceStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putString("Key", "police");
                Intent intent = new Intent(getContext(),NearByPlace.class);
                intent.putExtras(b);
                startActivity(intent);
            }
        });



        return view;
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



    private boolean checkPermission() {
        return ( ContextCompat.checkSelfPermission(getContext(), SEND_SMS ) == PackageManager.PERMISSION_GRANTED);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_FINELOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;

                    if (checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                        mMap.setMyLocationEnabled(true);
                        //mMap.setTrafficEnabled(true);

                    }
                    else {
                        Toast.makeText(getActivity(), "permission denied", Toast.LENGTH_LONG).show();
                        getActivity().finish();
                    }
                }

                break;
            }
        }
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
