package com.ingada.beaconhackaton;

import java.util.ArrayList;

/**
 * Created by Bianka on 2015-06-27.
 */
public class JobOffer {
    private String title; //position
    private String description;
    private String interests;

    public JobOffer(String title, String description, String interests){
        this.title = title;
        this.description = description;
        this.interests = interests;

    }
    public JobOffer(){}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }
}
