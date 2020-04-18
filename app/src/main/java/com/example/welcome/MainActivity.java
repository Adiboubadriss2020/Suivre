package com.example.welcome;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.IntentFilter;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {
    GoogleMap map;
    Marker m;
    SupportMapFragment mapFragment;
    SearchView searchView;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.searchView = (SearchView) findViewById(R.id.search);
        this.mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        this.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            public boolean onQueryTextSubmit(String query) {
                String location = MainActivity.this.searchView.getQuery().toString();
                List list = null;
                if (location != null || !location.equals("")) {
                    try {
                        list = new Geocoder(MainActivity.this).getFromLocationName(location, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address address = (Address) list.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    m = MainActivity.this.map.addMarker(new MarkerOptions().position(latLng).title(location));
                    MainActivity.this.map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10.0f));
                }
                return false;
            }

            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        this.mapFragment.getMapAsync(this);
    }

    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;
    }
}



