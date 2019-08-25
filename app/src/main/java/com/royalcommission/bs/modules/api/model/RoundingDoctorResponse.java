package com.royalcommission.bs.modules.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RoundingDoctorResponse {
    @SerializedName("CommonResponse")
    private BaseResponse baseResponse;

    @SerializedName("DoctorRounding")
    private List<String> roundingDoctorList;

    public BaseResponse getBaseResponse() {
        return baseResponse;
    }

    public void setBaseResponse(BaseResponse baseResponse) {
        this.baseResponse = baseResponse;
    }

    public List<String> getRoundingDoctorList() {
        return roundingDoctorList;
    }

    public void setRoundingDoctorList(List<String> roundingDoctorList) {
        this.roundingDoctorList = roundingDoctorList;
    }
}
