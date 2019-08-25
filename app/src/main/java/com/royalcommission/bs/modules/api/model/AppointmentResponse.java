package com.keybs.rc.modules.network.retrofit.model.responses;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Prashant on 12/11/2018.
 */
public class AppointmentResponse {

    @SerializedName("CommonResponse")
    private BaseResponse baseResponse;

    @SerializedName("AppointmentResponse")
    private List<Appointment> appointmentList;

    @SerializedName("EmergencyVisitModel")
    private List<EmergencyAppointment> appointmentListEmergency;

    @SerializedName("AdmissionResultModel")
    private List<InpatientAppointment> appointmentListInpatient;

    @SerializedName("AppointmentByIDResponse")
    private Appointment updateAppointment;

    public List<EmergencyAppointment> getAppointmentListEmergency() {
        return appointmentListEmergency;
    }

    public void setAppointmentListEmergency(List<EmergencyAppointment> appointmentListEmergency) {
        this.appointmentListEmergency = appointmentListEmergency;
    }

    public BaseResponse getBaseResponse() {
        return baseResponse;
    }

    public void setBaseResponse(BaseResponse baseResponse) {
        this.baseResponse = baseResponse;
    }

    public List<Appointment> getAppointmentList() {
        return appointmentList;
    }

    public void setAppointmentList(List<Appointment> appointmentList) {
        this.appointmentList = appointmentList;
    }

    public Appointment getUpdateAppointment() {
        return updateAppointment;
    }

    public void setUpdateAppointment(Appointment updateAppointment) {
        this.updateAppointment = updateAppointment;
    }

    public List<InpatientAppointment> getAppointmentListInpatient() {
        return appointmentListInpatient;
    }

    public void setAppointmentListInpatient(List<InpatientAppointment> appointmentListInpatient) {
        this.appointmentListInpatient = appointmentListInpatient;
    }
}
