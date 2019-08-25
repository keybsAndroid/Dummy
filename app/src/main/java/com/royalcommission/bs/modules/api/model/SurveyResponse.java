package com.royalcommission.bs.modules.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Prashant on 12/10/2018.
 */
public class SurveyResponse {

    @SerializedName("CommonResponse")
    private BaseResponse baseResponse;

    @SerializedName("SurveyResponse")
    private String surveyResponse;

    public BaseResponse getBaseResponse() {
        return baseResponse;
    }

    public void setBaseResponse(BaseResponse baseResponse) {
        this.baseResponse = baseResponse;
    }

    public String getSurveyResponse() {
        return surveyResponse;
    }

    public void setSurveyResponse(String surveyResponse) {
        this.surveyResponse = surveyResponse;
    }
}
