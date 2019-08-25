package com.keybs.rc.modules.network.retrofit.model.responses;

import com.google.gson.annotations.SerializedName;

public class CalculateChargeResponse {


    @SerializedName("CommonResponse")
    private BaseResponse baseResponse;

    @SerializedName("PaymentResponse")
    private CalculateCharge calculateCharge;

    public BaseResponse getBaseResponse() {
        return baseResponse;
    }

    public void setBaseResponse(BaseResponse baseResponse) {
        this.baseResponse = baseResponse;
    }

    public CalculateCharge getCalculateCharge() {
        return calculateCharge;
    }

    public void setCalculateCharge(CalculateCharge calculateCharge) {
        this.calculateCharge = calculateCharge;
    }

}
