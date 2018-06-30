package com.brillicaservices.gurjas.googlemapssample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    boolean mapReady = false;
    GoogleMap googleMap;
    Button madhubanHotelDriveBtn;

    MarkerOptions madhubanHotelMarker = new MarkerOptions()
            .position(new LatLng(30.338377,78.0561103))
            .title("Madhuban Hotel");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        madhubanHotelDriveBtn = findViewById(R.id.map_madhuban);

        madhubanHotelDriveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mapReady) {
                    googleMap.animateCamera(CameraUpdateFactory.newLatLng(madhubanHotelMarker.getPosition()));
                }
            }
        });

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.maps_fragment);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapReady = true;
        this.googleMap = googleMap;

        this.googleMap.addMarker(madhubanHotelMarker);
    }
}
