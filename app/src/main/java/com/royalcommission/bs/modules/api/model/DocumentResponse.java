package com.keybs.rc.modules.network.retrofit.model.responses;

import com.google.gson.annotations.SerializedName;

import java.util.List;


/**
 * Created by Prashant on 12/17/2018.
 */
public class DocumentResponse {

    @SerializedName("CommonResponse")
    private BaseResponse baseResponse;

    @SerializedName("DocumentResponse")
    private List<Document> documentList;

    public BaseResponse getBaseResponse() {
        return baseResponse;
    }

    public void setBaseResponse(BaseResponse baseResponse) {
        this.baseResponse = baseResponse;
    }

    public List<Document> getDocumentList() {
        return documentList;
    }

    public void setDocumentList(List<Document> documentList) {
        this.documentList = documentList;
    }
}
