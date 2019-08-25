package com.royalcommission.bs.modules.api.model;

import com.google.gson.annotations.SerializedName;

public class InpatientInfo {


    @SerializedName("PT_NO")
    private String patientId;
    @SerializedName("PT_NM")
    private String patientName;
    @SerializedName("SEX")
    private String gender;
    @SerializedName("AGE")
    private String age;
    @SerializedName("BED_NO")
    private String bedNumber;
    @SerializedName("MED_DEPT_NM")
    private String dept;
    @SerializedName("ADS_DT")
    private String date;
    @SerializedName("DIAGNOSIS_NAME")
    private String diagnosis;
    @SerializedName("ALRG_YN")
    private String allergy;
    @SerializedName("PACT_ID")
    private String appointmentId;

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getBedNumber() {
        return bedNumber;
    }

    public void setBedNumber(String bedNumber) {
        this.bedNumber = bedNumber;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
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

    public String getAllergy() {
        return allergy;
    }

    public void setAllergy(String allergy) {
        this.allergy = allergy;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }
}
