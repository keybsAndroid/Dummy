package com.royalcommission.bs.modules.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NotificationMessagesResponse {

    @SerializedName("CommonResponse")
    private BaseResponse baseResponse;

    @SerializedName("MessagesNotificationModel")
    private List<NotificationMessages> notificationMessagesList;

    public BaseResponse getBaseResponse() {
        return baseResponse;
    }

    public void setBaseResponse(BaseResponse baseResponse) {
        this.baseResponse = baseResponse;
    }

    public List<NotificationMessages> getNotificationMessagesList() {
        return notificationMessagesList;
    }

    public void setNotificationMessagesList(List<NotificationMessages> notificationMessagesList) {
        this.notificationMessagesList = notificationMessagesList;
    }
}
