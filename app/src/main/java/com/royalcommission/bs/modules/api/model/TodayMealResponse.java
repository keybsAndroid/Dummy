package com.royalcommission.bs.modules.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TodayMealResponse {
    @SerializedName("CommonResponse")
    private BaseResponse baseResponse;

    @SerializedName("InPatientMealResponse")
    private List<TodayMeal> todayMealList;

    public BaseResponse getBaseResponse() {
        return baseResponse;
    }

    public void setBaseResponse(BaseResponse baseResponse) {
        this.baseResponse = baseResponse;
    }

    public List<TodayMeal> getTodayMealList() {
        return todayMealList;
    }

    public void setTodayMealList(List<TodayMeal> todayMealList) {
        this.todayMealList = todayMealList;
    }
}
