package com.opiant.voymate.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.opiant.voymate.activities.NearByPlace;
import com.opiant.voymate.R;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.Manifest.permission.SEND_SMS;
import static android.content.Context.MODE_PRIVATE;
import static android.support.v4.content.ContextCompat.checkSelfPermission;



public class NearBy extends Fragment implements View.OnClickListener{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    View view;
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    LinearLayout Atm,Bank,Hospital,PoliceStation,Hotels,Restaurent;
    Bundle b = new Bundle();
    private String Values;
    Intent intent;
    String Language;
    TextView Hotel,Restaurant,BankText,AtmText,HospitalText,Police;
    SharedPreferences IdShared;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

           view = inflater.inflate(R.layout.near_by_me, container, false);
           initViews();


        return view;
    }


    public void initViews(){

        Atm = view.findViewById(R.id.atm);
        Bank = view.findViewById(R.id.banks);
        Hospital = view.findViewById(R.id.hospitals);
        PoliceStation = view.findViewById(R.id.police);
        Hotels = view.findViewById(R.id.hotel);
        Restaurent = view.findViewById(R.id.restaurant);

        Hotel = view.findViewById(R.id.hoteltxt);
        Restaurant = view.findViewById(R.id.rstatext);
        BankText = view.findViewById(R.id.mntext);
        AtmText = view.findViewById(R.id.hgtext);
        HospitalText = view.findViewById(R.id.shtext);
        Police = view.findViewById(R.id.rstext);

        IdShared = getActivity().getSharedPreferences("VoyMate", MODE_PRIVATE);


        if (IdShared!=null) {
            Language = IdShared.getString("language", "");

            if (Language.equals("hi")) {

              Hotel.setText(R.string.hhotel);
              Restaurant.setText(R.string.hrestaurant);
              BankText.setText(R.string.hbank);
              AtmText.setText(R.string.hatm);
              HospitalText.setText(R.string.hhospital);
              Police.setText(R.string.hpolice);
            }
            else if (Language.equals("fr")){
                Hotel.setText(R.string.fhotel);
                Restaurant.setText(R.string.frestaurant);
                BankText.setText(R.string.fbank);
                AtmText.setText(R.string.fatm);
                HospitalText.setText(R.string.fhospital);
                Police.setText(R.string.fpolice);

            }
        }

        Atm.setOnClickListener(this);
        Bank.setOnClickListener(this);
        Hospital.setOnClickListener(this);
        PoliceStation.setOnClickListener(this);
        Hotels.setOnClickListener(this);
        Restaurent.setOnClickListener(this);
    }

    public  void reToMap(String values){

        Bundle b = new Bundle();
        b.putString("Key",Values);
        intent = new Intent(getContext(),NearByPlace.class);
        intent.putExtras(b);
        startActivity(intent);

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

    @SuppressLint("MissingSuperCall")
    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.atm:
                 Values = "atm";
                 reToMap(Values);
                break;
            case R.id.banks:
                Values = "bank";
                reToMap(Values);
                break;
            case R.id.hospitals:
                Values = "hospital";
                reToMap(Values);
                break;
            case R.id.police:
                Values = "police";
                reToMap(Values);
                break;
            case R.id.hotel:
                Values = "hotel";
                reToMap(Values);
                break;
            case R.id.restaurant:
                Values = "restaurant";
                reToMap(Values);
                break;

        }

    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
