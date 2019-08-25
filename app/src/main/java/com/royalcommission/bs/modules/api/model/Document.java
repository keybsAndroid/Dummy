package com.keybs.rc.modules.network.retrofit.model.responses;

/**
 * Created by Prashant on 12/17/2018.
 */
public class Document {

    private String AppointmentId;
    private String DocumentName;
    private String FormName;
    private int FormId;
    private int MedicalRecordId;
    private String DownloadUrl;
    private String DocumentDate;
    private String Base64;


    public String getAppointmentId() {
        return AppointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        AppointmentId = appointmentId;
    }

    public String getDocumentName() {
        return DocumentName;
    }

    public void setDocumentName(String documentName) {
        DocumentName = documentName;
    }


    public String getDownloadUrl() {
        return DownloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        DownloadUrl = downloadUrl;
    }

    public String getDocumentDate() {
        return DocumentDate;
    }

    public void setDocumentDate(String documentDate) {
        DocumentDate = documentDate;
    }

    public String getBase64() {
        return Base64;
    }

    public void setBase64(String base64) {
        Base64 = base64;
    }

    public int getFormId() {
        return FormId;
    }

    public void setFormId(int formId) {
        FormId = formId;
    }

    public int getMedicalRecordId() {
        return MedicalRecordId;
    }

    public void setMedicalRecordId(int medicalRecordId) {
        MedicalRecordId = medicalRecordId;
    }

    public String getFormName() {
        return FormName;
    }

    public void setFormName(String formName) {
        FormName = formName;
    }
}
