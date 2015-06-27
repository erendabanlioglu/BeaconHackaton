package com.ingada.beaconhackaton;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.RemoteException;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.kontakt.sdk.android.configuration.ForceScanConfiguration;
import com.kontakt.sdk.android.configuration.MonitorPeriod;
import com.kontakt.sdk.android.connection.OnServiceBoundListener;
import com.kontakt.sdk.android.device.BeaconDevice;
import com.kontakt.sdk.android.device.Region;
import com.kontakt.sdk.android.manager.BeaconManager;
import java.util.List;
import java.util.Set;


public class MainActivity extends ActionBarActivity {
    private static final int REQUEST_CODE_ENABLE_BLUETOOTH = 1;

    private BeaconManager beaconManager;
    private EditText logintext;
    private Button loginbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //logn field mock
        logintext = (EditText) findViewById(R.id.login);
        loginbutton = (Button) findViewById(R.id.loginbutton);
        final String loginValue = logintext.getText().toString();
        beaconManager = BeaconManager.newInstance(this);
        beaconManager.setMonitorPeriod(MonitorPeriod.MINIMAL);
        beaconManager.setForceScanConfiguration(ForceScanConfiguration.DEFAULT);
        beaconManager.registerMonitoringListener(new BeaconManager.MonitoringListener() {
            @Override
            public void onMonitorStart() {

                Log.d("Monitor","Start");

            } // active scan period starts

            @Override
            public void onMonitorStop() {} // passive scan period starts
        final Intent visitorintent = new Intent(MainActivity.this, VisitorActivity.class);
        loginbutton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (loginValue == "visitor") {
                            startActivity(visitorintent);
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "You shall not pass",Toast.LENGTH_LONG ).show();
                        }
                    }
                });

            @Override
            public void onBeaconAppeared(final Region region, final BeaconDevice beacon) {

                Log.d("Beacon","Appeared");

            } // beacon appeared within desired region for the first time

            @Override
            public void onBeaconsUpdated(final Region venue, final List<BeaconDevice> beacons) {

                Log.d("Beacon","Updated");

            } // beacons that are visible within specified region are provided through this method callback. This method has the same

            @Override
            public void onRegionEntered(final Region venue) {

                Toast toast = Toast.makeText(getApplicationContext() ,"Entered Beacon area", Toast.LENGTH_LONG);
                toast.show();

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