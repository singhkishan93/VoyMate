package com.opiant.voymate.nearbyapi;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.opiant.voymate.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * Created by navneet on 23/7/16.
 */
public class GetNearbyPlacesData extends AsyncTask<Object, String, String> {

    String googlePlacesData;
    GoogleMap mMap;
    String url;
    int i;
    private final Context mContext;

    public GetNearbyPlacesData(final Context context) {
        mContext = context;
    }

    @Override
    protected String doInBackground(Object... params) {
        try {
            Log.d("GetNearbyPlacesData", "doInBackground entered");
            mMap = (GoogleMap) params[0];
            url = (String) params[1];
            DownloadUrl downloadUrl = new DownloadUrl();
            googlePlacesData = downloadUrl.readUrl(url);
            Log.d("GooglePlacesReadTask", "doInBackground Exit");
        } catch (Exception e) {
            Log.d("GooglePlacesReadTask", e.toString());
        }
        return googlePlacesData;
    }

    @Override
    protected void onPostExecute(String result) {
        Log.d("GooglePlacesReadTask", "onPostExecute Entered");
        List<HashMap<String, String>> nearbyPlacesList = null;
        DataParser dataParser = new DataParser();
        nearbyPlacesList = dataParser.parse(result);
        ShowNearbyPlaces(nearbyPlacesList);

        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            int k = jsonArray.length();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("dataResult",result);
        Log.d("GooglePlacesReadTask", "onPostExecute Exit");
    }

    private void ShowNearbyPlaces(final List<HashMap<String, String>> nearbyPlacesList) {
        for (i = 0; i < nearbyPlacesList.size(); i++) {
            Log.d("onPostExecute", "Entered into showing locations");
            MarkerOptions markerOptions = new MarkerOptions();
            HashMap<String, String> googlePlace = nearbyPlacesList.get(i);
            final double lat = Double.parseDouble(googlePlace.get("lat"));
            final double lng = Double.parseDouble(googlePlace.get("lng"));
            final String placeName = googlePlace.get("place_name");
            //final String ID = googlePlace.get("place-id");
            String vicinity = googlePlace.get("vicinity");
            LatLng latLng = new LatLng(lat,lng);
            markerOptions.position(latLng);
            markerOptions.title(placeName + " : " + vicinity);
           /* mMap.addMarker(markerOptions);
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.mark_red));*/

            final String PlaceTitle = placeName + ":" + vicinity;

            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(lat, lng))
                    .title(placeName)
                    .snippet(vicinity)
                    .anchor(.6f, .6f)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.mark_red)));

            /*MarkerOptions destoptions = new MarkerOptions().title(placeName).snippet(String.format("Base Plan Cost: $%.2f", vicinity)).position(latLng).anchor(.5f, .5f).draggable(true)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.mark_red));
            mMap.addMarker(destoptions);*/

            /*addMarker(mMap, lat, lng,
                    placeName, vicinity);*/
            //move map camera
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(12));

            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                /*Intent intent = new Intent(getContext(),LanguageSelection.class);
                startActivity(intent);*/

                    /*double lati = Double.parseDouble(nearbyPlacesList.get(2).get("lat"));
                    double lngi = Double.parseDouble(nearbyPlacesList.get(2).get("lng"));*/
                    /*SharedPreferences usp = mContext.getSharedPreferences("VoyMate", MODE_PRIVATE);
                    String latitude = usp.getString("Lat", "");
                    String longitude = usp.getString("Lng", "");
                    double ulat= Double.parseDouble(latitude);
                    double ulng= Double.parseDouble(longitude);
                    int Radius = 6371;
                    double dLat = Math.toRadians(lat - ulat);
                    double dLng = Math.toRadians(lng - ulng);
                    double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                            Math.cos(Math.toRadians(ulat)) * Math.cos(Math.toRadians(lat)) *
                                    Math.sin(dLng / 2) * Math.sin(dLng / 2);

                    double a1 = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                            + Math.cos(Math.toRadians(ulat))
                            * Math.cos(Math.toRadians(lat)) * Math.sin(dLng / 2)
                            * Math.sin(dLng / 2);

                    double c1 = 2 * Math.atan2(Math.sqrt(a1), Math.sqrt(1 - a1));
                    //double c = 2 * Math.asin(Math.sqrt(a1));
                    double valueResult = Radius * c1;
                    double km = valueResult / 1;
                    DecimalFormat newFormat = new DecimalFormat("####");
                    int kmInDec = Integer.valueOf(newFormat.format(km));
                    int meter = (int) (km*1000);*/
                    /*Log.d("mdis:", String.valueOf(meter));
                    Log.d("kdis:", String.valueOf(kmInDec));
                    Log.d("kdis1:", String.valueOf(km));
                    Log.d("name:", String.valueOf(placeName));*/
                    //Log.d("PlaceId:", ID);

                   /* AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    //Uncomment the below code to Set the message and title from the strings.xml file
                    //builder.setMessage(R.string.dialog_message) .setTitle(R.string.dialog_title);

                    //Setting message manually and performing action on button click
                    if (km<1){
                        builder.setMessage(placeName+"\n"+"Distance:"+String.valueOf(meter)+"m")
                            .setCancelable(false)
                            .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //mContext.finish();
                                }
                            });
                    }
                    else {
                        builder.setMessage(placeName + "\n" +"Distance:"+ String.valueOf(km) + "Km")
                                .setCancelable(false)
                                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //mContext.finish();
                                    }
                                })
                            *//*.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //  Action for 'NO' Button
                                    dialog.cancel();
                                }
                            })*//*;
                    }
                    //Creating dialog box
                    AlertDialog alert = builder.create();
                    //Setting the title manually
                    alert.setTitle(PlaceTitle);
                    alert.show();*/

                }
            });
        }
    }

    private void addMarker(GoogleMap map, double lat, double lon,
                           String title, String snippet) {
        map.addMarker(new MarkerOptions().position(new LatLng(lat, lon))
                .title(String.valueOf(title))
                .snippet(String.valueOf(snippet)));
    }

}
