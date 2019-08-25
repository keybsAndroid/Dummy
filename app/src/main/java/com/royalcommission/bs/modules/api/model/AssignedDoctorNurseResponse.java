package com.royalcommission.bs.modules.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AssignedDoctorNurseResponse {

    @SerializedName("CommonResponse")
    private BaseResponse baseResponse;

    @SerializedName("NurseNames")
    private List<String> assignedNurseList;

    @SerializedName("DoctorNames")
    private List<String> assignedDoctorList;

    public BaseResponse getBaseResponse() {
        return baseResponse;
    }

    public void setBaseResponse(BaseResponse baseResponse) {
        this.baseResponse = baseResponse;
    }

    public List<String> getAssignedNurseList() {
        return assignedNurseList;
    }

    public void setAssignedNurseList(List<String> assignedNurseList) {
        this.assignedNurseList = assignedNurseList;
    }

    public List<String> getAssignedDoctorList() {
        return assignedDoctorList;
    }

    public void setAssignedDoctorList(List<String> assignedDoctorList) {
        this.assignedDoctorList = assignedDoctorList;
    }
}
