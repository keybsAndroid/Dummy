package com.royalcommission.bs.modules.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Prashant on 12/10/2018.
 */
public class SurveyQuestionResponse {

    @SerializedName("CommonResponse")
    private BaseResponse baseResponse;

    @SerializedName("SurveyResponse")
    private SurveyQuestion surveyQuestion;

    public BaseResponse getBaseResponse() {
        return baseResponse;
    }

    public void setBaseResponse(BaseResponse baseResponse) {
        this.baseResponse = baseResponse;
    }

    public SurveyQuestion getSurveyQuestion() {
        return surveyQuestion;
    }

    public void setSurveyQuestion(SurveyQuestion surveyQuestion) {
        this.surveyQuestion = surveyQuestion;
    }
}
