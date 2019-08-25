package com.royalcommission.bs.modules.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Prashant on 8/7/2018.
 */
public class BaseResponse {

    @SerializedName("LocationCode")
    private String locationCode;

    @SerializedName("ResponseCode")
    private int responseCode;

    @SerializedName("ResponseMessage")
    private String responseMessage;

    @SerializedName("RCHResponseCode")
    private String rchResponseCode;

    @SerializedName("RCHResponseMessage")
    private String rchResponseMessage;

    @SerializedName("GUSessionID")
    private String guSessionID;

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getRchResponseCode() {
        return rchResponseCode;
    }

    public void setRchResponseCode(String rchResponseCode) {
        this.rchResponseCode = rchResponseCode;
    }

    public String getRchResponseMessage() {
        return rchResponseMessage;
    }

    public void setRchResponseMessage(String rchResponseMessage) {
        this.rchResponseMessage = rchResponseMessage;
    }

    public String getGuSessionID() {
        return guSessionID;
    }

    public void setGuSessionID(String guSessionID) {
        this.guSessionID = guSessionID;
    }
}
