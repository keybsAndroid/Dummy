package com.royalcommission.bs.modules.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Prashant on 12/10/2018.
 */
public class SpecialClinicResponse {

    @SerializedName("CommonResponse")
    private BaseResponse baseResponse;

    @SerializedName("ExaminationModel")
    private List<SpecialClinicDetail> specialClinicDetails;

    public BaseResponse getBaseResponse() {
        return baseResponse;
    }

    public void setBaseResponse(BaseResponse baseResponse) {
        this.baseResponse = baseResponse;
    }

    public List<SpecialClinicDetail> getSpecialClinicDetails() {
        return specialClinicDetails;
    }

    public void setSpecialClinicDetails(List<SpecialClinicDetail> specialClinicDetails) {
        this.specialClinicDetails = specialClinicDetails;
    }


}
