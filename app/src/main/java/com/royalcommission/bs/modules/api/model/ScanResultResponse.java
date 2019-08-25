package com.keybs.rc.modules.network.retrofit.model.responses;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ScanResultResponse {

    @SerializedName("CommonResponse")
    private BaseResponse baseResponse;

    @SerializedName("ScanResultResponse")
    private List<ScanResult> scanResultList;

    public BaseResponse getBaseResponse() {
        return baseResponse;
    }

    public void setBaseResponse(BaseResponse baseResponse) {
        this.baseResponse = baseResponse;
    }

    public List<ScanResult> getScanResultList() {
        return scanResultList;
    }

    public void setScanResultList(List<ScanResult> scanResultList) {
        this.scanResultList = scanResultList;
    }

}
