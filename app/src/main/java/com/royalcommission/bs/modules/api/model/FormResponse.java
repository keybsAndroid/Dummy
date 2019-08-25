package com.keybs.rc.modules.network.retrofit.model.responses;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FormResponse {

    @SerializedName("CommonResponse")
    private BaseResponse baseResponse;

    @SerializedName("FormResponse")
    private List<Form> formList;

    public BaseResponse getBaseResponse() {
        return baseResponse;
    }

    public void setBaseResponse(BaseResponse baseResponse) {
        this.baseResponse = baseResponse;
    }

    public List<Form> getFormList() {
        return formList;
    }

    public void setFormList(List<Form> formList) {
        this.formList = formList;
    }
}
