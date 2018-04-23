package com.opiant.voymate;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.opiant.voymate.utils.AppUtils;

import static android.content.Context.MODE_PRIVATE;


public class MyProfile extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    View view;
    TextView Logout;
    String isFB,userEmail;
    private OnFragmentInteractionListener mListener;

    public MyProfile() {
        // Required empty public constructor
    }


    public static MyProfile newInstance(String param1, String param2) {
        MyProfile fragment = new MyProfile();
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
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my_profile, container, false);
        Logout = view.findViewById(R.id.logout);
        SharedPreferences IdShared = getActivity().getSharedPreferences("VoyMate", MODE_PRIVATE);
        userEmail = IdShared.getString("email", "");
        isFB = IdShared.getString("isFB", "");

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isFB.equals("yes")){

                    performLogout();
                }
                else {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    SharedPreferences sp = getActivity().getSharedPreferences("VoyMate", MODE_PRIVATE);
                    SharedPreferences.Editor ueditor = sp.edit();
                    ueditor.putString("email", "");
                    ueditor.apply();
                    startActivity(intent);
                    //performLogout();
                }

            }
        });

        return view;
    }

    protected void performLogout() {

        // Create a confirmation dialog
        String logout = getResources().getString(com.facebook.R.string.com_facebook_loginview_log_out_action);
        String cancel = getResources().getString(com.facebook.R.string.com_facebook_loginview_cancel_action);
        String message;


        message = getResources().getString(R.string.logout_message);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message)
                .setCancelable(true)
                .setPositiveButton(logout, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        LoginManager.getInstance().logOut();
                        SharedPreferences passwordPref = getActivity().getSharedPreferences("VoyMate", MODE_PRIVATE);
                        SharedPreferences.Editor editor = passwordPref.edit();
                        editor.putString("email", "");
                        editor.putString("isFB", "no");
                        editor.apply();
                        Intent intent = new Intent(getContext(),LoginActivity.class);
                        startActivity(intent);

                    }
                })
                .setNegativeButton(cancel, null);
        builder.create().show();
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

        void onFragmentInteraction(Uri uri);
    }
}
