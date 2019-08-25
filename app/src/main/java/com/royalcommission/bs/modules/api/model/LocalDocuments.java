package com.royalcommission.bs.modules.api.model;

import com.google.gson.annotations.SerializedName;

public class LocalDocuments {

    @SerializedName("DocumentName")
    private String documentName;

    @SerializedName("DocumentID")
    private String documentId;

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }
}
