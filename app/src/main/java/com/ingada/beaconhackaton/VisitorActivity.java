package com.ingada.beaconhackaton;


import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.RemoteException;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import com.kontakt.sdk.android.configuration.ForceScanConfiguration;
import com.kontakt.sdk.android.configuration.MonitorPeriod;
import com.kontakt.sdk.android.connection.OnServiceBoundListener;
import com.kontakt.sdk.android.device.BeaconDevice;
import com.kontakt.sdk.android.device.Region;
import com.kontakt.sdk.android.manager.BeaconManager;
import java.util.HashSet;
import java.util.List;
import java.util.Set;



public class VisitorActivity extends ActionBarActivity {
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    private static final int REQUEST_CODE_ENABLE_BLUETOOTH = 1;
    private BeaconManager beaconManager;
    private SharedPreferences sharedPreferences;
    private TextView welcomeText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        welcomeText = (TextView) findViewById(R.id.welcome_text);

        final SharedPreferences mSharedPreference= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String value= (mSharedPreference.getString("NameOfShared", "Default_Value"));
        welcomeText.setText(value);

        beaconManager = BeaconManager.newInstance(this);
        beaconManager.setMonitorPeriod(MonitorPeriod.MINIMAL);
        beaconManager.setForceScanConfiguration(ForceScanConfiguration.DEFAULT);
//        beaconManager.addFilter(new Filters.CustomFilter() {
//            @Override
//            public Boolean apply(AdvertisingPackage object) {
//                return object.getBeaconUniqueId().equals("wsn9");
//            }
//        });
        beaconManager.registerMonitoringListener(new BeaconManager.MonitoringListener() {
            @Override
            public void onMonitorStart() {

                Log.d("Monitor","Start");

            } // active scan period starts

            @Override
            public void onMonitorStop() {} // passive scan period starts


            @Override
            public void onBeaconAppeared(final Region region, final BeaconDevice beacon) {

                Log.d("Beacon", "Appeared");

            } // beacon appeared within desired region for the first time

            @Override
            public void onBeaconsUpdated(final Region venue, final List<BeaconDevice> beacons) {

                Log.d("Beacon","Updated");

            } // beacons that are visible within specified region are provided through this method callback. This method has the same

            @Override
            public void onRegionEntered(final Region venue) {

            } // Android device enters the Region for the first time

            @Override
            public void onRegionAbandoned(final Region venue) {

            } // Android device abandons the region
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!beaconManager.isBluetoothEnabled()) {
            final Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, REQUEST_CODE_ENABLE_BLUETOOTH);
        } else if(beaconManager.isConnected()) {
            try {
                beaconManager.startRanging();
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        } else {
            connect();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        beaconManager.stopMonitoring();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        beaconManager.disconnect();
        beaconManager = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == REQUEST_CODE_ENABLE_BLUETOOTH) {
            if(resultCode == Activity.RESULT_OK) {
                connect();
            } else {
                Toast.makeText(this, "Bluetooth not enabled", Toast.LENGTH_LONG).show();
                getActionBar().setSubtitle("Bluetooth not enabled");
            }
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void connect() {
        try {
            beaconManager.connect(new OnServiceBoundListener() {
                @Override
                public void onServiceBound() {
                    try {
                        Set<Region> regions = new HashSet<Region>();

                     /*   UUID id = UUID.fromString("f7826da6-4fa2-4e98-8024-bc5b71e0893e");
                        Region ourBeacon = new Region (id,62609,54573);*/
//
//                        regions.add(ourBeacon);
                        beaconManager.startMonitoring();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (RemoteException e) {
            throw new IllegalStateException(e);
        }
    }
}