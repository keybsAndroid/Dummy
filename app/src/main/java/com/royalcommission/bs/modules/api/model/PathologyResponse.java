package com.keybs.rc.modules.network.retrofit.model.responses;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PathologyResponse {

    @SerializedName("CommonResponse")
    private BaseResponse baseResponse;

    public List<Pathology> getPathologyList() {
        return pathologyList;
    }

    public void setPathologyList(List<Pathology> pathologyList) {
        this.pathologyList = pathologyList;
    }

    @SerializedName("ExaminationModel")
    private List<Pathology> pathologyList;

    public BaseResponse getBaseResponse() {
        return baseResponse;
    }

    public void setBaseResponse(BaseResponse baseResponse) {
        this.baseResponse = baseResponse;
    }


}
