package com.keybs.rc.modules.network.retrofit.model.responses;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Prashant on 12/11/2018.
 */
public class Appointment implements Serializable {

    private String AppointmentId;
    private String HospitalName;
    @SerializedName("HospitalID")
    private String HospitalId;
    private String DoctorName;
    private String DepartmentName;
    private String DepartmentCode;
    private String ClinicCode;
    private String ClinicNameAr;
    private String ClinicNameEn;
    private String AppointmentDate;
    private String Doctor;
    private String DoctorCode;
    private String IsPaid;
    private String IsCheckin;
    private String CheckDateTime;
    private String IsFinished;
    private String Med_YN;
    private String Apcn_YN;
    private String VisitType;
    private String Type;
    private boolean isExpanded = false;

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }


    public String getAppointmentId() {
        return AppointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        AppointmentId = appointmentId;
    }

    public String getHospitalName() {
        return HospitalName;
    }

    public void setHospitalName(String hospitalName) {
        HospitalName = hospitalName;
    }

    public String getDepartmentName() {
        return DepartmentName;
    }

    public void setDepartmentName(String departmentName) {
        DepartmentName = departmentName;
    }

    public String getDepartmentCode() {
        return DepartmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        DepartmentCode = departmentCode;
    }

    public String getClinicNameAr() {
        return ClinicNameAr;
    }

    public void setClinicNameAr(String clinicNameAr) {
        ClinicNameAr = clinicNameAr;
    }

    public String getClinicNameEn() {
        return ClinicNameEn;
    }

    public void setClinicNameEn(String clinicNameEn) {
        ClinicNameEn = clinicNameEn;
    }

    public String getAppointmentDate() {
        return AppointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        AppointmentDate = appointmentDate;
    }

    public String getDoctor() {
        return Doctor;
    }

    public void setDoctor(String doctor) {
        Doctor = doctor;
    }

    public String getDoctorCode() {
        return DoctorCode;
    }

    public void setDoctorCode(String doctorCode) {
        DoctorCode = doctorCode;
    }

    public String getIsPaid() {
        return IsPaid;
    }

    public void setIsPaid(String isPaid) {
        IsPaid = isPaid;
    }

    public String getIsCheckin() {
        return IsCheckin;
    }

    public void setIsCheckin(String isCheckin) {
        IsCheckin = isCheckin;
    }

    public String getCheckDateTime() {
        return CheckDateTime;
    }

    public void setCheckDateTime(String checkDateTime) {
        CheckDateTime = checkDateTime;
    }

    public String getIsFinished() {
        return IsFinished;
    }

    public void setIsFinished(String isFinished) {
        IsFinished = isFinished;
    }

    public String getMed_YN() {
        return Med_YN;
    }

    public void setMed_YN(String med_YN) {
        Med_YN = med_YN;
    }

    public String getApcn_YN() {
        return Apcn_YN;
    }

    public void setApcn_YN(String apcn_YN) {
        Apcn_YN = apcn_YN;
    }

    public String getVisitType() {
        return VisitType;
    }

    public void setVisitType(String visitType) {
        VisitType = visitType;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    public String getDoctorName() {
        return DoctorName;
    }

    public void setDoctorName(String doctorName) {
        DoctorName = doctorName;
    }

    public String getClinicCode() {
        return ClinicCode;
    }

    public void setClinicCode(String clinicCode) {
        ClinicCode = clinicCode;
    }

    public String getHospitalId() {
        return HospitalId;
    }

    public void setHospitalId(String hospitalId) {
        HospitalId = hospitalId;
    }


}
