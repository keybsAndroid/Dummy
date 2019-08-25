package com.royalcommission.bs.modules.api.model;

import com.google.gson.annotations.SerializedName;

public class SpecialClinicDetail {

    @SerializedName("ORD_ID")
    private String orderId;

    @SerializedName("HSP_TP_CD")
    private String hospitalCode;


    @SerializedName("ORD_DT")
    private String date;

    @SerializedName("ORD_NM")
    private String name;

    @SerializedName("POST_ORD_NM")
    private String result;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getHospitalCode() {
        return hospitalCode;
    }

    public void setHospitalCode(String hospitalCode) {
        this.hospitalCode = hospitalCode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

}
