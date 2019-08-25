package com.royalcommission.bs.modules.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DischargeInfoResponse {

    @SerializedName("CommonResponse")
    private BaseResponse baseResponse;

    @SerializedName("DischargesInformationModel")
    private List<DischargeInfo> dischargeInfoList;

    public BaseResponse getBaseResponse() {
        return baseResponse;
    }

    public void setBaseResponse(BaseResponse baseResponse) {
        this.baseResponse = baseResponse;
    }

    public List<DischargeInfo> getDischargeInfoList() {
        return dischargeInfoList;
    }

    public void setDischargeInfoList(List<DischargeInfo> dischargeInfoList) {
        this.dischargeInfoList = dischargeInfoList;
    }
}
