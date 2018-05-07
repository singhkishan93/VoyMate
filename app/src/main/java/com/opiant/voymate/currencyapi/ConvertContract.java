package com.opiant.voymate.currencyapi;

public class ConvertContract {

    public interface View {
        void showResult(String result);
    }

    public interface Presenter {
        void setView(View view);
        void onValueChanged(String value, double rate);
    }

}
