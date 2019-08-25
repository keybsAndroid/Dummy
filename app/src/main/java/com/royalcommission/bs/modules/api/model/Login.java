package com.keybs.rc.modules.network.retrofit.model.responses;

/**
 * Created by Prashant on 12/13/2018.
 */
public class Login {
    private String PatientId;
    private String Msg;

    public String getPatientId() {
        return PatientId;
    }

    public void setPatientId(String patientId) {
        PatientId = patientId;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }
}
