package com.royalcommission.bs.modules.api.model;

import com.google.gson.annotations.SerializedName;

public class DoctorLoginResponse {

    @SerializedName("CommonResponse")
    private BaseResponse baseResponse;

    @SerializedName("DoctorLoginResponse")
    private String doctorLoginResponse;

    public BaseResponse getBaseResponse() {
        return baseResponse;
    }

    public void setBaseResponse(BaseResponse baseResponse) {
        this.baseResponse = baseResponse;
    }

    public String getDoctorLoginResponse() {
        return doctorLoginResponse;
    }

    public void setDoctorLoginResponse(String doctorLoginResponse) {
        this.doctorLoginResponse = doctorLoginResponse;
    }
}
