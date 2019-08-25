package com.keybs.rc.modules.network.retrofit.model.responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Prashant on 12/10/2018.
 */
public class SurveyQuestion {

    @SerializedName("CommonResponse")
    private BaseResponse baseResponse;

    @SerializedName("SurveyResponse")
    private Question question;

    public BaseResponse getBaseResponse() {
        return baseResponse;
    }

    public void setBaseResponse(BaseResponse baseResponse) {
        this.baseResponse = baseResponse;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
