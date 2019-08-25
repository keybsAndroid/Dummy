package com.royalcommission.bs.modules.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Prashant on 12/10/2018.
 */
public class DocumentResponse {

    @SerializedName("CommonResponse")
    private BaseResponse baseResponse;

    @SerializedName("DocumentListResponse")
    private List<Documents> documents;


    public BaseResponse getBaseResponse() {
        return baseResponse;
    }

    public void setBaseResponse(BaseResponse baseResponse) {
        this.baseResponse = baseResponse;
    }

    public List<Documents> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Documents> documents) {
        this.documents = documents;
    }
}
