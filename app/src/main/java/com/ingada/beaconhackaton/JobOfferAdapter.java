package com.ingada.beaconhackaton;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class JobOfferAdapter extends ArrayAdapter<JobOffer> {

    public JobOfferAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public JobOfferAdapter(Context context, int resource, ArrayList<JobOffer> items) {
        super(context, resource, items);
    }

    final SharedPreferences mSharedPreference= PreferenceManager.getDefaultSharedPreferences(getContext());
    String value= (mSharedPreference.getString("NameOfShared", "Default_Value"));

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.array, null);
        }

        JobOffer p = getItem(position);


        if (p != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.offer_tittle);
            TextView tt2 = (TextView) v.findViewById(R.id.offer_description);


            if (tt1 != null) {
                if(value.equals("poet")) {tt1.setText("POET");}
                tt1.setText(p.getTitle());
            }

            if (tt2 != null) {
                tt2.setText(p.getDescription());
            }


        }

        return v;
    }
}
