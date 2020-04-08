package com.example.welcome;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;
import java.util.List;

public class details extends AppCompatActivity {
    private TextView infos ;
    public static String battery;
    public static String lvl="";
    String SIM;
    TelephonyManager tm;
    Button exit;
    Date date = new Date();
    final long time =  date.getDate();
    /*battery level!!!*/
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            battery = String.valueOf(level) + "%";
lvl = battery;

        }
    };

    @SuppressLint("SetTextI18n")

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        
        this.registerReceiver(this.broadcastReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        infos = (TextView)findViewById(R.id.infos) ;
        WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        String server = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());


        infos.setText(String.format("\nProduct : %s\n BRAND :%s\n BOARD:%s\n BOOTLOADER: %s\n DEVICE: %s\n HARDWARE: %s\n HOST: %s\n ID: %s\n MANUFACTUER: %s\n MODEL: %s\n TAGS: %s\n TYPE: %s\n USER: %s\n TIME: %d\n Battery: %s\n version : %s\n time : %s\n @IP : %s", Build.PRODUCT, Build.BRAND, Build.BOARD, Build.BOOTLOADER, Build.DEVICE, Build.HARDWARE, Build.HOST, Build.ID, Build.MANUFACTURER, Build.MODEL, Build.TAGS, Build.TYPE, Build.USER, Build.TIME, lvl, Build.VERSION.RELEASE, date.getYear(),server)


        );
        exit = findViewById(R.id.exit1);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                details.this.finish();

            }
        });
    }
}
