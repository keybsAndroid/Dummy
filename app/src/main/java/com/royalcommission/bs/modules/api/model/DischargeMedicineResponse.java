package com.royalcommission.bs.modules.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DischargeMedicineResponse {

    @SerializedName("CommonResponse")
    private BaseResponse baseResponse;

    @SerializedName("DischargeDrugs")
    private List<String> dischargeMedicineList;

    public BaseResponse getBaseResponse() {
        return baseResponse;
    }

    public void setBaseResponse(BaseResponse baseResponse) {
        this.baseResponse = baseResponse;
    }


    public List<String> getDischargeMedicineList() {
        return dischargeMedicineList;
    }

    public void setDischargeMedicineList(List<String> dischargeMedicineList) {
        this.dischargeMedicineList = dischargeMedicineList;
    }
}
