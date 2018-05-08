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


            final String PlaceTitle = placeName + ":" + vicinity;

            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(lat, lng))
                    .title(placeName)
                    .snippet(vicinity)
                    .anchor(.6f, .6f)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.mark_red)));


            //move map camera
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(12));

            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {


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
