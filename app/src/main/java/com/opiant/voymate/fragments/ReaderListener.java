package com.opiant.voymate.fragments;

import android.support.v4.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.opiant.voymate.R;

import java.util.Locale;

public class ReaderListener extends Fragment implements TextToSpeech.OnInitListener {

    String favlang;
    int Id;
    private TextToSpeech tts;
    private Button btnSpeak;
    private EditText txtText;
    private static String speed="Normal";
    View view;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.activity_reader_listener, container, false);
        Bundle b = getArguments();
        favlang =b.getString("FavLang");
        Id = b.getInt("ID",0);
        tts = new TextToSpeech(getContext(), this);

        //btnSpeak = (Button) view.findViewById(R.id.btnSpeak);

        txtText = view.findViewById(R.id.txtText);

        if (favlang.equals("hi")){

            if (Id==1) {
                txtText.setText(R.string.hindi);
            }
            else if (Id==2){

                txtText.setText(R.string.akhindi);
            }
            else if (Id==3){

                txtText.setText(R.string.tajhindi);
            }
            else if (Id==4){

                txtText.setText(R.string.dillihaathindi);
            }
        }
        else if (favlang.equals("en")){

            if (Id==1) {
                txtText.setText(R.string.english);
            }
            else if (Id==2){
                txtText.setText(R.string.akenglish);
            }
            else if (Id==3){

                txtText.setText(R.string.tajenglish);
            }
            else if (Id==4){

                txtText.setText(R.string.dillihaatenglish);
            }

        }
        else if (favlang.equals("fr")){
            if (Id==1) {
                txtText.setText(R.string.french);
            }
            else if (Id==2){

                txtText.setText(R.string.akfrench);
            }

            else if (Id==3){

                txtText.setText(R.string.tajfrench);
            }
            else if (Id==4){

                txtText.setText(R.string.dillihaatfrench);
            }
        }
        else if (favlang.equals("ES")){

            if (Id==1) {

                txtText.setText(R.string.spanish);
            }

            else if (Id==2){

                txtText.setText(R.string.akspanish);
            }
            else if (Id==3){

                txtText.setText(R.string.tajspanish);
            }
            else if (Id==4){

                txtText.setText(R.string.dillihaatspanish);
            }
        }

        if (favlang!=null){

            speakOut();
            //setSpeed();
        }


        /*btnSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                speakOut();
            }

        });*/

        return view;
    }

    @Override
    public void onDestroy() {
            super.onDestroy();
            tts.shutdown();

    }

    @Override
    public void onInit(int status) {

        // TODO Auto-generated method stub

        if (status == TextToSpeech.SUCCESS) {

            //int result = tts.setLanguage(Locale.CHINESE);
            int result = tts.setLanguage(new Locale(favlang));

            // tts.setPitch(5); // set pitch level

            // tts.setSpeechRate(2); // set speech speed rate

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "Language is not supported");
            } else {
                //btnSpeak.setEnabled(true);
                speakOut();
            }

        } else {
            Log.e("TTS", "Initilization Failed");
        }

    }

    private void speakOut() {

        String text = txtText.getText().toString();

        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    private void setSpeed(){
        if(speed.equals("Very Slow")){
            tts.setSpeechRate(0.1f);
        }
        if(speed.equals("Slow")){
            tts.setSpeechRate(0.5f);
        }
        if(speed.equals("Normal")){
            tts.setSpeechRate(1.0f);//default 1.0
        }
        if(speed.equals("Fast")){
            tts.setSpeechRate(1.5f);
        }
        if(speed.equals("Very Fast")){
            tts.setSpeechRate(2.0f);
        }
        //for setting pitch you may call
        //tts.setPitch(1.0f);//default 1.0
    }
}
