package com.opiant.voymate.activities;

import android.Manifest;
import android.annotation.TargetApi;
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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.opiant.voymate.nearbyapi.GetNearbyPlacesData;
import com.opiant.voymate.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class PlacesOnMap extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener, LocationListener, View.OnClickListener {

    private int PROXIMITY_RADIUS = 1000;
    private boolean mLocationPermissionGranted;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    double mlatitude = 0.0, mlongitude = 0.0, sLatitude=0.0,sLongitude=0.0;
    private GoogleMap mMap;
    Location location;
    boolean isConnected = false;
    private GoogleApiClient mGoogleApiClient;
    private final static int MY_PERMISSION_FINELOCATION = 101;
    String cityname, address, address1, city, country, postalCode, state,HelpKey,Key,cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places_on_map);
        TextView Back = findViewById(R.id.backtext);
        mGoogleApiClient = new GoogleApiClient
                .Builder(getApplicationContext())
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Key = bundle.getString("Key");
        sLatitude = bundle.getDouble("lat");
        sLongitude = bundle.getDouble("lng");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fr_map);
        mapFragment.getMapAsync(this);
    }

    private String getUrl(double latitude, double longitude, String nearbyPlace) {

        StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlacesUrl.append("location=" + latitude + "," + longitude);
        googlePlacesUrl.append("&radius=" + PROXIMITY_RADIUS);
        googlePlacesUrl.append("&type=" + nearbyPlace);
        googlePlacesUrl.append("&sensor=true");
        //googlePlacesUrl.append("&key=" + "AIzaSyATuUiZUkEc_UgHuqsBJa1oqaODI-3mLs0");
        googlePlacesUrl.append("&key=" + "AIzaSyBfK9hv6qIpL7vLV-WRa1qkiNzD4ix1Hjc");
        //googlePlacesUrl.append("&key=" + "AIzaSyAGPF0HDsgWWWDXnYB3Ee5EtBEy7gUYS2A");
        Log.d("getUrl", googlePlacesUrl.toString());
        return (googlePlacesUrl.toString());
    }

    public Address getAddress(double latitude, double longitude) {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            return addresses.get(0);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        if (mMap!=null){
            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                @Override
                // Return null here, so that getInfoContents() is called next.
                public View getInfoWindow(Marker arg0) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {
                    // Inflate the layouts for the info window, title and snippet.
                    //*//*View infoWindow = getLayoutInflater().inflate(R.layout.custom_info_contents,
                    //(FrameLayout) findViewById(R.id.fr_map), false);
                    //return infoWindow;*//*
                    return null;
                }
            });

        }
        if (mLocationPermissionGranted == true) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ) {
                mMap.setMyLocationEnabled(true);
            }
        }

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(sLatitude, sLongitude, 1);
            cityName = addresses.get(0).getAddressLine(0);
            //String stateName = addresses.get(0).getAddressLine(1);
            //String countryName = addresses.get(0).getAddressLine(2);
        } catch (IOException e) {
            e.printStackTrace();
        }


        LocationManager locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, true);
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            location = locationManager.getLastKnownLocation(bestProvider);
            if (location != null) {
                onLocationChanged(location);
                mlatitude=location.getLatitude();
                mlongitude=location.getLongitude();

                SharedPreferences passwordPref = getSharedPreferences("VoyMate", MODE_PRIVATE);
                //initializing editor
                SharedPreferences.Editor editor = passwordPref.edit();
                editor.putString("Lat", String.valueOf(mlatitude));
                editor.putString("Lng", String.valueOf(mlongitude));
                editor.apply();

                Address locationAddress=getAddress(sLatitude,sLongitude);

                if(locationAddress!=null) {
                    address = locationAddress.getAddressLine(0);
                    address1 = locationAddress.getAddressLine(1);
                    city = locationAddress.getLocality();
                    state = locationAddress.getAdminArea();
                    country = locationAddress.getCountryName();
                    postalCode = locationAddress.getPostalCode();
                    //float rating = locationAddress.getRating();

                }


            }
            locationManager.requestLocationUpdates(bestProvider, 20000, 0, this);

        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                //requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_FINELOCATION);
            }
        }

        View locationButton = ((View)findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        rlp.setMargins(0,0,30,30);

        LatLng origin = new LatLng(sLatitude, sLongitude);

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(sLatitude, sLongitude))
                .title(cityName)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.mark_blue)));


        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if (!isConnected) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
            builder.setTitle("Something went wrong")
                    .setIcon(R.drawable.error)
                    .setPositiveButton(android.R.string.ok, null);
            AlertDialog dialog = builder.create();
            dialog.show();
        } else {

            String url = getUrl(sLatitude, sLongitude, Key);
            Object[] DataTransfer = new Object[2];
            DataTransfer[0] = googleMap;
            DataTransfer[1] = url;
            Log.d("onClick", url);
            GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData(getApplicationContext());
            getNearbyPlacesData.execute(DataTransfer);

        }

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(origin, 12));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(sLatitude, sLongitude), 12));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(sLatitude, sLongitude), 12), 2000, null);

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                /*Intent intent = new Intent(getContext(),LanguageSelection.class);
                startActivity(intent);*/
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void getLocationPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
                mMap.setMyLocationEnabled(true);

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_FINELOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;


                }

                break;
            }

        }
    }

    @Override
    public void onClick(View view) {

    }
}
