package com.royalcommission.bs.modules.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Prashant on 12/17/2018.
 */
public class RequestedDocumentStatus {

    @SerializedName("Doc_Name")
    private String name;
    @SerializedName("DocumentStatus")
    private String status;
    @SerializedName("RequestedDate")
    private String date;

    @SerializedName("Doc_ID")
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
