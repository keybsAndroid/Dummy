package com.royalcommission.bs.modules.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InpatientResponse {

    @SerializedName("CommonResponse")
    private BaseResponse baseResponse;

    @SerializedName("AdmissionResultModel")
    private List<InpatientInfo> inpatientInfoList;

    public BaseResponse getBaseResponse() {
        return baseResponse;
    }

    public void setBaseResponse(BaseResponse baseResponse) {
        this.baseResponse = baseResponse;
    }

    public List<InpatientInfo> getInpatientInfoList() {
        return inpatientInfoList;
    }

    public void setInpatientInfoList(List<InpatientInfo> inpatientInfoList) {
        this.inpatientInfoList = inpatientInfoList;
    }
}
