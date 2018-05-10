package com.opiant.voymate.fragments;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.opiant.voymate.R;
import com.opiant.voymate.activities.MainActivity;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static android.content.Context.MODE_PRIVATE;


public class Settings extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    TextView ReportProblem,ChangePassword,AllowNoti;
    String info="Phone Details:\n";
    private static final String FILENAME = "ReportProblem.txt";
    private FileWriter writer;
    private String emailBody;
    private OnFragmentInteractionListener mListener;
    private PendingIntent pendingIntent;
    String finalInfo;
    private static final int REQUEST_READ_PHONE_STATE =0 ;
    File fileToSend;

    public Settings() {
        // Required empty public constructor
    }

    public static Settings newInstance(String param1, String param2) {
        Settings fragment = new Settings();
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
          View view = inflater.inflate(R.layout.fragment_settings, container, false);
          final Switch simpleSwitch = (Switch) view.findViewById(R.id.notification);
          ReportProblem = view.findViewById(R.id.report);
          ChangePassword = view.findViewById(R.id.changepass);


        SharedPreferences IdShared = getActivity().getSharedPreferences("VoyMate", MODE_PRIVATE);
        if (IdShared!=null){

            boolean notiPref = IdShared.getBoolean("notipref",false);
            String Language = IdShared.getString("language","");

            if (Language.equals("hi")){

                ReportProblem.setText(R.string.hreport);
                ChangePassword.setText(R.string.hchangepas);
                simpleSwitch.setText(R.string.hallownoti);
            }

            if (notiPref==true){
                simpleSwitch.setChecked(true);
            }
            else {
                simpleSwitch.setChecked(false);
            }

        }
        else {
            simpleSwitch.setChecked(true);
        }

        // check current state of a Switch (true or false).
        Boolean switchState = simpleSwitch.isChecked();

        simpleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if (bChecked) {
                    simpleSwitch.setChecked(true);
                    Toast.makeText(getContext(), ""+bChecked, Toast.LENGTH_SHORT).show();
                    SharedPreferences passwordPref = getActivity().getSharedPreferences("VoyMate", MODE_PRIVATE);
                    SharedPreferences.Editor editor = passwordPref.edit();
                    editor.putBoolean("notipref", true);
                    editor.apply();
                    //Intent intent = new Intent(getContext(), MainActivity.class);
                    //startActivity(intent);
                } else {
                    simpleSwitch.setChecked(false);
                    Toast.makeText(getContext(), ""+bChecked, Toast.LENGTH_SHORT).show();
                    SharedPreferences passwordPref = getActivity().getSharedPreferences("VoyMate", MODE_PRIVATE);
                    SharedPreferences.Editor editor = passwordPref.edit();
                    editor.putBoolean("notipref", false);
                    editor.apply();
                }
            }
        });


        @SuppressLint("WrongConstant") TelephonyManager tm = (TelephonyManager)getActivity().getSystemService("phone");
        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.READ_PHONE_STATE);
        //Calling the methods of TelephonyManager the returns the information
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);


        } else {
            String IMEINumber = tm.getDeviceId();
            String subscriberID = tm.getDeviceId();
            String SIMSerialNumber = tm.getSimSerialNumber();
            String networkCountryISO = tm.getNetworkCountryIso();
            String SIMCountryISO = tm.getSimCountryIso();
            String softwareVersion = tm.getDeviceSoftwareVersion();
            String voiceMailNumber = tm.getVoiceMailNumber();
            String strphoneType = "";
            int phoneType = tm.getPhoneType();
            switch(phoneType) {
                case 0:
                    strphoneType = "NONE";
                    break;
                case 1:
                    strphoneType = "GSM";
                    break;
                case 2:
                    strphoneType = "CDMA";
            }

            boolean isRoaming = tm.isNetworkRoaming();
            //String info = "Phone Details:\n";
            info = info + "\n IMEI Number:" + IMEINumber;
            info = info + "\n SubscriberID:" + subscriberID;
            info = info + "\n Sim Serial Number:" + SIMSerialNumber;
            info = info + "\n Network Country ISO:" + networkCountryISO;
            info = info + "\n SIM Country ISO:" + SIMCountryISO;
            info = info + "\n Software Version:" + softwareVersion;
            info = info + "\n Voice Mail Number:" + voiceMailNumber;
            info = info + "\n Phone Network Type:" + strphoneType;
            info = info + "\n In Roaming? :" + isRoaming;

        }

        finalInfo = info;
        emailBody = "This is email to " +""+ getString(R.string.app_name) +"\n"+finalInfo;

        ReportProblem.setOnClickListener(this);

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

    private void sendIntentToGmailApp() {

            String[] TO = {"voymateapp@gmail.com"};
            String[] CC = {"opianttech@gmail.com"};
            String[] BCC = {"kishang.90@gmail.com"};
            Intent email = new Intent(Intent.ACTION_SEND);
            email.putExtra(Intent.EXTRA_SUBJECT, "Report Problem");
            email.putExtra(Intent.EXTRA_EMAIL, TO);
            email.putExtra(Intent.EXTRA_CC, CC);
            email.putExtra(Intent.EXTRA_BCC, BCC);
            email.putExtra(Intent.EXTRA_TEXT, "Message:"+"\n"+emailBody);
            //email.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + fileToSend.getAbsoluteFile()));
            //email.putExtra(Intent.EXTRA_STREAM, emailBody);
            email.setType("message/rfc822");
            startActivity(Intent.createChooser(email , "Send Text File"));

    }

    private File createFileWithContent(String content) {
        if(TextUtils.isEmpty(content)){
            content = emailBody;
        }
        File file = null;
        try{

            Environment.getExternalStorageState();

            file = new File(Environment.getExternalStorageDirectory(), FILENAME);
            writer = new FileWriter(file);
            writer.write(content);
            writer.close();
            Toast.makeText(getActivity(), "Temporarily saved contents in " + file.getPath(), Toast.LENGTH_LONG).show();
        }catch(IOException e){
            Toast.makeText(getActivity(), "Unable create temp file. Check logcat for stackTrace", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        return file;
    }

    @Override
    public void onClick(View view) {
         //fileToSend = createFileWithContent(finalInfo);

        sendIntentToGmailApp();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
