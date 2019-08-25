package com.royalcommission.bs.modules.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Prashant on 12/10/2018.
 */
public class PrescriptionResponse {

    @SerializedName("CommonResponse")
    private BaseResponse baseResponse;

    @SerializedName("Prescription")
    private List<Prescription> prescriptionList;

    public BaseResponse getBaseResponse() {
        return baseResponse;
    }

    public void setBaseResponse(BaseResponse baseResponse) {
        this.baseResponse = baseResponse;
    }

    public List<Prescription> getPrescriptionList() {
        return prescriptionList;
    }

    public void setPrescriptionList(List<Prescription> prescriptionList) {
        this.prescriptionList = prescriptionList;
    }
}
