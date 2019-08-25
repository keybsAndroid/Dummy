package com.keybs.rc.modules.network.retrofit.model.responses;

import com.google.gson.annotations.SerializedName;

public class CalculateCharge {


    @SerializedName("status")
    private boolean status;

    @SerializedName("message")
    private String message;

    @SerializedName("ToTalAmount")
    private int totalAmount;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

}
