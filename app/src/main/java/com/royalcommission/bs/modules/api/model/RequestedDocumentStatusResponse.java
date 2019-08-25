package com.royalcommission.bs.modules.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


/**
 * Created by Prashant on 12/17/2018.
 */
public class RequestedDocumentStatusResponse {

    @SerializedName("CommonResponse")
    private BaseResponse baseResponse;

    @SerializedName("MedicalDocStatusListResponse")
    private List<RequestedDocumentStatus> requestedDocumentStatusList;

    public BaseResponse getBaseResponse() {
        return baseResponse;
    }

    public void setBaseResponse(BaseResponse baseResponse) {
        this.baseResponse = baseResponse;
    }

    public List<RequestedDocumentStatus> getRequestedDocumentStatusList() {
        return requestedDocumentStatusList;
    }

    public void setRequestedDocumentStatusList(List<RequestedDocumentStatus> requestedDocumentStatusList) {
        this.requestedDocumentStatusList = requestedDocumentStatusList;
    }
}
