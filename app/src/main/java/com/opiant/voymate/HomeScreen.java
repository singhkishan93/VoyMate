package com.opiant.voymate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.support.v4.content.ContextCompat;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
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
import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import de.hdodenhof.circleimageview.CircleImageView;
import static android.Manifest.permission.SEND_SMS;
import static android.app.Activity.RESULT_OK;
import static android.content.Context.LOCATION_SERVICE;
import static android.content.Context.MODE_PRIVATE;
import static android.support.v4.content.ContextCompat.checkSelfPermission;


public class HomeScreen extends Fragment implements GoogleApiClient.OnConnectionFailedListener, OnMapReadyCallback, LocationListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    String cityname, address, address1, city, country, postalCode, state,HelpKey;
    private OnHeadlineSelectedListener mListener;
    private int PLACE_PICKER_REQUEST = 1;
    private boolean mLocationPermissionGranted;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    EditText searchPlace;
    CircleImageView Restaurent, Hotel, Shopping, Activities, Sights,SOS;
    private GoogleApiClient mGoogleApiClient;
    double mlatitude = 0.0, mlongitude = 0.0, sLatitude=0.0,sLongitude=0.0;
    private GoogleMap mMap;
    SupportMapFragment mapFragment;
    Location location;
    private int PROXIMITY_RADIUS = 500;
    private final static int MY_PERMISSION_FINELOCATION = 101;
    View view;
    private static final int REQUEST_SMS = 0;
    private static final int REQUEST_PHONE_CALL = 1;
    Bundle b = getArguments();
    Intent callIntent;
    boolean isConnected = false;
    private AnimationDrawable animationDrawable;
    private RelativeLayout constraintLayout;

    public HomeScreen() {
        // Required empty public constructor
    }

    public interface OnHeadlineSelectedListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public static HomeScreen newInstance(String param1, String param2) {
        HomeScreen fragment = new HomeScreen();
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

        if (view!=null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }

        try {
            view = inflater.inflate(R.layout.fragment_home_screen, container, false);
            searchPlace = (EditText) view.findViewById(R.id.searchbox);
            Restaurent = (CircleImageView) view.findViewById(R.id.res_image);
            Hotel = (CircleImageView) view.findViewById(R.id.hotel_image);
            Shopping = (CircleImageView) view.findViewById(R.id.shoping_image);
            Activities = (CircleImageView) view.findViewById(R.id.activities_image);
            Sights = (CircleImageView) view.findViewById(R.id.sights);
            SOS = (CircleImageView) view.findViewById(R.id.sos);

            mGoogleApiClient = new GoogleApiClient
                    .Builder(getContext())
                    .addApi(Places.GEO_DATA_API)
                    .addApi(Places.PLACE_DETECTION_API)
                    .enableAutoManage(getActivity(), this)
                    .build();
           /* constraintLayout = (RelativeLayout) view.findViewById(R.id.rl);
            // initializing animation drawable by getting background from constraint layout
            animationDrawable = (AnimationDrawable) constraintLayout.getBackground();

            // setting enter fade animation duration to 5 seconds
            animationDrawable.setEnterFadeDuration(3000);

            // setting exit fade animation duration to 2 seconds
            animationDrawable.setExitFadeDuration(1000);*/

            getLocationPermission();
            searchPlace.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mMap.clear();
                    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                    try {
                        startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST);
                    } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                        e.printStackTrace();
                    }
                }
            });

            Bundle bundle = getArguments();

            if (bundle!=null){
                HelpKey = bundle.getString("police");
            }

            SOS.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

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
            });


            Restaurent.setOnClickListener(new View.OnClickListener() {

                String Restaurant = "restaurant";

                @Override
                public void onClick(View v) {

                    // Checking Network Connectivity
                    ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                    isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
                    if (!isConnected) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("Something went wrong")
                                .setIcon(R.drawable.error)
                                .setPositiveButton(android.R.string.ok, null);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    } else {

                        mMap.clear();
                        String url = getUrl(mlatitude, mlongitude, Restaurant);
                        Object[] DataTransfer = new Object[2];
                        DataTransfer[0] = mMap;
                        DataTransfer[1] = url;
                        Log.d("onClick", url);
                        GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData(getContext());
                        getNearbyPlacesData.execute(DataTransfer);

                    }

                }
            });

        } catch (InflateException e) {
            /* map is already there, just return view as it is */
        }
        return view;
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

    @RequiresApi(api = Build.VERSION_CODES.CUR_DEVELOPMENT)
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Obtain the MapFragment and get notified when the map is ready to be used.
        MapFragment mapFragment = (MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.fr_map);
        mapFragment.getMapAsync(this);

    }

    private void getLocationPermission() {

        if (checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;

        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

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
        if (context instanceof OnHeadlineSelectedListener) {
            mListener = (OnHeadlineSelectedListener) context;
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
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public Address getAddress(double latitude, double longitude) {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(getContext(), Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            return addresses.get(0);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    public void sendSMS(String phoneNo, String msg) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, msg, null, null);
            Toast.makeText(getContext(), phoneNo+""+msg, Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(getContext(),ex.getMessage().toString(), Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
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

        /*if (b!=null) {
            String Key = b.getString("Key");
            String url = getUrl(mlatitude, mlongitude, Key);
            Object[] DataTransfer = new Object[2];
            DataTransfer[0] = mMap;
            DataTransfer[1] = url;
            Log.d("onClick", url);
            GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData(getContext());
            getNearbyPlacesData.execute(DataTransfer);
        }*/

        if (mLocationPermissionGranted == true) {

            if (checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ) {
                mMap.setMyLocationEnabled(true);
                //mMap.setTrafficEnabled(true);
            }

        }
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, true);
        if (checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            //mMap.setTrafficEnabled(true);
            location = locationManager.getLastKnownLocation(bestProvider);
            if (location != null) {
                onLocationChanged(location);
                mlatitude=location.getLatitude();
                mlongitude=location.getLongitude();

                SharedPreferences passwordPref = getActivity().getSharedPreferences("VoyMate", MODE_PRIVATE);
                //initializing editor
                SharedPreferences.Editor editor = passwordPref.edit();
                editor.putString("Lat", String.valueOf(mlatitude));
                editor.putString("Lng", String.valueOf(mlongitude));
                editor.apply();

                Address locationAddress=getAddress(mlatitude,mlongitude);

                if(locationAddress!=null) {
                    address = locationAddress.getAddressLine(0);
                    address1 = locationAddress.getAddressLine(1);
                    city = locationAddress.getLocality();
                    state = locationAddress.getAdminArea();
                    country = locationAddress.getCountryName();
                    postalCode = locationAddress.getPostalCode();
                    int desc = locationAddress.describeContents();
                    //float rating = locationAddress.getRating();


                    /*StringTokenizer tokens = new StringTokenizer(address, " ");
                    String first = tokens.nextToken();
                    String second = tokens.nextToken();
.snippet(vicinity)
.anchor(.6f, .6f)
                    Log.d("string split",second);*/
                }


            }
            locationManager.requestLocationUpdates(bestProvider, 20000, 0, this);

        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                //requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_FINELOCATION);
            }
        }

        View locationButton = ((View) view.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        rlp.setMargins(0,0,30,30);

        LatLng origin = new LatLng(mlatitude, mlongitude);

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(mlatitude, mlongitude))
                .title(address)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.mark_blue)));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(origin, 14));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mlatitude, mlongitude), 14));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mlatitude, mlongitude), 10), 2000, null);

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                /*Intent intent = new Intent(getContext(),LanguageSelection.class);
                startActivity(intent);*/
            }
        });


    }

    private boolean checkPermission() {
        return ( ContextCompat.checkSelfPermission(getContext(), SEND_SMS ) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{SEND_SMS}, REQUEST_SMS);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_FINELOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;

                    if (checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                        mMap.setMyLocationEnabled(true);
                        //mMap.setTrafficEnabled(true);
                        mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(mlatitude, mlongitude))
                                .title(address)
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.mark_blue)));

                        Intent intent = new Intent(getContext(),MainActivity.class);
                        startActivity(intent);

                    }
                    else {
                        Toast.makeText(getActivity(), "permission denied", Toast.LENGTH_LONG).show();
                        getActivity().finish();
                    }
                }

                break;
            }
            case REQUEST_PHONE_CALL:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startActivity(callIntent);
                }
                else
                {
                   return;
                }

        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onResume() {
        super.onResume();
      /*MapFragment mapFragment = (MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.fr_map);
        mapFragment.getMapAsync(this);

        if (mMap!=null){
            onMapReady(mMap);
        }*/

       /* if (animationDrawable != null && !animationDrawable.isRunning()) {
            // start the animation
            animationDrawable.start();
        }*/
    }

    @Override
    public void onLocationChanged(Location location) {

       /* //Getting latitude and longitude of user after changing location
        mlatitude = location.getLatitude();
        mlongitude = location.getLongitude();
        LatLng latLng = new LatLng(mlatitude, mlongitude);
        MarkerOptions sourceoptions = new MarkerOptions().position(latLng)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        mMap.addMarker(sourceoptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));*/

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, getContext());
                StringBuilder stBuilder = new StringBuilder();
                String placename = String.format("%s", place.getName());
                String id = String.format("%s", place.getId());
                String latitude = String.valueOf(place.getLatLng().latitude);
                String longitude = String.valueOf(place.getLatLng().longitude);
                String address = String.format("%s", place.getAddress());
                float rating = place.getRating();
                String number = (String) place.getPhoneNumber();
                int pricelevel = place.getPriceLevel();
                stBuilder.append("Name: ");
                stBuilder.append(placename);
                stBuilder.append("\n");
                stBuilder.append("Latitude: ");
                stBuilder.append(latitude);
                stBuilder.append("\n");
                stBuilder.append("Logitude: ");
                stBuilder.append(longitude);
                stBuilder.append("\n");
                stBuilder.append("Address: ");
                stBuilder.append(address);
                //placePicker.setText(stBuilder.toString());
                searchPlace.setText(placename);

                sLatitude = place.getLatLng().latitude;
                sLongitude = place.getLatLng().longitude;

                LatLng origin = new LatLng(sLatitude, sLongitude);

                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(sLatitude, sLongitude))
                        .title(placename)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.mark_blue)));

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(origin, 14));

                /*Location locationA = new Location("Origin");

                locationA.setLatitude(mlatitude);
                locationA.setLongitude(mlongitude);

                Location locationB = new Location("Destination");

                locationB.setLatitude(sLatitude);
                locationB.setLongitude(sLongitude);

                final float distance = locationA.distanceTo(locationB);

                int Radius = 6371;
                double dLat = Math.toRadians(sLatitude - mlatitude);
                double dLon = Math.toRadians(sLongitude - mlongitude);
                double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                        + Math.cos(Math.toRadians(mlatitude))
                        * Math.cos(Math.toRadians(sLatitude)) * Math.sin(dLon / 2)
                        * Math.sin(dLon / 2);
                double c = 2 * Math.asin(Math.sqrt(a));
                double valueResult = Radius * c;
                double km = valueResult / 1;
                DecimalFormat newFormat = new DecimalFormat("####");
                final int kmInDec = Integer.valueOf(newFormat.format(km));
                double meter = valueResult % 1000;
                double finalmeter = meter * 1000;
                int meterInDec = Integer.valueOf(newFormat.format(meter));
*/


                mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        int Radius = 6371;
                        double dLat = Math.toRadians(sLatitude - mlatitude);
                        double dLng = Math.toRadians(sLongitude - mlongitude);
                        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                                Math.cos(Math.toRadians(mlatitude)) * Math.cos(Math.toRadians(sLatitude)) *
                                        Math.sin(dLng / 2) * Math.sin(dLng / 2);

                        double a1 = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                                + Math.cos(Math.toRadians(mlatitude))
                                * Math.cos(Math.toRadians(sLatitude)) * Math.sin(dLng / 2)
                                * Math.sin(dLng / 2);

        /*double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        float dist = (float) (earthRadius * c);

        int Km = (int) (dist/1000);*/
                        double c1 = 2 * Math.atan2(Math.sqrt(a1), Math.sqrt(1 - a1));
                        //double c = 2 * Math.asin(Math.sqrt(a1));
                        double valueResult = Radius * c1;
                        double km = valueResult / 1;
                        DecimalFormat newFormat = new DecimalFormat("####");
                        int kmInDec = Integer.valueOf(newFormat.format(km));
                        Log.d("dis:", String.valueOf(kmInDec));


                    }
                });

               /* Atm.setOnClickListener(new View.OnClickListener() {

                    String ATM = "atm";

                    @Override
                    public void onClick(View view) {

                        String url = getUrl(sLatitude, sLongitude, ATM);
                        Object[] DataTransfer = new Object[2];
                        DataTransfer[0] = mMap;
                        DataTransfer[1] = url;
                        Log.d("Sights", url);
                        GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData(getContext());
                        getNearbyPlacesData.execute(DataTransfer);

                    }
                });*/

                Sights.setOnClickListener(new View.OnClickListener() {

                    String Sight = "museum";

                    @Override
                    public void onClick(View v) {

                        // Checking Network Connectivity
                        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                        isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
                        if (!isConnected) {

                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setTitle("Something went wrong")
                                    .setIcon(R.drawable.error)
                                    .setPositiveButton(android.R.string.ok, null);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        } else {

                            mMap.clear();
                            String url = getUrl(sLatitude, sLongitude, Sight);
                            Object[] DataTransfer = new Object[2];
                            DataTransfer[0] = mMap;
                            DataTransfer[1] = url;
                            Log.d("Sights", url);
                            GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData(getContext());
                            getNearbyPlacesData.execute(DataTransfer);

                        }

                    }
                });

                Restaurent.setOnClickListener(new View.OnClickListener() {

                    String Restaurant = "restaurant";

                    @Override
                    public void onClick(View v) {

                        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                        isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
                        if (!isConnected) {

                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setTitle("Something went wrong")
                                    .setIcon(R.drawable.error)
                                    .setPositiveButton(android.R.string.ok, null);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        } else {

                            mMap.clear();
                            String url = getUrl(sLatitude, sLongitude, Restaurant);
                            Object[] DataTransfer = new Object[2];
                            DataTransfer[0] = mMap;
                            DataTransfer[1] = url;
                            Log.d("Restaurent", url);
                            GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData(getContext());
                            getNearbyPlacesData.execute(DataTransfer);

                        }
                    }
                });

                Shopping.setOnClickListener(new View.OnClickListener() {

                    String ShoppingPlace = "shopping_mall";
                    @Override
                    public void onClick(View v) {

                        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                        isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
                        if (!isConnected) {

                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setTitle("Something went wrong")
                                    .setIcon(R.drawable.error)
                                    .setPositiveButton(android.R.string.ok, null);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }else {
                            mMap.clear();
                            String url = getUrl(sLatitude, sLongitude, ShoppingPlace);
                            Object[] DataTransfer = new Object[2];
                            DataTransfer[0] = mMap;
                            DataTransfer[1] = url;
                            Log.d("Shopping", url);
                            GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData(getContext());
                            getNearbyPlacesData.execute(DataTransfer);

                        }
                    }
                });

                Activities.setOnClickListener(new View.OnClickListener() {

                    String ActivitiesPlace = "park";
                    @Override
                    public void onClick(View v) {
                        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                        isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
                        if (!isConnected) {

                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setTitle("Something went wrong")
                                    .setIcon(R.drawable.error)
                                    .setPositiveButton(android.R.string.ok, null);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }else {
                            mMap.clear();
                            String url = getUrl(sLatitude, sLongitude, ActivitiesPlace);
                            Object[] DataTransfer = new Object[2];
                            DataTransfer[0] = mMap;
                            DataTransfer[1] = url;
                            Log.d("Activities", url);
                            GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData(getContext());
                            getNearbyPlacesData.execute(DataTransfer);
                        }

                    }
                });


                Hotel.setOnClickListener(new View.OnClickListener() {

                    String Hotels = "restaurant";

                    @Override
                    public void onClick(View v) {

                        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                        isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
                        if (!isConnected) {

                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setTitle("Something went wrong")
                                    .setIcon(R.drawable.error)
                                    .setPositiveButton(android.R.string.ok, null);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }else {

                            mMap.clear();
                            String url = getUrl(sLatitude, sLongitude, Hotels);
                            Object[] DataTransfer = new Object[2];
                            DataTransfer[0] = mMap;
                            DataTransfer[1] = url;
                            Log.d("Hotel", url);
                            GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData(getContext());
                            getNearbyPlacesData.execute(DataTransfer);

                        }

                    }
                });

            }
        }
    }

    /*private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;

        int KM = (int) (dist/1000);

        Log.d("dis:", String.valueOf(KM));
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }*/

    public static int distance(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 6371000; //meters
        int Radius = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                   Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);

        double a1 = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLng / 2)
                * Math.sin(dLng / 2);

        /*double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        float dist = (float) (earthRadius * c);

        int Km = (int) (dist/1000);*/

        double c = 2 * Math.asin(Math.sqrt(a1));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));

        Log.d("dis:", String.valueOf(kmInDec));

        return kmInDec;
    }


}
