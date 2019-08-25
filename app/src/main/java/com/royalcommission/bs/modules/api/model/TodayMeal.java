package com.royalcommission.bs.modules.api.model;

import com.google.gson.annotations.SerializedName;

public class TodayMeal implements Comparable<TodayMeal> {

    @SerializedName("PACT_ID")
    private String appointmentId;

    @SerializedName("PT_NO")
    private String patientId;

    @SerializedName("PT_NM")
    private String patientName;

    @SerializedName("SPML_DT")
    private String mealDate;

    @SerializedName("ML_NM")
    private String mealName;

    @SerializedName("SPML_DLML_TP_CD")
    private int mealCode;

    @SerializedName("TH1_MNSN_CNTE")
    private String patientBreakFast;

    @SerializedName("TH1_NNSN_CNTE")
    private String patientLunch;

    @SerializedName("TH1_EVSN_CNTE")
    private String patientDinner;

    @SerializedName("TH1_SN3_CNTE")
    private String patient3oClockSnack;

    @SerializedName("TH1_SN10_CNTE")
    private String patient10oClockSnack;

    @SerializedName("TH2_MNSN_CNTE")
    private String careGiverBreakFast;

    @SerializedName("TH2_NNSN_CNTE")
    private String careGiverLunch;

    @SerializedName("TH2_EVSN_CNTE")
    private String careGiverDinner;

    @SerializedName("TH2_SN3_CNTE")
    private String careGiver3oClockSnack;

    @SerializedName("TH2_SN10_CNTE")
    private String careGiver10oClockSnack;

    @SerializedName("LSH_STF_NM")
    private String staffName;

    @SerializedName("DETAIL")
    private String mealDetail;


    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

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

    public String getMealDate() {
        return mealDate;
    }

    public void setMealDate(String mealDate) {
        this.mealDate = mealDate;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public String getPatientBreakFast() {
        return patientBreakFast;
    }

    public void setPatientBreakFast(String patientBreakFast) {
        this.patientBreakFast = patientBreakFast;
    }

    public String getPatientLunch() {
        return patientLunch;
    }

    public void setPatientLunch(String patientLunch) {
        this.patientLunch = patientLunch;
    }

    public String getPatientDinner() {
        return patientDinner;
    }

    public void setPatientDinner(String patientDinner) {
        this.patientDinner = patientDinner;
    }

    public String getPatient3oClockSnack() {
        return patient3oClockSnack;
    }

    public void setPatient3oClockSnack(String patient3oClockSnack) {
        this.patient3oClockSnack = patient3oClockSnack;
    }

    public String getCareGiverBreakFast() {
        return careGiverBreakFast;
    }

    public void setCareGiverBreakFast(String careGiverBreakFast) {
        this.careGiverBreakFast = careGiverBreakFast;
    }

    public String getCareGiverLunch() {
        return careGiverLunch;
    }

    public void setCareGiverLunch(String careGiverLunch) {
        this.careGiverLunch = careGiverLunch;
    }

    public String getCareGiverDinner() {
        return careGiverDinner;
    }

    public void setCareGiverDinner(String careGiverDinner) {
        this.careGiverDinner = careGiverDinner;
    }

    public String getCareGiver3oClockSnack() {
        return careGiver3oClockSnack;
    }

    public void setCareGiver3oClockSnack(String careGiver3oClockSnack) {
        this.careGiver3oClockSnack = careGiver3oClockSnack;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }


    public String getMealDetail() {
        return mealDetail;
    }

    public void setMealDetail(String mealDetail) {
        this.mealDetail = mealDetail;
    }

    public int getMealCode() {
        return mealCode;
    }

    public void setMealCode(int mealCode) {
        this.mealCode = mealCode;
    }

    public String getCareGiver10oClockSnack() {
        return careGiver10oClockSnack;
    }

    public void setCareGiver10oClockSnack(String careGiver10oClockSnack) {
        this.careGiver10oClockSnack = careGiver10oClockSnack;
    }

    public String getPatient10oClockSnack() {
        return patient10oClockSnack;
    }

    public void setPatient10oClockSnack(String patient10oClockSnack) {
        this.patient10oClockSnack = patient10oClockSnack;
    }

    @Override
    public int compareTo(TodayMeal todayMeal) {
        return this.mealCode - todayMeal.getMealCode();
    }
}
