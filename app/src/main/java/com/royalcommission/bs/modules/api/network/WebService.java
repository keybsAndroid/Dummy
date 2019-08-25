package com.royalcommission.bs.modules.api.network;


import com.royalcommission.bs.modules.api.model.NotificationMessagesResponse;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Prashant on 5/14/2018.
 */
public interface WebService {

    /*GET METHODS*/

    @GET(EndPoints.LoginByPatient)
    Observable<Object> getLoginByPatientID(@Query("id") String id);

    @GET(EndPoints.LoginByPatientIdWithPassword)
    Observable<Object> getLoginByPassword(@Query("id") String id, @Query("pwd") String password);

    @GET(EndPoints.VerifyLoginOTP)
    Observable<Object> getVerifyLoginOTP(@Query("id") String id, @Query("token") String token);

    @GET(EndPoints.GetTodayMedicines)
    Observable<Object> getTodayMedicines(@Query("PatientID") String patientID, @Query("CategoryID") String categoryID, @Query("HospitalID") String hospitalID);

    @GET(EndPoints.GetTodayMeals)
    Observable<Object> getTodayMeals(@Query("PatientID") String patientID, @Query("HospitalID") String hospitalID);

    @GET(EndPoints.GetTodayRoundingDoctor)
    Observable<Object> getTodayRoundingDoctor(@Query("PatientID") String patientID, @Query("HospitalID") String hospitalID);

    @GET(EndPoints.GetAssignedDoctorNurseList)
    Observable<Object> getAssignedDoctorNurseList(@Query("PatientID") String patientID, @Query("HospitalID") String hospitalID);

    @GET(EndPoints.GetTodayNurse)
    Observable<Object> getTodayNurse(@Query("PatientID") String patientID, @Query("HospitalID") String hospitalID);

    @GET(EndPoints.GetAvailableMedicalDocumentList)
    Observable<Object> getAvailableMedicalDocumentList();

    @GET(EndPoints.GetRequestedMedicalDocumentStatus)
    Observable<Object> getRequestedMedicalDocumentStatus(@Query("PatientID") String patientID);

    @GET(EndPoints.MedicalDocumentList)
    Observable<Object> getCompletedMedicalDocumentList(@Query("PatientID") String patientID);

    @GET(EndPoints.MedicalDocumentFormData)
    Observable<Object> geFormData(@Query("FormID") String formID, @Query("PatientID") String patientID, @Query("AppID") String appointmentID);

    @GET(EndPoints.SendMedicalDocument)
    Observable<Object> sendDocumentEmail(@Query("FormID") String formID, @Query("PatientID") String patientID, @Query("AppID") String appointmentID, @Query("email") String email);

    @GET(EndPoints.RequestMedicalDocumentList)
    Observable<Object> requestMedicalDocumentList(@Query("PatientID") String patientID, @Query("Doc_Name") String docName, @Query("Doc_ID") String docId, @Query("Doc_Purpose") String docPurpose);

    @GET(EndPoints.GetOperationInfo)
    Observable<Object> getOperationInfo(@Query("PatientID") String patientID, @Query("HospitalID") String hospitalID, @Query("FromDate") String fromDate, @Query("Todate") String toDate);

    @GET(EndPoints.GetInPatientInfo)
    Observable<Object> getInpatientInfo(@Query("PatientID") String patientID, @Query("HospitalID") String hospitalID);

    @GET(EndPoints.GetOutPatientInfo)
    Observable<Object> getOutPatientInfo(@Query("PatientID") String patientID);

    @GET(EndPoints.GetDischargeInfo)
    Observable<Object> getDischargeInfo(@Query("PatientID") String patientID);

    @GET(EndPoints.GetDischargeMedicineInfo)
    Observable<Object> getDischargeMedicineInfo(@Query("PatientID") String patientID, @Query("HospitalID") String hospitalID, @Query("FromDate") String fromDate, @Query("Todate") String toDate);

    @GET(EndPoints.GetPatientIdByEmail)
    Observable<Object> getPatientIdByEmail(@Query("email") String email);

    @GET(EndPoints.GetPatientIdByPhone)
    Observable<Object> getPatientIdByPhone(@Query("mobile") String mobile);

    @GET(EndPoints.GetPassword)
    Observable<Object> getPassword(@Query("id") String patientID);

    @GET(EndPoints.LaboratoryByDate)
    Observable<Object> getLaboratory(@Query("PatientID") String patientID, @Query("HospitalID") String hospitalID, @Query("SD") String startDate, @Query("ED") String endDate);

    @GET(EndPoints.SpecialClinic)
    Observable<Object> getSpecialClinicDetails(@Query("PatientID") String patientID, @Query("HospitalID") String hospitalID, @Query("SD") String startDate, @Query("ED") String endDate);

    @GET(EndPoints.Pathology)
    Observable<Object> getPathology(@Query("PatientID") String patientID, @Query("HospitalID") String hospitalID, @Query("SD") String startDate, @Query("ED") String endDate);

    @GET(EndPoints.RadiologyByDate)
    Observable<Object> getRadiologyByDate(@Query("PatientID") String patientID, @Query("HospitalID") String hospitalID, @Query("SD") String startDate, @Query("ED") String endDate);

    @GET(EndPoints.TestResultsByID)
    Observable<Object> getTestResultsByID(@Query("OrderID") String OrderId, @Query("HospitalID") String hospitalID);

    @GET(EndPoints.CalculateCharge)
    Observable<Object> calculateCharge(@Query("id") String patientID, @Query("appid") String appointmentId);

    @GET(EndPoints.GetMessageNotifications)
    Observable<NotificationMessagesResponse> getMessageNotifications(@Query("PatientID") String patientID, @Query("HospitalID") String hospitalID);

    @GET(EndPoints.GetSurveyQuestions)
    Observable<Object> getSurveyQuestions(@Query("PatientID") String patientID);


    /*POST METHODS*/

    @POST(EndPoints.PostSurveyAnswer)
    @FormUrlEncoded
    Observable<Object> postSurveyAnswer(@Field("PatientID") String patientID, @Field("SurveyQuestionNumber") String qNo, @Field("Answer") String qAns);

    @POST(EndPoints.PostComplaints)
    @FormUrlEncoded
    Observable<Object> postComplaint(@Field("PatientID") String patientID, @Field("Msg") String complaints);

    @POST(EndPoints.RoundingDoctorLogin)
    @FormUrlEncoded
    Observable<Object> doRoundingDoctorLogin(@Field("DoctorName") String doctorName, @Field("Password") String password);
}
