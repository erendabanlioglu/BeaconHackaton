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

    public ArrayList<String> getInterests() {
        return interests;
    }

    public void setInterests(ArrayList<String> interests) {
        this.interests = interests;
    }

    private ArrayList<String> interests;

    public Company(String beacon_id, String name, ArrayList<JobOffer> jobOffers,ArrayList<String> interests) {
        this.beacon_id = beacon_id;
        this.name = name;
        this.jobOffers = jobOffers;
        this.interests = interests;
    }

    public ArrayList<JobOffer> getJobOffers() {
        return jobOffers;
    }

    public ArrayList<JobOffer> getJobOffersByInterest(ArrayList<String> interest) {

        ArrayList<JobOffer> jobOffersFilteredByInterest = new ArrayList<>();

        for (JobOffer jobOffer : jobOffers) {

            for (String jobInterest : jobOffer.getInterests()) {

                if(interest.contains(jobInterest)) {
                    jobOffersFilteredByInterest.add(jobOffer);
                    break;
                }
            }
        }
        return jobOffersFilteredByInterest;
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
