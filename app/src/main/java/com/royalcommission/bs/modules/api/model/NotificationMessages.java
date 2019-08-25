package com.royalcommission.bs.modules.api.model;

import com.royalcommission.bs.modules.utils.CommonUtils;
import com.royalcommission.bs.modules.utils.DateUtils;

import java.util.Comparator;
import java.util.Objects;

public class NotificationMessages {

    private String sms;
    private String date;

    public String getSms() {
        return sms;
    }

    public void setSms(String sms) {
        this.sms = sms;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public class CompareByDate implements Comparator<NotificationMessages> {
        @Override
        public int compare(NotificationMessages p1, NotificationMessages p2) {

            if (CommonUtils.isValidString(p1.getDate()) && CommonUtils.isValidString(p2.getDate())) {
                if (DateUtils.getDate(p1.getDate()) != null && DateUtils.getDate(p2.getDate()) != null) {
                    if (Objects.requireNonNull(DateUtils.getDate(p1.getDate())).after(DateUtils.getDate(p2.getDate()))) {
                        return 1;
                    }
                }
            }

            if (CommonUtils.isValidString(p1.getDate()) && CommonUtils.isValidString(p2.getDate())) {
                if (DateUtils.getDate(p1.getDate()) != null && DateUtils.getDate(p2.getDate()) != null) {
                    if (Objects.requireNonNull(DateUtils.getDate(p1.getDate())).before(DateUtils.getDate(p2.getDate()))) {
                        return -1;
                    }
                }
            }
            return 0;
        }

        @Override
        public boolean equals(Object obj) {
            return super.equals(obj);
        }
    }

}
