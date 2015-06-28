package com.ingada.beaconhackaton;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class OfferDetailActivity extends ActionBarActivity {

    
    Button applyButton;
    Button lnApplyButton;
    TextView offerTitle,offerDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_detail);
        applyButton = (Button) findViewById(R.id.activity_offer_button);
        lnApplyButton = (Button) findViewById(R.id.activity_offer_ln_button);

        applyButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                Toast.makeText(getApplicationContext(), "Thanks for applying", Toast.LENGTH_LONG).show();
            }
        });
        lnApplyButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                Toast.makeText(getApplicationContext(), "Thanks for applying", Toast.LENGTH_LONG).show();
            }
        });


        offerTitle = (TextView) findViewById(R.id.activity_offer_description);
        offerDesc = (TextView) findViewById(R.id.textView2);

        Intent intent = getIntent();
        offerTitle.setText(intent.getStringExtra("jobTitle"));
        offerDesc.setText(intent.getStringExtra("jobDesc"));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

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
}
