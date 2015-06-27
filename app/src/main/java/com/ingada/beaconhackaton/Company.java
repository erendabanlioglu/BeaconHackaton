package com.ingada.beaconhackaton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bianka on 2015-06-27.
 */
public class Company {
    private String beacon_id;
    private  String name;
    private ArrayList<JobOffer> jobOffers;

    public ArrayList<JobOffer> getJobOffers() {
        return jobOffers;
    }

    public void setJobOffers(ArrayList<JobOffer> jobOffers) {
        this.jobOffers = jobOffers;
    }

    public String getBeacon_id() {
        return beacon_id;
    }

    public void setBeacon_id(String beacon_id) {
        this.beacon_id = beacon_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
