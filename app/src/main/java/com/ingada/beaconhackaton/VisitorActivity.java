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
import android.view.View;
import android.widget.*;
import com.kontakt.sdk.android.configuration.ForceScanConfiguration;
import com.kontakt.sdk.android.configuration.MonitorPeriod;
import com.kontakt.sdk.android.connection.OnServiceBoundListener;
import com.kontakt.sdk.android.device.BeaconDevice;
import com.kontakt.sdk.android.device.Region;
import com.kontakt.sdk.android.factory.AdvertisingPackage;
import com.kontakt.sdk.android.factory.Filters;
import com.kontakt.sdk.android.http.KontaktApiClient;
import com.kontakt.sdk.android.manager.BeaconManager;

import java.util.*;

import com.kontakt.sdk.android.model.BrowserAction;
import com.kontakt.sdk.android.model.ContentAction;
import com.kontakt.sdk.android.model.Device;
import com.kontakt.sdk.core.Proximity;
import com.kontakt.sdk.core.exception.ClientException;
import com.kontakt.sdk.core.http.FileData;
import com.kontakt.sdk.core.http.HttpResult;

import static com.kontakt.sdk.android.factory.Filters.*;


public class VisitorActivity extends ActionBarActivity {
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    private static final int REQUEST_CODE_ENABLE_BLUETOOTH = 1;
    private BeaconManager beaconManager;
    private SharedPreferences sharedPreferences;
    private TextView welcomeText;
    private BeaconDevice currentBeacon ;
    private ListView listview;
    private ArrayList<Company> CompanyList;
    private ArrayList<String> userInterests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitor);

        welcomeText = (TextView) findViewById(R.id.welcome_text);
        listview = (ListView) findViewById(R.id.offer_list);
        CompanyList = new ArrayList<>();
        final SharedPreferences mSharedPreference= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String value= (mSharedPreference.getString("NameOfShared", "Listening..."));
        welcomeText.setText(value);

        userInterests = new ArrayList<String>() {{
            add(mSharedPreference.getString("ios_check",""));
            add(mSharedPreference.getString("android_check",""));
            add(mSharedPreference.getString("web_check",""));
            add(mSharedPreference.getString("kitten_check",""));
        }};



        //Creation of all JobOffers

        final ArrayList<JobOffer> arraylist = new ArrayList<>();
                arraylist.add(new JobOffer("Kontakt Designer", "Poet", new ArrayList<String>() {{add("ios");}}));
                arraylist.add(new JobOffer("Senior Android Developer", "it", new ArrayList<String>() {{add("android");add("ios");}}));
                arraylist.add(new JobOffer("Cat Lover", "You need the pet the cat", new ArrayList<String>() {{
                    add("kitten");
                }}));
                arraylist.add(new JobOffer("Full-stack developer", "Mobile, web, back-end everything you must have", new ArrayList<String>() {{
                    add("web");
                    add("android");
                    add("ios");
                }}));

        ArrayList<JobOffer> arraylist2 = new ArrayList<>();
            arraylist2.add(new JobOffer("Hub:Raum Ceo", "Poet", new ArrayList<String>() {{add("kitten");}}));
            arraylist2.add(new JobOffer("Entrepreneur", "Find the best business ideas", new ArrayList<String>() {{add("ios");}}));
            arraylist2.add(new JobOffer("Call Center Assistance", "Work more paid less", new ArrayList<String>() {{add("android");}}));
            arraylist2.add(new JobOffer("Developer", "Find something to develop", new ArrayList<String>() {{add("web");add("android");add("ios");}}));

        ArrayList<JobOffer> arraylist3 = new ArrayList<>();
            arraylist3.add(new JobOffer("Google intern", "Poet", new ArrayList<String>() {{add("cat");add("web");}}));
            arraylist3.add(new JobOffer("Web Designer", "We actually search for spider-man", new ArrayList<String>() {{add("web");}}));
            arraylist3.add(new JobOffer("Mobile Developer", "We love cats", new ArrayList<String>() {{add("ios");add("cat");}}));
            arraylist3.add(new JobOffer("Combine Developer", "Android Ios Web no matter", new ArrayList<String>() {{add("web");add("android");add("ios");}}));

        final Company KontaktIO = new Company("9vVd","Kontakt.io", arraylist,new ArrayList<String>() {{add("ios");add("kitten");}});
        Company HubRaum = new Company("iTXT","HubRaum", arraylist2,new ArrayList<String>() {{add("web");add("android");}});
        Company Google = new Company("IfZ4", "Google", arraylist3,new ArrayList<String>() {{add("web");add("ios");}});

        CompanyList.add(KontaktIO);
        CompanyList.add(HubRaum);
        CompanyList.add(Google);


        beaconManager = BeaconManager.newInstance(this);
        beaconManager.setMonitorPeriod(MonitorPeriod.MINIMAL);
        beaconManager.setForceScanConfiguration(ForceScanConfiguration.DEFAULT);
        beaconManager.addFilter(new CustomFilter() {
            @Override
            public Boolean apply(AdvertisingPackage object) {
                return(object.getMinor()==4545 && object.getProximity().name().equals("IMMEDIATE"));
                 //return (object.getBeaconUniqueId().equals("iTXT"));
            }
        });
        beaconManager.registerMonitoringListener(new BeaconManager.MonitoringListener() {
            @Override
            public void onMonitorStart() {

                Log.d("Monitor","Start");

            } // active scan period starts

            @Override
            public void onMonitorStop() {} // passive scan period starts


            @Override
            public void onBeaconAppeared(final Region region, final BeaconDevice beacon) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Toast.makeText(getApplicationContext(), "Appeared Black prox: "+beacon.getProximity(), Toast.LENGTH_LONG).show();
                    }});

                Log.d("Beacon", "Appeared prox: " + beacon.getProximity() + " major: " + beacon.getMajor() + " minor: " + beacon.getMinor() + " name: " + beacon.getName() + " id: " + beacon.getUniqueId() + " Tx power: " + beacon.getTxPower());

                if(currentBeacon == null || beacon.compareTo(currentBeacon)!=0)
                {
                    currentBeacon = beacon;
                    Log.d("Change","view");
                    // change view
                    for (final Company company : CompanyList) {
                        if(company.getBeacon_id().compareTo(beacon.getUniqueId())==0)
                        {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    if(listview.getAdapter()!= null)listview.setAdapter(null);
                                    welcomeText.setText(company.getName());
                                    JobOfferAdapter adapter = new JobOfferAdapter(getBaseContext(),
                                            R.layout.array, company.getJobOffersByInterest(userInterests));
                                    listview.setAdapter(adapter);

                                    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position,
                                                                long id) {
                                            JobOffer obj = (JobOffer) parent.getAdapter().getItem(position);
                                            String title = (String) obj.getTitle();
                                            Log.d("Yourtag", title);

                                            Intent OfferDetailIntent = new Intent(VisitorActivity.this, OfferDetailActivity.class);
                                            OfferDetailIntent.putExtra("jobTitle", obj.getTitle());
                                            OfferDetailIntent.putExtra("jobDesc", obj.getDescription());
                                            startActivity(OfferDetailIntent);
                                        }
                                    });
                                    adapter.notifyDataSetChanged();
                                }
                            });



                        }
                        Log.d("id","id");
                    }
                }


            } // beacon appeared within desired region for the first time

            @Override
            public void onBeaconsUpdated(final Region venue, final List<BeaconDevice> beacons) {


                for (final BeaconDevice beacon : beacons) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Toast.makeText(getApplicationContext(), "Update Black + prox: "+beacon.getProximity(), Toast.LENGTH_LONG).show();
                        }});


                    Log.d("Beacon","Updated prox: " + beacon.getProximity()+ " major: "+ beacon.getMajor()+" minor: "+beacon.getMinor()+" name: "+ beacon.getName() +" id: "+beacon.getUniqueId()+ " Tx power: "+beacon.getTxPower());

                    if(currentBeacon != null && beacon.compareTo(currentBeacon)!=0)
                    {
                        currentBeacon = beacon;
                        for (final Company company : CompanyList) {

                            if(company.getBeacon_id().compareTo(beacon.getUniqueId())==0)
                            {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if(listview.getAdapter()!= null)listview.setAdapter(null);
                                        welcomeText.setText(company.getName());
                                        JobOfferAdapter adapter = new JobOfferAdapter(getBaseContext(),
                                                R.layout.array, company.getJobOffersByInterest(userInterests));
                                        listview.setAdapter(adapter);
                                        adapter.notifyDataSetChanged();
                                    }
                                });
                            }
                        }
                    }

                }


            } // beacons that are visible within specified region are provided through this method callback. This method has the same

            @Override
            public void onRegionEntered(final Region venue) {

                Log.d("Region","Entered major: "+venue.getMajor()+" minor: "+venue.getMinor());

            } // Android device enters the Region for the first time

            @Override
            public void onRegionAbandoned(final Region venue) {

                Log.d("Region","Abandoned major: "+venue.getMajor()+" minor: "+venue.getMinor());

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