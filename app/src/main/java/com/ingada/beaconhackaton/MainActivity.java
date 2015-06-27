package com.ingada.beaconhackaton;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {


    public static final String MY_PREFS_NAME = "MyPrefsFile";

    private Button visitor_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        visitor_button = (Button) findViewById(R.id.button_visitor);
        visitor_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                Intent visitor_intent = new Intent(MainActivity.this, VisitorActivity.class);
                startActivity(visitor_intent);
            }
        });
        //Shared Preferences
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString("name", "Elena");
        editor.putInt("idName", 12);
        editor.commit();


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