package com.royalcommission.bs.modules.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Prashant on 8/7/2018.
 */
public class LoginResponse {

    @SerializedName("LoginResponse")
    private Login login;

    @SerializedName("CommonResponse")
    private BaseResponse baseResponse;

    public BaseResponse getBaseResponse() {
        return baseResponse;
    }

    public void setBaseResponse(BaseResponse baseResponse) {
        this.baseResponse = baseResponse;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }
}
