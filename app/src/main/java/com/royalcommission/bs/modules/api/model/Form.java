package com.royalcommission.bs.modules.api.model;

import com.google.gson.annotations.SerializedName;

public class Form {
    @SerializedName("DesignXML")
    private String designXML;

    @SerializedName("FormId")
    private String formId;

    @SerializedName("FormName")
    private String formName;

    public String getDesignXML() {
        return designXML;
    }

    public void setDesignXML(String designXML) {
        this.designXML = designXML;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }
}
