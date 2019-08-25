package com.royalcommission.bs.modules.api.model;

import com.google.gson.annotations.SerializedName;

public class DischargeInfo {

    @SerializedName("ORD_ID")
    private String orderID;

    @SerializedName("PT_NO")
    private String patientID;

    @SerializedName("PACT_ID")
    private String appointmentID;

    @SerializedName("ORD_NM")
    private String orderName;

    @SerializedName("DWT_HOPE_MED_DEPT_NM")
    private String deptName;

    @SerializedName("HOPE_MED_SRV_NM")
    private String serviceName;

    @SerializedName("DWT_HOPE_MED_STF_NM")
    private String staffName;




    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(String appointmentID) {
        this.appointmentID = appointmentID;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

}
