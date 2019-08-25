package com.keybs.rc.modules.network.retrofit.model.responses;

import com.google.gson.annotations.SerializedName;

public class ScanResult {

    @SerializedName("ORD_ID")
    private String orderId;

    @SerializedName("PT_NO")
    private String patientNumber;

    @SerializedName("PT_NM")
    private String patientName;

    @SerializedName("EXM_NM")
    private String scanName;

    @SerializedName("ACPT_DTM")
    private String date;

    @SerializedName("IPTN_CNCS_CNTE")
    private String scanDetails;

    @SerializedName("STATUS")
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPatientNumber() {
        return patientNumber;
    }

    public void setPatientNumber(String patientNumber) {
        this.patientNumber = patientNumber;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getScanName() {
        return scanName;
    }

    public void setScanName(String scanName) {
        this.scanName = scanName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getScanDetails() {
        return scanDetails;
    }

    public void setScanDetails(String scanDetails) {
        this.scanDetails = scanDetails;
    }


}
