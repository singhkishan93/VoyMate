package com.opiant.voymate.fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.opiant.voymate.R;


public class Help extends Fragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    Intent callIntent;
    private static final int REQUEST_PHONE_CALL = 1;
    private OnFragmentInteractionListener mListener;
    View view;

    public Help() {
        // Required empty public constructor
    }


    public static Help newInstance(String param1, String param2) {
        Help fragment = new Help();
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
        view = inflater.inflate(R.layout.fragment_help, container, false);
        initViews();
        return view;
    }

    public void initViews(){
        LinearLayout SOS = view.findViewById(R.id.sos);

        SOS.setOnClickListener(this);

    }

    public void callIntent(){

          /*String messageToSend = "this is a message";
                    String number = "9599367430";
                    SmsManager.getDefault().sendTextMessage(number, null, messageToSend, null,null);*/

        callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:9599367430"));
        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
        }
        else
        {
            startActivity(callIntent);
        }


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

            case R.id.sos:
                callIntent();
                break;
        }

    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
