package com.royalcommission.bs.modules.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RoundingDoctorNurseResponse {

    @SerializedName("CommonResponse")
    private BaseResponse baseResponse;

    @SerializedName("NurseNames")
    private List<String> roundingNurseList;

    @SerializedName("DoctorNames")
    private List<String> roundingDoctorList;

    public BaseResponse getBaseResponse() {
        return baseResponse;
    }

    public void setBaseResponse(BaseResponse baseResponse) {
        this.baseResponse = baseResponse;
    }

    public List<String> getRoundingNurseList() {
        return roundingNurseList;
    }

    public void setRoundingNurseList(List<String> roundingNurseList) {
        this.roundingNurseList = roundingNurseList;
    }

    public List<String> getRoundingDoctorList() {
        return roundingDoctorList;
    }

    public void setRoundingDoctorList(List<String> roundingDoctorList) {
        this.roundingDoctorList = roundingDoctorList;
    }
}
