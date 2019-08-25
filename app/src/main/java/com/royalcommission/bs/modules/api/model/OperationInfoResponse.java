package com.royalcommission.bs.modules.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


/**
 * Created by Prashant on 12/17/2018.
 */
public class OperationInfoResponse {

    @SerializedName("CommonResponse")
    private BaseResponse baseResponse;

    @SerializedName("OperationInformationResponse")
    private List<OperationInfo> operationInfoList;

    public BaseResponse getBaseResponse() {
        return baseResponse;
    }

    public void setBaseResponse(BaseResponse baseResponse) {
        this.baseResponse = baseResponse;
    }

    public List<OperationInfo> getOperationInfoList() {
        return operationInfoList;
    }

    public void setOperationInfoList(List<OperationInfo> operationInfoList) {
        this.operationInfoList = operationInfoList;
    }
}
