package com.ingada.beaconhackaton;


import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;


public class MainActivity extends ActionBarActivity {


    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "nameKey";
    SharedPreferences sharedpreferences;
    private Button visitor_button;
    private CheckBox int1;
    private CheckBox int2;
    private CheckBox int3;
    private CheckBox int4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        int1 = (CheckBox) findViewById(R.id.ios_check);
        int2 = (CheckBox) findViewById(R.id.android_check);
        int3 = (CheckBox) findViewById(R.id.web_check);
        int4 = (CheckBox) findViewById(R.id.kitten_check);


        //Shared



        


        visitor_button = (Button) findViewById(R.id.button_visitor);
        visitor_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = prefs.edit();
                editor.clear();
                editor.commit();

                if (int1.isChecked()) {
                    editor.putString("ios_check", "ios");
                    editor.commit();
                }
                if (int2.isChecked()) {
                    editor.putString("android_check", "android");
                    editor.commit();
                }
                if (int3.isChecked()) {
                    editor.putString("web_check", "web");
                    editor.commit();
                }
                if (int4.isChecked()) {
                    editor.putString("kitten_check", "kitten");
                    editor.commit();}

                Intent interest_intent = new Intent(MainActivity.this, InterestedCompaniesActivity.class);
                startActivity(interest_intent);

                Log.d("login", "login");
            }
        });

               final Intent visitorIntent = new Intent(MainActivity.this, VisitorActivity.class);

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }



}

