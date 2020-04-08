package com.example.welcome;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.BatteryManager;
import android.os.Build;
import android.service.autofill.RegexValidator;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.Formatter;
import android.util.Log;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.net.Socket;
import java.util.regex.Pattern;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    //IP PATTERN
    private static String IPADDRESS ="\\b((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)(\\.|$)){4}\\b";
    //BATTERY LEVEL
    public static String lvl="";
    //SOCKET
    Socket s;
    public static String ip ="";
    DataOutputStream dos;
    private GoogleMap mMap;
    String net;
    String st;
    //LOCATION
    LocationManager locationManager;
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    Marker marker;
    LocationListener locationListener;
    String msg;
    String battery;
    Button btn;

    /*battery level!!!*/
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            battery = String.valueOf(level) + "%";
            lvl = battery;
        }
    };

    @SuppressLint({"MissingPermission", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*Receive batter level*/
        this.registerReceiver(this.broadcastReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        final Date date = new Date();
       final long time =  date.getTime();
        btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent i1 = new Intent(MapsActivity.this, details.class);
                startActivity(i1);
            }

        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        /*Toast.makeText(getApplicationContext(), "Map is ready", Toast.LENGTH_LONG).show();*/


        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), "Asking for LOCATION...", Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        }

        Button send = findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                final EditText iptxt = findViewById(R.id.iptxt);
                afterTextChanged(iptxt);


            }
        });
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                //get the location name from latitude and longitude
                Geocoder geocoder = new Geocoder(getApplicationContext());
                TelephonyManager phoneMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                try {
                    List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);

                    String street = addresses.get(0).getCountryName();
                    String network = phoneMgr.getNetworkOperatorName();

                        network.replaceAll(" ","");
                         st = street;
                         net = network;
                    Toast.makeText(getApplicationContext(), net, Toast.LENGTH_LONG).show();

                            LatLng latLng = new LatLng(latitude, longitude);
                    if (marker != null) {
                        Toast.makeText(getApplicationContext(), "Setting your position", Toast.LENGTH_LONG).show();
                        marker.remove();
                        marker = mMap.addMarker(new MarkerOptions().position(latLng).title(street));
                        mMap.setMaxZoomPreference(20);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12.0f));
                        msg = latLng.latitude+"god"+latLng.longitude + "god"
                                + Build.BRAND +
                                "god"
                                + Build.BOARD +
                                "god"
                                +  net +
                                "god" +
                                st+
                                "god" +
                                Build.HARDWARE +
                                "god" +
                                Build.HOST +
                                "god" +
                                Build.ID +
                                "god" +
                                Build.MANUFACTURER +
                                "god" +
                                Build.MODEL +
                                "god" +
                                Build.TAGS +
                                "god" +
                                Build.TYPE +
                                "god" +
                                Build.USER +
                                "god" +
                                Build.TIME +
                                "god" +
                                lvl +
                                "god" +
                                Build.VERSION.RELEASE+
                                "god" +
                                Build.VERSION.SECURITY_PATCH+
                                "god" +
                                Build.VERSION.RELEASE



                        ;
                        send();
                    } else {
                        marker = mMap.addMarker(new MarkerOptions().position(latLng).title(street));
                        mMap.setMaxZoomPreference(20);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 21.0f));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }

        };
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 10, locationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
    }
        @Override
        public void onMapReady(GoogleMap googleMap){
            mMap = googleMap;
            try {
                // Customise the styling of the base map using a JSON object defined
                // in a raw resource file.
                boolean success = googleMap.setMapStyle(
                        MapStyleOptions.loadRawResourceStyle(
                                this, R.raw.mapstyle));
                if (!success) {
                    Log.e("MapsActivity", "Style parsing failed.");
                }
            } catch (Resources.NotFoundException e) {
                Log.e("MapsActivity", "Can't find style. Error: ", e);
            }
            // Position the map's camera near Sydney, Australia.
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(-34, 151)));
        }
        @Override
        protected void onStop () {
            super.onStop();
            locationManager.removeUpdates(locationListener);
        }


    public void afterTextChanged (EditText y) {
        if(ip.equals("")) {
            if (y.getText().toString().matches(IPADDRESS)) {
                ip = y.getText().toString();
                Toast.makeText(getApplicationContext(), "" + ip + "...", Toast.LENGTH_LONG).show();
                send();
            } else {
                Toast.makeText(getApplicationContext(), "Enter a valid IP adresse!", Toast.LENGTH_LONG).show();
            }
        }
        else {
            if (y.getText().toString().matches(IPADDRESS)) {
                ip = y.getText().toString();
                Toast.makeText(getApplicationContext(), "" + ip + "...", Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(), "Data is on the road...", Toast.LENGTH_LONG).show();
                send();
            }else {
                Toast.makeText(getApplicationContext(), "Enter a valid IP adresse!", Toast.LENGTH_LONG).show();
            }
        }
    }
    public void send()
    {
        try{
if(ip.equals("")){
    Toast.makeText(getApplicationContext(),"Enter a valide IP adresse",Toast.LENGTH_LONG).show();
}
else {

    myTask mt = new myTask();
    mt.execute(msg);


}
        }catch (Exception t){
        Toast.makeText(getApplicationContext(),""+ t.getMessage(),Toast.LENGTH_LONG).show();
        t.printStackTrace();
    }
    }


      class myTask extends AsyncTask<String,Void,Void>
    {

        protected Void doInBackground(String... voids) {
            try {
                s = new Socket(ip,49899);
                dos = new DataOutputStream(s.getOutputStream());
                dos.writeUTF(msg);
                dos.flush();
                dos.close();
                s.close();
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }
    }

}










