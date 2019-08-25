package com.keybs.rc.modules.network.retrofit.model.responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Prashant on 12/10/2018.
 */
public class LocalAccountResponse {

    @SerializedName("CommonResponse")
    private BaseResponse baseResponse;

    @SerializedName("CreateResponse")
    private String createResponse;

    @SerializedName("LocalAccountResponse")
    private LocalAccount localAccount;


    public BaseResponse getBaseResponse() {
        return baseResponse;
    }

    public void setBaseResponse(BaseResponse baseResponse) {
        this.baseResponse = baseResponse;
    }

    public String getCreateResponse() {
        return createResponse;
    }

    public void setCreateResponse(String createResponse) {
        this.createResponse = createResponse;
    }

    public void setLocalAccount(LocalAccount localAccount) {
        this.localAccount = localAccount;
    }

    public LocalAccount getLocalAccount() {
        return this.localAccount;
    }
}
