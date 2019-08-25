package com.royalcommission.bs.modules.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RoundingNurseResponse {

    @SerializedName("CommonResponse")
    private BaseResponse baseResponse;

    @SerializedName("NurseInformation")
    private List<String> roundingNurseList;

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
}
