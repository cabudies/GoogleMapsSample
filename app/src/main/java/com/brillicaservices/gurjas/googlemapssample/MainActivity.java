package com.brillicaservices.gurjas.googlemapssample;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = MainActivity.class.getName();

    boolean mapReady = false;
    GoogleMap googleMap;
    PlaceAutocompleteFragment placeAutocompleteFragment;

    AlertDialog.Builder alertDialogBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        placeAutocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.search_places);

        alertDialogBuilder = new AlertDialog.Builder(this)
                .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AutocompleteFilter filter = new AutocompleteFilter.Builder()
                .setTypeFilter(Place.TYPE_COUNTRY)
                .setCountry("INDIA")
                .build();
        placeAutocompleteFragment.setFilter(filter);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.maps_fragment);
        mapFragment.getMapAsync(this);

        placeAutocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                MarkerOptions newMarker = new MarkerOptions()
                        .position(new LatLng(place.getLatLng().latitude, place.getLatLng().longitude))
                        .title(place.getName().toString());

                if (mapReady) {
                    googleMap.addMarker(newMarker);
                    googleMap.animateCamera(CameraUpdateFactory.newLatLng(newMarker.getPosition()));
                }
            }

            @Override
            public void onError(Status status) {
                Log.e(TAG, status.getStatusMessage());

                alertDialogBuilder.setTitle("Places API Error");
                alertDialogBuilder.setMessage("Error: " + status.getStatusMessage());

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapReady = true;
        this.googleMap = googleMap;
    }
}
