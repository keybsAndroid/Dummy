package com.keybs.rc.modules.network.retrofit.model.responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Prashant on 8/7/2018.
 */
public class VerifyOTPResponse {
    @SerializedName("CommonResponse")
    private BaseResponse baseResponse;

    @SerializedName("PatientResponse")
    private Patient patient;

    public BaseResponse getBaseResponse() {
        return baseResponse;
    }

    public void setBaseResponse(BaseResponse baseResponse) {
        this.baseResponse = baseResponse;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
