package com.royalcommission.bs.modules.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Prashant on 12/10/2018.
 */
public class LocalDocumentResponse {

    @SerializedName("CommonResponse")
    private BaseResponse baseResponse;

    @SerializedName("DocumentListResponse")
    private List<LocalDocuments> documents;


    public BaseResponse getBaseResponse() {
        return baseResponse;
    }

    public void setBaseResponse(BaseResponse baseResponse) {
        this.baseResponse = baseResponse;
    }

    public List<LocalDocuments> getDocuments() {
        return documents;
    }

    public void setDocuments(List<LocalDocuments> documents) {
        this.documents = documents;
    }
}
