package com.royalcommission.bs.modules.api.model;

import com.google.gson.annotations.SerializedName;

public class Pathology {

    @SerializedName("ORD_ID")
    private String orderID;

    @SerializedName("HSP_TP_CD")
    private String hospitalID;

    @SerializedName("ORD_DT")
    private String orderDate;

    @SerializedName("ORD_NM")
    private String orderName;

    @SerializedName("POST_ORD_NM")
    private String postOrderName;

    @SerializedName("PT_HME_DEPT_CD")
    private String departmentCode;

    @SerializedName("PACT_ID")
    private String appointmentID;

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getHospitalID() {
        return hospitalID;
    }

    public void setHospitalID(String hospitalID) {
        this.hospitalID = hospitalID;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getPostOrderName() {
        return postOrderName;
    }

    public void setPostOrderName(String postOrderName) {
        this.postOrderName = postOrderName;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(String appointmentID) {
        this.appointmentID = appointmentID;
    }


}
