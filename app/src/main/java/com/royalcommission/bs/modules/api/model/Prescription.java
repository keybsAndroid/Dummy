package com.royalcommission.bs.modules.api.model;

import com.google.gson.annotations.SerializedName;
import com.royalcommission.bs.modules.utils.CommonUtils;
import com.royalcommission.bs.modules.utils.DateUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Created by Prashant on 12/23/2018.
 */
public class Prescription {

    @SerializedName("Endofdose")
    private String endOfDose;

    @SerializedName("QuantityOfDose")
    private float quantityOfDose;

    @SerializedName("StartOfDose")
    private String startOfDose;

    @SerializedName("DurationOfDoseInDays")
    private int durationOfDoseInDays;

    @SerializedName("Medicine")
    private String medicine;

    @SerializedName("AppDate")
    private String appDate;

    @SerializedName("VisitType")
    private String VisitType;

    @SerializedName("TypeOfDose")
    private String typeOfDose;

    @SerializedName("AppointmentId")
    private String appointmentId;

    @SerializedName("RefillPossibleNoOfTimes")
    private String refillPossibleNoOfTimes;

    @SerializedName("RefillProgressNumberOfTimes")
    private String refillProgressNumberOfTimes;

    @SerializedName("RefillExpirationDate")
    private String refillExpirationDate;

    @SerializedName("MedicineRefillPossibleNumberOfTimes")
    private String medicineRefillPossibleNumberOfTimes;


    @SerializedName("PRPD_NOTM")
    private String perDayFrequency;

    @SerializedName("BDY_NOTM")
    private String dailyFrequency;

    private List<Prescription> prescriptionList;

    public Prescription() {

    }

    public Prescription(String medicine, String endOfDose, String startOfDose, String typeOfDose, float quantityOfDose, int duration) {
        this.medicine = medicine;
        this.endOfDose = endOfDose;
        this.startOfDose = startOfDose;
        this.typeOfDose = typeOfDose;
        this.quantityOfDose = quantityOfDose;
        this.durationOfDoseInDays = duration;
    }


    public String getEndOfDose() {
        return endOfDose;
    }

    public void setEndOfDose(String endOfDose) {
        this.endOfDose = endOfDose;
    }

    public String getStartOfDose() {
        return startOfDose;
    }

    public void setStartOfDose(String startOfDose) {
        this.startOfDose = startOfDose;
    }

    public int getDurationOfDoseInDays() {
        return durationOfDoseInDays;
    }

    public void setDurationOfDoseInDays(int durationOfDoseInDays) {
        this.durationOfDoseInDays = durationOfDoseInDays;
    }

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public String getAppDate() {
        return appDate;
    }

    public void setAppDate(String appDate) {
        this.appDate = appDate;
    }

    public String getTypeOfDose() {
        return typeOfDose;
    }

    public void setTypeOfDose(String typeOfDose) {
        this.typeOfDose = typeOfDose;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public float getQuantityOfDose() {
        return quantityOfDose;
    }

    public void setQuantityOfDose(float quantityOfDose) {
        this.quantityOfDose = quantityOfDose;
    }

    public String getRefillPossibleNoOfTimes() {
        return refillPossibleNoOfTimes;
    }

    public void setRefillPossibleNoOfTimes(String refillPossibleNoOfTimes) {
        this.refillPossibleNoOfTimes = refillPossibleNoOfTimes;
    }

    public String getRefillProgressNumberOfTimes() {
        return refillProgressNumberOfTimes;
    }

    public void setRefillProgressNumberOfTimes(String refillProgressNumberOfTimes) {
        this.refillProgressNumberOfTimes = refillProgressNumberOfTimes;
    }

    public String getRefillExpirationDate() {
        return refillExpirationDate;
    }

    public void setRefillExpirationDate(String refillExpirationDate) {
        this.refillExpirationDate = refillExpirationDate;
    }

    public String getMedicineRefillPossibleNumberOfTimes() {
        return medicineRefillPossibleNumberOfTimes;
    }

    public void setMedicineRefillPossibleNumberOfTimes(String medicineRefillPossibleNumberOfTimes) {
        this.medicineRefillPossibleNumberOfTimes = medicineRefillPossibleNumberOfTimes;
    }

    public String getPerDayFrequency() {
        return perDayFrequency;
    }

    public void setPerDayFrequency(String perDayFrequency) {
        this.perDayFrequency = perDayFrequency;
    }

    public String getDailyFrequency() {
        return dailyFrequency;
    }

    public void setDailyFrequency(String dailyFrequency) {
        this.dailyFrequency = dailyFrequency;
    }

    public String getVisitType() {
        return VisitType;
    }

    public void setVisitType(String visitType) {
        VisitType = visitType;
    }

    public List<Prescription> getPrescriptionList() {
        return prescriptionList;
    }

    public void setPrescriptionList(List<Prescription> prescriptionList) {
        this.prescriptionList = prescriptionList;
    }


    public class CompareByCreateDate implements Comparator<Prescription> {
        @Override
        public int compare(Prescription p1, Prescription p2) {

            if (CommonUtils.isValidString(p1.appDate) && CommonUtils.isValidString(p2.appDate)) {
                if (DateUtils.getDate(p1.appDate) != null && DateUtils.getDate(p2.appDate) != null) {
                    if (Objects.requireNonNull(DateUtils.getDate(p1.appDate)).after(DateUtils.getDate(p2.appDate))) {
                        return 1;
                    }
                }
            }

            if (CommonUtils.isValidString(p1.appDate) && CommonUtils.isValidString(p2.appDate)) {
                if (DateUtils.getDate(p1.appDate) != null && DateUtils.getDate(p2.appDate) != null) {
                    if (Objects.requireNonNull(DateUtils.getDate(p1.appDate)).before(DateUtils.getDate(p2.appDate))) {
                        return -1;
                    }
                }
            }
            return 0;
        }

        @Override
        public boolean equals(Object obj) {
            return super.equals(obj);
        }
    }

}
