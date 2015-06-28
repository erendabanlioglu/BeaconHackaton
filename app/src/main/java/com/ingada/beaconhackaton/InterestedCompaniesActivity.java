package com.ingada.beaconhackaton;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;


public class InterestedCompaniesActivity extends ActionBarActivity {

    private Button nextButton;
    private ArrayList<Company> CompanyList = new ArrayList<>();
    private ArrayList<String> userInterests;
    private ListView matchedCompaniesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intereste_companies);

        nextButton = (Button) findViewById(R.id.buttonInterestNext);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent visitor_intent = new Intent(InterestedCompaniesActivity.this, VisitorActivity.class);
                startActivity(visitor_intent);
            }
        });

        final ArrayList<JobOffer> arraylist = new ArrayList<>();

        ArrayList<JobOffer> arraylist2 = new ArrayList<>();

        ArrayList<JobOffer> arraylist3 = new ArrayList<>();

        final Company KontaktIO = new Company("9vVd","Kontakt.io", arraylist,new ArrayList<String>() {{add("kitten");}});
        Company HubRaum = new Company("iTXT","HubRaum", arraylist2,new ArrayList<String>() {{add("web");add("android");}});
        Company Google = new Company("IfZ4", "Google", arraylist3,new ArrayList<String>() {{add("web");add("ios");}});

        CompanyList.add(KontaktIO);
        CompanyList.add(HubRaum);
        CompanyList.add(Google);

        final SharedPreferences mSharedPreference= PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        userInterests = new ArrayList<String>() {{
            add(mSharedPreference.getString("ios_check",""));
            add(mSharedPreference.getString("android_check",""));
            add(mSharedPreference.getString("web_check",""));
            add(mSharedPreference.getString("kitten_check",""));
        }};

        ArrayList<Company> matchedCompanies = getCompaniesByInterest(userInterests);

        matchedCompaniesListView = (ListView) findViewById(R.id.listViewMatchedCompanies);

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1);

        for (Company matchedCompany : matchedCompanies) {

            adapter.add(matchedCompany.getName());
        }


        matchedCompaniesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                String obj = (String) parent.getAdapter().getItem(position);

                AlertDialog alertDialog = new AlertDialog.Builder(InterestedCompaniesActivity.this).create();
                alertDialog.setTitle(obj);

                alertDialog.setMessage("hub:raum connects the digital startup ecosystem with Deutsche Telekom, linking tech entrepreneurs and high growth startup companies with the expert network, capital, and business opportunities of Deutsche Telekom. hub:raum offers various programs in three locations: Berlin (covering Germany and Western Europe), Krakow (serving CEE region), and Tel Aviv (Israel).");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Take me there",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();

            }
        });

        matchedCompaniesListView.setAdapter(adapter);

        if(matchedCompanies.size()==0)
        {
            Intent visitor_intent = new Intent(InterestedCompaniesActivity.this, VisitorActivity.class);
            startActivity(visitor_intent);
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_intereste_companies, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public ArrayList<Company> getCompaniesByInterest(ArrayList<String> interests)
    {
        ArrayList<Company> companiesFilteredByInterest = new ArrayList<>();

        for (Company company : CompanyList) {

            for (String interest : company.getInterests()) {

                if(interests.contains(interest))
                {
                    companiesFilteredByInterest.add(company);
                    break;
                }
            }
        }

        return companiesFilteredByInterest;
    }
}
