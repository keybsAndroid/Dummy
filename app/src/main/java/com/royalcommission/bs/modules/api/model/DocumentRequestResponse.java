package com.royalcommission.bs.modules.api.model;

import com.google.gson.annotations.SerializedName;

public class DocumentRequestResponse {


    @SerializedName("CommonResponse")
    private BaseResponse baseResponse;

    @SerializedName("FormResponse")
    private String formResponse;

    public BaseResponse getBaseResponse() {
        return baseResponse;
    }

    public void setBaseResponse(BaseResponse baseResponse) {
        this.baseResponse = baseResponse;
    }

    public String getFormResponse() {
        return formResponse;
    }

    public void setFormResponse(String formResponse) {
        this.formResponse = formResponse;
    }
}
