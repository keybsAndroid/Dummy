package com.royalcommission.bs.modules.api.model;

import com.google.gson.annotations.SerializedName;

public class OperationInfo {

    @SerializedName("PT_NO")
    private String patientID;
    @SerializedName("PACT_ID")
    private String appID;
    @SerializedName("OPRM_NM")
    private String room;
    @SerializedName("OP_EXPT_DT")
    private String date;
    @SerializedName("DGNS_NM")
    private String diagnosis;
    @SerializedName("OP_NM")
    private String name;

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
