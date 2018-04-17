package com.opiant.voymate;

import android.support.v4.app.Fragment;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

public class CurrencyConvert extends Fragment implements MainContract.View {

    private MainContract.Presenter mPresenter;

    private String[] mCurrencies;

    private Spinner mBaseSpinner;
    private RecyclerView mRecyclerView;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.activity_currency_convert, container, false);

        mPresenter = new MainPresenter();
        mPresenter.setView(this);

        mCurrencies = getResources().getStringArray(R.array.currencies);

        mBaseSpinner = (Spinner) view.findViewById(R.id.base_selector);
        mRecyclerView = (RecyclerView) view.findViewById(android.R.id.list);

        mBaseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mPresenter.onBaseSelected(mCurrencies[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                mPresenter.onBaseSelected(null);
            }
        });

        mBaseSpinner.setAdapter(new BaseCurrencyAdapter(getContext(), mCurrencies));

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.setView(null);
    }


    @Override
    public void showError() {

        mRecyclerView.setAdapter(null);
        /*Snackbar.make(findViewById(android.R.id.content),
                R.string.service_error, Snackbar.LENGTH_LONG).show();*/

    }

    @Override
    public void showRates(Rates rates) {
        mRecyclerView.setAdapter(
                new CurrencyAdapter((String) mBaseSpinner.getSelectedItem(), rates.getRates()));
    }
}
