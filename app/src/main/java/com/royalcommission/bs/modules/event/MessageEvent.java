package com.royalcommission.bs.modules.event;

import com.royalcommission.bs.modules.api.model.NotificationMessages;

import java.util.List;

public class MessageEvent {

    private int appStatus;
    private List<NotificationMessages> notificationMessages;

    public MessageEvent(int appStatus, List<NotificationMessages> notificationMessagesList) {
        this.setAppStatus(appStatus);
        this.setNotificationMessages(notificationMessagesList);
    }


    public List<NotificationMessages> getNotificationMessages() {
        return notificationMessages;
    }

    public void setNotificationMessages(List<NotificationMessages> notificationMessages) {
        this.notificationMessages = notificationMessages;
    }

    public int getAppStatus() {
        return appStatus;
    }

    public void setAppStatus(int appStatus) {
        this.appStatus = appStatus;
    }
}
