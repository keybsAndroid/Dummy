package com.royalcommission.bs.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Prashant on 10/21/2018.
 */
public class TodayTaskList {

    private String name, profilePicture;

    public TodayTaskList(String name, String profilePicture) {
        this.name = name;
        this.profilePicture = profilePicture;
    }

    public TodayTaskList() {

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

    public List<TodayTaskList> getTodayTaskList() {
        List<TodayTaskList> todayTaskLists = new ArrayList<>();
        todayTaskLists.add(new TodayTaskList("Today's Medicine", "https://www.todayshospitalist.com/wp-content/uploads/2016/07/prescription-300x350.jpg"));
        todayTaskLists.add(new TodayTaskList("Today's Medical Test", "https://www.madrid.es/UnidadWeb/Contenidos/Ficheros2015/ConsumoyComercio/LaboratorioSaludPublica_260.jpg"));
        todayTaskLists.add(new TodayTaskList("Meal Information", "https://2g7bar1igrwfndph8zaqq21a-wpengine.netdna-ssl.com/wp-content/uploads/2014/12/delta-1.png"));
        todayTaskLists.add(new TodayTaskList("Physician Rounding", "https://www.nauh.org/blog/wp-content/uploads/2010/06/iStock_000009782912XSmall.jpg"));
        todayTaskLists.add(new TodayTaskList("Nurse Visit Information", "https://www.visitingnurseservice.org/images/photos/nurse4.jpg"));
        todayTaskLists.add(new TodayTaskList("Education Information", "https://www.healthcareers.nhs.uk/sites/default/files/styles/hero_image/public/hero_images/optometrists%20with%20patient.jpg?itok=9zYf5XS6"));
        return todayTaskLists;
    }
}
