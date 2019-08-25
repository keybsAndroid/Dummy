package com.royalcommission.bs.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Prashant on 10/21/2018.
 */
public class MedicalTeamList {

    private String name, profilePicture, designation, department;

    public MedicalTeamList(String name, String profilePicture, String designation, String department) {
        this.name = name;
        this.profilePicture = profilePicture;
        this.designation = designation;
        this.department = department;
    }

    public MedicalTeamList() {

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

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public List<MedicalTeamList> getMedicalTeamLists() {
        List<MedicalTeamList> medicalTeamLists = new ArrayList<>();
        medicalTeamLists.add(new MedicalTeamList("Dr. Leoka Shilla Ahmed", "https://kgmthospital.com/assets/images/dr-grid-3.jpg", "MBBS, D.G.O. (DIB)", "Gynaecology and Obstetrics"));
        medicalTeamLists.add(new MedicalTeamList("Dr. Nivedita Goswami", "https://kgmthospital.com/assets/images/dr-grid-4.jpg", "MBBS, M.D.", "Paediatrics"));
        medicalTeamLists.add(new MedicalTeamList("Dr. Pradip Saikia", "https://kgmthospital.com/assets/images/dr-grid-5.jpg", "MBBS, DGO (DIB)", "PGCC (IMA)"));
        medicalTeamLists.add(new MedicalTeamList("Dr. Jutika Tahbildar", "https://kgmthospital.com/assets/images/dr-grid-6.jpg", "MBBS", "DCH (GMC)"));
        medicalTeamLists.add(new MedicalTeamList("Dr. Leoka Shilla Ahmed", "https://kgmthospital.com/assets/images/dr-grid-3.jpg", "MBBS, D.G.O. (DIB)", "Gynaecology and Obstetrics"));
        medicalTeamLists.add(new MedicalTeamList("Dr. Nivedita Goswami", "https://kgmthospital.com/assets/images/dr-grid-4.jpg", "MBBS, M.D.", "Paediatrics"));
        medicalTeamLists.add(new MedicalTeamList("Dr. Pradip Saikia", "https://kgmthospital.com/assets/images/dr-grid-5.jpg", "MBBS, DGO (DIB)", "PGCC (IMA)"));
        medicalTeamLists.add(new MedicalTeamList("Dr. Jutika Tahbildar", "https://kgmthospital.com/assets/images/dr-grid-6.jpg", "MBBS", "DCH (GMC)"));
        return medicalTeamLists;
    }
}
