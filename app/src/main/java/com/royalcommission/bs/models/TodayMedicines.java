package com.royalcommission.bs.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Prashant on 10/21/2018.
 */
public class TodayMedicines {

    private String name, profilePicture;

    public TodayMedicines(String name, String profilePicture) {
        this.name = name;
        this.profilePicture = profilePicture;
    }

    public TodayMedicines() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public List<TodayMedicines> getMedicinesList() {
        List<TodayMedicines> todayTaskLists = new ArrayList<>();
        todayTaskLists.add(new TodayMedicines("Ulcerant", "https://cdn166.picsart.com/219378997022202.jpg?c256x256"));
        todayTaskLists.add(new TodayMedicines("Anti Ulcerant", "https://www.verywellhealth.com/thmb/4-rIiCoS7ax8tmehcE37PdilVho=/768x0/filters:no_upscale():max_bytes(150000):strip_icc()/single-aspirin-pill-with-copy-space-161933990-5971290eaf5d3a0011214501.jpg"));
        todayTaskLists.add(new TodayMedicines("Antibiotic", "https://cdn.hepatitisc.uw.edu/doc/195-1/capsules-ribavirin-generic-teva.jpg"));
        todayTaskLists.add(new TodayMedicines("Ulcerant", "http://images.medscape.com/pi/features/drugdirectory/octupdate/PAR00450.jpg"));
        todayTaskLists.add(new TodayMedicines("Anti Ulcerant", "https://i.dailymail.co.uk/i/pix/2016/11/07/23/19C99F9500000578-3914960-image-m-98_1478562800089.jpg"));
        todayTaskLists.add(new TodayMedicines("Antibiotic", "http://filmcoating-troubleshooting.com/wp-content/uploads/2014/08/LogoBridging-800x600.jpg"));

        return todayTaskLists;
    }
}
